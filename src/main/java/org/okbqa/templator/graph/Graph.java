package org.okbqa.templator.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.okbqa.templator.utils.Pair;

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
      
    public Node getNode(int i,boolean pleaseforward) {
        
        if (pleaseforward && forward.containsKey(i)) {
            i = forward.get(i);
        }
        
        for (Node n : nodes) {
            if (n.getId() == i) {
                return n;
            }
        }
        return null;
    }
    
    public List<Integer> findNode(String s) {
        
        List<Integer> ids = new ArrayList<>();
        
        for (Node n : nodes) {
             if (n.form.equals(s)) {
                 if (forward.containsKey(n.getId())) {
                     ids.add(forward.get(n.getId()));
                 } else {
                     ids.add(n.id);
                 }
             }
        }
        
        return ids;
    }
    
    public void renameNode(int n_old, int n_new) {
        
        for (Node n : nodes) {
             if (forward.containsKey(n.getId())) {
                 if (forward.get(n.getId()) == n_old) {
                     n.setId(n_new);
                 }
             } else if (n.getId() == n_old) {
                 n.setId(n_new);
             }
        }
        for (Edge e : edges) {
             if (e.getHead() == n_old) {
                 e.setHead(n_new);
             }
             if (e.getDependent() == n_old) {
                 e.setDependent(n_new);
             }
        }
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
    
    public Map<Integer,Integer> getForward() {
        return forward;
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
        if (!nodes.contains(n)) {
             nodes.add(n);
        }
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
    public void setForward(Map<Integer,Integer> m) {
        forward = m;
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
    
    // Merging with another graph 
    
    public void merge(Graph other) {
        
        Map<Integer,Integer> renaming = new HashMap<>();
        
        int max = this.getMaxId() + 1;
        
        for (Node n : other.getNodes()) {
             renaming.put(n.getId(),max);
             n.setId(max);
             this.addNode(n);
             max++;
        }
        for (Edge e : other.getEdges()) {
             e.setHead(renaming.get(e.getHead()));
             e.setDependent(renaming.get(e.getDependent()));
             this.addEdge(e);
        }
        
    }
    
    // Matching
    
    public boolean containsEdge(Edge edge) {
        
        for (Edge e : edges) {
            if (e.equals(edge)) {
                return true;
            }
        }
        return false;
    }
    
    public Edge findEdgeBetween(Integer i1, Integer i2) {
        
        Edge edge = null;
        
        for (Edge e : this.getEdges()) {
             if ((e.getHead() == i1) && e.getDependent() == i2) {
                 edge = e;
                 break;
             }
        }
        
        return edge;
    }
    
    public boolean containsEdgeBetween(Integer i1, Integer i2) {
        
        for (Edge e : edges) {
            if (e.getHead() == i1 && e.getDependent() == i2) {
                return true;
            }
        }
        return false;
    }    
    
    public Node getMatchingNode(Node node) {
                            
        for (Node n : nodes) {
             if (n.matches(node)) {
                 return n;
             }
        }
        return null;
    }
    
    public boolean contains(Graph g) {
        
        for (Edge e_g : g.getEdges()) {
             boolean found = false;
             for (Edge e_this : edges) {
                  if (e_this.getLabel().equals(e_g.getLabel())
                   && e_this.getHead() == e_g.getHead() 
                   && e_this.getDependent() == e_g.getDependent()) {
                      found = true;
                      break;
                  }
             }
             if (!found) {
                 return false;
             }
        }
        return true;
    }
    
    public List<Pair<Graph,Map<Integer,Integer>>> subGraphMatches(Graph g) {

        List<Pair<Graph,Map<Integer,Integer>>> matches = new ArrayList<>();
        List<Edge> alreadyMatched = new ArrayList<>();

        boolean keepGoing = true;
        while  (keepGoing) { // find a matching subgraph
        
            Graph match = new Graph();
            Map<Integer,Integer> indexmap = new HashMap<>();

            for (Edge e_sub : edges) {

                int head_sub = e_sub.getHead();
                int depd_sub = e_sub.getDependent();
                Node head_sub_node = getNode(head_sub,true);
                Node depd_sub_node = getNode(depd_sub,true);

                // find corresponding edge in g
                boolean found = false;

                for (Edge e_super : g.edges) {
                    
                    if (alreadyMatched.contains(e_super)) {
                        continue;
                    }

                    int head_super = e_super.getHead();
                    int depd_super = e_super.getDependent();
                    Node head_super_node = g.getNode(head_super,true);
                    Node depd_super_node = g.getNode(depd_super,true);

                    boolean match_head = head_sub_node.matches(head_super_node);
                    if (indexmap.containsKey(head_sub) 
                    && (indexmap.get(head_sub) != head_super)) {
                            match_head = false;
                    } 
                    boolean match_depd = depd_sub_node.matches(depd_super_node);
                    if (indexmap.containsKey(depd_sub) 
                    && (indexmap.get(depd_sub) != depd_super)) {
                            match_depd = false;
                    } 
                    boolean match_label = e_sub.getLabel().equals(e_super.getLabel());

                    if (match_head && match_depd && match_label) {

                        found = true;

                        match.addEdge(e_super);
                        match.addNode(head_super_node);
                        match.addNode(depd_super_node);
                        match.setForward(g.getForward());

                        indexmap.put(head_sub,head_super);
                        indexmap.put(depd_sub,depd_super);

                        break;
                    }
                }
                
                if (!found) {
                    keepGoing = false;
                }
            }
            
            if (keepGoing) { // i.e. if a match was found
                matches.add(new Pair<>(match,indexmap));
                alreadyMatched.addAll(match.getEdges());
            }
        }
               
        return matches;
    }
    
    // Show 
    
    @Override
    public String toString() { 
        
        String out = "";
                
        for (Edge e : edges) {
             out += " " + e.toString() + ";";
        }
        out += "\n";
        
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
    
    public String toCompressedString() {
        
        String out = "";
        for (Edge e : edges) {
            String head = "";
            for (Node n : nodes) { 
                 if (n.id == e.getHead()) {
                     head = n.form;
                     break;
                 }
            }
            String dpnd = "";
            for (Node n : nodes) { 
                 if (n.id == e.getDependent()) {
                     dpnd = n.form;
                     break;
                 }
            }            
            out += dpnd + " <--" + e.label + "-- " + head + "; ";
        }
        return out;
    }
    
}
