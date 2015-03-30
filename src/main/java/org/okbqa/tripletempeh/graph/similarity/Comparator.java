package org.okbqa.tripletempeh.graph.similarity;

import java.util.HashMap;
import java.util.Map;
import java.io.File;
import java.io.IOException;
import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.models.word2vec.Word2Vec;
import org.okbqa.tripletempeh.graph.Edge;
import org.okbqa.tripletempeh.graph.Graph;
import org.okbqa.tripletempeh.graph.Node;

/**
 *
 * @author cunger
 */
public class Comparator {
    
    Word2Vec word2vec;
    String googlebin = new File("GoogleNews-vectors-negative300.bin").getAbsolutePath();
    
    public Comparator() throws IOException { 
        
        word2vec = WordVectorSerializer.loadGoogleModel(googlebin,true);
    }
    
    public double similarity(Graph g1, Graph g2) {
        
        Map<Integer,Integer> nodeMatch = new HashMap<>();
        
        double avgNodeSim = 0.0;
        double avgEdgeSim = 0.0;
               
        Map<Integer,Map<Integer,Double>> nodeSims = new HashMap<>();
        double maxSim = 0.0;

        for (Node n1 : g1.getNodes()) {
            for (Node n2 : g2.getNodes()) {
                double sim = word2vec.similarity(n1.getForm(),n2.getForm()); 
                nodeSims.put(n1.getId(),new HashMap<Integer,Double>() {{ put(n2.getId(),sim); }});
                if (sim > maxSim) {
                    maxSim = sim;
                    nodeMatch.put(n1.getId(),n2.getId());
                }
            }
            avgNodeSim += maxSim;
            maxSim = 0.0;
        }
        avgNodeSim = avgNodeSim / g2.getNodes().size();
        
        int strictEdgeMatches = 0;
        int lazyEdgeMatches   = 0;
                
        for (Edge e : g1.getEdges()) {
             Edge matchingEdge = g2.findEdgeBetween(nodeMatch.get(e.getHead()),nodeMatch.get(e.getDependent()));
             if  (matchingEdge != null) {
                  if (e.getLabel().equals(matchingEdge.getLabel())) {
                      strictEdgeMatches++;
                  } else {
                      lazyEdgeMatches++;
                  }
            }
        }
        double weight = 0.1;
        avgEdgeSim = strictEdgeMatches / g1.getEdges().size() + weight * (lazyEdgeMatches / g1.getEdges().size());
        
        return (avgNodeSim + avgEdgeSim) / 2 ;
    }
    
}
