package org.okbqa.templator.transformer;

import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.sparql.core.Var;
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
    
    int expansionDepth;
    int step; 
    
    public TemplateRewriting() {
        expansionDepth = 1;
        step = 0;
    }
    
    public Set<Template> rewrite(Template template) {
        
        Set<Template> variations = new HashSet<>();         
        
        // perform each rewriting operation once
        Set<Template> current = firstStep(template);
        variations.addAll(current);
        
        // repeat operations in nextStep until expansionDepth is reached
        while (step < expansionDepth) {
            variations.addAll(nextStep(current));
        }
        
        return variations;
    }
    
    public Set<Template> firstStep(Template template) {
        
        step++;
        
        Set<Template> variations = new HashSet<>();
        variations.add(template);
        
        Set<Template> variations1 = new HashSet<>();
        for (Template t : variations) {
            variations1.addAll(class2Property(t));
        }
        variations.addAll(variations1);

        Set<Template> variations2 = new HashSet<>();
        for (Template t : variations) {
            variations2.addAll(inversion(t));
        }   
        variations.addAll(variations2);

        Set<Template> variations3 = new HashSet<>();
        for (Template t : variations) {
            variations3.addAll(replaceCount(t));
        }
        variations.addAll(variations3);
        
        Set<Template> variations4 = new HashSet<>();
        for (Template t : variations) {
            variations4.addAll(split(t));
        }
        variations.addAll(variations4);

        variations.remove(template);
        return variations;
    }
        
    public Set<Template> nextStep(Set<Template> templates) {
        
        step++;
        
        Set<Template> variations = new HashSet<>();

        for (Template template : templates) {
               //variations.addAll(join(template)); 
                 variations.addAll(split(template));
        }
        
        return variations;
    }
    
    
    // REWRITING RULES 
    
    private Set<Template> inversion(Template template) {
        // ?s ?p ?o . -> ?o ?p ?s .
        
        Set<Template> variations = new HashSet<>();
        
        for (Triple t : template.getTriples()) {
            Node prop = t.getPredicate();
            for (Slot s : template.getSlots()) {
                if ((s.getVar().equals(prop.getName())) && !s.isSortal()) {
                    Template v = template.clone();
                    v.removeTriple(t);
                    v.addTriple(new Triple(t.getObject(),prop,t.getSubject()));
                    for (Slot slot : v.getSlots()) {
                         if ((slot.getVar()).equals(t.getObject().getName())
                           && slot.getType().equals(SlotType.RESOURCEorLITERAL)) {
                              slot.setType(SlotType.RESOURCE);
                         }
                         if ((slot.getVar()).equals(t.getSubject().getName())
                           && slot.getType().equals(SlotType.RESOURCE)) {
                              slot.setType(SlotType.RESOURCEorLITERAL);
                         }
                    }
                    v.assemble();
                    variations.add(v);
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
        // ?s ?p ?o . -> ?s ?p1 ?x . ?x ?p2 ?o .
        
        Set<Template> variations = new HashSet<>();
        
        for (Triple t : template.getTriples()) {
            Node prop = t.getPredicate();
            if (prop.isVariable()) {
               for (Slot s : template.getSlots()) {
                   if ((s.getVar()).equals(((Var)prop).getVarName()) && !s.isSortal()) {
                        // build new template
                        Template v = template.clone();
                        // remove triple
                        v.removeTriple(t);
                        v.removeSlot(t.getPredicate().getName());
                        // add two new triples
                        Node x = Var.alloc(t.getObject().getName()+"0");
                        Node p1 = Var.alloc(t.getPredicate().getName()+"1");
                        Node p2 = Var.alloc(t.getPredicate().getName()+"2");
                        v.addTriple(new Triple(t.getSubject(),p1,x));
                        v.addTriple(new Triple(x,p2,t.getObject()));
                        v.addSlot(new Slot(p1.getName(),"",SlotType.OBJECTPROPERTY));
                        v.addSlot(new Slot(p2.getName(),"",SlotType.PROPERTY));
                        // assemble and add to variations
                        v.assemble();
                        variations.add(v);
                   }
                }
            }
        }
        
        return variations;
    }
    
    private Set<Template> replaceCount(Template template) {
        
        Set<Template> variations = new HashSet<>();
        
        // TODO 
        // template.adjustScore()
        
        return variations;
    }
}
