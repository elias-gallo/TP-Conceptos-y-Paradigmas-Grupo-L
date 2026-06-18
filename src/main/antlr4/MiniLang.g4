grammar MiniLang;

@header {
package org.example;
}

// 1. REGLAS LÉXICAS (LEXER) — MAYÚSCULAS

// PALABRAS CLAVE
PROGRAM : 'programa';
VAR     : 'var';
PRINTLN : 'mostrar';

// TIPOS DE DATOS
T_ENTERO   : 'entero';
T_FLOTANTE : 'flotante';
T_BOOL     : 'bool';
T_TEXTO    : 'texto';

// OPERADORES ARITMÉTICOS
SUM  : '+';
RES  : '-';
DIV  : '/';
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
PAR_A       : '(';
PAR_C       : ')';
LLA_A       : '{';
LLA_C       : '}';
PNT_COMA    : ';';
DOS_PUNTOS  : ':';
ASIGNAR     : '=';

// NUMERALES
NUM_REAL : [0-9]+'.'[0-9]+;
NUM_ENT  : [0-9]+;

// IDENTIFICADOR
ID : [a-zA-Z_][a-zA-Z0-9_]*;

// BOOLEANOS
BOOLEANO : 'true' | 'false';

// CADENAS
TEXTO : '"' (~["\r\n])* '"';

// DESCARTABLES
COMENTARIO : '//' ~[\r\n]* -> skip;
WS         : [ \t\r\n]+ -> skip;

// 2. REGLAS SINTÁCTICAS (PARSER) — minúsculas

programa : instruccion* EOF ;

instruccion
    : varDecl
    | asignacion
    | printlnStmt
    ;

varDecl : VAR ID DOS_PUNTOS tipo ASIGNAR expr PNT_COMA ;

asignacion : ID ASIGNAR expr PNT_COMA ;

printlnStmt : PRINTLN expr PNT_COMA ;

tipo
    : T_ENTERO
    | T_FLOTANTE
    | T_BOOL
    | T_TEXTO
    ;

expr
    : NUM_ENT   # intLiteral
    | NUM_REAL  # floatLiteral
    | BOOLEANO  # boolLiteral
    | TEXTO     # stringLiteral
    | ID        # varRef
    ;
