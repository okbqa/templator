package org.okbqa.tripletempeh.transformer;

import org.okbqa.tripletempeh.transformer.rules.Rule;
import java.util.List;
import org.okbqa.tripletempeh.graph.Graph;
import org.okbqa.tripletempeh.transformer.rules.RuleEngine;

/**
 *
 * @author cunger
 */
public class GraphManipulation {

    RuleEngine engine;
    List<Rule> SRL_rules;
    
    public GraphManipulation(RuleEngine e) {
        
        engine    = e;
        SRL_rules = engine.SRL_rules();
    }
    
    
    // Semantic Role Labeling 
    
    public void doSRL(Graph g) {
        
        for (Rule rule : SRL_rules) {
             engine.apply(rule,g);
        }
    }
    
}
