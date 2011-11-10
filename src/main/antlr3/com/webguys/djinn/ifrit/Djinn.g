grammar Djinn;
options {output=AST;}

tokens {
	KEY_VALUE;
	METHOD;
	STACK_DESCRIPTION;
	STACK_DESCRIPTION_ELEMENTS;
	FUNCTION_TYPE;
	FUNCTION_TYPE_QUALIFIER;
	FUNCTION_TYPE_QUALIFIER_ELEMENTS;
	FUNCTION;
	INNER_FUNCTION;
	PATTERN;
	BODY;
	LAMBDA;
	ASSIGNMENT;
	COMPOUND_ASSIGNMENT;
	IMMEDIATE;
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

translation_unit
	:	key_value_pair* (method | function | assignment_statement)+
	;

key_value_pair
	:	key=NAME value=VALUE -> ^(KEY_VALUE $key $value)
	;

statement
	:	method
	|	function
	|	assignment_statement
	|	lambda
	|	immediate_statement
	;

tag
	:	AT key_value_pair -> key_value_pair
	;

method
	:	tag* NAME DBL_COLON before=stack_description THIN_ARROW after=stack_description -> ^(METHOD NAME tag* $before $after)
	;

stack_description
	:	(NAME NAME (COMMA NAME NAME)* THICK_ARROW)? stack_name=NAME? ( LBRACK (elements+=stack_description_element | elements+=function_type_declaration)* RBRACK )?
		-> ^(STACK_DESCRIPTION $stack_name? ^(STACK_DESCRIPTION_ELEMENTS $elements*)?)
	;

stack_description_element
	:	NAME
	;

function_type_declaration
	:	LPAREN in=function_type_qualifier? THIN_ARROW out=function_type_qualifier RPAREN -> ^(FUNCTION_TYPE $in? $out)
	;

function_type_qualifier
	:	NAME (LBRACK names+=NAME+ RBRACK)? -> ^(FUNCTION_TYPE_QUALIFIER NAME ^(FUNCTION_TYPE_QUALIFIER_ELEMENTS $names+)?)
	;

function
	:	tag* DEF LBRACK NAME function_body functions+=inner_function* vardefs+=assignment_statement* RBRACK
		-> ^(FUNCTION NAME tag* function_body $functions* $vardefs*)
	;

inner_function
	:	DEF LBRACK NAME function_body RBRACK
		-> ^(INNER_FUNCTION NAME function_body )
	;

function_body
	:	( lambda QUESTION ) => (pattern=lambda QUESTION) (body+=lambda | body+=atom)+
		-> ^(PATTERN $pattern)? ^(BODY $body+)
	|	(body+=lambda | body+=atom)+
		-> ^(BODY $body+)
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
	|	list
//	|	literal record
	|	lambda
	;

list
	:	LPAREN atom* RPAREN -> ^(LIST_LITERAL atom*)
//	|	list comprehension
	;

immediate_statement
	:	atom+ -> ^(IMMEDIATE atom+)
	;

atom
	:	literal
	|	symbol
	|	list
	;

symbol
	:	NAME -> ^(SYMBOL NAME)
	;

literal
	:	INTEGER -> ^(INTEGER_LITERAL INTEGER)
	|	DECIMAL -> ^(DECIMAL_LITERAL DECIMAL)
	|	STRING -> ^(STRING_LITERAL STRING)
	;

COLON    	: ':' ;

DBL_COLON	: '::' ;

SEMICOLON	: ';' ;

THICK_ARROW	: '=>' ;

THIN_ARROW  : '->' ;

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
    |   '#' ('o' | 'O') ((('0'..'3') ('0'..'7') ('0'..'7')) | (('0'..'7') ('0'..'7')) | ('0'..'7'))
    // binary
    |	'#' ('b' | 'B') ('0' | '1') ('0' | '1')*
    // decimal
    |   DIGITS (DIGITS  '_')*
    ;

fragment
DIGITS : ( '0' .. '9' )+ ;


NAME
	:	( '$' ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9') | '_'* ( '<=' | '>=' | '==' | '!=' | '<' | '>' | '.' | '+' | '-' | '*' | '/' | '%' | '^' | 'a' .. 'z' | 'A' .. 'Z' ) )
		( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' | '-' | '.' )* ( '!' | '?' | '\'' )?
	;

STRING
    :	'"' (('\\' .)|~('\\'|'\n'|'"'))* '"'
    ;

WS  :  	(' '|'\r'|'\t'|'\u000C'|'\n') {$channel=HIDDEN;}
	;

COMMENT
    :   '/*' ( options {greedy=false;} : . )* '*/' {$channel=HIDDEN;}
	;

LINE_COMMENT
    : 	'//' ~('\n'|'\r')* '\r'? '\n' {$channel=HIDDEN;}
    ;

VALUE
	:	':' ~('\n'|'\r'|';'|':')* ';'
	;
