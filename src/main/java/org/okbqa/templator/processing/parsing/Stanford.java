package org.okbqa.templator.processing.parsing;

import edu.stanford.nlp.dcoref.CorefChain;
import edu.stanford.nlp.dcoref.CorefChain.CorefMention;
import edu.stanford.nlp.dcoref.CorefCoreAnnotations.CorefChainAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations.BasicDependenciesAnnotation;
import edu.stanford.nlp.util.CoreMap;
import java.util.ArrayList;
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
        props.put("annotators", "tokenize, ssplit, pos, lemma, parse");
        pipeline = new StanfordCoreNLP(props);
    }

    
    public ParseResult parse(String text) {
        
        ParseResult result = new ParseResult();
        
        List<String> parses    = new ArrayList<>();
        List<String> sentences = new ArrayList<>();
        
//        List<List<String>> corefChains = new ArrayList<>();
        
        Annotation document = new Annotation(text);
        pipeline.annotate(document);
        
        List<CoreMap> annotations = document.get(SentencesAnnotation.class);
        for (CoreMap s: annotations) {
            
            sentences.add(s.toString());
            
            SemanticGraph dependencies = s.get(BasicDependenciesAnnotation.class);
            parses.add(dependencies.toList().trim());
        }
        
//        Map<Integer,CorefChain> chains = document.get(CorefChainAnnotation.class);
//        for (CorefChain c : chains.values()) {
//             List<String> mentions = new ArrayList<>(); 
//             for (CorefMention mention : c.getMentionsInTextualOrder()) {
//                  mentions.add(mention.toString());
//             }
//             corefChains.add(mentions);
//        }
        
        result.setSentences(sentences);
        result.setParses(parses);
//      result.setCorefChains(corefChains);
        
        return result;
    }


}