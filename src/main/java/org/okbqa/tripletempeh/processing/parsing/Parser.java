package org.okbqa.tripletempeh.processing.parsing;

import java.util.List;

/**
 *
 * @author cunger
 */

public interface Parser {
    
    List<String> getSentences(String text);
    String parse(String sentence);
            
}
