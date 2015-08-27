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
    
    boolean restricted = true;
    double  penalizationFactor = 0.8;
    int     expansionDepth = 0;
    
    // private parameter
    int currentDepth; 
    
    public TemplateRewriting() { 
    }
    
    public Set<Template> rewrite(Template template) {
        // repeat all operations until no new variations are added
        // except for split(), which is repeated only as often as expansionDepth

        currentDepth = 0;
        
        Set<Template> variations = new HashSet<>();
        
        Set<Template> current = new HashSet<>();
        current.add(template);
        
        boolean keepgoing = true; 
        while  (keepgoing) { // i.e. as long as there is at least one new template
            current = nextStep(current);
            keepgoing = false;
            for (Template t : current) { 
                if (!variations.contains(t)) {
                     variations.add(t);
                     // keepgoing = true; // TODO
                }
            }
            current.removeAll(variations);
        }
        
        return variations;
    }
    
    public Set<Template> nextStep(Set<Template> templates) {
        
        Set<Template> variations = new HashSet<>();

        for (Template t : templates) {
            
            variations.addAll(replaceCount(t));
            
            if (restricted && t.getTriples().size() == 1) {
                variations.addAll(inversion(t));
            }
            if (!restricted) {
                variations.addAll(class2Property(t));
                variations.addAll(inversion(t));
                variations.addAll(join(t));           
                if (currentDepth < expansionDepth) {
                    variations.addAll(split(t));
                    currentDepth++;
                }
            }
        }
        
        return variations;
    }    
    
    // REWRITING RULES 
    
    private Set<Template> inversion(Template template) {
        // ?s ?p ?o . 
        // -> 
        // ?o ?p ?s .
        
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
                    v.setScore(penalizationFactor * v.getScore());
                    variations.add(v);
                    break;
                }
            }
        }
        
        return variations;
    }
    
    private Set<Template> join(Template template) {
        // ?s ?p1 ?x . ?x ?p2 ?o . 
        // -> 
        // ?s ?p ?o . with ?p.form=?p1.form+?p2.form
        
        // TODO take care to not re-join triples that result from splitting
        
        Set<Template> variations = new HashSet<>();
        
        // TODO 
        
        return variations;
    }
    
    private Set<Template> class2Property(Template template) {
        // ?s rdf:type ?c . ?s ?p ?o . 
        // ->
        // ?s ?c ?o .
        
        Set<Template> variations = new HashSet<>();
        
        // TODO 
        
        return variations;
    }
    
    private Set<Template> split(Template template) {
        // ?s ?p ?o . 
        // -> 
        // ?s ?p1 ?x . ?x ?p2 ?o .
        
        // TODO what to do about slot.form of ?p 
        
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
                        v.setScore(2 * penalizationFactor * v.getScore());
                        variations.add(v);
                   }
                }
            }
        }
        
        return variations;
    }
    
    private Set<Template> replaceCount(Template template) {
        // SELECT COUNT(?o) WHERE ?s ?p ?o .
        // -> 
        // SELECT ?o WHERE ?s ?p ?o . with ?o.type=DATAPROPERTY
        
        Set<Template> variations = new HashSet<>();
        
        for (String var : template.getCountvars()) {
            Template v = template.clone();
            v.getCountvars().remove(var);
            v.getProjvars().add(var);
            String classForm = null;
            for (Triple t : v.getTriples()) {
                if (t.getSubject().getName().equals(var)) {
                    String classVar = null;
                    for (Slot s : v.getSlots()) {
                        if (s.getVar().equals(t.getPredicate().getName()) 
                         && s.getType().equals(SlotType.SORTAL)) {
                            classVar = t.getObject().getName();
                            break;
                        }
                    }
                    if (classVar != null) {
                        for (Slot s : v.getSlots()) {
                            if (s.getVar().equals(classVar)) {
                                classForm = s.getForm();
                                break;
                            }
                        }
                    }
                }
            }
            for (Triple t : v.getTriples()) {
                 if (t.getObject().getName().equals(var)) {
                     for (Slot s : v.getSlots()) {
                          if (s.getVar().equals(t.getPredicate().getName())) {
                              s.setType(SlotType.DATAPROPERTY);
                              if (classForm != null) {
                                  s.setForm(classForm);
                              }
                          }
                     }
                 }
            }
            v.assemble();
            v.setScore(penalizationFactor * v.getScore());
            variations.add(v);
        }
        
        return variations;
    }
}
