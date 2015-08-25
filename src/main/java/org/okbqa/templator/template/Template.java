package org.okbqa.templator.template;

import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.sparql.core.Var;
import com.hp.hpl.jena.sparql.expr.ExprVar;
import com.hp.hpl.jena.sparql.expr.aggregate.AggCountVar;
import com.hp.hpl.jena.sparql.syntax.ElementGroup;
import com.hp.hpl.jena.sparql.syntax.ElementTriplesBlock;
import static java.lang.Math.log;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
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
    
    ElementGroup body;
    Query        query; // constructed by assemble()
    
    double       score;
    
        
    public Template() {
        projvars   = new HashSet<>();
        countvars  = new HashSet<>();
        body       = new ElementGroup();        
        slots      = new HashSet<>();
        query      = QueryFactory.make();
    }
    
    public Template(Query q, Set<Slot> s) {
        query = q;
        slots = s;
    }
    
    
    // Getter 
    
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
    
    public void addTriples(ElementTriplesBlock triples) {
        Iterator<Triple> iter = triples.patternElts();
        while (iter.hasNext()) {
            body.addTriplePattern(iter.next());
        }
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
    
    
    // Assembly
    
    public void assemble(List<String> slot_blacklist) {        
        // query body
        query.setQueryPattern(body);
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
            if (slot_blacklist.contains(s.getForm().toLowerCase())) {
                blacklisted.add(s);
            }
        }
        slots.removeAll(blacklisted);
    }
    
    public JSONObject toJSON() {
        
        JSONObject template = new JSONObject();
        
        template.put("query",sanityCheck(query.toString()));
        
        JSONArray slotlist = new JSONArray();
        for (Slot slot : slots) {
            for (JSONObject j : slot.toListofJSONObjects()) {
                 slotlist.add(j);
            }
            // alternative: slotlist.add(slot.toJSON());
        }
        template.put("slots",slotlist);
        template.put("score",score()); 
        
        return template;
    }
    
    private String sanityCheck(String querystring) {
        return querystring.replaceAll("\\n"," ").replaceAll("\\s+"," ");
    }
    
    // Scoring 
    
    public double score() {
        
        // this.score = start score
        // reduce depending on number of variables that are neither projvars or countvars, nor slots
        
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
        
        return out;
    }
    
}
