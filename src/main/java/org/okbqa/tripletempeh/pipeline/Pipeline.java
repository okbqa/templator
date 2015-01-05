package org.okbqa.tripletempeh.pipeline;

import java.io.IOException;
import org.okbqa.tripletempeh.graph.Graph;
import org.okbqa.tripletempeh.interpreter.Interpreter;
import org.okbqa.tripletempeh.parsing.ClearNLP;
import org.okbqa.tripletempeh.parsing.Parser;
import org.okbqa.tripletempeh.template.Template;
import org.okbqa.tripletempeh.transformer.Graph2Template;

/**
 *
 * @author cunger
 */
public class Pipeline {
    
    Parser parser;
    Interpreter interpreter;
    Graph2Template transformer;
    
    boolean verbose;
    
    public Pipeline() throws IOException {
        this(true);
    }
    
    public Pipeline(boolean b) throws IOException {
      
        parser      = new ClearNLP(); // TODO param
        interpreter = new Interpreter();
        transformer = new Graph2Template();
        
        verbose = b;
    }
    
    
    public Template run(String input) {
        
        // 1. Parse 
        String parse = parser.parse(input);

        // 2. Reading: Parse -> Graph
        Graph g = interpreter.interpret(parse);
        
        // 3. Semantic role labeling
        // TODO
        
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
