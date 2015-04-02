
import java.io.IOException;
import org.okbqa.tripletempeh.pipeline.SemSimPipeline;


/**
 *
 * @author cunger
 */
public class SemSimTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        
        test();
    }
    
    public static void test() throws IOException {
        
        String text1 = "John has a nice black cat. It often sleeps in his lap.";
        String text2 = "John's black cat sleeps a lot.";
        
        SemSimPipeline pipeline = new SemSimPipeline("en",true);
        
        double sim = pipeline.run(text1,text2);
        
        System.out.println("Similarity: " + sim);
    }
    
}
