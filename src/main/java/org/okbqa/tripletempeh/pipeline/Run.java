package org.okbqa.tripletempeh.pipeline;

import java.io.IOException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author cunger
 */

public class Run {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
                
//        if (args.length != 1) {
//            System.out.println("Expected exactly one argument...");
//            System.exit(1);
//        }
//                
//        String input = args[0];
        
        // mimicking command line argument
        String input = "{ \"string\": \"Which rivers flow through Daejeon?\", \"language\": \"en\" }";
        
        try {
            JSONParser parser = new JSONParser();
            JSONObject json   = (JSONObject) parser.parse(input);
            
            String string   = (String) json.get("string");
            String language = (String) json.get("language");
            
            Pipeline pipeline = new Pipeline(language);
            
            pipeline.run(string);

        } catch (ParseException ex) {
            System.out.println("Failed to parse input as JSON:\n" + input);
        }        
    }
    
}
