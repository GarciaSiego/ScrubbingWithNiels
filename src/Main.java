import com.jaunt.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.Semaphore;

/**
 * http://jaunt-api.com/jaunt-tutorial.htm
 */
public class Main {

    private static PrintWriter writer;

    private static Semaphore semaphore;

    public static void main(String[] args) {
        semaphore = new Semaphore(1);

//        long curr = System.currentTimeMillis() / 1000;

        try {

            writer = new PrintWriter("output.csv", "UTF-8");

            for (int i = 1; i < 2399; i++) {
                new Thread(() -> {
                    try {
                        UserAgent userAgent = new UserAgent(); //create new userAgent (headless browser).

                        boolean success = false;
//                System.out.println((System.currentTimeMillis() / 1000 - curr));
//                curr = System.currentTimeMillis() / 1000;

                        while (!success) {
                            try {
                                userAgent.visit("https://www.zorgkaartnederland.nl/zorginstelling/pagina" + i + "?sort=naam-asc");
                                success = true;
                            } catch (JauntException e) {         //if an HTTP/connection error occurs, handle JauntException.
                                System.err.println(e.getMessage());
                            }
                        }

                        userAgent.visit("https://www.zorgkaartnederland.nl/zorginstelling/pagina" + i + "?sort=naam-asc");
                        Elements divs = userAgent.doc.findEach("<div class=\"media-body\">");
                        for (Element div : divs) {
                            Element p = div.findFirst("<p class=\"description\">");
//                    System.out.println(p.getChildText()); // Kind of company
                            writer.print(p.getChildText() + ",");
                            // get url link
                            Element h = div.getFirst("<h4 class=\"media-heading title orange\">");
                            Element href = h.getFirst("<a href");
                            String url = href.toString();
                            url = url.substring(9, url.length() - 2);
                            Element company = h.getFirst("<a href=\"" + url + "\">");
//                    System.out.println(company.getChildText()); // Company name
                            semaphore.acquire();
                            writer.print(company.getChildText() + ",");
                            semaphore.release();
                            // Adress url info
                            UserAgent userAgent2 = new UserAgent();
                            success = false;

                            while (!success) {
                                try {
                                    userAgent2.visit(url);
                                    success = true;
                                } catch (JauntException e) {         //if an HTTP/connection error occurs, handle JauntException.
                                    System.err.println(e.getMessage());
                                }
                            }

                            Element div2 = userAgent2.doc.findEach("<div class=\"address_row\">");
                            Element div3 = div2.findFirst("<span class=\"address_content\">");
//                    System.out.println(div3.getChildText().trim()); //STREET NAME

                            semaphore.acquire();
                            writer.print(div3.getChildText().trim() + ",");
                            semaphore.release();

                            Element span = div2.getElement(1);
                            Element span2 = span.getFirst("<span>");
                            Elements spans = span2.getEach("<span>");
//                    System.out.println(spans.getElement(0).getChildText()); // postcode
//                    System.out.println(spans.getElement(1).getChildText()); // dorp

                            semaphore.acquire();
                            writer.println(spans.getElement(0).getChildText() + "," + spans.getElement(1).getChildText());
                            semaphore.release();

                            userAgent2.close();
                        }
                    } catch (JauntException | IOException | InterruptedException e) {         //if an HTTP/connection error occurs, handle JauntException.
                        System.err.println(e.getMessage());
                    }
                }).start();
            }

            writer.close();

        } catch (Exception e) {         //if an HTTP/connection error occurs, handle JauntException.
            System.err.println(e.getMessage());
        }
    }
}
