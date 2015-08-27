package org.okbqa.templator.template;

import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.Syntax;
import com.hp.hpl.jena.sparql.core.Var;
import com.hp.hpl.jena.sparql.expr.ExprVar;
import com.hp.hpl.jena.sparql.expr.aggregate.AggCountVar;
import com.hp.hpl.jena.sparql.syntax.Element;
import com.hp.hpl.jena.sparql.syntax.ElementGroup;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author cunger
 */
public class Template {
       
    Set<String>  projvars;
    Set<String>  countvars;

    Set<Triple>  triples;
    Set<Slot>    slots;
    Set<String>  blacklist;
    
    ElementGroup body;
    
    Query        query; // constructed by assemble()
    
    double       score;
        
    public Template() {
        projvars   = new HashSet<>();
        countvars  = new HashSet<>();
        triples    = new HashSet<>();
        slots      = new HashSet<>();
        blacklist  = new HashSet<>();
        body       = new ElementGroup();        
    }
    
    public Template(Query q, Set<Slot> s) {
        query = q;
        slots = s;
    }
    
    
    // Getter 
    
    public Set<Triple> getTriples() {
        return triples;
    }
    public ElementGroup getBody() {
        return body;
    }
    public Query getQuery() {
        return query;
    }
    public Set<Slot> getSlots() {
        return slots;
    }
    
    // Setter 
    
    public void setScore(double s) {
        score = s;
    }
    
    public void addSlot(Slot s) {
        if (!containsSlotFor(s.getVar())) {
            slots.add(s);
        }
    }
    
    public void addToBlacklist(String s) {
        blacklist.add(s);
    }
    
    public void addTriple(Triple triple) {
        triples.add(triple);
    }
    
    public void addProjVar(String var) {
        projvars.add(var);
    }
    public void addCountVar(String var) {
        countvars.add(var);
    }
    
    
    // Tests 
    
    public boolean containsSlotFor(String var) {
        for (Slot slot : slots) {
            if (slot.getVar().equals(var)) {
                return true;
            }
        }
        return false;
    }
    
    // Removing
    
    public void removeTriple(Triple t) {
        triples.remove(t);
    }
    
    // Assembly
    
    public void assemble() {  
        
        query = QueryFactory.make();
        
        // query body
        ElementGroup queryBody = new ElementGroup();
        for (Triple t : triples) {
            queryBody.addTriplePattern(t);
        }
        for (Element e : body.getElements()) {
            queryBody.addElement(e);
        }        
        query.setQueryPattern(queryBody);
        
        // projection variables
        for (String v : projvars) {
            query.getProject().add(Var.alloc(v));
        }
        for (String v : countvars) {
            query.getProject().add(Var.alloc(v+"_count"),query.allocAggregate(new AggCountVar(new ExprVar(Var.alloc(v)))));
        }

        // query type
        if (query.getProjectVars().isEmpty()) {
            query.setQueryAskType();
        }
        else {
            query.setQuerySelectType();
        }
                
        // delete slots that are on the blacklist
        List<Slot> blacklisted = new ArrayList<>();
        for (Slot s : slots) {
            if (blacklist.contains(s.getForm().toLowerCase())) {
                blacklisted.add(s);
            }
        }
        slots.removeAll(blacklisted);
        
        score();
    }
    
    public JSONObject toJSON() {
        
        JSONObject template = new JSONObject();
        
        template.put("query",sanityCheck(query.toString()));
        
        JSONArray slotlist = new JSONArray();
        for (Slot slot : slots) {
            for (JSONObject j : slot.toListofJSONObjects()) {
                 slotlist.add(j);
            }
        }
        template.put("slots",slotlist);
        template.put("score",Double.toString(score)); 
        
        return template;
    }
    
    private String sanityCheck(String querystring) {
        return querystring.replaceAll("\\n"," ").replaceAll("\\s+"," ");
    }
    
    // Scoring 
    
    public double score() {

        int numberOfTriples = triples.size();

        int numberOfSlots = slots.size() - blacklist.size();
        
        Set<Node> nodes = new HashSet<>();
        
        int numberOfUnknownVars = 0;
        for (Triple t : triples) {
            List<Node> ns = Arrays.asList(
                                t.getSubject(),
                                t.getPredicate(),
                                t.getObject());
            nodes.addAll(ns);
            for (Node n : ns) {
                if (n.isVariable() && !projvars.contains(n.toString())
                                   && !countvars.contains(n.toString()) 
                                   && !containsSlotFor(n.toString())) {
                    numberOfUnknownVars++;
                }
            }
        }
        
        int numberOfNodes = nodes.size();
  
        score = 0.9;
//        score = (1/numberOfTriples) 
//              * (1/numberOfSlots)
//              * (numberOfUnknownVars / numberOfNodes); 
        
        return score;
    }
    
    
    // Show 
    
    @Override
    public String toString() {
        
        String out = "";
        
        out += query.toString();
        for (Slot slot : slots) {
            out += "\n " + slot.toString();
        }
        
        return out + "\n\nScore: " + score;
    }
    
    // Clone 
    
    @Override
    public Template clone() {
        
        Template clone = new Template();
        
        Set<String> new_projvars  = new HashSet<>();
        Set<String> new_countvars = new HashSet<>();
        new_projvars.addAll(projvars);
        new_countvars.addAll(countvars);

        Set<Triple> new_triples = new HashSet<>();
        for (Triple t : triples) {
            new_triples.add(new Triple(t.getSubject(),t.getPredicate(),t.getObject()));
        }
        
        Set<Slot> new_slots = new HashSet<>();
        for (Slot s : slots) {
            new_slots.add(s);
        }
        Set<String> new_blacklist = new HashSet<>();
        for (String s : blacklist) {
            new_blacklist.add(s);
        }
        
        clone.projvars = new_projvars;
        clone.countvars = new_countvars;
        clone.triples = new_triples;
        clone.slots = new_slots;
        clone.blacklist = new_blacklist;
        clone.body = body;
        clone.query = query;
        clone.score = score;
        
        return clone;
    }
    
}
