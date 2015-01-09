package org.okbqa.tripletempeh.template;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.sparql.syntax.ElementGroup;
import com.hp.hpl.jena.sparql.syntax.ElementTriplesBlock;
import java.util.HashSet;
import java.util.Set;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author cunger
 */
public class Template {
    
    Query query;
    ElementGroup query_body;
    Set<String> projvars;
    Set<Slot> slots;
    
    public Template() {
        query      = QueryFactory.make();
        query_body = new ElementGroup();
        projvars   = new HashSet<>();
        slots      = new HashSet<>();
    }
    
    public Template(Query q, Set<Slot> s) {
        query = q;
        slots = s;
    }
    
    // Getter 
    
    public Query getQuery() {
        return query;
    }
    public Set<Slot> getSlots() {
        return slots;
    }
    
    // Adder
    
    public void addSlot(Slot s) {
        if (!containsSlotFor(s.getVar())) {
            slots.add(s);
        }
    }
    
    public void addTriples(ElementTriplesBlock triples) {
        query_body.addElement(triples);
    }
    
    public void addProjVar(String var) {
        projvars.add(var);
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
    
    public void assemble() {
        // query body
        query.setQueryPattern(query_body);
        // projection variables
        query.addProjectVars(projvars);
        // query type
        if (query.getProjectVars().isEmpty()) {
            query.setQueryAskType();
        }
        else {
            query.setQuerySelectType();
        }
    }
    
    // JSON
    
    public JSONObject toJSON() {
        
        JSONObject template = new JSONObject();
        
        template.put("query",query.toString());
        
        JSONArray slotlist = new JSONArray();
        for (Slot slot : slots) {
            for (JSONObject j : slot.toListofJSONObjects()) {
                 slotlist.add(j);
            }
            // alternative: slotlist.add(slot.toJSON());
        }
        template.put("slots",slotlist);
        template.put("score",1); // TODO
        
        return template;
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
