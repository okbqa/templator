package org.okbqa.tripletempeh.processing.parsing;

import edu.stanford.nlp.dcoref.CorefChain;
import edu.stanford.nlp.dcoref.CorefCoreAnnotations.CorefChainAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations.BasicDependenciesAnnotation;
import edu.stanford.nlp.util.CoreMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;


/**
 *
 * @author cunger
 */
public class Stanford implements Parser {
    
    StanfordCoreNLP pipeline;
    
    public Stanford() {
    }
    
    public List<String> getSentences(String text) {

        List<String> sentences = new ArrayList<>();       
        
        Properties props = new Properties();
        props.put("annotators", "tokenize, ssplit, pos, lemma, parse, ner, dcoref");
        pipeline = new StanfordCoreNLP(props);
        
        Annotation document = new Annotation(text);
        pipeline.annotate(document);
        
        List<CoreMap> result = document.get(SentencesAnnotation.class);
        for (CoreMap s : result) {
             sentences.add(s.toString());
        }
        
        return sentences;
    }
    
    public String parse(String sentence) {
        
        String parse = "";
        
        Properties props = new Properties();
        props.put("annotators", "tokenize, ssplit, pos, lemma, parse");
        pipeline = new StanfordCoreNLP(props);
        
        Annotation document = new Annotation(sentence);
        pipeline.annotate(document);
        
        List<CoreMap> result = document.get(SentencesAnnotation.class);
        for (CoreMap s: result) {
            
            SemanticGraph dependencies = s.get(BasicDependenciesAnnotation.class);
            parse += dependencies.toList();
        }
        
        //Map<Integer,CorefChain> graph = document.get(CorefChainAnnotation.class);
        
        return parse.trim();
    }


}