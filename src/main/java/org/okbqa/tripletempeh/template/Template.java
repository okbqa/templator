package org.okbqa.tripletempeh.template;

import com.hp.hpl.jena.query.Query;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

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
