package org.okbqa.templator.transformer.rules;

import java.util.List;
import org.okbqa.templator.graph.Graph;

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
    
    public String toString() {
        return target.toCompressedString() + "\n >>>> " + todos.toString();
    }
}
