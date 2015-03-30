package org.okbqa.tripletempeh.pipeline;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;

/**
 * Main class.
 *
 */
public class Main {

    public static final String host = "http://110.45.246.131:1555/"; // OKBQA server
    //public static final String host = "http://localhost:8080/"; 
    // Base URI the Grizzly HTTP server will listen on
    public static final URI BASE_URI = URI.create(host+"templategeneration/");
    
    // Init template generation pipeline (one for each language)
    public static final Pipeline pipeline_en = new Pipeline("en",false,true);
    public static final Pipeline pipeline_ko = new Pipeline("ko",false,true);
    
    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
     * @return Grizzly HTTP server.
     */
    public static HttpServer startServer() {
        // create a resource config that scans for JAX-RS resources and providers
        // in org.okbqa.tripletempeh.pipeline
        final ResourceConfig rconf = new ResourceConfig().packages("org.okbqa.tripletempeh.pipeline");

        // create and start a new instance of grizzly http server
        // exposing the Jersey application at BASE_URI
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI,rconf);
    }

    /**
     * Main method.
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        final HttpServer server = startServer();
        System.out.println(String.format("Jersey app started with WADL available at "
                + "%sapplication.wadl\nHit enter to stop it...", BASE_URI));
        System.in.read();
        server.stop();
    }
}

