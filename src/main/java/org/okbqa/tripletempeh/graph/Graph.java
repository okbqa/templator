package org.okbqa.tripletempeh.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    
    public List<Edge> getEdges(String label) {
        
        List<Edge> results = new ArrayList<>();
        
        for (Edge e : edges) {
             if (e.getLabel().equals(label)) {
                 results.add(e);
             }
        }
        
        return results;
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
    
    public Map<Integer,Integer> subGraphMatch(Graph g) {
                        
        Map<Integer,Integer> indexmap = new HashMap<>();
        
        for (Edge e_sub : edges) {
            
            int head_sub = e_sub.getHead();
            int depd_sub = e_sub.getDependent();
            
            // find corresponding edge in g
            boolean found = false;

            for (Edge e_super : g.edges) {
                
                int head_super = e_super.getHead();
                int depd_super = e_super.getDependent();
            
                if (e_super.getLabel().equals(e_sub.getLabel())
                    && g.getNode(head_super).matches(getNode(head_sub))
                    && g.getNode(depd_super).matches(getNode(depd_sub))) {
                    
                    found = true;
                    indexmap.put(head_sub,head_super);
                    indexmap.put(depd_sub,depd_super);
                    break;
                }
            }
            
            // if there is an edge for which no corresponding edge was found, fail
            if (!found) { 
                return null;
            }                        
        }
                
        return indexmap;
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
