package Evaluations;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.DocumentBuilderFactory;
import org.okbqa.tripletempeh.pipeline.SemSimPipeline;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author cunger
 */
public class EntranceExamsEval {

    static String train = "EntranceExams/clef2014-qatrack-exam-reading-trial-en.xml"; 
    static String test  = "EntranceExams/clef2014-qatrack-exam-reading-testlabel-en.xml";


    public static void main(String[] args) {
                     
        try { 

            SemSimPipeline pipeline = new SemSimPipeline("en",true);
            
            // Read XML
            String   xml   = Thread.currentThread().getContextClassLoader().getResource(train).getPath();
            Document doc   = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(xml);
            Element  root  = doc.getDocumentElement();
            Element  topic = (Element) root.getElementsByTagName("topic").item(0); 
            NodeList nodes = topic.getElementsByTagName("reading-test");
            
            for (int i = 0; i < nodes.getLength(); i++) {

                Element node = (Element) nodes.item(i);
                
                // Text
                Element textnode = (Element) node.getElementsByTagName("doc").item(0);
                String  text     = textnode.getTextContent();
                                
                // Questions
                NodeList questionnodes = root.getElementsByTagName("question");
                for (int j = 0; j < questionnodes.getLength(); j++) {
                    
                     Element questionnode = (Element) questionnodes.item(j);
                     String  question     = questionnode.getElementsByTagName("q_str").item(0).getTextContent();

                     // Answer scores
                     Map<String,Double> scores = new HashMap<>();
                     
                     // Answers
                     NodeList answernodes = questionnode.getElementsByTagName("answer");
                     for (int k = 0; k < answernodes.getLength(); k++) {
                         
                         Element answernode = (Element) answernodes.item(k);
                         
                         String id     = answernode.getAttribute("a_id");
                         String answer = answernode.getTextContent();

                         System.out.println("Text:\n" + text); // DEBUG
                         System.out.println("Question:\n" + question); // DEBUG
                         System.out.println("Answer:\n" + answer); // DEBUG
                         
                         double score  = pipeline.run(text,question + " " + answer);

                         scores.put(id,score);
                         
                         System.out.println(id + " >>>> " + score); // DEBUG
                     }                        
                }
            }

        }
        catch (Exception e) {
	       e.printStackTrace();
        }        
    }
    
}
