package org.okbqa.tripletempeh.main;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


@Path("tripletempeh")
public class Service {
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String serve(String data) {
        
        try {
            JSONArray  output = null;
            
            JSONParser json  = new JSONParser();
            JSONObject input = (JSONObject) json.parse(data);
            
            String text = (String) input.get("string");
            String lang = (String) input.get("language");
            
            switch (lang) {
                case "en": output = Main.pipeline_en.run(text); break;
                case "ko": output = Main.pipeline_ko.run(text); break;
            }
            
            if (output == null) {
                throw new IllegalArgumentException("Unknown language: " + lang + " (currently supported: en, ko)");
            }
            return output.toJSONString();
        }
        catch (Exception e) {
            e.printStackTrace(System.out);
            return "null";
        }
    }
}
