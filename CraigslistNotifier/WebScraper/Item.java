import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Attributes;

//import org.openqa.selenium.*;
//import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;
import java.awt.Desktop;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Class that represents an item in craigslist
 * @author Ryan Lee, Arti Shala
 *
 */

public class Item {
	protected String itemName, make, model, description, location, datePosted, dateUpdated;
	protected float itemPrice;
	protected LocalDateTime dateTimePosted,dateTimeUpdated;
	protected String itemURL; //change later when URL class is defined
	protected URI itemURI;
	protected ArrayList<String> itemThumbs, itemPicsFull; //change later to arraylist of URL 
	protected String fullsizeimg;
	protected Document website;
	protected boolean hasImages;
	
	public Item(String itemURL) throws NumberFormatException, IOException {
		this.itemURL = itemURL;
		/*System.setProperty("webdriver.chrome.driver", "C:\\Users\\ryanm\\Documents\\GitHub\\CraigslistNotifier\\CraigslistNotifier\\Chrome Driver\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.get(itemURL);
		driver.findElement(By.cssSelector(".reply-button.js-only")).click();
		String html = "";
		for (WebElement element: driver.findElements(By.cssSelector("*"))) {
			html += element.getText();
		}*/
		this.website = Jsoup.parse(itemURL);
		
		this.website = Jsoup.connect(itemURL).get();
		this.itemName = website.getElementById("titletextonly").html();
		this.description = website.getElementById("postingbody").html();
		this.itemURI = URI.create(this.itemURL);
		try {
			this.itemPrice = Float.parseFloat(website.getElementsByClass("price").first().html().substring(1));
		} catch (NullPointerException e) {
			this.itemPrice = 0.00f;
		}
		
		try {
			String city = website.getElementsByClass("postingtitle").first().getElementsByTag("small").first().html().replace("(", "");
			city = city.substring(0, city.length() - 1);
			this.location = city + " " +
					website.getElementsByClass("crumb area").first().getElementsByTag("a").first().html();
			System.out.println(this.location);
		} catch (NullPointerException e) {
			try {
				String city = website.getElementsByClass("postingtitle").first().getElementsByTag("small").first().html().replace("(", "");
				city = city.substring(0, city.length() - 1);
				this.location = city;
				System.out.println(this.location);
			} catch (NullPointerException d) {
				try {
					this.location = website.getElementsByClass("crumb area").first().getElementsByTag("a").first().html();
					System.out.println(this.location);
				} catch (NullPointerException c) {
					this.location = null;
					System.out.println("crumb area not found");
				}
			}
			
		}
		
		
		
		Elements times = website.getElementsByClass("postinginfos").first().getElementsByTag("time");
		try {
			String rawtimepost = times.first().html();
			rawtimepost = rawtimepost.substring(0,10) + "T" + rawtimepost.substring(11) + ":00";
			String rawtimeupdate = times.first().html();
			rawtimeupdate = rawtimeupdate.substring(0,10) + "T" + rawtimeupdate.substring(11) + ":00";
			this.dateTimePosted = LocalDateTime.parse(rawtimepost);
			this.dateTimeUpdated = LocalDateTime.parse(rawtimeupdate);
			System.out.println(this.dateTimePosted.toString() + " " + this.dateTimeUpdated);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			//System.out.println(website.toString());
			Elements images = website.getElementsByTag("img");
			this.itemThumbs = new ArrayList<String>();
			this.fullsizeimg = images.get(0).attr("src");
			for (int i = 0; i < images.size(); i++) {
				if (i != 0 && !images.get(i).equals(null)) {
					try {
						String imgtag = images.get(i).attr("src");
						this.itemThumbs.add(imgtag);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			System.out.println("Fullsize: " + this.fullsizeimg);
			for (String s : this.itemThumbs) {
				System.out.println(s);
			}
			this.hasImages = true;
			
		} catch (NullPointerException e) {
			System.out.println("no images, null pointer");
			this.hasImages = false;
		} catch (IndexOutOfBoundsException o) {
			System.out.println("no images, out of bounds");
			this.hasImages = false;
		}
<<<<<<< HEAD
		*/
		//driver.close();
=======
		
>>>>>>> master
	}
	
	public Item(String name, String description) {
		this.itemName = name;
		this.description = description;
	}
	
	@Override
	public String toString() {
		return this.itemName + " $" + this.itemPrice;
	}
	
	public void openPage() {
		if (!this.itemURI.equals(null)) {
			if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
			    try {
					Desktop.getDesktop().browse(this.itemURI);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
