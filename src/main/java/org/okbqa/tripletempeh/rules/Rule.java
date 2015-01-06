package org.okbqa.tripletempeh.rules;

import org.okbqa.tripletempeh.graph.Graph;

/**
 *
 * @author cunger
 */
public class Rule {

    Graph  target;    
    String todo;
    String todoType;
    
    public Rule(Graph g,String s1,String s2) {
        target   = g;
        todo     = s1;
        todoType = s2;
    }
    
    public Graph getTarget() {
        return target;
    }
    
    public String getTodo() {
        return todo;
    }
    
    public String getTodoType() {
        return todoType;
    }
}
