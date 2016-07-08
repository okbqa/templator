package org.okbqa.templator.main;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.okbqa.templator.pipeline.TemplatorPipeline;
import org.restlet.representation.Representation;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

/**
 *
 * @author cunger
 */
public class ProcessRequest extends ServerResource {
    
    public TemplatorPipeline pipeline_en;
    public TemplatorPipeline pipeline_ko;
            
    public ProcessRequest() {

        pipeline_en = new TemplatorPipeline("en");
        pipeline_ko = new TemplatorPipeline("ko");
    }

    @Post
    public String process(Representation entity) throws Exception {
          
        JSONArray output = new JSONArray();
        
        try {
            JSONParser json  = new JSONParser();
            JSONObject input = (JSONObject) json.parse(entity.getText());
                    
            String str  = (String) input.get("string");
            String lang = (String) input.get("language");
                             
            switch (lang) {
                case "en": output = pipeline_en.run(str); break;
                case "ko": output = pipeline_ko.run(str); break;
                default: throw new IllegalArgumentException("Unknown language: " + lang + " (currently supported: en, ko)");
            }
                  
            if (output == null) {
                throw new Exception("Oops, something went wrong...");
            }
                  
        } catch (ParseException ex) {
        }
    
        return output.toJSONString();
    }
    
}
