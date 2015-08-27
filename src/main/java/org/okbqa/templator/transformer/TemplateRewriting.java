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
    
    int step = 0;
        
    public TemplateRewriting() {
    }
    
    public Set<Template> rewrite(Template template) {
        
        double threshold = template.getScore() / 2.0;
        
        Set<Template> variations = new HashSet<>();         
        Set<Template> current = new HashSet<>();
        current.add(template);
        
        int new_variations = 1; // to get while loop started
        
        while (new_variations >= 1) { // i.e. as long as there is at least one new template (with score not below threshold)
            int old_variations = variations.size();          
            current = rewriteStep(current);
            for (Template t : current) {
                 if (!variations.contains(t) && t.getScore() >= threshold) {
                     System.out.println(t.toString());
                     System.out.println(">>>>> "+t.getScore()+">"+threshold);
                     variations.add(t);
                 }
            }
            new_variations = variations.size() - old_variations;
            System.out.println("------" + new_variations);
        }
        
        return variations;
    }
    
    public Set<Template> rewriteStep(Set<Template> templates) {
        
        step++;
        
        Set<Template> variations = new HashSet<>();
        
        for (Template template : templates) {
            if (step == 1) {
                variations.addAll(inversion(template));
                variations.addAll(class2Property(template));
                variations.addAll(replaceCount(template));
            } else {
                //variations.addAll(join(template)); 
                variations.addAll(split(template));
            }
        }
        
        return variations;
    }
    
    private Set<Template> inversion(Template template) {
        // ?s ?p ?o . -> ?o ?p ?s .
        // score = score
        
        Set<Template> variations = new HashSet<>();
        
        for (Triple t : template.getTriples()) {
            Node prop = t.getPredicate();
            if (prop.isVariable()) {
                for (Slot s : template.getSlots()) {
                    if ((s.getVar()).equals(((Var)prop).getVarName()) && !s.isSortal()) {
                        Template v = template.clone();
                        v.removeTriple(t);
                        v.addTriple(new Triple(t.getObject(),prop,t.getSubject()));
                        for (Slot slot : v.getSlots()) {
                            if (("?"+slot.getVar()).equals(t.getObject().toString())
                             && slot.getType().equals(SlotType.RESOURCEorLITERAL)) {
                                slot.setType(SlotType.RESOURCE);
                            }
                            if (("?"+slot.getVar()).equals(t.getSubject().toString())
                             && slot.getType().equals(SlotType.RESOURCE)) {
                                slot.setType(SlotType.RESOURCEorLITERAL);
                            }
                        }
                        v.assemble();
                        v.setScore(template.getScore()); 
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
        // ?s ?p ?o . -> ?s ?p1 ?x . ?x ?p2 ?o .
        // score = score/2
        
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
                        v.addSlot(new Slot(x.getName(),"",SlotType.RESOURCE));
                        v.addSlot(new Slot(p1.getName(),"",SlotType.OBJECTPROPERTY));
                        v.addSlot(new Slot(p2.getName(),"",SlotType.PROPERTY));
                        // assemble, ajust score and add to variations
                        v.assemble();
                        v.setScore(template.getScore()/2.0); 
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
