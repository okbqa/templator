package org.okbqa.tripletempeh.graph;

import java.util.ArrayList;

/**
 *
 * @author cunger
 */
public class Graph {

    Format format;
    
    ArrayList<Node> nodes;
    ArrayList<Edge> edges;
    
    public Graph() {
        nodes = new ArrayList<>();
        edges = new ArrayList<>();
    }
    
    public Graph(Format f) {
        format = f;
        nodes  = new ArrayList<>();
        edges  = new ArrayList<>();
    }
    
    
    // Getter and setter
    
    public void setFormat(Format f) {
        format = f;
    }
    public Format getFormat() {
        return format;
    }
    public ArrayList<Edge> getEdges() {
        return edges;
    }
    
    public Node getNode(int i) {
        
        for (Node n : nodes) {
            if (n.getId() == i) {
                return n;
            }
        }
        return null;
    }
    
    public int getMaxId() {
        
        int max = 0;
        
        for (Node n : nodes) {
            if (n.getId() > max) {
                max = n.getId();
            }
        }
        
        return max;
    }
    
    // Build graph
    
    public void addNode(Node n) {
        nodes.add(n);
    }
    public void addEdge(Edge e) {
        edges.add(e);
    }
    
    // Matching
    
    public Node getMatchingNode(Node node) {
                            
        for (Node n : nodes) {
             if (n.matches(node)) {
                 return n;
             }
        }
        return null;
    }
    
    public boolean subGraphOf(Graph g) {
                        
        for (Edge e_sub : edges) {
            
            // find corresponding edge in g
            boolean found = false;

            for (Edge e_super : g.edges) {
                                 
                Node head_match = g.getMatchingNode(getNode(e_sub.getHead()));
                Node depd_match = g.getMatchingNode(getNode(e_sub.getDependent()));
            
                if (head_match != null && depd_match != null
                    && e_super.getLabel().equals(e_sub.getLabel())) { 
                    found = true;
                    break;
                }
            }
            
            // if there is an edge for which no corresponding edge was found, fail
            if (!found) { 
                return false;
            }                        
        }
                
        return true;
    }

    
    // Show 
    
    @Override
    public String toString() { 
        
        String out = "";
                
        for (Edge e : edges) {
             out += " " + e.getDependent() + " <--"+e.getLabel()+"-- " + e.getHead() + ";";
        }
        
        out += "\nTokens:";
        
        for (Node n : nodes) {
            out += "\n" + n.getId() + " " + n.getForm() + " (" + n.getPOS() + ")";
        }
        
        return out;
    }
    
}
