import com.jaunt.*;
import com.jaunt.component.*;
import java.io.*;

public class Example1{
  public static void main(String[] args){
    try{
      UserAgent userAgent = new UserAgent();                       //create new userAgent (headless browser).
      userAgent.visit("https://www.detelefoongids.nl/pharmades-apotheek-hoogerheide/11421611/5-1/");                        //visit a url
      System.out.println(userAgent.doc.innerHTML());               //print the content as HTML
    }
    catch(JauntException e){         //if an HTTP/connection error occurs, handle JauntException.
      System.err.println(e);
    }
  }
}