package org.okbqa.templator.transformer.rules;

import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.sparql.core.Var;
import java.io.FileReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.okbqa.templator.graph.Color;
import org.okbqa.templator.graph.Edge;
import org.okbqa.templator.graph.Graph;
import org.okbqa.templator.graph.Node;
import org.okbqa.templator.interpreter.Interpreter;
import org.okbqa.templator.template.Slot;
import org.okbqa.templator.template.Template;
import org.okbqa.templator.utils.Pair;

/**
 * 
 * @author cunger
 */
public class RuleEngine {
       
    String path_SRL;
    String path_map;
    String path_map_common;
    
    List<Rule> SRL_rules;
    List<Rule> map_rules;
        
    JSONParser  parser;
    Interpreter interpreter;

    // slot annotations
    public String PROPERTY = "rdf:Property";
    public String CLASS    = "rdf:Class";
    public String RESOURCE = "rdf:Resource";
    public String RESOURCEorLITERAL = "rdf:Resource|Literal";
    
    // fresh variable counter (see fresh())
    int i = 0; 

    
    public RuleEngine(String language) {
    
        path_SRL = "rules/SRL_rules_"+language+".json";
        path_map = "rules/map_rules_"+language+".json";
        path_map_common = "rules/map_rules_common.json";
        
        parser = new JSONParser();
        interpreter = new Interpreter();
    }
    
    
    // Rules 
    
