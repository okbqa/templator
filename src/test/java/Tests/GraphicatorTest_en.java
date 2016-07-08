package Tests;


import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import org.json.simple.JSONArray;
import org.okbqa.templator.graph.Graph;
import org.okbqa.templator.pipeline.GraphicatorPipeline;
import org.okbqa.templator.pipeline.TemplatorPipeline;


/**
 *
 * @author cunger
 */
public class GraphicatorTest_en {
    
    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
                                
        test();
    }
    
    public static void test() throws IOException {
        
        List<String> test = Arrays.asList(
                            
            "What color is the bus?",
            "Is there a shadow?",
            "Is this a grocery store?",
            "Where is the dog?",
            "What is the capital of Korea?",
            "Which rivers flow through Gunsan?",
            "Who is the founder of Google?",
            "List all cities in Korea.",
            "How high is Hallasan?",
            "How many students does KAIST have?" );

        GraphicatorPipeline pipeline = new GraphicatorPipeline("en");
        pipeline.debugMode();
        Scanner scanner = new Scanner(System.in);
        
        for (String question : test) {
            
            Graph output = pipeline.run(question);
                        
            System.out.println("\n\nContinue? y/n");
            String response = scanner.nextLine();
            if (response.equals("n")) {
                System.exit(0);
            }
        }
    }
    
}
