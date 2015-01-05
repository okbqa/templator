package org.okbqa.tripletempeh.transformer;

import org.okbqa.tripletempeh.graph.Graph;

/**
 *
 * @author cunger
 */
public class Rule {

    Graph  target;    
    String todo;
    
    public Rule(Graph g,String s) {
        target = g;
        todo   = s;
    }
}
