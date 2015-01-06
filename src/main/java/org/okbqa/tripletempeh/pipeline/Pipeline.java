package org.okbqa.tripletempeh.pipeline;

import java.io.IOException;
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
    
    String language;
    
    Parser            parser;
    Interpreter       interpreter;
    RuleEngine        engine;
    GraphManipulation manipulator;
    Graph2Template    transformer;
    
    boolean verbose;
    
    public Pipeline(String l) throws IOException {
        this(l,true);
    }
    
    public Pipeline(String l,boolean b) throws IOException {
      
        language = l;
        verbose  = b;
        
        parser      = new ClearNLP();
        interpreter = new Interpreter();
        engine      = new RuleEngine(language);
        manipulator = new GraphManipulation(engine);
        transformer = new Graph2Template(engine);
    }
    
    
    public Template run(String input) {
        
        // 1. Parse 
        String parse = parser.parse(input);

        // 2. Reading: Parse -> Graph
        Graph g = interpreter.interpret(parse);
        
        // 3. Semantic role labeling
        manipulator.doSRL(g);
        
        // 4. Mapping: Graph -> Template       
        Template t = transformer.transform(g);
                   
        if (verbose) {
            System.out.println("------------INPUT----------------");
            System.out.println(input);
            System.out.println("------------PARSE----------------");
            System.out.println(parse);
            System.out.println("------------GRAPH ("+(g.getFormat())+")----------------");
            System.out.println(g.toString());
            System.out.println("------------TEMPLATE-------------");
            System.out.println(t.toString()); 
        }
         
        return t;
    }
    
}
