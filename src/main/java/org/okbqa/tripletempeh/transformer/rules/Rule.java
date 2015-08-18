package org.okbqa.tripletempeh.transformer.rules;

import java.util.List;
import org.okbqa.tripletempeh.graph.Graph;

/**
 *
 * @author cunger
 */
public class Rule {

    Graph target;    
    List<String> todos;
    String todoType;
    
    public Rule(Graph g,List<String> ts,String td) {
        target   = g;
        todos    = ts;
        todoType = td;
    }
    
    public Graph getTarget() {
        return target;
    }
    
    public List<String> getTodos() {
        return todos;
    }
    
    public String getTodoType() {
        return todoType;
    }

}
