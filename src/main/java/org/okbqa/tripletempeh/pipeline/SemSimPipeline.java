package org.okbqa.tripletempeh.pipeline;

import java.io.IOException;
import org.okbqa.tripletempeh.graph.Graph;
import org.okbqa.tripletempeh.graph.similarity.Comparator;
import org.okbqa.tripletempeh.interpreter.Interpreter;
import org.okbqa.tripletempeh.processing.Processor;
import org.okbqa.tripletempeh.transformer.GraphManipulation;

/**
 *
 * @author cunger
 */
public class SemSimPipeline {
    
    Interpreter       interpreter;
    Processor         processor; 
    GraphManipulation manipulator;
    Comparator        comparator;
    
    boolean verbose;
    
    public SemSimPipeline(String language, boolean b) throws IOException {
    
        processor   = new Processor(language);
        manipulator = new GraphManipulation(language);
        comparator  = new Comparator();
        
        verbose = b;
    }
    
    public double run(String text1, String text2) {
        
        if (verbose) {
            System.out.println("------------INPUT----------------");
            System.out.println("----Text 1:\n" + text1);
            System.out.println("----Text 2:\n" + text2);
        }
        
        Graph g1 = processor.process(text1);
        Graph g2 = processor.process(text2);
 
        if (verbose) {
            System.out.println("------------PARSES---------------");
            System.out.println("\n----Text 1:\n" + g1.toString());
            System.out.println("\n----Text 2:\n" + g2.toString());
        }
        
        manipulator.doSRL(g1);
        manipulator.doSRL(g2);
        g1.prune();
        g2.prune();
        
        if (verbose) {
            System.out.println("------------GRAPHS---------------");
            System.out.println("\n----Text 1:\n" + g1.toString());
            System.out.println("\n----Text 2:\n" + g2.toString());
        }
        
        double similarity = comparator.similarity(g1,g2);
        
        if (verbose) {
            System.out.println("------------SIMILARITY-----------");
            System.out.println(similarity);
        }
        
        return similarity;
    }
    
}
