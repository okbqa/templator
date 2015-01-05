grammar Dependencies;

@header {package org.okbqa.tripletempeh.interpreter.grammar;}


graph : conll    (NEWLINE conll)* 
      | stanford (NEWLINE stanford)*;

conll    : id STRING STRING STRING features NUMBER STRING (sheads) ;
stanford : STRING '(' STRING '-' NUMBER ',' STRING '-' NUMBER ')' ;

id : NUMBER;

features : '_' | feature ('|' feature)* ; 
feature  : STRING '=' STRING ;

sheads   : '_' | shead (';' shead)* ; 
shead    : NUMBER ':' STRING ('=' STRING)? ;


NUMBER : [0-9]+ ;
STRING : [a-zA-Z0-9'-.,?!*#]+ ; 

NEWLINE    : '\r'? '\n' ; 
WHITESPACE : [ \t]+ -> skip ; 
