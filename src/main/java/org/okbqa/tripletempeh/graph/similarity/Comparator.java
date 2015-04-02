package org.okbqa.tripletempeh.graph.similarity;

import java.util.HashMap;
import java.util.Map;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
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
    
    //Word2Vec word2vec;
    //String googlebin = new File("src/main/resources/models/GoogleNews-vectors-negative300.bin").getAbsolutePath();
    
    public Comparator() throws IOException { 
                
        //word2vec = WordVectorSerializer.loadGoogleModel(googlebin,true);
    }
    
    public double similarity(Graph g1, Graph g2) {
        
        Map<Integer,Integer> nodeMatch = new HashMap<>();
        
        double avgNodeSim = 0.0;
        double avgEdgeSim = 0.0;
               
        Map<Integer,Map<Integer,Double>> nodeSims = new HashMap<>();
        double maxSim = 0.0;

        for (Node n1 : g1.getNodes()) {
            for (Node n2 : g2.getNodes()) {
                // init nodeMatch
                if (!nodeMatch.containsKey(n1.getId())) {
                     nodeMatch.put(n1.getId(),n2.getId());
                }
                // node similarity
                //double sim = word2vec.similarity(n1.getForm(),n2.getForm()); 
                double sim = easyESA(n1.getForm(),n2.getForm());
                nodeSims.put(n1.getId(),new HashMap<Integer,Double>() {{ put(n2.getId(),sim); }});
                // update nodeMatch
                if (sim >= maxSim) {
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
    
    private double easyESA(String s1, String s2) {
                
        String url = "http://vmdeb20.deri.ie:8890/esaservice?task=esa&term1=" + s1 + "&term2=" + s2; 
        
        try {
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet(url);
            request.addHeader("Accept","text/plain");
            HttpResponse response = client.execute(request);
            String sim = EntityUtils.toString(response.getEntity(),HTTP.UTF_8);
                                    
            double simval = (double) Float.parseFloat(sim.replaceAll("\"",""));
            if (simval * 100 < 1) {
                return simval * 100;
            } else {
                return simval;
            }
        
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return 0.0;
        }
    }
    
}
