package org.okbqa.templator.processing;

import java.util.List;
import org.okbqa.templator.graph.Graph;
import org.okbqa.templator.interpreter.Interpreter;
import org.okbqa.templator.processing.parsing.ETRI;
import org.okbqa.templator.processing.parsing.ParseResult;
import org.okbqa.templator.processing.parsing.Parser;
import org.okbqa.templator.processing.parsing.Stanford;

/**
 *
 * @author cunger
 */
public class Processor {

    Parser parser;
    Interpreter interpreter;
    
    
    public Processor(String language) {
        
        switch (language) {
            case "en": parser = new Stanford(); break; // TODO select language model 
            case "ko": parser = new ETRI(); break;
            default:   parser = new Stanford(); break;
        }
        
        interpreter = new Interpreter();
    }
    
    
    public Graph process(String text) {

        Graph graph = new Graph();
       
        // Parse 
        
        ParseResult result = parser.parse(text);

        // Merge sentence parses  
        
        for (String parse : result.getParses()) {
             graph.merge(interpreter.interpret(parse));
        }
        
        // Process coreference chains
        
        int fresh = graph.getMaxId() + 1;
        
        for (List<String> chain : result.getCorefChains()) {             
             for (String s : chain) {
                 for (String token : s.split("\\s")) {
                     for (int n : graph.findNode(token)) {
                          graph.renameNode(n,fresh);
                     }
                 }
             }
             fresh++;
        }
        
        return graph;
    }
    
}
