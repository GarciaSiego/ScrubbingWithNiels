import com.jaunt.Element;
import com.jaunt.Elements;
import com.jaunt.JauntException;
import com.jaunt.UserAgent;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        run();
    }

    private static void run(){
        try{
            UserAgent userAgent = new UserAgent();                       //create new userAgent (headless browser).
            for (int i = 1; i < 2; i++) {
                userAgent.visit("https://www.zorgkaartnederland.nl/zorginstelling/pagina" + i + "?sort=naam-asc");
                Elements divs = userAgent.doc.findEach("<div class=\"media-body\">");
                for(Element div : divs){
                    Element p = div.findFirst("<p class=\"description\">");
                    System.out.println("Title:" + p.getChildText());//join child text of body element
                }
            }
        }
        catch(JauntException e){         //if an HTTP/connection error occurs, handle JauntException.
            System.err.println(e);
        }
    }
}
