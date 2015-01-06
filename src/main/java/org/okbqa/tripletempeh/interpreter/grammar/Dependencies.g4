grammar Dependencies;

@header {package org.okbqa.tripletempeh.interpreter.grammar;}


graph : conll    (NEWLINE conll)* 
      | stanford (NEWLINE stanford)*;

conll    : NUMBER STRING STRING STRING features NUMBER STRING (roles) ;
stanford : STRING '(' STRING '-' NUMBER ',' STRING '-' NUMBER ')' ;


features : '_' | feature ('|' feature)* ; 
feature  : STRING '=' STRING ;

roles : '_' | role (';' role)* ; 
role  : NUMBER ':' STRING ('-' STRING)? ('=' STRING )? ;


NUMBER : [0-9]+ ;
STRING : [a-zA-Z0-9'.?!*#]+ ; 

NEWLINE    : '\r'? '\n' ; 
WHITESPACE : [ \t]+ -> skip ; 
