import com.jaunt.*;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * http://jaunt-api.com/jaunt-tutorial.htm
 */
public class Main {

    public static void main(String[] args) throws ResponseException, IOException {
        try {
            UserAgent userAgent = new UserAgent();                       //create new userAgent (headless browser).
            PrintWriter writer = new PrintWriter("output.csv", "UTF-8");

            for (int i = 1; i < 2; i++) {
                userAgent.visit("https://www.zorgkaartnederland.nl/zorginstelling/pagina" + i + "?sort=naam-asc");
                Elements divs = userAgent.doc.findEach("<div class=\"media-body\">");
                for (Element div : divs) {
                    Element p = div.findFirst("<p class=\"description\">");
                    System.out.println(p.getChildText()); // Kind of company
                    // get url link
                    Element h = div.getFirst("<h4 class=\"media-heading title orange\">");
                    Element href = h.getFirst("<a href");
                    String url = href.toString();
                    url = url.substring(9, url.length() - 2);
                    Element company = h.getFirst("<a href=\"" + url + "\">");
                    System.out.println(company.getChildText()); // Company name
                    // Adress url info
                    UserAgent userAgent2 = new UserAgent();
                    userAgent2.visit(url);
                    Element div2 = userAgent2.doc.findEach("<div class=\"address_row\">");
                    Element div3 = div2.findFirst("<span class=\"address_content\">");
                    System.out.println(div3.getChildText().trim()); //STREET NAME

                    Element span = div2.getElement(1);
                    Element span2 = span.getFirst("<span>");
                    Elements spans = span2.getEach("<span>");
                    System.out.println(spans.getElement(0).getChildText()); // postcode
                    System.out.println(spans.getElement(1).getChildText()); // dorp
                    userAgent2.close();

//                    writer.println(p.getChildText() + "," + company.getChildText() + "," + url);
                }
            }

            writer.close();

        } catch (JauntException e) {         //if an HTTP/connection error occurs, handle JauntException.
            System.err.println(e);
        }
    }
}
