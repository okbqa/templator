package org.okbqa.tripletempeh.template;

import org.json.simple.JSONObject;

/**
 *
 * @author cunger
 */
public class Slot {
    
    String var;
    String form;
    String annotation;
    
    public Slot(String v,String f) {
        this(v,f,"");
    }
    
    public Slot(String v,String f, String a) {
        var  = v;
        form = f;
        annotation = a;
    }
    
    
    public JSONObject toJSON() {
        
        JSONObject slot = new JSONObject();
        
        slot.put("var",var);
        slot.put("form",form);
        slot.put("annotation",annotation);
        
        return slot;
    }
    
    // Show 
    
    @Override
    public String toString() {
        return var + " " + form + " (" + annotation + ")";
    }
    
    // hash and equals
    
    @Override
    public int hashCode() {
        return var.hashCode()+form.hashCode()+annotation.hashCode();
    }

    @Override
    public boolean equals(Object other){
           if (!(other instanceof Slot)) return false;
           if (other == this) return true;
           Slot s = (Slot) other;
           return (var.equals(s.var) && form.equals(s.form) && annotation.equals(s.annotation));
    }
    
}
