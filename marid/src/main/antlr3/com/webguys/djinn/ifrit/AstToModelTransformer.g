tree grammar AstToModelTransformer;
options {
	tokenVocab=Djinn;
	ASTLabelType=CommonTree; 
}

@header {
package com.webguys.djinn.ifrit;

import org.apache.commons.lang3.StringUtils;
import com.webguys.ponzu.api.list.MutableList;
import com.webguys.ponzu.impl.list.mutable.FastList;

import com.webguys.djinn.ifrit.model.*;
import com.webguys.djinn.marid.primitive.BuiltinRepository;
import com.webguys.djinn.marid.runtime.*;

}

@members {
	private Dictionary dictionary;
	
	public AstToModelTransformer(TreeNodeStream input, Dictionary dictionary)
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
		MutableList<Atom> body = FastList.newList();
		MutableList<InnerFunction> functions = FastList.newList();
		MutableList<Declaration> declarations = FastList.newList();
		Dictionary functionDictionary = this.dictionary.newChild();
	}
	:	^(FUNCTION NAME tag* (^(PATTERN p=lambda))? ^(BODY (l=lambda { body.add($l.result); } | atom { body.add($atom.result); })+) (f=inner_function[functionDictionary] { functions.add($f.result); })* (s=assignment_statement[functionDictionary] { declarations.add($s.result); })*)
	{
		Method method = this.dictionary.getOrCreateMethod($NAME.text);
		if(method.isSealed())
		{
			throw new RuntimeException("The method \"" + $NAME.text + "\" is sealed and cannot have new functions added to it.");
		}

		ModuleFunction function;
		if(1 == body.size() && "__primitive__".equals(body.get(0).getValue()))
		{
			function = BuiltinRepository.getBuiltinFunction(method);
			if(null == function)
			{
				throw new RuntimeException("There is no primitive defined for the method " + $NAME.text);
			}
		}
		else
		{
			if(null == $p.result)
			{
				function = new ModuleFunction($NAME.text, method, body.toImmutable());
			}
			else
			{
                if(method.isConditionDefined($p.result))
                {
                    // xxx - need a proper way to present warnings from the parser
                    System.out.println("WARNING: Method \"" + method.getName() + "\" already has a function defined for the condition " + $p.result.toSourceRep());
                }
				function = new ModuleFunction($NAME.text, method, body.toImmutable(), $p.result);
			}
		}
		
		if(functions.notEmpty() || declarations.notEmpty())
		{
			function.setLocalDictionary(functionDictionary);

            if(functions.notEmpty())
            {
                function.setChildren(functions.toImmutable());
            }
            if(declarations.notEmpty())
            {
                function.setDeclarations(declarations.toImmutable());
            }
		}

		$result = function;
	}
	;

inner_function[Dictionary dictionary] returns [InnerFunction result]
	@init
	{
		MutableList<Atom> body = FastList.newList();
	}
	:	^(INNER_FUNCTION NAME (^(PATTERN p=lambda))? ^(BODY (l=lambda { body.add($l.result); } | atom { body.add($atom.result); })+))
	{
		Method method = this.dictionary.getOrCreateMethod($NAME.text);
		
		InnerFunction function;
		if(null == $p.result)
		{
			function = new InnerFunction($NAME.text, method, body.toImmutable());
		}
		else
		{
		    if(method.isConditionDefined($p.result))
		    {
                // xxx - need a proper way to present warnings from the parser
                System.out.println("WARNING: Method " + method.getName() + " already has a function defined for the condition " + $p.result.toSourceRep());
		    }
			function = new InnerFunction($NAME.text, method, body.toImmutable(), $p.result);
		}
		
		$result = function;
	}
	;

lambda returns [Lambda result]
	@init {
		MutableList<Atom> atoms = FastList.newList();
	}
	:	^(LAMBDA (atom { atoms.add($atom.result); })+)
		{ $result = new Lambda(atoms.toImmutable()); }
	;

assignment_statement[Dictionary dictionary] returns [Declaration result]
	:	single_assignment_statement
		{
			SingleDeclaration single = $single_assignment_statement.result;
			dictionary.defineName(single);
			$result = single;
		}
	|	compound_assignment_statement
		{
			CompoundDeclaration compound = $compound_assignment_statement.result;
			$result = compound;
		}
	;

single_assignment_statement returns [SingleDeclaration result]
	:	^(ASSIGNMENT NAME expression)
		{ $result = new SingleDeclaration($NAME.text, $expression.result); }
	;

compound_assignment_statement returns [CompoundDeclaration result]
	@init {
		MutableList<String> names = FastList.newList();
	}
	:	^(COMPOUND_ASSIGNMENT (NAME { names.add($NAME.text); })+ lambda)
		{ $result = new CompoundDeclaration(names.toImmutable(), $lambda.result); }
	;

expression returns [Lambda result]
	:	literal
		{
			MutableList<Atom> atoms = FastList.newList();
			atoms.add($literal.result);
			$result = new Lambda(atoms.toImmutable());
		}
	|	list
		{
			MutableList<Atom> atoms = FastList.newList();
			atoms.add($list.result);
			$result = new Lambda(atoms.toImmutable());
		}
	|	lambda
		{ $result = $lambda.result; }
	;

list returns [ListAtom result]
	@init {
		MutableList<Atom> atoms = FastList.newList();
	}
	:	^(LIST_LITERAL (atom { atoms.add($atom.result); })*)
		{ $result = new ListAtom(atoms); }
	;

immediate_statement returns [ImmediateStatement result]
	@init {
		MutableList<Atom> atoms = FastList.newList();
	}
	:	^(IMMEDIATE (atom { atoms.add($atom.result); } | lambda { atoms.add($lambda.result); } )+)
		{ $result = new ImmediateStatement(atoms.toImmutable()); }
	;

atom returns [Atom result]
	:	list
		{ $result = $list.result; }
	|	literal
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
	;
