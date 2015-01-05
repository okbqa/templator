package org.okbqa.tripletempeh.template;

import com.hp.hpl.jena.query.Query;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

/**
 *
 * @author cunger
 */
public class Template {
    
    Query query;
    HashMap slots;
    
    public Template(Query q, HashMap s) {
        query = q;
        slots = s;
    }
    
    
    // Show 
    
    @Override
    public String toString() {
        
        String out = "";
        
        out += query.toString();
        Iterator iter = slots.entrySet().iterator();
        while (iter.hasNext()) {
            Entry entry = (Entry)iter.next();
            out += "\n " + entry.getKey() + " --> " + entry.getValue().toString();
        }
        
        return out;
    }
    
}
