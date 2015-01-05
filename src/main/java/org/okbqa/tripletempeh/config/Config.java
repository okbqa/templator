package org.okbqa.tripletempeh.config;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.okbqa.tripletempeh.graph.Format;
import org.okbqa.tripletempeh.graph.Graph;
import org.okbqa.tripletempeh.interpreter.Interpreter;
import org.okbqa.tripletempeh.transformer.Rule;

/**
 *
 * @author cunger
 */
public class Config {
    
    Format format;
    
    public Config(Format f) {
        format = f;
    }
    
    
    // Rules for semantic role labeling
    
    public List<Rule> SRL_rules() {
        
        String file = "SRL_rules.json";
        
        JSONParser  parser = new JSONParser();
        Interpreter interpreter = new Interpreter();

        List<Rule> rules = new ArrayList<>();

	try {
	      JSONArray json = (JSONArray) parser.parse(new FileReader(file));
 
              
	      Iterator<JSONObject> iterator = json.iterator();
	      while (iterator.hasNext()) {
                    JSONObject j = iterator.next();
                    String depString = (String) j.get("dep");
                    String srlString = (String) j.get("srl");
                    Graph  dep = interpreter.interpret(depString);
                    
                    rules.add(new Rule(dep,srlString));
    	      }
	} catch (Exception e) {
	    e.printStackTrace();
	}
        
        return rules;
    }
    
    
    // Rules for mapping Graph -> Template
    // TODO
    
}
