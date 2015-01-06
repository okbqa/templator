package org.okbqa.tripletempeh.graph;

/**
 *
 * @author cunger
 */
public class Node {

    int     id;
    
    String  form;
    String  lemma;
    String  POS;
        
    
    public Node(int i, String f) {
        this(i, f, null, null);
    }
    
    public Node(int i, String f, String l, String p) {
        id    = i;
        form  = f;
        lemma = l;
        POS   = p;
    }
    
    
    // Getters
    
    public int getId() {
        return id;
    }

    public String getForm() {
        if (lemma != null && !lemma.contains("#")) {
            return lemma;
        } else { 
            return form;
        }
    }

    public String getPOS() {
        return POS;   
    }
    
    // Matching 
    
    public boolean matches(Node n) {
                            
        if (getPOS() != null && n.getPOS() != null && !getPOS().equals(n.getPOS())) {
            return false;
        }
        if (getForm().equals("*") || n.getForm().equals("*")) {
            return true;
        }
        return (getForm().toLowerCase().equals(n.getForm().toLowerCase()));
    }
    
    
    // Show 
    
    @Override
    public String toString() {
        
        String out = id + "-" + form;
        
        if (lemma != null) {
            out += "-" + lemma;
        }
        if (POS != null) {
            out += "(" + POS + ")";
        }
        
        return out;
    }
    
}
