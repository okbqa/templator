package org.okbqa.tripletempeh.pipeline;

import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.okbqa.tripletempeh.graph.Graph;
import org.okbqa.tripletempeh.interpreter.Interpreter;
import org.okbqa.tripletempeh.parsing.ClearNLP;
import org.okbqa.tripletempeh.parsing.Parser;
import org.okbqa.tripletempeh.rules.RuleEngine;
import org.okbqa.tripletempeh.template.Template;
import org.okbqa.tripletempeh.transformer.Graph2Template;
import org.okbqa.tripletempeh.transformer.GraphManipulation;

/**
 *
 * @author cunger
 */
public class Pipeline {
        
    Interpreter       interpreter;
    
    // English
    Parser            parser_en;
    RuleEngine        engine_en;
    GraphManipulation manipulator_en;
    Graph2Template    transformer_en;

    // Korean
    Parser            parser_ko;
    RuleEngine        engine_ko;
    GraphManipulation manipulator_ko;
    Graph2Template    transformer_ko;
    
    boolean verbose;
    
    public Pipeline() {
        this(true);
    }
    
    public Pipeline(boolean b) {
      
        verbose  = b;
          
        interpreter = new Interpreter();
        
        // English
        try {
        parser_en      = new ClearNLP();
        } catch (IOException e) { parser_en = null; }; // TODO
        engine_en      = new RuleEngine("en");
        manipulator_en = new GraphManipulation(engine_en);
        transformer_en = new Graph2Template(engine_en);
        
        // Korean 
        //parser_ko      = new ...();
        //engine_ko      = new RuleEngine("ko");        
        //manipulator_ko = new GraphManipulation(engine_ko);
        //transformer_ko = new Graph2Template(engine_ko);
    }
    
 
    public JSONArray run(String input) {
        
        try {
            // parse input JSON string
            JSONParser jsonparser = new JSONParser();
            JSONObject json = (JSONObject) jsonparser.parse(input);
            
            String string   = (String) json.get("string");
            String language = (String) json.get("language");
            
            // process
            Template template = process(string,language);
            
            // return ouput as JSON object
            JSONArray output = new JSONArray();
            output.add(template.toJSON());
            
            return output;
        } 
        catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return null;
    }
    
    public Template process(String string,String language) {
        
        // 1. Parse 
        // 2. Reading: Parse -> Graph
        // 3. Semantic role labeling
        // 4. Mapping: Graph -> Template       
        
        String parse = null;
        Graph g      = null;
        Template t   = null;
        
        switch (language) {
            case "en":
                parse = parser_en.parse(string);
                g = interpreter.interpret(parse);
                manipulator_en.doSRL(g);
                t = transformer_en.transform(g);
                break;
            case "ko":
                parse = parser_ko.parse(string);
                g = interpreter.interpret(parse);
                manipulator_ko.doSRL(g);
                t = transformer_ko.transform(g);
                break;
        }
                   
        if (verbose) {
            System.out.println("------------INPUT----------------");
            System.out.println(string);
            System.out.println("------------PARSE----------------");
            System.out.println(parse);
            System.out.println("------------GRAPH----------------");
            System.out.println(g.toString());
            System.out.println("------------TEMPLATE-------------");
            System.out.println(t.toString()); 
        }
         
        return t;
    }
    
}
