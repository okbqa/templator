package org.okbqa.tripletempeh.transformer;

import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.sparql.core.Var;
import com.hp.hpl.jena.sparql.syntax.ElementGroup;
import com.hp.hpl.jena.sparql.syntax.ElementTriplesBlock;
import com.hp.hpl.jena.vocabulary.RDF;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.okbqa.tripletempeh.graph.Color;
import org.okbqa.tripletempeh.graph.Edge;
import org.okbqa.tripletempeh.graph.Graph;
import org.okbqa.tripletempeh.graph.Node;
import org.okbqa.tripletempeh.rules.Rule;
import org.okbqa.tripletempeh.rules.RuleEngine;
import org.okbqa.tripletempeh.template.Slot;
import org.okbqa.tripletempeh.template.Template;

/**
 *
 * @author cunger
 */
public class Graph2Template {
       
    int i = 0; // for supply of fresh variables
    
    RuleEngine engine;
    List<Rule> map_rules;
        
    public Graph2Template(RuleEngine e) {

        engine = e;
        map_rules = engine.map_rules();
    }
    
    
    public Template transform(Graph graph) {
        
        i = graph.getMaxId();
        
        List<Slot> slots = new ArrayList<>();
        
        // Collect heads with their arguments
        HashMap<Integer,ArrayList<Integer>> heads = new HashMap();
        
        for (Edge e : graph.getEdges()) {
            if (e.getColor() == Color.SRL) {
            
                int head = e.getHead();
            
                if (head != 0) { // i.e.don't do this with root
                    ArrayList<Integer> args = heads.get(head);              
                    if (args == null) {
                        args = new ArrayList<>();
                    }
                    args.add(e.getDependent());
                    heads.put(head,args);
                }
            }
        }
                
        // Build triples 
        ElementTriplesBlock block = new ElementTriplesBlock();
        
        for (int head : heads.keySet()) {
            
            Node   h = graph.getNode(head);
            String vh = "v" + h.getId();
            
            ArrayList<Integer> args = heads.get(head);
                          
            switch (args.size()) {
                
                // In:  H <--A0-- X; 
                // Out: ?X rdf:type ?H .
                case 1:
                    Node   x = graph.getNode(args.get(0));
                    String vx = "v" + x.getId();
                    // triple
                    block.addTriple(new Triple(Var.alloc(vx), RDF.type.asNode(), Var.alloc(vh)));
                    // slots
                    slots.add(new Slot(vx,x.getForm()));
                    slots.add(new Slot(vh,h.getForm()));
                    break;
                    
                // In:  S <--A0-- H; O <--A1-- H;
                // Out: ?S ?H ?O .
                case 2:
                    Node   s = graph.getNode(args.get(0));
                    Node   o = graph.getNode(args.get(1));
                    String vs = "v" + s.getId();
                    String vo = "v" + o.getId();
                    // triple
                    block.addTriple(new Triple(Var.alloc("v"+s.getId()), Var.alloc("v"+head), Var.alloc("v"+o.getId())));
                    // slots 
                    slots.add(new Slot(vs,s.getForm()));
                    slots.add(new Slot(vo,o.getForm()));
                    slots.add(new Slot(vh,h.getForm()));
                    break;
                    
                // In:  X <--A0-- H; Y <--A1-- H; Z <--A2-- H; ...
                // Out: reified triples?
                default:
                    break;
            }
        }
                
        // Build query
        ElementGroup body = new ElementGroup();
        body.addElement(block);
        
        Query query = QueryFactory.make();
        query.setQueryPattern(body);
        query.setQuerySelectType();
        query.addResultVar("v"); // TODO
        
        return new Template(query, slots);
    }
    
    public int fresh() {
        i++;
        return i;
    }

    
}
