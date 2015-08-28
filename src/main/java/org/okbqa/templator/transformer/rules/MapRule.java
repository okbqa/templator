package org.okbqa.templator.transformer.rules;

import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.sparql.core.Var;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.okbqa.templator.graph.Graph;
import org.okbqa.templator.graph.Node;
import org.okbqa.templator.template.Slot;
import org.okbqa.templator.template.SlotType;
import org.okbqa.templator.template.Template;
import org.okbqa.templator.utils.Pair;

/**
 *
 * @author cunger
 */
public class MapRule {
    
    Graph target;
    List<String> effects;
    
    int i;
    
    
    public MapRule(Graph g, List<String> ls) {
        target  = g;
        effects = ls;
    }
    
    public Graph getTarget() {
        return target;
    }
    public List<String> getEffects() {
        return effects;
    }
    
    public void apply(Graph graph, Template template) {
        
        for (Node n : target.getNodes()) {
            if (!n.getForm().equals("*")) {
                template.addToBlacklist(n.getForm().toLowerCase());
            }
        }
        
        List<Pair<Graph,Map<Integer,Integer>>> matches = target.subGraphMatches(graph);
        
        for (Pair<Graph,Map<Integer,Integer>> match : matches) {
            
            Graph subgraph = match.getLeft();
            Map<Integer,Integer> forward = subgraph.getForward();
            Map<Integer,Integer> map = match.getRight();
            
            String effect;
            for (String str : effects) {
                effect = str;
                // create fresh variables
                Pattern fresh_pattern = Pattern.compile("(fresh)");
                Matcher fresh_matcher = fresh_pattern.matcher(str);
                while  (fresh_matcher.find()) {
                        int newv = fresh();
                        map.put(newv,newv);
                        effect = str.replace(fresh_matcher.group(1),Integer.toString(newv));
                }
                // projvar(count(1)) 
                Pattern count_pattern = Pattern.compile("projvar\\(count\\((\\d+)\\)\\)");
                Matcher count_matcher = count_pattern.matcher(effect);
                while  (count_matcher.find()) {
                        // add projection variable with count modifier
                        int projvar = map.get(Integer.parseInt(count_matcher.group(1)));
                        if (forward.containsKey(projvar)) {
                            projvar = forward.get(projvar);
                        }
                        template.addCountVar(varString(projvar));
                }
                // projvar(1)
                Pattern projvar_pattern = Pattern.compile("projvar\\((\\d+)\\)");
                Matcher projvar_matcher = projvar_pattern.matcher(effect);
                while  (projvar_matcher.find()) {
                        // add projection variable
                        int projvar = map.get(Integer.parseInt(projvar_matcher.group(1)));
                        if (forward.containsKey(projvar)) {
                            projvar = forward.get(projvar);
                        }
                        template.addProjVar(varString(projvar));
                }
                // triple patterns
                boolean alreadyMatched = false;
                // triple(1,SORTAL|UNSPEC,2)
                Pattern sortal_pattern = Pattern.compile("triple\\((\\d+),((SORTAL)|(UNSPEC)),(\\d+)\\)");
                Matcher sortal_matcher = sortal_pattern.matcher(effect);
                while  (sortal_matcher.find()) {
                        alreadyMatched = true;
                        String prop = sortal_matcher.group(2);
                        // add triple
                        int     s = map.get(Integer.parseInt(sortal_matcher.group(1)));
                        int     o = map.get(Integer.parseInt(sortal_matcher.group(5)));
                        if (forward.containsKey(s)) {
                                s = forward.get(s);
                        }
                        if (forward.containsKey(o)) {
                                o = forward.get(o);
                        }
                        String vs = varString(s);
                        String vo = varString(o);
                        String v  = varString(fresh());
                        template.addTriple(new Triple(Var.alloc(vs),Var.alloc(v),Var.alloc(vo)));
                        // add slots
                        if (prop.equals("SORTAL")) { 
                            template.addSlot(new Slot(vo,subgraph.getNode(o,true).getForm(),SlotType.CLASS));
                            template.addSlot(new Slot(v,"",SlotType.SORTAL));
                        } 
                        if (prop.equals("UNSPEC")) {
                            template.addSlot(new Slot(vo,subgraph.getNode(o,true).getForm(),SlotType.RESOURCEorLITERAL)); 
                            template.addSlot(new Slot(v,"",SlotType.PROPERTY));
                        }                                                
                }
                // triple(1,SORTAL,(THING|PERSON|PLACE|TIME))
                if (!alreadyMatched) {
                Pattern type_pattern = Pattern.compile("triple\\((\\d+),SORTAL,(\\w+)\\)");
                Matcher type_matcher = type_pattern.matcher(effect);
                while  (type_matcher.find()) {
                        alreadyMatched = true;
                        // add triple
                        int s = map.get(Integer.parseInt(type_matcher.group(1)));
                        if (forward.containsKey(s)) {
                            s = forward.get(s);
                        }
                        String vs = varString(s);
                        String vo = varString(fresh());
                        String v  = varString(fresh());
                        template.addTriple(new Triple(Var.alloc(vs),Var.alloc(v),Var.alloc(vo)));
                        // add slots
                        String form;
                        switch (type_matcher.group(2)) {
                        case "PERSON":
                            form = "person"; break;
                        case "PLACE":
                            form = "place"; break;
                        case "TIME":
                            form = "date"; break;
                        default:
                            form = "thing"; break;
                        }
                        template.addSlot(new Slot(vo,form,SlotType.CLASS));
                        template.addSlot(new Slot(v,"",SlotType.SORTAL));
                }}
                if (!alreadyMatched) {
                // triple(1,2,3)
                Pattern triple_pattern = Pattern.compile("triple\\((\\d+),(\\d+),(\\d+)\\)");
                Matcher triple_matcher = triple_pattern.matcher(effect);
                while  (triple_matcher.find()) {
                        // add triple
                        int     s = map.get(Integer.parseInt(triple_matcher.group(1)));
                        int     p = map.get(Integer.parseInt(triple_matcher.group(2)));
                        int     o = map.get(Integer.parseInt(triple_matcher.group(3)));
                        if (forward.containsKey(s)) {
                            s = forward.get(s);
                        }
                        if (forward.containsKey(p)) {
                            p = forward.get(p);
                        }
                        if (forward.containsKey(o)) {
                            o = forward.get(o);
                        }
                        String vs = varString(s);
                        String vp = varString(p);
                        String vo = varString(o);
                        template.addTriple(new Triple(Var.alloc(vs),Var.alloc(vp),Var.alloc(vo)));
                        // add slots
                        String fs = ""; String fp = ""; String fo = "";
                        Node ns = subgraph.getNode(s,true);
                        Node np = subgraph.getNode(p,true);
                        Node no = subgraph.getNode(o,true);
                        if (ns != null) { fs = ns.getForm(); }
                        if (np != null) { fp = np.getForm(); }
                        if (no != null) { fo = no.getForm(); }
                        template.addSlot(new Slot(vs,fs,SlotType.RESOURCE));
                        template.addSlot(new Slot(vp,fp,SlotType.PROPERTY));
                        template.addSlot(new Slot(vo,fo,SlotType.RESOURCEorLITERAL));
                }}
                // forward(1->2)
                Pattern map_pattern = Pattern.compile("forward\\((\\d+)->(\\d+)\\)");
                Matcher map_matcher = map_pattern.matcher(effect);
                while  (map_matcher.find()) {
                        // forward graph node
                        int old_i = map.get(Integer.parseInt(map_matcher.group(1)));
                        int new_i = map.get(Integer.parseInt(map_matcher.group(2)));
                        graph.addForward(old_i,new_i);
                }
            } 
            graph.delete(subgraph);
        }
    }
    
    
    // AUX 
    
    // Building variable strings
    public String varString(Node node) {
        return "v" + node.getId();
    }
    public String varString(int i) {
        return "v" + i;
    }
    // Fresh variable supply
    public void set_i(int n) {
        i = n;
    }
    public int fresh() {
        i++;
        return i;
    }
    
}
