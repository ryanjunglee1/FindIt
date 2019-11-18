import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Attributes;

import java.util.ArrayList;
import java.io.IOException;
import java.io.StringWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Scanner;

//Class that represents an item in craigslist
public class Item {
	protected String itemName, make, model, description, contactInfo, contactMailLink, location, datePosted, dateUpdated;
	protected float itemPrice;
	protected String dateTimePosted,dateTimeUpdated;
	protected String itemURL; //change later when URL class is defined
	protected ArrayList<String> itemImages; // change later to arraylist of URL 
	protected Document website;
	
	public Item(String itemURL) throws NumberFormatException, IOException {
		this.itemURL = itemURL;
		this.website = Jsoup.connect(itemURL).get();
		this.itemName = website.getElementById("titletextonly").html();
		this.description = website.getElementById("postingbody").html();
		Element contact = website.getElementsByClass("mailapp").first();
		this.contactInfo = contact.html();
		this.contactMailLink = contact.attributes().get("href");
		this.contactMailLink = this.contactMailLink.substring(0, this.contactMailLink.length() > 100 ? 100 : this.contactMailLink.length());
		this.itemPrice = Float.parseFloat(website.getElementsByClass("price").first().html());
		this.location = website.getElementsByClass("postingtitletext").first().html();
		this.location = this.location.substring(this.location.indexOf("("), this.location.indexOf(")") + 1);
		Elements times = website.getElementsByClass("date timeago");
		this.dateTimePosted = times.get(1).attributes().get("title");
		this.dateTimeUpdated = times.get(2).attributes().get("title");
		Elements images = website.getElementById("thumbs").children();
		for (Element image: images) {
			itemImages.add(image.children().first().attributes().get("src"));
		}
	}
	
	public Item(String name, String description) {
		this.itemName = name;
		this.description = description;
	}
	
	@Override
	public String toString() {
		return this.itemName;
	}
}
