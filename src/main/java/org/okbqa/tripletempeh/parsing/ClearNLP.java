package org.okbqa.tripletempeh.parsing;

//import com.clearnlp.component.AbstractComponent;
//import com.clearnlp.dependency.DEPTree;
//import com.clearnlp.nlp.NLPGetter;
//import com.clearnlp.nlp.NLPMode;
//import com.clearnlp.reader.AbstractReader;
//import com.clearnlp.tokenization.AbstractTokenizer;
//import java.io.IOException;

/**
 * 
 * @author cunger
 */

public class ClearNLP implements Parser {
    
//    public static final String DEFAULT_language = AbstractReader.LANG_EN;
//    public static final String DEFAULT_model = "general-en";
//    
//    AbstractTokenizer tokenizer;
//    AbstractComponent tagger;
//    AbstractComponent parser;
//    AbstractComponent identifier;
//    AbstractComponent classifier;
//    AbstractComponent labeler;
//    
//    //private AbstractComponent[] components;
//   
//    
//    public ClearNLP() throws IOException {
//        this(DEFAULT_language, DEFAULT_model);
//    }
//    
//    public ClearNLP(String language, String model) throws IOException {
//    
//        tokenizer  = NLPGetter.getTokenizer(language);
//	tagger     = NLPGetter.getComponent(model, language, NLPMode.MODE_POS);
//	parser     = NLPGetter.getComponent(model, language, NLPMode.MODE_DEP);
//        identifier = NLPGetter.getComponent(model, language, NLPMode.MODE_PRED);
//	classifier = NLPGetter.getComponent(model, language, NLPMode.MODE_ROLE);
//        labeler    = NLPGetter.getComponent(model, language, NLPMode.MODE_SRL);
//
//    }
//    
//    
//    public String parse(String phrase) {
//        
//        AbstractComponent[] components = {tagger, parser, identifier, classifier, labeler};
//              
//        DEPTree tree = NLPGetter.toDEPTree(tokenizer.getTokens(phrase));
//        	
//	for (AbstractComponent component : components) {
//	     component.process(tree);
//        }
//        
//        return tree.toStringSRL();
//    }
    
    public ClearNLP() {
    }
    
    public String parse (String text) {
        throw new UnsupportedOperationException("The ClearNLP class is deprecated. Please use Stanford instead.");
    }
    
}
