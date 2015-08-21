package org.okbqa.templator.interpreter;

import org.okbqa.templator.interpreter.grammar.DependenciesParser;
import org.okbqa.templator.interpreter.grammar.DependenciesLexer;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import org.okbqa.templator.graph.Graph;

/**
 *
 * @author cunger
 */
public class Interpreter {
           
    public Interpreter() {
    }
    
    public Graph interpret(String dependency) {
        
        // if Stanford, then replace "," by ";" (in order to comply with grammar)
        if (dependency.contains("(") && dependency.contains(")") && dependency.contains(",")) {
            dependency = dependency.replaceAll(",",";");
        }
        
        try {
            // Parse dependency string
            
            InputStream stream = new ByteArrayInputStream(dependency.getBytes(StandardCharsets.UTF_8));
            ANTLRInputStream input = new ANTLRInputStream(stream);
            
            DependenciesLexer  lexer  = new DependenciesLexer(input);
            CommonTokenStream  tokens = new CommonTokenStream(lexer);
            DependenciesParser parser = new DependenciesParser(tokens);
            
            ParseTree tree = parser.graph(); 
            
            // Constructor graph from parse tree 
            
            ParseTreeWalker walker = new ParseTreeWalker();
            GraphConstructor constructor = new GraphConstructor();
            walker.walk(constructor,tree);
            
            return(constructor.graph);

        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
        
        return null;
    }
    
}
