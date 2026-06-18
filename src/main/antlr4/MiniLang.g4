grammar MiniLang;

@header {
package org.example;
}

//REGLAS SINTÁCTICAS
programa : instruccion* EOF ;


instruccion : PRINTLN ID PNT_COMA ;


//REGLAS LÉXICAS o TOKENS (en mayúscula)

//PALABRAS CLAVE
PROGRAM : 'programa';
VAR     : 'var';
PRINTLN : 'mostrar';

//OPERADOR ARITMÉTICOS
SUM : '+';
RES : '-';
DIV : '/';
MULT : '*';

//OPERADORES LÓGICOS
AND : '&';
OR : '|';
NOT : '!';

//OPERADORES RELACIONALES
MAYOR       : '>';
MENOR       : '<';
MAYOR_IGUAL : '>=';
MENOR_IGUAL : '<=';
IGUALDAD    : '=='; // '=' es para asignación
DISTINTO    : '!=';

// SÍMBOLOS ESTRUCTURALES
PAR_A   : '(';
PAR_C   : ')';
LLA_A   : '{';
LLA_C   : '}';
PNT_COMA : ';';
ASIGNAR : '=';

//NUMERALES
NUM_REAL : [0-9]+'.'[0-9]+;
NUM_ENT : [0-9]+;

//IDENTIFICADOR
ID : [a-zA-Z_][a-zA-Z0-9_]*;

//CADENAS
TEXTO     : '"' (~["\r\n])* '"';
COMENTARIO : '//' ~[\r\n]* -> skip; // ~ -> excepto

//ESPACIOS EN BLANCO
WS : [ \t\r\n]+ -> skip;