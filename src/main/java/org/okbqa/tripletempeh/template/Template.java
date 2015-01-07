package org.okbqa.tripletempeh.template;

import com.hp.hpl.jena.query.Query;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author cunger
 */
public class Template {
    
    Query query;
    List<Slot> slots;
    
    public Template(Query q, List<Slot> s) {
        query = q;
        slots = s;
    }
    
    // Getter 
    
    public Query getQuery() {
        return query;
    }
    public List<Slot> getSlots() {
        return slots;
    }
    
    
    public JSONObject toJSON() {
        
        JSONObject template = new JSONObject();
        
        template.put("query",query.toString());
        
        JSONArray slotlist = new JSONArray();
        for (Slot slot : slots) {
             slotlist.add(slot.toJSON());
        }
        template.put("slots",slotlist);
        template.put("score",0); // TODO
        
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
