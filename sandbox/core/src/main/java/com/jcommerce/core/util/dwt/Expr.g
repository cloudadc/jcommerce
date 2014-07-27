lexer grammar Expr;

STRING	:	'"' (~'"')* '"'
	|	'\'' (~'\'')* '\''
	;

Lparen	:	'('
	;

Rparen	:	')'
	;
fragment
Lbrack	:	'['
	;
fragment
Rbrack	:	']'
	;

Lbrace	:	'{'
	;

Rbrace	:	'}'
	;

COMMA	:	','
	;

SEMICOLON
	:	';'
	;

ADD	:	'+'
	;

SUB	:	'-'
	;

MUL	:	'*'
	;

DIV	:	'/'
	;

EQEQ	:	'=='
	;

NE	:	'!='
	;

LT	:	'<'
	;

LE	:	'<='
	;

GT	:	'>'
	;

GE	:	'>='
	;

BANG	:	'!'
	;

ANDAND	:	'&&'
	;

OROR	:	'||'
	;

EQ	:	'='
	;

IF	:	'if'
	;

ELSE	:	'else'
	;

WHILE	:	'while'
	;

BREAK	:	'break'
	;

INT	:	'int'
	;
fragment
Doller	:	'$'
	;
	
EMPTY   :	BANG? 'empty(' Identifier ')'
	;
	
ISSET	:	BANG? 'isset(' Identifier ')'
	;

Identifier
	:	Doller? LetterOrUnderscore ( LetterOrUnderscore | Digit | '.')* 
	;

VARIABLEACCESS
	:	Identifier ( Lbrack Integer Rbrack )*
	;

Integer	:	Digit+
	;

RealNumber
	:	Digit+ '.' Digit+
	;

fragment
Digit	:	'0'..'9'
	;

fragment
LetterOrUnderscore
	:	Letter | '_'
	;

fragment
Letter	:	( 'a'..'z' | 'A'..'Z' )
	;

WS	:	( ' ' | '\t' | '\r' | '\n' )+ { $channel = HIDDEN; }   
	;