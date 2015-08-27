# Templator

Templator is a module for dependency-driven SPARQL template generation from natural language.
It is part of the [OKBQA](http://www.okbqa.org) question answering framework and currently works for English and Korean. 

## REST service

The module is running on `http://110.45.246.131:1555/templategeneration/templator`. The repository contains two examples files (`src/test/test_en.json` and `src/test/test_ko.json`) you can use for testing:

    curl -i -H "Content-Type: application/json" -X POST -d @test_ko.json http://110.45.246.131:1555/templategeneration/templator

## Example 

_Input:_ 

    { "string": "어떤 강이 군산을 흐르는가?", "language":"ko" }

_Output (for expansionDepth=0):_ 

[{"score":"1.0","slots":[{"p":"is","s":"v5","o":"<http:\/\/lodqa.org\/vocabulary\/sort_of>"},{"p":"is","s":"v4","o":"rdf:Resource|rdfs:Literal"},{"p":"verbalization","s":"v4","o":"군산을"},{"p":"is","s":"v3","o":"rdf:Property"},{"p":"verbalization","s":"v3","o":"흐르는가"},{"p":"is","s":"v1","o":"rdf:Class"},{"p":"verbalization","s":"v1","o":"강이"}],"query":"SELECT ?v2 WHERE { ?v2 ?v3 ?v4 ; ?v5 ?v1 . } "}]

Which expresses the following SPARQL template:

```
SELECT  ?v2
WHERE
  { ?v2  ?v5  ?v1 ;
         ?v3  ?v4 .
  }

 v5 - (<http://lodqa.org/vocabulary/sort_of>)
 v1 강이 (rdf:Class)
 v3 흐르는가 (rdf:Property)
 v4 군산을 (rdf:Resource|rdfs:Literal)

Score: 1.0
```