    public List<Rule> SRL_rules() {
        
        List<String> types = new ArrayList<>();
        types.add("arg");
        types.add("mod");
        types.add("neg");
                
        List<Rule> rules = new ArrayList<>();

        try {
            // read path_SRL file (JSON)
            URL url = this.getClass().getClassLoader().getResource(path_SRL);
            String file = url.toString().replace("file:","");
            JSONArray json = (JSONArray) parser.parse(new FileReader(file));
        
            // get each rule and add it to rules
            Iterator<JSONObject> iterator = json.iterator();
            while (iterator.hasNext()) {
                   JSONObject j = iterator.next();
                                      
                   Graph dep = interpreter.interpret((String) j.get("dep"));
                                                      
                   for (String type : types) {
                       if (j.containsKey(type)) {
                           rules.add(new Rule(dep,new ArrayList<>(Arrays.asList((String) j.get(type))),type));
                       }
                   }
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
                
        return rules;
    }
        
    public List<Rule> map_rules() {
                
        List<Rule> rules = new ArrayList<>();

        try {
            // read path_SRL files (JSON)
            URL url;
            String file;
            JSONArray json;
            // language-specific one
            url  = this.getClass().getClassLoader().getResource(path_map);
            file = url.toString().replace("file:","");
            json = (JSONArray) parser.parse(new FileReader(file));
            // common one
            url = this.getClass().getClassLoader().getResource(path_map_common);
            file = url.toString().replace("file:","");
            JSONArray json_common = (JSONArray) parser.parse(new FileReader(file));
            // merge 
            json.addAll(json_common);
            // => language-specific rules are applied before common rules
        
            // get each rule and add it to rules
            Iterator<JSONObject> iterator = json.iterator();
            while (iterator.hasNext()) {
                   JSONObject j = iterator.next();
                   // structures
                   List<Graph> structures = new ArrayList<>();
                   Iterator<String> i1 = ((JSONArray) j.get("struct")).iterator();
                   // actions 
                   List<Graph> actions = new ArrayList<>();
                   Iterator<String> i2 = ((JSONArray) j.get("action")).iterator();
                   //
                   while (i1.hasNext()) {
                        String struct = i1.next();
                        Graph gstruct = interpreter.interpret(struct);
                        List<String> acts = new ArrayList<>();
                        while (i2.hasNext()) {
                            String act = i2.next();
                            acts.add(act);
                        }
                        rules.add(new Rule(gstruct,acts,"map"));
                   }
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        
        return rules;
    }
    
    // Rule application 
    
    public void apply(Rule rule,Graph graph) {
                
        List<Pair<Graph,Map<Integer,Integer>>> matches = rule.getTarget().subGraphMatches(graph);
        
        for (Pair<Graph,Map<Integer,Integer>> match : matches) {
            
            Map<Integer,Integer> map = match.getRight();

            // SRL rules

            String  todoType = rule.getTodoType();
                        
            if (todoType.equals("arg") || todoType.equals("mod") || todoType.equals("neg")) {
                
                Color color;
                switch (todoType) {
                    case "arg": color = Color.ARG; break;
                    case "mod": color = Color.MOD; break;
                    case "neg": color = Color.NEG; break;
                    default:    color = Color.UNKNOWN; 
                }

                for (String todo : rule.getTodos()) {    
                     Pattern pattern = Pattern.compile("(\\w+)\\((\\d+),(\\d+)\\)");
                     Matcher matcher = pattern.matcher(todo);
                     while  (matcher.find()) {
                             String role = matcher.group(1);
                             int    head = map.get(Integer.parseInt(matcher.group(2)));
                             int    depd = map.get(Integer.parseInt(matcher.group(3)));
                             if (!graph.containsRoleEdgeBetween(head,depd)) {
                                 graph.addEdge(new Edge(color,head,role,depd));
                             } // i.e. every node can have only one semantic role
                     }
                     break;
                }
            }
        }
    }
    
    public void apply(Rule rule,Graph graph,Template template) {
                
        List<Pair<Graph,Map<Integer,Integer>>> matches = rule.getTarget().subGraphMatches(graph);
        
        for (Pair<Graph,Map<Integer,Integer>> match : matches) {
            
            // System.out.println("Applying rule to " + match.getLeft().toCompressedString() + ":\n" + rule.toString());
                        
            Graph subgraph = match.getLeft();
            Map<Integer,Integer> forward = subgraph.getForward();
            Map<Integer,Integer> map = match.getRight();
            
            switch (rule.getTodoType()) {
                // mapping rules
                case "map":
                    String todo;
                    for (String t : rule.getTodos()) {
                        todo = t;
                        // create fresh variables
                        Pattern fresh_pattern = Pattern.compile("(fresh)");
                        Matcher fresh_matcher = fresh_pattern.matcher(t);
                        while  (fresh_matcher.find()) {
                            int newv = fresh();
                            map.put(newv,newv);
                            todo = t.replace(fresh_matcher.group(1),Integer.toString(newv));
                        }
                        // projvar(count(1)) 
                        Pattern count_pattern = Pattern.compile("projvar\\(count\\((\\d+)\\)\\)");
                        Matcher count_matcher = count_pattern.matcher(todo);
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
                        Matcher projvar_matcher = projvar_pattern.matcher(todo);
                        while  (projvar_matcher.find()) {
                            // add projection variable
                            int projvar = map.get(Integer.parseInt(projvar_matcher.group(1)));
                            if (forward.containsKey(projvar)) {
                                projvar = forward.get(projvar);
                            }
                            template.addProjVar(varString(projvar));
                        }
                        // triple(1,SORTAL|UNSPEC,2)
                        Pattern sortal_pattern = Pattern.compile("triple\\((\\d+),((SORTAL)|(UNSPEC)),(\\d+)\\)");
                        Matcher sortal_matcher = sortal_pattern.matcher(todo);
                        while  (sortal_matcher.find()) {
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
                           String kindofobject;
                           if (prop.equals("SORTAL")) { kindofobject = CLASS; } else { kindofobject = RESOURCEorLITERAL; }
                           template.addSlot(new Slot(vo,subgraph.getNode(o,true).getForm(),kindofobject));
                           template.addSlot(new Slot(v,"",PROPERTY,prop)); 
                        }
                        // triple(1,2,3)
                        Pattern triple_pattern = Pattern.compile("triple\\((\\d+),(\\d+),(\\d+)\\)");
                        Matcher triple_matcher = triple_pattern.matcher(todo);
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
                           template.addSlot(new Slot(vs,fs,RESOURCE));
                           template.addSlot(new Slot(vp,fp,PROPERTY));
                           template.addSlot(new Slot(vo,fo,RESOURCEorLITERAL));
                        }
                        // forward(1->2)
                        Pattern map_pattern = Pattern.compile("forward\\((\\d+)->(\\d+)\\)");
                        Matcher map_matcher = map_pattern.matcher(todo);
                        while  (map_matcher.find()) {
                            // forward graph node
                            int old_i = map.get(Integer.parseInt(map_matcher.group(1)));
                            int new_i = map.get(Integer.parseInt(map_matcher.group(2)));
                            graph.addForward(old_i,new_i);
                        }
                        // delete
                        if (todo.equals("delete")) {
                           // delete matched subgraph
                           graph.delete(subgraph); 
                        }
                    } 
           }
        }
    }
    
    // building variable strings
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
