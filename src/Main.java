import com.jaunt.*;

/**
 * http://jaunt-api.com/jaunt-tutorial.htm
 */
public class Main {

    public static void main(String[] args) throws ResponseException {
//        UserAgent userAgent = new UserAgent();
//        userAgent.visit("https://www.detelefoongids.nl/pharmades-apotheek-hoogerheide/11421611/5-1/");                        //visit a url
//        System.out.println(userAgent.doc.innerHTML());
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
                    // get url link
                    Element href = div.findFirst("<a href=\"");

                }
            }
        }
        catch(JauntException e){         //if an HTTP/connection error occurs, handle JauntException.
            System.err.println(e);
        }
    }
}
