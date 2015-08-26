package org.okbqa.templator.processing.parsing;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author cunger
 */
public class ETRI implements Parser {
    
    String ip;
    String url;
    String boundaries;
    
    public ETRI() {

        ip = "143.248.135.60";
        url = "http://143.248.135.187:10117/controller/service/etri_parser";
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
                //parse = request(url,sentence);
                parse = socket(sentence);
                parses.add(convertToStanfordFormat(parse));
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
        HttpPost post = new HttpPost("http://143.248.135.187:10117/controller/service/etri_parser");
                
        post.addHeader("Accept","application/x-www-form-urlencoded; charset=utf-8");
        post.addHeader("content-type","text/plain; charset=utf-8");
        HttpEntity entity = new ByteArrayEntity(text.getBytes("UTF-8"));
        post.setEntity(entity);

        // System.out.println("URI: " + post.getURI());
        
        HttpResponse response = client.execute(post);

        // System.out.println("ETRI response:\n" + response.getStatusLine());
            
        if (response.getStatusLine().getStatusCode() == 200) {
            return EntityUtils.toString(response.getEntity(),HTTP.UTF_8).trim();
        }
        else {
            throw new Exception("Status code returned by ETRI: " + response.getStatusLine().getStatusCode());
        }
    }
    
    public String socket(String text) throws Exception {
        
        StringBuffer sb = new StringBuffer();

        InetAddress ia = null;

        try {
            ia = InetAddress.getByName(ip);
            Socket soc = new Socket(ia,10117);

            OutputStream os = soc.getOutputStream();
            BufferedOutputStream bos = new BufferedOutputStream(os);

            bos.write((text).getBytes()); 
            bos.flush();
            soc.shutdownOutput();

            InputStream is = soc.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);

            String line = null;
            while (true) {
                line = br.readLine();
                if (line == null) break;
                line = line.trim();
                if (line.equals("")) continue;

                sb.append(line);
                sb.append("\n");
            }

            bos.close();
            br.close();

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        
        // System.out.println(sb.toString());
        
        return sb.toString();
    }
    
    public String convertToStanfordFormat(String etriResponse) {

        String stanford = "";
        
        JSONParser parser = new JSONParser();

        try {
            JSONObject json = (JSONObject) parser.parse(etriResponse);

            JSONArray sentences = (JSONArray) json.get("sentence");

            for (Object sentence : sentences) {
                JSONObject s = (JSONObject) sentence;
                
                // Words 
                Map<String,String> wordIndex = new HashMap<>();
                
                JSONArray words = (JSONArray) s.get("word");
                
                for (Object word : words) {
                    JSONObject w = (JSONObject) word;                   
                    String id = w.get("id").toString();
                    String text = (String) w.get("text");                   
                    wordIndex.put(id,text);
                }
                
                // Dependency relations 
                
                JSONArray dependencies = (JSONArray) s.get("dependency");
                
                for (Object dependency : dependencies) {
                    JSONObject d = (JSONObject) dependency;
                    
                    String dpnd  = d.get("id").toString();
                    String head  = d.get("head").toString();
                    String label = (String) d.get("label");
                    if (label.contains("_")) {
                        label = label.split("_")[1];
                    }
                    label = label.replace("-","");
                    
                    if (head.equals("-1")) continue;
                    
                    stanford += "\n" + label + "(" + wordIndex.get(head) + "-" + head + "," 
                                                   + wordIndex.get(dpnd) + "-" + dpnd + ")";
                }
                
                // SRLs
                
                JSONArray srls = (JSONArray) s.get("SRL");
                
                for (Object srl : srls) {
                    JSONObject r = (JSONObject) srl;
                    
                    String head = r.get("word_id").toString();
                    
                    JSONArray arguments = (JSONArray) r.get("argument");
                    
                    for (Object argument : arguments) {
                        JSONObject a = (JSONObject) argument;
                        
                        String role = (String) a.get("type");
                        role = role.replace("-","");

                        String dpnd = a.get("word_id").toString();
                        
                        stanford += "\n" + role + "(" + wordIndex.get(head) + "-" + head + ","
                                                      + wordIndex.get(dpnd) + "-" + dpnd + ")";
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        
        // System.out.println(stanford);
        
        return stanford.trim();
    }
}
