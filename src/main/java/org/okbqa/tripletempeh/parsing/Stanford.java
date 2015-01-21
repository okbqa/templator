package org.okbqa.tripletempeh.parsing;

import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations.BasicDependenciesAnnotation;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations.CollapsedCCProcessedDependenciesAnnotation;
import edu.stanford.nlp.util.CoreMap;
import java.util.List;
import java.util.Properties;


/**
 *
 * @author cunger
 */
public class Stanford implements Parser {
    
    StanfordCoreNLP pipeline;
    
    public Stanford() {
        Properties props = new Properties();
        props.put("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");
        pipeline = new StanfordCoreNLP(props);
    }
    
    public String parse(String text) {
        
        String parse = "";
        
        Annotation document = new Annotation(text);
        pipeline.annotate(document);
        
        List<CoreMap> sentences = document.get(SentencesAnnotation.class);
        for (CoreMap sentence: sentences) {
            
            SemanticGraph dependencies = sentence.get(BasicDependenciesAnnotation.class);
            parse += "\n" + dependencies.toList();
        }
        
        return parse.trim();
    }


}