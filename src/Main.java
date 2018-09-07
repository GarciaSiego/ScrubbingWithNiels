import com.jaunt.*;

/**
 * http://jaunt-api.com/jaunt-tutorial.htm
 */
public class Main {

    public static void main(String[] args) throws ResponseException {
        try {
            UserAgent userAgent = new UserAgent();                       //create new userAgent (headless browser).
            for (int i = 1; i < 2; i++) {
                userAgent.visit("https://www.zorgkaartnederland.nl/zorginstelling/pagina" + i + "?sort=naam-asc");
                Elements divs = userAgent.doc.findEach("<div class=\"media-body\">");
                for (Element div : divs) {
                    Element p = div.findFirst("<p class=\"description\">");
                    System.out.println(p.getChildText());
                    // get url link
                    Element h = div.getFirst("<h4 class=\"media-heading title orange\">");
                    Element href = h.getFirst("<a href");
                    String url = href.toString();
                    url = url.substring(9,url.length() -2);
                    Element company = h.getFirst("<a href=\"" + url + "\">");
                    System.out.println(url);
                    System.out.println(company.getChildText());

                }
            }
        } catch (JauntException e) {         //if an HTTP/connection error occurs, handle JauntException.
            System.err.println(e);
        }
    }
}
