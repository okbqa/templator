package org.okbqa.tripletempeh.pipeline;

import java.io.IOException;

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
        
        Pipeline pipeline = new Pipeline();
        pipeline.run("Which cities in South Korea have more than two universities?");
        
    }
    
}
