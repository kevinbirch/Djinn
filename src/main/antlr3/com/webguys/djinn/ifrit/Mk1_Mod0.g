grammar Mk1_Mod0;
options {output=AST;}

tokens {
	IMMEDIATE;
	ASSIGNMENT;
	COMPOUND_ASSIGNMENT;
	LAMBDA;
	FUNCTION;
	PATTERN;
	BODY;
	INTEGER_LITERAL;
	DECIMAL_LITERAL;
	STRING_LITERAL;
	LIST_LITERAL;
	SYMBOL;
}

@header
{
package com.webguys.djinn.ifrit;
}

@lexer::header
{
package com.webguys.djinn.ifrit;
}

statement
	:	function
	|	assignment_statement
	|	immediate_statement
	;

function
	:	DEF LBRACK NAME (pattern=lambda QUESTION)? (body+=lambda | body+=atom)+ functions+=function* vardefs+=assignment_statement* RBRACK
		-> ^(FUNCTION NAME ^(PATTERN $pattern)? ^(BODY $body+) $functions* $vardefs*)
	;

lambda
	:	LBRACK atom+ RBRACK -> ^(LAMBDA atom+)
	;

assignment_statement
	:	single_assignment_statment
	|	compound_assignment_statement
	;

single_assignment_statment
	:	DEF NAME expression -> ^(ASSIGNMENT NAME expression)
	;

compound_assignment_statement
	:	DEF names+=NAME (COMMA DEF names+=NAME)+ lambda -> ^(COMPOUND_ASSIGNMENT $names+ lambda)
	;

expression
	:	literal
	|	lambda
	;

immediate_statement
	:	atom+ -> ^(IMMEDIATE atom+)
	;

atom
	:	literal
	|	symbol
	;

symbol
	:	NAME -> ^(SYMBOL NAME)
	;

literal
	:	INTEGER -> ^(INTEGER_LITERAL INTEGER)
	|	DECIMAL -> ^(DECIMAL_LITERAL DECIMAL)
	|	STRING -> ^(STRING_LITERAL STRING)
	|	empty_list -> ^(LIST_LITERAL empty_list)
//	|	list comprehension
//	|	literal record
//	|	LBRACK subscriptlist RBRACK
	;

empty_list  : '()';

COLON    	: ':' ;

RESULT   	: '=>' ;

ARROW   	: '->' ;

COMMA    	: ',' ;

AT		 	: '@' ;

QUESTION 	: '?' ;

LPAREN	 	: '(' ;

RPAREN   	: ')' ;

DEF			: '\\' ;

LBRACK		: '[';

RBRACK		: ']' ;

DOT		 	: '.' ;

SLICE		: '..';

DECIMAL
    :   '.' DIGITS (Exponent)?
    |   DIGITS '.' Exponent
    |   DIGITS ('.' (DIGITS (Exponent)?)? | Exponent)
    ;

fragment
Exponent
    :    ('e' | 'E') ( '+' | '-' )? DIGITS
    ;

INTEGER
	// hexidecimal
	:	'#' ('x' | 'X') ( '0' .. '9' | 'a' .. 'f' | 'A' .. 'F') ( '0' .. '9' | 'a' .. 'f' | 'A' .. 'F' | '_' )*
	// octal
    |   '#' ('o' | 'O') DIGITS (DIGITS | '_')*
    // binary
    |	'#' ('b' | 'B') ('0' | '1') ('0' | '1' | '_')*
    // decimal
    |   DIGITS (DIGITS  '_')*
    ;

fragment
DIGITS : ( '0' .. '9' )+ ;

NAME
	:	( ('<' | '$') ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9') | '_'* ( '+' | '-' | '*' | '/' | '%' | '^' | 'a' .. 'z' | 'A' .. 'Z') )
        ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' | '-')* ('!' | '?' | '*' | '>' | '\'')?
    ;

STRING
    :	'"' (('\\' .)|~('\\'|'\n'|'"'))* '"'
    ;

NEWLINE
    :   (('\u000C')?('\r')? '\n' )+
	;

WS	:	(' '|'\t'|NEWLINE)+ {$channel=HIDDEN;}
	;
