package org.okbqa.templator.pipeline;

import java.util.Set;
import org.json.simple.JSONArray;
import org.okbqa.templator.graph.Graph;
import org.okbqa.templator.template.Template;
import org.okbqa.templator.transformer.Graph2Template;
import org.okbqa.templator.transformer.TemplateRewriting;
import org.okbqa.templator.transformer.rules.RuleEngine;

/**
 *
 * @author cunger
 */
public class TemplatorPipeline {
        
    GraphBuilder      graphBuilder; 
    Graph2Template    transformer;
    TemplateRewriting rewriter;
    
    boolean verbose = false;
    
    public TemplatorPipeline(String language) {
                
        graphBuilder = new GraphBuilder(language);
        transformer  = new Graph2Template(new RuleEngine(language));
        rewriter     = new TemplateRewriting();
    }
    
    public void debugMode() {
        verbose = true;
        graphBuilder.debugMode();
    }
    
 
    public JSONArray run(String input) {
        
        JSONArray output = new JSONArray();
        
        // 1. Graph construction :: String -> Graph
        
        if (verbose) {
            System.out.println("\n------------INPUT----------------");
            System.out.println(input);
        }
        
        Graph g = graphBuilder.constructGraph(input);
        
        // 2. Mapping :: Graph -> Template 

        Template t = transformer.constructTemplate(g);
        output.add(t.toJSON());
        
        if (verbose) {
            System.out.println("\n------------TEMPLATE-------------");
            System.out.println(t.toString()); 
        }
        
        if (verbose) {
            System.out.println("\n------------VARIATIONS-----------");            
        }
        
        // 3. Template rewriting
        
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
