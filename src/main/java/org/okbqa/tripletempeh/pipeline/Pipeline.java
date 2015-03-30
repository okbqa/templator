package org.okbqa.tripletempeh.pipeline;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.okbqa.tripletempeh.graph.Graph;
import org.okbqa.tripletempeh.interpreter.Interpreter;
import org.okbqa.tripletempeh.processing.Processor;
import org.okbqa.tripletempeh.processing.parsing.ETRI;
import org.okbqa.tripletempeh.processing.parsing.Parser;
import org.okbqa.tripletempeh.processing.parsing.Stanford;
import org.okbqa.tripletempeh.transformer.rules.RuleEngine;
import org.okbqa.tripletempeh.template.Template;
import org.okbqa.tripletempeh.transformer.Graph2Template;
import org.okbqa.tripletempeh.transformer.GraphManipulation;

/**
 *
 * @author cunger
 */
public class Pipeline {
        
    Interpreter       interpreter;
    Processor         processor; 
    GraphManipulation manipulator;
    Graph2Template    transformer;
    
    boolean verbose;
    boolean template;
    
    public Pipeline(String language) {
        this(language, true, true);
    }
    
    public Pipeline(String language, boolean v, boolean t) {
      
        verbose  = v;
        template = t;
          
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
        
        if (template) {
        
            Template t = transformer.constructTemplate(g);
            output.add(t.toJSON());
        
            if (verbose) {
                System.out.println("------------TEMPLATE-------------");
                System.out.println(t.toString()); 
                System.out.println("------------JSON-----------------");
                System.out.println(t.toJSON().toJSONString());
            }
        }
        else {
            output.add(g.toString());
        }
    
        return output;
    }
    
}
