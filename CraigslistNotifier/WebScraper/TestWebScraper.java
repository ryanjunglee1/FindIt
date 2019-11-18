import java.io.IOException;
import java.util.Scanner;

public class TestWebScraper {

    public static void main(String args[]) {
    	/*
    	Scanner scan = new Scanner(System.in);
       	WebScraper scrape = new WebScraper(new Search(), scan);

       	System.out.println(scrape);
       	*/
    	try {
			Item i = new Item("https://washingtondc.craigslist.org/doc/ele/d/washington-home-security-camera/7019702676.html");
			System.out.println(i);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
