package org.okbqa.tripletempeh.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.okbqa.tripletempeh.utils.Pair;

/**
 *
 * @author cunger
 */
public class Graph {

    Format format;
    
    ArrayList<Node> nodes;
    ArrayList<Edge> edges;
    ArrayList<Integer> roots;
    
    Map<Integer,Integer> forward;
    
    public Graph() {
        nodes   = new ArrayList<>();
        edges   = new ArrayList<>();
        roots   = new ArrayList<>();
        forward = new HashMap<>();
    }
    
    public Graph(Format f) {
        format  = f;
        nodes   = new ArrayList<>();
        edges   = new ArrayList<>();
        roots   = new ArrayList<>();        
        forward = new HashMap<>();
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
    public ArrayList<Node> getNodes() {
        return nodes;
    }
    
    public Node getNode(int i) {
        
        if (forward.containsKey(i)) {
            i = forward.get(i);
        }
        
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
    
    public List<Edge> getEdges(Color color) {

        List<Edge> results = new ArrayList<>();
        
        for (Edge e : edges) {
             if (e.getColor() == color) {
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
    public void addRoot(int i) {
        roots.add(i);
    }
    public void addForward(int i, int j) {
        forward.put(i,j);
    }
    
    // Manipulate graph 
    
    public void deleteNode(Node n) {
        nodes.remove(n);
    }
    public void deleteEdge(Edge e) {
        edges.remove(e);
    }
    
    public void delete(Graph g) {
//      for (Node n : g.getNodes()) {
//          deleteNode(n);
//      }
        for (Edge e : g.getEdges()) {
            deleteEdge(e);
        }
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
    
    public Pair<Graph,Map<Integer,Integer>> subGraphMatch(Graph g) {
        
        Graph match = new Graph();                        
        Map<Integer,Integer> indexmap = new HashMap<>();
        
        for (Edge e_sub : edges) {
            
            int head_sub = e_sub.getHead();
            int depd_sub = e_sub.getDependent();
            Node head_sub_node = getNode(head_sub);
            Node depd_sub_node = getNode(depd_sub);
            
            // find corresponding edge in g
            boolean found = false;

            for (Edge e_super : g.edges) {
                
                int head_super = e_super.getHead();
                int depd_super = e_super.getDependent();
                
                Node head_super_node = g.getNode(head_super);
                Node depd_super_node = g.getNode(depd_super);
                
                if (head_super_node.matches(head_sub_node)
                 && depd_super_node.matches(depd_sub_node)) {
                   
                    if ((e_sub.getLabel().equals("#SRL#") && e_super.getColor() == Color.SRL)
                        || e_sub.getLabel().equals(e_super.getLabel())) {
                    
                        found = true;
                        match.addEdge(e_super);
                        match.addNode(head_super_node);
                        match.addNode(depd_super_node);
                        indexmap.put(head_sub,head_super);
                        indexmap.put(depd_sub,depd_super);
                        break;
                    }
                }
            }
            
            // if there is an edge for which no corresponding edge was found, fail
            if (!found) { 
                return null;
            }                        
        }
               
        return new Pair<>(match,indexmap);
    }

    
    // Show 
    
    @Override
    public String toString() { 
        
        String out = "";
                
        for (Edge e : edges) {
            if (e.getColor() == Color.DEPENDENCY) {
                out += " " + e.toString() + ";";
            }
        }
        out += "\n";
        for (Edge e : edges) {
            if (e.getColor() == Color.SRL) {
                out += " " + e.toString() + ";";
            }
        }
        
        out += "\nRoot node(s):";
        for (int i : roots) {
            out += "\n* " + i;
        }
        
        out += "\nNodes:";
        for (Node n : nodes) {
            out += "\n* " + n.getId() + " " + n.getForm() + " (" + n.getPOS() + ")";
        }
        
        return out;
    }
    
}
