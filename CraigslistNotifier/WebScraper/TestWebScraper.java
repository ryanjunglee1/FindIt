import java.util.Scanner;

public class TestWebScraper {

    public static void main(String args[]) {
    	Scanner scan = new Scanner(System.in);
       	WebScraper scrape = new WebScraper(new Search(), scan);

       	System.out.println(scrape);
    }
}
