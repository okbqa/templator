package Tests;


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
        
        SemSimPipeline pipeline = new SemSimPipeline("en",true);
        
        // Stanford test examples
        String stan01 = "This is not good and not bad";
        String stan02 = "'Oh no!' she said";
        String stan03 = "The cookie was eaten by the monster";
        
        System.out.println("stan: " + pipeline.run(stan01,stan02));
        System.out.println("stan: " + pipeline.run(stan01,stan03));
        
//        // ClausIE patterns
//        String text01 = "Albert Einstein died.";
//        String text02 = "Albert Einstein remained in Princeton.";
//        String text03 = "Albert Einstein is smart.";
//        String text04 = "Albert Einstein has won the Nobel Prize.";
//        String text05 = "RSAS gave Albert Einstein the Nobel Prize."; 
//        String text06 = "The doorman showed Albert Einstein to his office"; 
//        String text07 = "Albert Einstein declared the meeting open.";
//        String text08 = "Albert Einstein died in Princton in 1955.";
//        String text09 = "Albert Einstein remained in Princeton until his death.";
//        String text10 = "Albert Einstein is a scientist of the 20th century.";
//        String text11 = "In 1921, Albert Einstein was awarded the Novel Prize in Sweden.";
//               
//        System.out.println("01-08: " + pipeline.run(text01,text08));
//        System.out.println("04-05: " + pipeline.run(text04,text05));
//        System.out.println("04-11: " + pipeline.run(text04,text11));
//        System.out.println("06-07: " + pipeline.run(text06,text07));
//        System.out.println("03-10: " + pipeline.run(text03,text10));
//        System.out.println("08-09: " + pipeline.run(text08,text09));
//        System.out.println("02-09: " + pipeline.run(text02,text09));
    }
    
}
