package org.okbqa.templator.graph;

/**
 *
 * @author cunger
 */
public class Edge {
    
    int    head;
    int    dependent;
    String label;
    Color  color;
    
   
    public Edge(Color c, int t1, String l, int t2) {
        head      = t1;
        dependent = t2;
        label     = l;
        color     = c;
    }

    public Edge() {
    }
    
    public void setHead(int t) {
        head = t;
    }
    public void setDependent(int t) {
        dependent = t;
    }
    public void setLabel(String s) {
        label = s;
    }
    public void setColor(Color c) {
        color = c;
    }
    
    public int getHead() {
        return head;
    }
    public int getDependent() {
        return dependent;
    }
    public String getLabel() {
        return label;
    }
    public Color getColor() {
        return color;
    }
    
    // Matching 
    
    public boolean matches(Edge e) {
        
        return (head == e.getHead() && dependent == e.getDependent()
             && label.equals(e.getLabel()) && color == e.getColor());
    }
    
    // Show 
    
    @Override
    public String toString() {
           return " " + dependent + " <--"+label+"-- " + head;
    }
    
}
