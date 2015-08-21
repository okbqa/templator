package org.okbqa.templator.transformer;

import org.okbqa.templator.transformer.rules.Rule;
import java.util.List;
import org.okbqa.templator.graph.Graph;
import org.okbqa.templator.transformer.rules.RuleEngine;

/**
 *
 * @author cunger
 */
public class GraphManipulation {

    RuleEngine engine;
    List<Rule> SRL_rules;
    
    public GraphManipulation(String language) {
        
        engine    = new RuleEngine(language);
        SRL_rules = engine.SRL_rules();
    }
    
    
    // Semantic Role Labeling 
    
    public void doSRL(Graph graph) {
        
        for (Rule rule : SRL_rules) {
             engine.apply(rule,graph);
        }
    }
    
}
