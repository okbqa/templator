package org.okbqa.templator.pipeline;

import org.okbqa.templator.graph.Graph;
import org.okbqa.templator.interpreter.Interpreter;
import org.okbqa.templator.processing.Processor;
import org.okbqa.templator.transformer.rules.RuleEngine;

/**
 *
 * @author cunger
 */
public class GraphBuilder {
    
    Interpreter interpreter;
    Processor   processor; 
    RuleEngine  rule_engine;
    
    boolean     verbose;
    
    public GraphBuilder(String language) {
                
        processor   = new Processor(language);
        rule_engine = new RuleEngine(language);
        
        verbose = false;
    }   
    
    public void debugMode() {
        verbose = true;
    }
 
    public Graph constructGraph(String input) {
                
        // 1. Processing (sentence splitting,dependency parsing) :: String -> Graph
        
        if (verbose) {
            System.out.println("\n------------INPUT----------------");
            System.out.println(input);
        }
        
        Graph g = processor.process(input);
        
        if (verbose) {
            System.out.println("\n------------PARSE----------------");
            System.out.println(g.toCompressedString());  
        }
        
        // 2. Semantic role labeling :: Graph -> Graph
        
        rule_engine.apply_SRL_rules(g);
        
        if (verbose) {
            System.out.println("\n------------GRAPH----------------");
            System.out.println(g.toCompressedString());
        }
        
        return g;
    }
}
