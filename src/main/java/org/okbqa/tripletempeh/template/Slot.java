package org.okbqa.tripletempeh.template;

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
    
    
    // Show 
    
    @Override
    public String toString() {
        return var + " " + form + " (" + annotation + ")";
    }
    
}
