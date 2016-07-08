package org.okbqa.templator.transformer.rules;

import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.okbqa.templator.graph.Graph;
import org.okbqa.templator.graph.Node;
import org.okbqa.templator.interpreter.Interpreter;
import org.okbqa.templator.template.Slot;
import org.okbqa.templator.template.Template;

/**
 * 
 * @author cunger
 */
public class RuleEngine {
       
    String path_SRL;
    String path_map;
    String path_flat;
    
    List<SRLRule> SRL_rules;
    List<MapRule> map_rules;
    List<MapRule> flat_rules;
            
    JSONParser  parser;
    Interpreter interpreter;
    
    List<String> blacklist;
    int i = 0; // counter for fresh variables

    
    public RuleEngine(String language) {
    
        path_SRL  = "rules/SRL_rules_"+language+".json";
        path_map  = "rules/map_rules.json";
        path_flat = "rules/flatten_rules.json";
        
        parser = new JSONParser();
        interpreter = new Interpreter();
        
        blacklist  = new ArrayList<>();
        
        map_rules  = new ArrayList<>();
        flat_rules = new ArrayList<>();
        
        read_SRL_rules();
        read_map_rules(map_rules,path_map);
        read_map_rules(flat_rules,path_flat);
    }
    
    public void set_i(int i) {
        this.i = i;
    }
    
    
    // Read rules 
    
    private void read_SRL_rules() {
        
        SRL_rules = new ArrayList<>();
        
        try {
            // read path_SRL file (JSON)
            URL url = this.getClass().getClassLoader().getResource(path_SRL);
            File file = Paths.get(url.toURI()).toFile();
            JSONArray json = (JSONArray) parser.parse(new FileReader(file));
        
            // get each rule and add it to rules
            Iterator<JSONObject> iterator = json.iterator();
            while (iterator.hasNext()) {
                   JSONObject j = iterator.next();  
                   // dep
                   Graph dep = interpreter.interpret((String) j.get("dep"));
                   // srl 
                   Iterator<String> i = ((JSONArray) j.get("srl")).iterator();
                   List<String> srls = new ArrayList<>();
                   while (i.hasNext()) {
                       srls.add(i.next());
                   }
                   SRL_rules.add(new SRLRule(dep,srls));
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }
        
    private void read_map_rules(List<MapRule> rules, String path) {
                        
        try {
            URL url  = this.getClass().getClassLoader().getResource(path);
            File file = Paths.get(url.toURI()).toFile();
            JSONArray json = (JSONArray) parser.parse(new FileReader(file));
        
            // get each rule and add it to rules
            Iterator<JSONObject> iterator = json.iterator();
            while (iterator.hasNext()) {
                   JSONObject j = iterator.next();
                   // input
                   Iterator<String> i1 = ((JSONArray) j.get("in")).iterator();
                   // output 
                   List<String> actions = new ArrayList<>();
                   Iterator<String> i2 = ((JSONArray) j.get("out")).iterator();
                   //
                   while (i1.hasNext()) {
                        String struct = i1.next();
                        Graph gstruct = interpreter.interpret(struct);
                        while (i2.hasNext()) {
                            String act = i2.next();
                            actions.add(act);
                        }
                        rules.add(new MapRule(gstruct,actions));
                   }
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }
    
    // Rule application 
    
    public void apply_SRL_rules(Graph graph) {
                       
        for (SRLRule r : SRL_rules) {
            for (Node n : r.getTarget().getNodes()) {
               if (!n.getForm().equals("*")) {
                    blacklist.add(n.getForm().toLowerCase());
                }
            }
            r.apply(graph);
        }
    }
    
    public void apply_map_rules(Graph graph,Template template) {
        
        template.getBlacklist().addAll(blacklist);
        
        for (MapRule r : map_rules) {
             r.set_i(i);
             r.apply(graph,template);
        }
        
        ArrayList<String> vars = new ArrayList<>();
        for (Slot s : template.getSlots()) if (template.getProjvars().contains(s.getVar())) vars.add(s.getVar());
        for (String var : vars) template.removeSlot(var);
    }
    
    public void apply_flat_rules(Graph graph) {
                
        for (MapRule r : flat_rules) {
             r.set_i(i);
             r.apply(graph);
        }
    }
}
