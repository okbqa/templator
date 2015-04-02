package org.okbqa.tripletempeh.processing.parsing;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author cunger
 */
public class ParseResult {

    List<String> sentences;
    List<String> parses;
    List<List<String>> corefChains;
    
    public ParseResult() {
        parses      = new ArrayList<>();
        sentences   = new ArrayList<>();
        corefChains = new ArrayList<>(); 
    }

    public void setSentences(List<String> ls) {
        sentences = ls;
    }
    public void setParses(List<String> ls) {
        parses = ls;
    }
    public void setCorefChains(List<List<String>> ls) {
        corefChains = ls;
    }
    
    public List<String> getSentences() {
        return sentences;
    }
    public List<String> getParses() {
        return parses;
    }
    public List<List<String>> getCorefChains() {
        return corefChains;
    }
    
}
