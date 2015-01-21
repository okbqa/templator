package org.okbqa.tripletempeh.graph;

import java.util.Objects;

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
        return form.replaceAll("\\?","");
        // would be nice to return the lemma (if available), 
        // but the lemma is something non-sensical in the case of numbers
    }

    public String getPOS() {
        return POS;   
    }
    
    // Setter 
    
    public void setId(int i) {
        id = i;
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

    // Equality (automatically generated)
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + this.id;
        hash = 37 * hash + Objects.hashCode(this.form);
        hash = 37 * hash + Objects.hashCode(this.lemma);
        hash = 37 * hash + Objects.hashCode(this.POS);
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
        final Node other = (Node) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.form, other.form)) {
            return false;
        }
        if (!Objects.equals(this.POS, other.POS)) {
            return false;
        }
        return true;
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
