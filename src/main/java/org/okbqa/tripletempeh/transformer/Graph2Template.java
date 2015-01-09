package org.okbqa.tripletempeh.transformer;

import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.sparql.core.Var;
import com.hp.hpl.jena.sparql.syntax.ElementTriplesBlock;
import com.hp.hpl.jena.vocabulary.RDF;
import java.util.ArrayList;
import java.util.List;
import org.okbqa.tripletempeh.graph.Color;
import org.okbqa.tripletempeh.graph.Edge;
import org.okbqa.tripletempeh.graph.Graph;
import org.okbqa.tripletempeh.graph.Node;
import org.okbqa.tripletempeh.transformer.rules.Rule;
import org.okbqa.tripletempeh.transformer.rules.RuleEngine;
import org.okbqa.tripletempeh.template.Slot;
import org.okbqa.tripletempeh.template.Template;

/**
 *
 * @author cunger
 */
public class Graph2Template {
           
    RuleEngine engine;
    List<Rule> map_rules;
        
    public Graph2Template(RuleEngine e) {

        engine = e;
        map_rules = engine.map_rules();
    }
    
    
    public Template constructTemplate(Graph graph) {
        
        // 1. init template
        Template template = new Template();
        
        // 2. apply rules
        for (Rule rule : map_rules) {
             engine.apply(rule,graph,template);
        }
        
        // 3. convert remaining semantic roles into triples
        ElementTriplesBlock block = new ElementTriplesBlock();

        engine.set_i(graph.getMaxId());
        
        for (List<Edge> edges : collectEdgeLists(graph,Color.SRL)) {
            if (edges.size() == 1) {
                Edge edge = edges.get(0);
                Node    x = graph.getNode(edge.getDependent());
                Node    h = graph.getNode(edge.getHead());
                String vx = engine.varString(x);
                String vh = engine.varString(h);
                String v  = engine.varString(engine.fresh());
                // A0 -> rdf:type
                if (edge.getLabel().equals("A0")) {    
                    block.addTriple(new Triple(Var.alloc(vx),Var.alloc(v),Var.alloc(vh)));
                    template.addSlot(new Slot(vx,x.getForm(),engine.INDIVIDUAL));
                    template.addSlot(new Slot(vh,h.getForm(),engine.CLASS));
                    template.addSlot(new Slot(v,"",engine.PROPERTY,"SORTAL"));
                }
                // otherwise: triple with unknown subject
                // TODO try to unify subject with some other node?
                else {
                    block.addTriple(new Triple(Var.alloc("v"+engine.fresh()),Var.alloc(vh),Var.alloc(vx)));
                    template.addSlot(new Slot(vx,x.getForm(),engine.INDIVIDUAL));
                    template.addSlot(new Slot(vh,h.getForm(),engine.PROPERTY));
                }
            }
            else if (edges.size() == 2) {
                Edge edge1 = edges.get(0);
                Edge edge2 = edges.get(1);
                Node     h = graph.getNode(edge1.getHead());
                Node     x = graph.getNode(edge1.getDependent());
                Node     y = graph.getNode(edge2.getDependent());
                String  vh = engine.varString(h);
                String  vx = engine.varString(x);
                String  vy = engine.varString(y);
                //
                block.addTriple(new Triple(Var.alloc(vx),Var.alloc(vh),Var.alloc(vy)));
                template.addSlot(new Slot(vh,h.getForm(),engine.PROPERTY));
                template.addSlot(new Slot(vx,x.getForm(),engine.INDIVIDUAL));
                template.addSlot(new Slot(vy,y.getForm(),engine.INDIVIDUAL));
            }
        }
        
        template.addTriples(block);
        
        // 4. assemble and return template
        
        template.assemble();
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
