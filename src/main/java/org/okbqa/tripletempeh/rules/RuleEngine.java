package org.okbqa.tripletempeh.rules;

import java.io.FileReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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
                   rules.add(new Rule(dep,srlString,"role"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return rules;
    }
    
    public List<Rule> map_rules() {
        
        List<Rule> rules = new ArrayList<>();
        
        // TODO

        return rules;
    }

    // Rule application
    
    public void apply(Rule rule,Graph g) {
        
        Graph target = rule.getTarget();
        
        if (target.subGraphOf(g)) { 
        
           switch (rule.getTodoType()) {
                // SRL rules
                case "role": 
                     Pattern pattern = Pattern.compile("(\\w+)\\((\\d+),(\\d+)\\)");
                     Matcher matcher = pattern.matcher(rule.getTodo());
                     while (matcher.find()) {
                            String role = matcher.group(1);
                            int    head = Integer.parseInt(matcher.group(2));
                            int    depd = Integer.parseInt(matcher.group(3));
                            // TODO Fix this!
                            for (Edge e : g.getEdges(role)) {
                                 Node ghead = g.getNode(e.getHead());
                                 Node gdepd = g.getNode(e.getDependent());
                                 if (ghead.matches(target.getNode(head)) && gdepd.matches(target.getNode(depd))) {
                                     g.addEdge(new Edge(Color.SRL,ghead.getId(),role,gdepd.getId()));
                                     break;
                                 }
                            }                         
                     }
                     break;
                // mapping rules
                // TODO
           }
        }
    }
    
}
