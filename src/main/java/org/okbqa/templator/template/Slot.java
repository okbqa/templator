package org.okbqa.templator.template;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.json.simple.JSONObject;

/**
 *
 * @author cunger
 */
public class Slot {
    
    String var;
    String form;
    SlotType type;
    
    public Slot(String var,String f,SlotType t) {
        this.var   = var;
        this.form  = f;
        this.type  = t;
    }
    
    // Boolean 
    
    public boolean isSortal() {
        return type.equals(SlotType.SORTAL);
    }
    
    // Getter 
    
    public String getVar() {
        return var;
    }
    public String getForm() {
        return form;
    }
    public SlotType getType() {
        return type;
    }
    
    // Setter 
    
    public void setType(SlotType t) {
        type = t;
    }
    
    // JSON
      
    public List<JSONObject> toListofJSONObjects() {
        
        List<JSONObject> triples = new ArrayList<>();
        
        // type
        JSONObject t = new JSONObject();
        t.put("s",var);
        t.put("p","is");
        t.put("o",type.toString());
        triples.add(t);
        // form
        if (!form.isEmpty()) {
            JSONObject f = new JSONObject();
            f.put("s",var);
            f.put("p","verbalization");
            f.put("o",form); 
            triples.add(f);
        }
        
        return triples;
    }
    
    // Show 
    
    @Override
    public String toString() {
        String out = var + " ";
        if (form.isEmpty()) {
            out += "-";
        } else {
            out += form;
        }
        out += " (" + type + ")";
        return out;
    }
    
    public Slot clone() {       
        return new Slot(var,form,type);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + Objects.hashCode(this.var);
        hash = 67 * hash + Objects.hashCode(this.form);
        hash = 67 * hash + Objects.hashCode(this.type);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Slot other = (Slot) obj;
        if (!Objects.equals(this.var, other.var)) {
            return false;
        }
        if (!Objects.equals(this.form, other.form)) {
            return false;
        }
        if (this.type != other.type) {
            return false;
        }
        return true;
    }
}
