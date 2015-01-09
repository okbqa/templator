package org.okbqa.tripletempeh.transformer;

import java.util.ArrayList;
import java.util.List;
import org.okbqa.tripletempeh.graph.Color;
import org.okbqa.tripletempeh.graph.Edge;
import org.okbqa.tripletempeh.graph.Graph;
import org.okbqa.tripletempeh.graph.Node;
import org.okbqa.tripletempeh.transformer.rules.Rule;
import org.okbqa.tripletempeh.transformer.rules.RuleEngine;
import org.okbqa.tripletempeh.template.Template;

/**
 *
 * @author cunger
 */
public class Graph2Template {
           
    RuleEngine engine;
    List<Rule> map_rules;
    List<String> slot_blacklist;
        
    public Graph2Template(RuleEngine e) {

        engine = e;
        map_rules = engine.map_rules();
        slot_blacklist = new ArrayList<>();
    }
    
    
    public Template constructTemplate(Graph graph) {
        
        // 0. init fresh variable counter
        engine.set_i(graph.getMaxId());
        
        // 1. init template
        Template template = new Template();
        
        // 2. apply rules
        for (Rule rule : map_rules) {
            // add edge forms to slot backlist
             for (Node n : rule.getTarget().getNodes()) {
                 if (!n.getForm().equals("*")) {
                     slot_blacklist.add(n.getForm().toLowerCase());
                 }
             }
             // apply rule
             engine.apply(rule,graph,template);
        }
        
        // 4. assemble and return template
        template.assemble(slot_blacklist);
        return template;
    }
    
    
    private List<List<Edge>> collectEdgeLists(Graph graph,Color color) {
    // collect SRL edges in equivalence classes depending on shared heads
        
        List<List<Edge>> edgeLists = new ArrayList<>();
        
        for (Edge edge : graph.getEdges(color)) {
            int head  = edge.getHead();
            int index = -1;
            search:
            for (int n = 0; n < edgeLists.size(); n++) {
                List<Edge> list = edgeLists.get(n);
                for (Edge e : list) {
                    if (e.getHead() == head) {
                       index = n;
                       break search;
                    }
                }
            }
            if (index >= 0) {
                edgeLists.get(index).add(edge);
            }
            else {
                List<Edge> newone = new ArrayList<>();
                newone.add(edge);
                edgeLists.add(newone);
            }
        }
        
        return edgeLists;
    }
        
}
