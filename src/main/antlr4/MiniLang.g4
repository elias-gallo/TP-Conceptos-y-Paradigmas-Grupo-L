grammar MiniLang;

@header {
package org.example;
}

// 1. REGLAS LÉXICAS (LEXER) — MAYÚSCULAS

// PALABRAS CLAVE
PROGRAM : 'programa';
VAR     : 'var';
PRINTLN : 'mostrar';

// OPERADORES ARITMÉTICOS
SUM : '+';
RES : '-';
DIV : '/';
MULT : '*';

// OPERADORES LÓGICOS
AND : '&';
OR  : '|';
NOT : '!';

// OPERADORES RELACIONALES
MAYOR       : '>';
MENOR       : '<';
MAYOR_IGUAL : '>=';
MENOR_IGUAL : '<=';
IGUALDAD    : '==';
DISTINTO    : '!=';

// SÍMBOLOS ESTRUCTURALES
PAR_A    : '(';
PAR_C    : ')';
LLA_A    : '{';
LLA_C    : '}';
PNT_COMA : ';';
ASIGNAR  : '=';

// NUMERALES
NUM_REAL : [0-9]+'.'[0-9]+;
NUM_ENT  : [0-9]+;

// IDENTIFICADOR
ID : [a-zA-Z_][a-zA-Z0-9_]*;

// CADENAS
TEXTO : '"' (~["\r\n])* '"';

// DESCARTABLES
COMENTARIO : '//' ~[\r\n]* -> skip;
WS         : [ \t\r\n]+ -> skip;

// 2. REGLAS SINTÁCTICAS (PARSER) — minúsculas

programa : instruccion* EOF ;

instruccion : PRINTLN ID PNT_COMA ;
