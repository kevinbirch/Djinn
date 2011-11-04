tree grammar Mk1_Mod0ModelGenerator;
options {
	tokenVocab=Mk1_Mod0;
	ASTLabelType=CommonTree; 
}

@header {
package com.webguys.djinn.ifrit;

import org.apache.commons.lang3.StringUtils;
import com.google.common.collect.Iterables;

import com.webguys.djinn.marid.runtime.*;
import com.webguys.djinn.marid.Runtime;
import com.webguys.djinn.marid.primitive.BuiltinFactory;
import com.webguys.djinn.ifrit.model.*;

}

@members {
	private Dictionary dictionary;
	
	public Mk1_Mod0ModelGenerator(TreeNodeStream input, Dictionary dictionary)
	{
		this(input);
		this.dictionary = dictionary;
	}
}

translation_unit returns [Module result]
	:	key_value_pair* (method | function | assignment_statement[this.dictionary])+
		{ $result = Module.getRootModule(); }
	;

key_value_pair
	:	^(KEY_VALUE NAME VALUE)
	;

statement returns [Executable result]
	:	method									{ $result = $method.result; }
	|	function 								{ $result = $function.result; }
	|	assignment_statement[this.dictionary] 	{ $result = $assignment_statement.result; }
	|	lambda 									{ $result = $lambda.result; }
	|	immediate_statement 					{ $result = $immediate_statement.result; }
	;

tag
	:	key_value_pair
	;

method returns [Method result]
	:	^(METHOD NAME tag* stack_description stack_description)
	{
		Method method = new Method($NAME.text);
		this.dictionary.defineMethod(method);
		
		$result = method;
	}
	;

stack_description
	:	^(STACK_DESCRIPTION NAME? (^(STACK_DESCRIPTION_ELEMENTS (stack_description_element | function_type_declaration)*) )? )
	;

stack_description_element
	:	NAME
	;

function_type_declaration
	:	^(FUNCTION_TYPE function_type_qualifier? function_type_qualifier)
	;

function_type_qualifier
	:	 ^(FUNCTION_TYPE_QUALIFIER NAME (^(FUNCTION_TYPE_QUALIFIER_ELEMENTS NAME+))?)
	;

function returns [Function result]
	@init
	{
		List<Atom> body = new ArrayList<Atom>();
		List<InnerFunction> functions = new ArrayList<InnerFunction>();
		List<Declaration> declarations = new ArrayList<Declaration>();
		Dictionary functionDictionary = this.dictionary.newChild();
	}
	:	^(FUNCTION NAME tag* (^(PATTERN p=lambda))? ^(BODY (l=lambda { body.add($l.result); } | atom { body.add($atom.result); })+) (f=inner_function[functionDictionary] { functions.add($f.result); })* (s=assignment_statement[functionDictionary] { declarations.add($s.result); })*)
	{
		Method method;
		if(this.dictionary.isMethodDefined($NAME.text))
		{
			method = this.dictionary.getMethod($NAME.text);
		}
		else
		{
			method = new Method($NAME.text);
			this.dictionary.defineMethod(method);
		}
		if(method.isSealed())
		{
			throw new RuntimeException("The method \"" + $NAME.text + "\" is sealed and cannot have new functions added to it.");
		}
		if(null != $p.result)
		{
			for(Function function : method.getMembers())
        	{
				if(function.getPattern().equals($p.result))
				{
					throw new FunctionPatternAlreadyDefinedException($NAME.text, $p.result);
				}
			}
        }

		Function function;
		if(1 == body.size() && "__primitive__".equals(body.get(0).getValue()))
		{
			BuiltinFactory factory = Runtime.getBuiltinFactory($NAME.text);
			if(null == factory)
			{
				throw new RuntimeException("There is no primitive defined for the method " + $NAME.text);
			}
			function = factory.makeInstance(method);
		}
		else
		{
			function = new Function($NAME.text, method, body);
		}
		if(!Iterables.isEmpty(functions) || !Iterables.isEmpty(declarations))
		{
			function.setLocalDictionary(functionDictionary);
		}
		function.setPattern($p.result);
		if(!functions.isEmpty())
		{
			function.setInner(functions);
		}
		if(!declarations.isEmpty())
		{
			function.setDeclarations(declarations);
		}

		$result = function;
	}
	;

inner_function[Dictionary dictionary] returns [InnerFunction result]
	@init
	{
		List<Atom> body = new ArrayList<Atom>();
	}
	:	^(INNER_FUNCTION NAME (^(PATTERN p=lambda))? ^(BODY (l=lambda { body.add($l.result); } | atom { body.add($atom.result); })+))
	{
		Method method;
		if(dictionary.isMethodDefined($NAME.text))
		{
			method = dictionary.getMethod($NAME.text);
		}
		else
		{
			method = new Method($NAME.text);
			dictionary.defineMethod(method);
		}
				InnerFunction function = new InnerFunction($NAME.text, method, body);
		function.setPattern($p.result);
		$result = function;
	}
	;

lambda returns [Lambda result]
	@init {
		List<Atom> atoms = new ArrayList<Atom>();
	}
	:	^(LAMBDA (atom { atoms.add($atom.result); })+)
		{ $result = new Lambda(atoms); }
	;

assignment_statement[Dictionary dictionary] returns [Declaration result]
	:	single_assignment_statment
		{
			Declaration single = $single_assignment_statment.result;
			dictionary.defineName(single);
			$result = single;
		}
	|	compound_assignment_statement
		{
			Declaration compound = $compound_assignment_statement.result;
			dictionary.defineName(compound);
			$result = compound;
		}
	;

single_assignment_statment returns [SingleDeclaration result]
	:	^(ASSIGNMENT NAME expression)
		{ $result = new SingleDeclaration($NAME.text, $expression.result); }
	;

compound_assignment_statement returns [CompoundDeclaration result]
	@init {
		List<String> names = new ArrayList<String>();
	}
	:	^(COMPOUND_ASSIGNMENT (NAME { names.add($NAME.text); })+ lambda)
		{ $result = new CompoundDeclaration(names, $lambda.result); }
	;

expression returns [Lambda result]
	:	literal
		{
			List<Atom> atoms = new ArrayList<Atom>();
			atoms.add($literal.result);
			$result = new Lambda(atoms);
		}
	|	lambda
		{ $result = $lambda.result; }
	;

immediate_statement returns [ImmediateStatement result]
	@init {
		List<Atom> atoms = new ArrayList<Atom>();
	}
	:	^(IMMEDIATE (atom { atoms.add($atom.result); })+) { $result = new ImmediateStatement(atoms); }
	;

atom returns [Atom result]
	:	literal
		{ $result = $literal.result; }
	|	symbol
		{ $result = $symbol.result; }
	;

symbol returns [Atom result]
	:	^(SYMBOL NAME)
		{ $result = new Symbol($NAME.text); }
	;

literal returns [Atom result]
	:	^(INTEGER_LITERAL INTEGER)
		{ $result = new IntegerAtom(Integer.valueOf($INTEGER.text)); }
	|	^(DECIMAL_LITERAL DECIMAL)
		{ $result = new DecimalAtom(Double.valueOf($DECIMAL.text)); }
	|	^(STRING_LITERAL STRING)
		{ $result = new StringAtom(StringUtils.strip($STRING.text, "\"")); }
	|	^(LIST_LITERAL '()')
//	|	list comprehension
//	|	literal record
//	|	LBRACK subscriptlist RBRACK
	;
