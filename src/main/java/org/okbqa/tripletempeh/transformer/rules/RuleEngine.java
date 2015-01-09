package org.okbqa.tripletempeh.transformer.rules;

import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.sparql.core.Var;
import com.hp.hpl.jena.sparql.syntax.ElementTriplesBlock;
import com.hp.hpl.jena.vocabulary.RDF;
import java.io.FileReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.okbqa.tripletempeh.graph.Color;
import org.okbqa.tripletempeh.graph.Edge;
import org.okbqa.tripletempeh.graph.Graph;
import org.okbqa.tripletempeh.graph.Node;
import org.okbqa.tripletempeh.interpreter.Interpreter;
import org.okbqa.tripletempeh.template.Slot;
import org.okbqa.tripletempeh.template.Template;
import org.okbqa.tripletempeh.utils.Pair;

/**
 * 
 * @author cunger
 */
public class RuleEngine {
    
    String path_SRL;
    String path_map;
    
    List<Rule> SRL_rules;
    List<Rule> map_rules;
    
    JSONParser parser;
    Interpreter interpreter;

    // slot annotations
    public String PROPERTY   = "owl:Property";
    public String CLASS      = "owl:Class";
    public String INDIVIDUAL = "owl:NamedIndividual";
    
    // fresh variable counter (see fresh())
    int i = 0; 

    
    public RuleEngine(String language) {
    
        path_SRL = "rules/SRL_rules_"+language+".json";
        path_map = "rules/map_rules_"+language+".json";
        
        parser = new JSONParser();
        interpreter = new Interpreter();
    }
    
    
    // Rules 
    
    public List<Rule> SRL_rules() {
                
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
                   String depString = (String) j.get("dep");
                   String srlString = (String) j.get("srl");
                   Graph dep = interpreter.interpret(depString);
                   List<String> srls = new ArrayList<>();
                   srls.add(srlString);
                   rules.add(new Rule(dep,srls,"role"));
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
                   
        return rules;
    }
        
    public List<Rule> map_rules() {
                
        List<Rule> rules = new ArrayList<>();

        try {
            // read path_SRL file (JSON)
            URL url = this.getClass().getClassLoader().getResource(path_map);
            String file = url.toString().replace("file:","");
            JSONArray json = (JSONArray) parser.parse(new FileReader(file));
        
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
        
        Pair<Graph,Map<Integer,Integer>> subgraphmatch = rule.getTarget().subGraphMatch(graph);
        
        if (subgraphmatch != null) {
  
            Map<Integer,Integer> map = subgraphmatch.getRight();

            switch (rule.getTodoType()) {
                // SRL rules
                case "role": 
                    for (String todo : rule.getTodos()) {
                        Pattern pattern = Pattern.compile("(\\w+)\\((\\d+),(\\d+)\\)");
                        Matcher matcher = pattern.matcher(todo);
                        while (matcher.find()) {
                               String role = matcher.group(1);
                               int    head = Integer.parseInt(matcher.group(2));
                               int    depd = Integer.parseInt(matcher.group(3));
                               graph.addEdge(new Edge(Color.SRL,map.get(head),role,map.get(depd)));
                        }
                    }
                    break;
           }
        }
    }
    
    public void apply(Rule rule,Graph graph,Template template) {
        
        Pair<Graph,Map<Integer,Integer>> subgraphmatch = rule.getTarget().subGraphMatch(graph);
        
        if (subgraphmatch != null && subgraphmatch.getLeft() != null) {
            
            Graph subgraph = subgraphmatch.getLeft();
            Map<Integer,Integer> map = subgraphmatch.getRight();
           
            switch (rule.getTodoType()) {
                // mapping rules
                case "map":
                    for (String todo : rule.getTodos()) {
                        // projvar(1)
                        Pattern projvar_pattern = Pattern.compile("projvar\\((\\d+)\\)");
                        Matcher projvar_matcher = projvar_pattern.matcher(todo);
                        while  (projvar_matcher.find()) {
                            // add projection variable
                            int projvar = map.get(Integer.parseInt(projvar_matcher.group(1)));
                            template.addProjVar(varString(projvar));
                        }
                        // triple(1,rdf:type,2)
                        Pattern triple_pattern = Pattern.compile("triple\\((\\d+),SORTAL,(\\d+)\\)");
                        Matcher triple_matcher = triple_pattern.matcher(todo);
                        while  (triple_matcher.find()) {
                           // add rdf:type triple
                           int     s = map.get(Integer.parseInt(triple_matcher.group(1)));
                           int     o = map.get(Integer.parseInt(triple_matcher.group(2)));
                           String vs = varString(s);
                           String vo = varString(o);
                           String v  = varString(fresh());
                           ElementTriplesBlock triples = new ElementTriplesBlock();
                           triples.addTriple(new Triple(Var.alloc(vs),Var.alloc(v),Var.alloc(vo)));
                           template.addTriples(triples);
                           // add class slot
                           template.addSlot(new Slot(vo,subgraph.getNode(o).getForm(),CLASS));
                           template.addSlot(new Slot(v,"",PROPERTY,"SORTAL"));
                        }
                        // forward(1->2)
                        Pattern map_pattern = Pattern.compile("rename\\((\\d+)->(\\d+)\\)");
                        Matcher map_matcher = map_pattern.matcher(todo);
                        while  (map_matcher.find()) {
                            // rename graph node
                            int old_i = map.get(Integer.parseInt(map_matcher.group(1)));
                            int new_i = map.get(Integer.parseInt(map_matcher.group(2)));
                            graph.addForward(old_i,map.get(new_i));
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
