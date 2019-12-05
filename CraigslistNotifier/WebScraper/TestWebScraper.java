import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class TestWebScraper {

    public static void main(String args[]) throws NumberFormatException, IOException {
    	/*
    	Scanner scan = new Scanner(System.in);
       	WebScraper scrape = new WebScraper(new Search(), scan);

       	System.out.println(scrape);
       	*/
    	/*try {
			Item i = new Item("https://washingtondc.craigslist.org/doc/ele/d/washington-home-security-camera/7019702676.html");
			System.out.println(i);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
    	ArrayList<Item> newItems = new ArrayList<Item>();
    	newItems.add(new Item("https://washingtondc.craigslist.org/doc/ele/d/washington-amazon-fire-tv-sticks-and-tv/7030842455.html"));
    	newItems.add(new Item("https://washingtondc.craigslist.org/nva/ele/d/fairfax-38l-electric-hot-water-boiler/7032340313.html"));
    	newItems.add(new Item("https://washingtondc.craigslist.org/nva/ele/d/new-bose-desktop-speakers/7032339967.html"));
    	SearchQuery z = new SearchQuery(new String[] {"test"},SearchQuery.newSearch());
    	SearchResult q = new SearchResult(newItems,"test",z);
    	String emailsubject = "Your " + q.query.search.getState() + " " + q.query.search.getCategory() + " search has new items!";
		String emailbody = "";
		
		for (Item i : newItems) {
			System.out.println("-----------");
			System.out.println("Item: " + i.toString() + " post date: " + i.dateTimePosted + " update date: " + i.dateTimeUpdated);
			System.out.println("-----------");
			try {
			emailbody = emailbody + i.itemName + " $" + i.itemPrice + "\n Posted: " + i.dateTimePosted.toString() + "\n" +
					i.itemURL + "\n" + "\n";
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
			// TODO Send email containing new items and fix new item condition
		}
		if (!newItems.isEmpty()) {
	    	MailSender sender = new MailSender("artishala0@gmail.com", emailsubject, emailbody);
	    	System.out.println("Email sent: " + sender.sendMail());
		}
    	MailSender sender = new MailSender("artishala0@gmail.com", "Test", "I hope this works");
    	System.out.println(sender.sendMail());


    	
    }
}
