package org.okbqa.templator.transformer;

import org.okbqa.templator.graph.Graph;
import org.okbqa.templator.transformer.rules.RuleEngine;
import org.okbqa.templator.template.Template;

/**
 *
 * @author cunger
 */
public class Graph2Template {
           
    RuleEngine engine;
        
    public Graph2Template(RuleEngine engine) {
        this.engine = engine;
    }
    
    
    public Template constructTemplate(Graph graph) {
        
        engine.set_i(graph.getMaxId());

        Template template = new Template();
        
        engine.apply_map_rules(graph,template);
        
        template.assemble();
        
        return template;
    }

}
