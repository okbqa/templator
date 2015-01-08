package org.okbqa.tripletempeh.transformer;

import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.sparql.core.Var;
import com.hp.hpl.jena.sparql.syntax.ElementGroup;
import com.hp.hpl.jena.sparql.syntax.ElementTriplesBlock;
import com.hp.hpl.jena.vocabulary.RDF;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
       
    int i = 0; // for supply of fresh variables (see fresh())
    
    RuleEngine engine;
    List<Rule> map_rules;
    
    String PROPERTY   = "owl:Property";
    String CLASS      = "owl:Class";
    String INDIVIDUAL = "owl:NamedIndividual";
        
    public Graph2Template(RuleEngine e) {

        engine = e;
        map_rules = engine.map_rules();
    }
    
    
    public Template transform(Graph graph) {
        
        Set<Slot> slots = new HashSet<>();
        ElementTriplesBlock block = new ElementTriplesBlock();

        i = graph.getMaxId();
        
        for (List<Edge> edges : collectEdgeLists(graph,Color.SRL)) {
            if (edges.size() == 1) {
                Edge edge = edges.get(0);
                Node    x = graph.getNode(edge.getDependent());
                Node    h = graph.getNode(edge.getHead());
                String vx = varString(x);
                String vh = varString(h);
                // A0 -> rdf:type
                if (edge.getLabel().equals("A0")) {    
                    block.addTriple(new Triple(Var.alloc(vx), RDF.type.asNode(), Var.alloc(vh)));
                    slots.add(new Slot(vx,x.getForm(),INDIVIDUAL));
                    slots.add(new Slot(vh,h.getForm(),CLASS));
                }
                // otherwise: triple with unknown subject
                // TODO try to unify subject with some other node?
                else {
                    block.addTriple(new Triple(Var.alloc("v"+fresh()), Var.alloc(vh), Var.alloc(vx)));
                    slots.add(new Slot(vx,x.getForm(),INDIVIDUAL));
                    slots.add(new Slot(vh,h.getForm(),PROPERTY));
                }
            }
            else if (edges.size() == 2) {
                Edge edge1 = edges.get(0);
                Edge edge2 = edges.get(1);
                Node     h = graph.getNode(edge1.getHead());
                Node     x = graph.getNode(edge1.getDependent());
                Node     y = graph.getNode(edge2.getDependent());
                String  vh = varString(h);
                String  vx = varString(x);
                String  vy = varString(y);
                //
                block.addTriple(new Triple(Var.alloc(vx), Var.alloc(vh), Var.alloc(vy)));
                slots.add(new Slot(vh,h.getForm(),PROPERTY));
                slots.add(new Slot(vx,x.getForm(),INDIVIDUAL));
                slots.add(new Slot(vy,y.getForm(),INDIVIDUAL));
            }
        }
                       
        // Build query
        ElementGroup body = new ElementGroup();
        body.addElement(block);
        
        Query query = QueryFactory.make();
        query.setQueryPattern(body);
        query.setQuerySelectType();
        query.addResultVar("*"); // TODO
        
        return new Template(query,slots);
    }
    
    
    private String varString(Node node) {
        return "v" + node.getId();
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
        
        
    // Fresh variable supply
    
    public int fresh() {
        i++;
        return i;
    }

    
}
