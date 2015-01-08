package org.okbqa.tripletempeh.pipeline;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.json.simple.JSONArray;


@Path("templategeneration")
public class Service {
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String serve(String data) {
        
        try {
            JSONArray output  = Main.pipeline.run(data);
            return output.toJSONString();
        }
        catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return "null";
    }
}
