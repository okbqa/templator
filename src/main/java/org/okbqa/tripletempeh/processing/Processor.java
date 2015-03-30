package org.okbqa.tripletempeh.processing;

import java.util.List;
import org.okbqa.tripletempeh.graph.Graph;
import org.okbqa.tripletempeh.interpreter.Interpreter;
import org.okbqa.tripletempeh.processing.parsing.ETRI;
import org.okbqa.tripletempeh.processing.parsing.Parser;
import org.okbqa.tripletempeh.processing.parsing.Stanford;

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
        
        List<String> sentences = parser.getSentences(text);        
        for (String sentence : sentences) {
            
             String parse = parser.parse(sentence);
             Graph  new_g = interpreter.interpret(parse);
             
             graph.merge(new_g);
        }
        
        return graph;
    }

    
    
}
