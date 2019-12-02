import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Attributes;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;
import java.awt.Desktop;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Class that represents an item in craigslist
 * @author Ryan Lee, Arti Shala
 *
 */

public class Item {
	protected String itemName, make, model, description, contactInfo, contactMailLink, location, datePosted, dateUpdated;
	protected float itemPrice;
	protected String dateTimePosted,dateTimeUpdated;
	protected String itemURL; //change later when URL class is defined
	protected URI itemURI;
	protected ArrayList<String> itemImages; // change later to arraylist of URL 
	protected Document website;
	
	public Item(String itemURL) throws NumberFormatException, IOException {
		this.itemURL = itemURL;
		/*System.setProperty("webdriver.chrome.driver", "C:\\Users\\ryanm\\Documents\\GitHub\\CraigslistNotifier\\CraigslistNotifier\\Chrome Driver\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.get(itemURL);
		driver.findElement(By.cssSelector(".reply-button.js-only")).click();
		String html = "";
		for (WebElement element: driver.findElements(By.cssSelector("*"))) {
			html += element.getText();
		}
		this.website = Jsoup.parse(html);
		*/
		this.website = Jsoup.connect(itemURL).get();
		this.itemName = website.getElementById("titletextonly").html();
		this.description = website.getElementById("postingbody").html();
		this.itemURI = URI.create(this.itemURL);
		/*
		Element contact = website.getElementsByClass("mailapp").first();
		this.contactInfo = contact.html();
		this.contactMailLink = contact.attributes().get("href");
		this.contactMailLink = this.contactMailLink.substring(0, this.contactMailLink.length() > 100 ? 100 : this.contactMailLink.length());
		*/
		try {
		this.itemPrice = Float.parseFloat(website.getElementsByClass("price").first().html().substring(1));
		} catch (NullPointerException e) {
			this.itemPrice = 0.00f;
		}
		/*
		this.location = website.getElementsByClass("postingtitletext").first().html();
		this.location = this.location.substring(this.location.indexOf("("), this.location.indexOf(")") + 1);
		
		Elements times = website.getElementsByClass("date timeago");
		this.dateTimePosted = times.get(1).attributes().get("title");
		this.dateTimeUpdated = times.get(2).attributes().get("title");
		
		Elements images = website.getElementById("thumbs").children();
		for (Element image: images) {
			itemImages.add(image.children().first().attributes().get("src"));
		}
		*/
		//driver.close();
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
