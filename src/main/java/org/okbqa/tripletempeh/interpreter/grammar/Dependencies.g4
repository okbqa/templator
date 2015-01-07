grammar Dependencies;

@header {package org.okbqa.tripletempeh.interpreter.grammar;}


graph : conll    (NEWLINE conll)* 
      | stanford (NEWLINE stanford)*;

conll    : STRING STRING STRING STRING features STRING STRING (roles) ;
stanford : STRING '(' STRING '-' STRING ';' STRING '-' STRING ')' ;


features : '_' | feature ('|' feature)* ; 
feature  : STRING '=' STRING ;

roles : '_' | role (';' role)* ; 
role  : STRING ':' STRING ('-' STRING)? ('=' STRING )? ;


STRING : [a-zA-Z0-9'.,?!*#]+ ; 

NEWLINE    : '\r'? '\n' ; 
WHITESPACE : [ \t]+ -> skip ; 
