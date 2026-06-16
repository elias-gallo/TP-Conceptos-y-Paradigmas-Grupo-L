grammar MiniLang;

// Reglas para el BUILD SUCCESS
programa : instruccion* EOF ;

instruccion : 'print' ID ';' ;

ID : [a-zA-Z]+ ;
WS : [ \t\r\n]+ -> skip ;