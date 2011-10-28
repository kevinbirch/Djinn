tree grammar Mk1_Mod0Walker;
options {
	tokenVocab=Mk1_Mod0;
	ASTLabelType=CommonTree; 
}

@header {
package com.webguys.djinn.ifrit;

import com.webguys.djinn.marid.runtime.Dictionary;
import com.webguys.djinn.ifrit.model.*;

}

@members {
	private Dictionary dictionary;
	
	public Mk1_Mod0Walker(TreeNodeStream input, Dictionary dictionary)
	{
		this(input);
		this.dictionary = dictionary;
	}
}

statement returns [Executable result]
	:	function
		{ $result = $function.result; }
	|	assignment_statement
		{ $result = $assignment_statement.result; }
	|	immediate_statement
		{ $result = $immediate_statement.result; }
	;

function returns [Function result]
	@init {
		List<Atom> body = new ArrayList<Atom>();
		List<Function> functions = new ArrayList<Function>();
		List<Declaration> declarations = new ArrayList<Declaration>();
	}
	:	^(FUNCTION NAME (^(PATTERN p=lambda))? ^(BODY (l=lambda { body.add($l.result); } | atom { body.add($atom.result); })+) (f=function { functions.add($f.result); })* (s=assignment_statement { declarations.add($s.result); })*)
	{
		Method method = this.dictionary.isMethodDefined($NAME.text) ? this.dictionary.getMethod($NAME.text) : new Method($NAME.text);
		Function function = new Function($NAME.text, method, body);
		function.setPattern($p.result);
		if(!functions.isEmpty())
		{
			function.setInner(functions);
		}
		if(!declarations.isEmpty())
		{
			function.setDeclarations(declarations);
		}
		this.dictionary.defineFunction(function);
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

assignment_statement returns [Declaration result]
	:	single_assignment_statment
		{
			Declaration single = $single_assignment_statment.result;
			this.dictionary.defineName(single);
			$result = single;
		}
	|	compound_assignment_statement
		{
			Declaration compound = $compound_assignment_statement.result;
			this.dictionary.defineName(compound);
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

immediate_statement returns [Lambda result]
	@init {
		List<Atom> atoms = new ArrayList<Atom>();
	}
	:	^(IMMEDIATE (atom { atoms.add($atom.result); })+)
		{ $result = new Lambda(atoms); }
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
		{ $result = new StringAtom($STRING.text); }
	|	^(LIST_LITERAL '()')
//	|	list comprehension
//	|	literal record
//	|	LBRACK subscriptlist RBRACK
	;
