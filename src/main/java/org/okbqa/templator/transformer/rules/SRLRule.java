package org.okbqa.templator.transformer.rules;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.okbqa.templator.graph.Edge;
import org.okbqa.templator.graph.Graph;
import org.okbqa.templator.graph.Node;
import org.okbqa.templator.utils.Pair;

/**
 *
 * @author cunger
 */
public class SRLRule {
    
    Graph target;
    List<String> effects;
    
    public SRLRule(Graph g, List<String> ls) {
        target  = g;
        effects = ls;
    }
    
    public Graph getTarget() {
        return target;
    }
    public List<String> getEffects() {
        return effects;
    }
    
    public void apply(Graph graph) {
                
        List<Pair<Graph,Map<Integer,Integer>>> matches = target.subGraphMatches(graph);
        
        for (Pair<Graph,Map<Integer,Integer>> match : matches) {
            
            Map<Integer,Integer> map = match.getRight();
                        
            for (String s : effects) {
                
                Pattern pattern;
                Matcher matcher;
                
                // Functional roots
                pattern = Pattern.compile("(\\w+)\\((\\d+)\\)");
                matcher = pattern.matcher(s);
                while  (matcher.find()) {
                    String func = matcher.group(1);
                    int root; 
                    if (matcher.group(2).equals("0")) {
                        root = 0; 
                        map.put(0,0);
                    } else { 
                        root = map.get(Integer.parseInt(matcher.group(2)));
                        graph.getNode(root,true).setForm(func);
                    }
                    graph.addNode(new Node(root,func));
                    break;
                }
                
                // Relations 
                pattern = Pattern.compile("(\\w+)\\((\\d+),(\\d+)\\)");
                matcher = pattern.matcher(s);
                while  (matcher.find()) {
                    
                    String role = matcher.group(1);
                    int    head = map.get(Integer.parseInt(matcher.group(2)));
                    int    depd = map.get(Integer.parseInt(matcher.group(3)));
                    graph.addEdge(new Edge(head,role,depd));
                    graph.delete(match.getLeft());
                    break;
                }
            }
        }
    }
    
}
