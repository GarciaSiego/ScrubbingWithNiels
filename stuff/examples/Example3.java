import com.jaunt.*;
import com.jaunt.component.*;
import java.io.*;

public class Example3{
  public static void main(String[] args){
    try{
      UserAgent userAgent = new UserAgent();                       
                                                                    //open HTML from a String.
      userAgent.openContent("<html><body>WebPage <div>Hobbies:<p>beer<p>skiing</div> Copyright 2013</body></html>");
      Element body = userAgent.doc.findEach("<div class=\"media-body\">>");
      Element div = body.findFirst("<div>");
      Element p = div.findFirst("<p class=\"description\">");
   
      System.out.println("body's child text: " + p.getChildText());//join child text of body element
    }
    catch(JauntException e){
      System.err.println(e);
    }
  }
}