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
        this(i, f, "", "");
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
    
}
