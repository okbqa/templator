package Tests;


import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import org.json.simple.JSONArray;
import org.okbqa.templator.pipeline.TemplatorPipeline;


/**
 *
 * @author cunger
 */
public class TemplatorTest {
    
    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
                                
        test();
    }
    
    public static void test() throws IOException {
        
        List<String> test_en = Arrays.asList(
                                
            "Which rivers flow through Gunsan?",
            "What is the capital of Korea?",
            "Who is the founder of Google?",
            "List all cities in Korea.",
            "How high is Hallasan?",
            "How many students does KAIST have?" );
            
        List<String> test_ko = Arrays.asList(
             
            "카이스트는 얼마나 많은 학생이 있는가?", // how many students does kaist have? (얼마나 많은 = how many)
            "어떤 강이 군산을 흐르는가?", // which rivers flow through gunsan
            "군산에 흐르는 강은 무엇인가?", // which rivers flow through gunsan          
            "한국의 수도는 무엇인가?", // what is the capital of korea
            "구글의 설립자는 누구인가?", // who is the founder of google 
            "대한민국에 있는 도시를 나열하라", // list cities in korea (있는 ~ be)
            "대한민국의 도시를 나열하라", // list cities in korea
            "한라산은 얼마나 높은가?" // how high is hallasan?
        ); 
                
//          // OKBQA sample questions
//                "What is the name of national high educational organization in the Goryeo Dynasty?",
//                "What is the organization of a specific numbers and more of legislators to discuss main agenda in proceeding in the Parliament?",
//                "What is the international organization representing international petroleum and gas, opened as a council in 1933?",
//                "What is the international environmental organization established by green activists in 1970?",
//                "Who is the Korean first baseball player won World Series in American professional baseball?",
//                "What was a trigger to divide Namin and Bukin in late Joseon Dynasty?",
//                "List the nations which the subordinate bodies of the United Nations is located in.",
//                "What are the main 8 countries in so-called G8, a summit meeting of main 8 countries?",
//                "What is the name of the organization to improve laborer’s various interests, referring to independent organization of laborers?",
//                "Present national metropolitan cities in order of size from the biggest to the smallest.",
//                "List two countries hosted jointly the 17th FIFA World Cup Game, as the first time in the World Cup history.",
//                "2004 Olympic was held in Athens, Greece. Then, in which city was 2008 Olympic held?",
//                "What is the foundation belonging to the Ministry of Culture, Sports and Tourism, established in May, 1973 to develop national performance art and contribute to cultivation of national sentiment through chorus music?",
//                "List all current Seoul subway operators.",
//                "When was the year which all 51 members of UN attended in regular general meeting at the first time?",
//                "List the subtitle of Harry Potter, the main novel of J.K. Rowling.",
//                "What is the name of government office in the Goryeo and Joseon Dynasty to undertake Astronomy and Geography?",
//                "Who is the person born in Daegu, a founder of Man of Korea and committed suicide by jumping from the Mapo Bridge?"
                
        TemplatorPipeline pipeline = new TemplatorPipeline("ko");
        pipeline.debugMode();
        Scanner scanner = new Scanner(System.in);
        
        for (String question : test_ko) {
            
            JSONArray output = pipeline.run(question);
                        
            System.out.println("\n\nContinue? y/n");
            String response = scanner.nextLine();
            if (response.equals("n")) {
                System.exit(0);
            }
        }
    }
    
}
