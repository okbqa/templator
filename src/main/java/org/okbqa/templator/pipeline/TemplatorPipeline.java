package org.okbqa.templator.pipeline;

import java.util.Set;
import org.json.simple.JSONArray;
import org.okbqa.templator.graph.Graph;
import org.okbqa.templator.interpreter.Interpreter;
import org.okbqa.templator.processing.Processor;
import org.okbqa.templator.template.Template;
import org.okbqa.templator.transformer.Graph2Template;
import org.okbqa.templator.transformer.GraphManipulation;
import org.okbqa.templator.transformer.TemplateRewriting;

/**
 *
 * @author cunger
 */
public class TemplatorPipeline {
        
    Interpreter       interpreter;
    Processor         processor; 
    GraphManipulation manipulator;
    Graph2Template    transformer;
    TemplateRewriting rewriter;
    
    boolean verbose = false;
    
    public TemplatorPipeline(String language) {
                
        processor   = new Processor(language);
        manipulator = new GraphManipulation(language);
        transformer = new Graph2Template(language);
        rewriter    = new TemplateRewriting();
    }
    
    public void debugMode() {
        verbose = true;
    }
    
 
    public JSONArray run(String input) {
        
        JSONArray output = new JSONArray();
        
        // 1. Processing (sentence splitting,dependency parsing) :: String -> Graph
        // 2. Semantic role labeling :: Graph -> Graph
        // 3. Mapping :: Graph -> Template 
        // 4. Template rewriting
        
        if (verbose) {
            System.out.println("\n------------INPUT----------------");
            System.out.println(input);
        }
        
        Graph g = processor.process(input);
        manipulator.doSRL(g);
        
        if (verbose) {
            System.out.println("\n------------GRAPH----------------");
            System.out.println(g.toString());  
        }
        
        Template t = transformer.constructTemplate(g);
        output.add(t.toJSON());
        
        if (verbose) {
            System.out.println("\n------------TEMPLATE-------------");
            System.out.println(t.toString()); 
        }
        
        if (verbose) {
            System.out.println("\n------------VARIATIONS-----------");            
        }
        
        Set<Template> variations = rewriter.rewrite(t);
        
        if (verbose && variations.isEmpty()) {
            System.out.println("NONE");
        }
        
        for (Template v : variations) {
            
            output.add(v.toJSON());
        
            if (verbose) {
                System.out.println(v.toString()); 
                System.out.println("-------------");
            }
        }
    
        if (verbose) {
            System.out.println("\n----------Final JSON Output-----------");
            System.out.println(output.toJSONString());
        }
        
        return output;
    }
    
}
