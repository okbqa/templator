package org.okbqa.templator.main;

import org.json.simple.parser.ParseException;
import org.restlet.Component;
import org.restlet.data.Protocol;

public class StartWebService {

  public static void main(String[]args) throws IllegalArgumentException, ParseException, Exception {
      
        Component component = new Component();

        component.getServers().add(Protocol.HTTP,1555);
	component.getDefaultHost().attach("/templategeneration/templator",new RestletApp());  
        component.start();
  }

}