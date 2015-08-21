package org.okbqa.templator.pipeline;

import org.json.simple.JSONArray;
import org.okbqa.templator.graph.Graph;
import org.okbqa.templator.interpreter.Interpreter;
import org.okbqa.templator.processing.Processor;
import org.okbqa.templator.template.Template;
import org.okbqa.templator.transformer.Graph2Template;
import org.okbqa.templator.transformer.GraphManipulation;

/**
 *
 * @author cunger
 */
public class TemplatorPipeline {
        
    Interpreter       interpreter;
    Processor         processor; 
    GraphManipulation manipulator;
    Graph2Template    transformer;
    
    boolean verbose;

    
    public TemplatorPipeline(String language, boolean b) {
      
        verbose = b;
          
        processor   = new Processor(language);
        manipulator = new GraphManipulation(language);
        transformer = new Graph2Template(language);
    }
    
 
    public JSONArray run(String input) {
        
        JSONArray output = new JSONArray();
        
        // 1. Processing (sentence splitting,dependency parsing) :: String -> Graph
        // 2. Semantic role labeling :: Graph -> Graph
        // 3. Mapping :: Graph -> Template 
        
        if (verbose) {
            System.out.println("------------INPUT----------------");
            System.out.println(input);
        }
        
        Graph g = processor.process(input);
        manipulator.doSRL(g);
        
        if (verbose) {
            System.out.println("------------GRAPH----------------");
            System.out.println(g.toString());  
        }
        
        Template t = transformer.constructTemplate(g);
        output.add(t.toJSON());
        
        if (verbose) {
            System.out.println("------------TEMPLATE-------------");
            System.out.println(t.toString()); 
            System.out.println("------------JSON-----------------");
            System.out.println(t.toJSON().toJSONString());
        }
    
        return output;
    }
    
}
