package org.okbqa.templator.pipeline;

import org.json.simple.JSONArray;
import org.okbqa.templator.graph.Graph;
import org.okbqa.templator.transformer.rules.RuleEngine;

/**
 *
 * @author cunger
 */
public class GraphicatorPipeline {
        
    GraphBuilder      graphBuilder; 
    RuleEngine        ruleEngine;
    
    boolean verbose = false;
    
    public GraphicatorPipeline(String language) {
                
        graphBuilder = new GraphBuilder(language);
        ruleEngine   = new RuleEngine(language);
    }
    
    public void debugMode() {
        verbose = true;
        graphBuilder.debugMode();
    }
    
 
    public Graph run(String input) {
        
        JSONArray output = new JSONArray();
        
        // 1. Graph construction :: String -> Graph
        
        if (verbose) {
            System.out.println("\n------------INPUT----------------");
            System.out.println(input);
        }
        
        Graph g = graphBuilder.constructGraph(input);
        
        // 2. Mapping :: Graph -> Graph 

        ruleEngine.apply_flat_rules(g);
                
        if (verbose) {
            System.out.println("\n--------FLATTENED GRAPH---------");
            System.out.println(g.toCompressedString()); 
        }
        
        return g;
    }
    
}
