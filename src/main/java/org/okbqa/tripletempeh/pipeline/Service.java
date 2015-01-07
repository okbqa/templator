package org.okbqa.tripletempeh.pipeline;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.json.simple.JSONArray;


/**
 * Root resource (exposed at "tripletempeh" path)
 */
@Path("tripletempeh")
public class Service {
    
    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @param data
     * @return String that will be returned as a text/plain response.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String serve(@QueryParam("data") String data) {
        
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
