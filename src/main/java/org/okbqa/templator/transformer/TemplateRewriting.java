package org.okbqa.templator.transformer;

import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.graph.Triple;
import java.util.HashSet;
import java.util.Set;
import org.okbqa.templator.template.Slot;
import org.okbqa.templator.template.SlotType;
import org.okbqa.templator.template.Template;

/**
 *
 * @author cunger
 */
public class TemplateRewriting {
    
    int steps = 0;
    
    public TemplateRewriting(int i) {
        steps = i;
    }
    
    public Set<Template> rewrite(Template template) {
        
        Set<Template> variations = new HashSet<>(); 
        
        int i = 0;

        Set<Template> current = new HashSet<>();
        current.add(template);
        
        while (i < steps) {
            current = rewriteStep(current);
            variations.addAll(current);
            i++;
        }
        
        return variations;
    }
    
    public Set<Template> rewriteStep(Set<Template> templates) {
        
        Set<Template> variations = new HashSet<>();
        
        for (Template template : templates) {
            variations.addAll(inversion(template));
            variations.addAll(join(template));
            variations.addAll(class2Property(template));
            variations.addAll(split(template));
            variations.addAll(replaceCount(template));
        }
        
        return variations;
    }
    
    private Set<Template> inversion(Template template) {
        
        Set<Template> variations = new HashSet<>();
        
        for (Triple t : template.getTriples()) {
            Node prop = t.getPredicate();
            if (prop.isVariable()) {
                for (Slot s : template.getSlots()) {
                    if (("?"+s.getVar()).equals(prop.toString()) && !s.isSortal()) {
                        Template v = template.clone();
                        v.removeTriple(t);
                        v.addTriple(new Triple(t.getObject(),prop,t.getSubject()));
                        for (Slot slot : v.getSlots()) {
                            if (("?"+slot.getVar()).equals(t.getObject().toString())
                             && slot.getType().equals(SlotType.RESOURCEorLITERAL)) {
                                slot.setType(SlotType.RESOURCE);
                            }
                        }
                        v.assemble();
                        variations.add(v);
                    }
                }
            }
        }
        
        return variations;
    }
    
    private Set<Template> join(Template template) {
        
        Set<Template> variations = new HashSet<>();
        
        // TODO 
        // template.adjustScore()
        
        return variations;
    }
    
    private Set<Template> class2Property(Template template) {
        
        Set<Template> variations = new HashSet<>();
        
        // TODO 
        // template.adjustScore()
        
        return variations;
    }
    
    private Set<Template> split(Template template) {
        
        Set<Template> variations = new HashSet<>();
        
        // TODO 
        // template.adjustScore() -- penalize!
        
        return variations;
    }
    
    private Set<Template> replaceCount(Template template) {
        
        Set<Template> variations = new HashSet<>();
        
        // TODO 
        // template.adjustScore()
        
        return variations;
    }
}
