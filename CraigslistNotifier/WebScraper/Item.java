import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Attributes;

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

//Class that represents an item in craigslist
public class Item {
	protected String itemName, make, model, description, location, datePosted, dateUpdated;
	protected float itemPrice;
	protected LocalDateTime dateTimePosted,dateTimeUpdated;
	protected String itemURL; //change later when URL class is defined
	protected URI itemURI;
	protected ArrayList<String> itemThumbs, itemPicsFull; //change later to arraylist of URL 
	protected String fullsizeimg;
	protected Document website;
	protected boolean hasImages, hasMultipleImages;
	protected boolean isNull;
	protected String postID;
	
	public Item(String itemURL) throws NumberFormatException, IOException {
		try {
			this.itemURL = itemURL;
			this.website = Jsoup.connect(itemURL).get();
			this.itemName = removeFormats(website.getElementById("titletextonly").html());
			this.description = removeFormats(website.getElementById("postingbody").html());
			this.cleanDescription();
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
			
			
			this.postID = website.getElementsByClass("postinginfos").first().getElementsByClass("postinginfo").first().html().replaceAll("[^\\d]","");
			System.out.println(this.postID);
			Elements times = website.getElementsByClass("postinginfos").first().getElementsByTag("time");
			try {
				
				String rawtimepost = times.first().html();
				rawtimepost = rawtimepost.substring(0,10) + "T" + rawtimepost.substring(11) + ":00";
				try {
					String rawtimeupdate = times.get(1).html();
					rawtimeupdate = rawtimeupdate.substring(0,10) + "T" + rawtimeupdate.substring(11) + ":00";
					this.dateTimeUpdated = LocalDateTime.parse(rawtimeupdate);
				} catch (IndexOutOfBoundsException ib) {
					this.dateTimeUpdated = LocalDateTime.parse(rawtimepost);
				}
				this.dateTimePosted = LocalDateTime.parse(rawtimepost);
				
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
				if (!this.itemThumbs.isEmpty())
					this.hasMultipleImages = true;
				else
					this.hasMultipleImages = false;
				
			} catch (NullPointerException e) {
				System.out.println("no images, null pointer");
				this.hasImages = false;
				this.hasMultipleImages = false;
			} catch (IndexOutOfBoundsException o) {
				System.out.println("no images, out of bounds");
				this.hasImages = false;
				this.hasMultipleImages = false;
			}
			isNull = false;
		} catch (NullPointerException z) {
			System.out.println("item deleted or expired");
			isNull = true;
		}
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
	

	public boolean equals(Item i) {
		if (this.postID.contentEquals(i.postID))
			return true;
		return false;
}

	public static String removeFormats(String str) {
		return str.replaceAll("&amp;", "&");
	}
	
	public void cleanDescription() {
		this.description = this.description.replaceAll("\\<.*?\\>", "");
		this.description = this.description.replace("QR Code Link to This Post", "");
	}
}
