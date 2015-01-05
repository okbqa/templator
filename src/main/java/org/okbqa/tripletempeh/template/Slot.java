package org.okbqa.tripletempeh.template;

// TODO Enum for slot type?
    // * Resource (or Literal)
    // * Class
    // * Path (undirected)
    // * Unknown

/**
 *
 * @author cunger
 */
public class Slot {
    
    String form;
    String type;
    
    public Slot(String f) {
        form = f;
        type = "UNKOWN";
    }
    
    public Slot(String f, String t) {
        form = f;
        type = t;
    }
    
    
    // Show 
    
    @Override
    public String toString() {
        return form + " (" + type + ")";
    }
    
}
