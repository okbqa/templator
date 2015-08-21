package org.okbqa.templator.processing.parsing;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author cunger
 */
public class ETRI implements Parser {
    
    String url;
    String boundaries;
    
    public ETRI() {
        
        url = "http://110.45.246.131:8080/myapp/service/post";
        boundaries = "\\.|\\?";
    }

    
    public ParseResult parse(String text) {
        
        ParseResult result = new ParseResult();
        
        List<String> sentences = new ArrayList<>();
        List<String> parses    = new ArrayList<>();
        
        // TODO Do something more sophisticated here!
        for (String sentence : text.split(boundaries)) {
            
            sentences.add(sentence);
            
            String parse;
            try {
                parse = request(url,sentence);
                parses.add(parse);
            }
            catch (Exception e) {
            }
        }
              
        result.setSentences(sentences);
        result.setParses(parses);
        
        return result;
    }
    
    public String request(String url, String text) throws UnsupportedEncodingException, IOException, Exception {
 
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);
        post.addHeader("Accept","text/plain");
        HttpEntity entity = new ByteArrayEntity(text.getBytes("UTF-8"));
        post.setEntity(entity);
        HttpResponse response = client.execute(post);
            
        if (response.getStatusLine().getStatusCode() == 200) {
            return EntityUtils.toString(response.getEntity(),HTTP.UTF_8).trim();
        }
        else {
            throw new Exception("Status code returned by ETRI: " + response.getStatusLine().getStatusCode());
        }
    }
    
}
