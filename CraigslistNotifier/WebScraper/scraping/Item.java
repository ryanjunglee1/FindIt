package scraping;
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
	private String itemName, make = "", model = "", description, location, datePosted, dateUpdated, condition = "", dimensions;
	private float itemPrice;
	private LocalDateTime dateTimePosted,dateTimeUpdated;
	private String itemURL; //change later when URL class is defined
	private URI itemURI;
	private ArrayList<String> itemThumbs, itemPicsFull; //change later to arraylist of URL 
	private String fullsizeimg;
	private Document website;
	private boolean hasImages, hasMultipleImages;
	private boolean isNull;
	private String postID;
	
	public Item(String itemURL) throws NumberFormatException, IOException {
		try {
			this.setItemURL(itemURL);
			this.website = Jsoup.connect(itemURL).get();
			this.setItemName(removeFormats(website.getElementById("titletextonly").html()));
			this.setDescription(removeFormats(website.getElementById("postingbody").html()));
			this.cleanDescription();
			this.itemURI = URI.create(this.getItemURL());
			try {
				this.setItemPrice(Float.parseFloat(website.getElementsByClass("price").first().html().substring(1)));
			} catch (NullPointerException e) {
				this.setItemPrice(0.00f);
			}
			try {
				Element attrGroup = website.getElementsByClass("attrgroup").first();
				for (Element element: attrGroup.children()) {
					String contents = element.html();
					if (contents.contains("condition: "))
						setCondition(contents.substring(contents.indexOf("<b>") + 3, contents.indexOf("</b>")));
					else if (contents.contains("make / manufacturer: "))
						setMake(contents.substring(contents.indexOf("<b>") + 3, contents.indexOf("</b>")));
					else if (contents.contains("model name / number: "))
						setModel(contents.substring(contents.indexOf("<b>") + 3, contents.indexOf("</b>")));
					else if (contents.contains("size / dimensions: "))
						dimensions = contents.substring(contents.indexOf("<b>") + 3, contents.indexOf("</b>"));
				}
			}
			catch (NullPointerException e) {
				
			}
			
			try {
				String city = website.getElementsByClass("postingtitle").first().getElementsByTag("small").first().html().replace("(", "");
				city = city.substring(0, city.length() - 1);
				this.location = city + " " +
						website.getElementsByClass("crumb area").first().getElementsByTag("a").first().html();
				//System.out.println(this.location);
			} catch (NullPointerException e) {
				try {
					String city = website.getElementsByClass("postingtitle").first().getElementsByTag("small").first().html().replace("(", "");
					city = city.substring(0, city.length() - 1);
					this.location = city;
					//System.out.println(this.location);
				} catch (NullPointerException d) {
					try {
						this.location = website.getElementsByClass("crumb area").first().getElementsByTag("a").first().html();
						//System.out.println(this.location);
					} catch (NullPointerException c) {
						this.location = null;
						//System.out.println("crumb area not found");
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
					this.setDateTimeUpdated(LocalDateTime.parse(rawtimeupdate));
				} catch (IndexOutOfBoundsException ib) {
					this.setDateTimeUpdated(LocalDateTime.parse(rawtimepost));
				}
				this.setDateTimePosted(LocalDateTime.parse(rawtimepost));
				
				System.out.println(this.getDateTimePosted().toString() + " " + this.getDateTimeUpdated());
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			try {
				//System.out.println(website.toString());
				Elements images = website.getElementsByTag("img");
				this.setItemThumbs(new ArrayList<String>());
				this.setFullsizeimg(images.get(0).attr("src"));
				for (int i = 0; i < images.size(); i++) {
					if (i != 0 && !images.get(i).equals(null)) {
						try {
							String imgtag = images.get(i).attr("src");
							this.getItemThumbs().add(imgtag);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				//System.out.println("Fullsize: " + this.fullsizeimg);
				for (String s : this.getItemThumbs()) {
					//System.out.println(s);
				}
				this.setHasImages(true);
				if (!this.getItemThumbs().isEmpty())
					this.setHasMultipleImages(true);
				else
					this.setHasMultipleImages(false);
				
			} catch (NullPointerException e) {
				//System.out.println("no images, null pointer");
				this.setHasImages(false);
				this.setHasMultipleImages(false);
			} catch (IndexOutOfBoundsException o) {
				//System.out.println("no images, out of bounds");
				this.setHasImages(false);
				this.setHasMultipleImages(false);
			}
			setNull(false);
		} catch (NullPointerException z) {
			System.out.println("item deleted or expired");
			setNull(true);
		}
	}
	
	public Item(String name, String description) {
		this.setItemName(name);
		this.setDescription(description);
	}
	
	@Override
	public String toString() {
		return this.getItemName() + " $" + this.getItemPrice();
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
	
	public void openFullImg() throws URISyntaxException {
		if (!this.itemURI.equals(null)) {
			if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
			    try {
					Desktop.getDesktop().browse(new URI(this.getFullsizeimg()));
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
		this.setDescription(this.getDescription().replaceAll("\\<.*?\\>", ""));
		this.setDescription(this.getDescription().replace("QR Code Link to This Post", ""));
	}

	/**
	 * @return the hasImages
	 */
	public boolean isHasImages() {
		return hasImages;
	}

	/**
	 * @param hasImages the hasImages to set
	 */
	public void setHasImages(boolean hasImages) {
		this.hasImages = hasImages;
	}

	/**
	 * @return the isNull
	 */
	public boolean isNull() {
		return isNull;
	}

	/**
	 * @param isNull the isNull to set
	 */
	public void setNull(boolean isNull) {
		this.isNull = isNull;
	}

	/**
	 * @return the hasMultipleImages
	 */
	public boolean isHasMultipleImages() {
		return hasMultipleImages;
	}

	/**
	 * @param hasMultipleImages the hasMultipleImages to set
	 */
	public void setHasMultipleImages(boolean hasMultipleImages) {
		this.hasMultipleImages = hasMultipleImages;
	}

	/**
	 * @return the dateTimePosted
	 */
	public LocalDateTime getDateTimePosted() {
		return dateTimePosted;
	}

	/**
	 * @param dateTimePosted the dateTimePosted to set
	 */
	public void setDateTimePosted(LocalDateTime dateTimePosted) {
		this.dateTimePosted = dateTimePosted;
	}

	/**
	 * @return the itemName
	 */
	public String getItemName() {
		return itemName;
	}

	/**
	 * @param itemName the itemName to set
	 */
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	/**
	 * @return the make
	 */
	public String getMake() {
		return make;
	}

	/**
	 * @param make the make to set
	 */
	public void setMake(String make) {
		this.make = make;
	}

	/**
	 * @return the model
	 */
	public String getModel() {
		return model;
	}

	/**
	 * @param model the model to set
	 */
	public void setModel(String model) {
		this.model = model;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the itemPrice
	 */
	public float getItemPrice() {
		return itemPrice;
	}

	/**
	 * @param itemPrice the itemPrice to set
	 */
	public void setItemPrice(float itemPrice) {
		this.itemPrice = itemPrice;
	}

	/**
	 * @return the itemURL
	 */
	public String getItemURL() {
		return itemURL;
	}

	/**
	 * @param itemURL the itemURL to set
	 */
	public void setItemURL(String itemURL) {
		this.itemURL = itemURL;
	}

	/**
	 * @return the dateTimeUpdated
	 */
	public LocalDateTime getDateTimeUpdated() {
		return dateTimeUpdated;
	}

	/**
	 * @param dateTimeUpdated the dateTimeUpdated to set
	 */
	public void setDateTimeUpdated(LocalDateTime dateTimeUpdated) {
		this.dateTimeUpdated = dateTimeUpdated;
	}

	/**
	 * @return the condition
	 */
	public String getCondition() {
		return condition;
	}

	/**
	 * @param condition the condition to set
	 */
	public void setCondition(String condition) {
		this.condition = condition;
	}

	/**
	 * @return the itemThumbs
	 */
	public ArrayList<String> getItemThumbs() {
		return itemThumbs;
	}

	/**
	 * @param itemThumbs the itemThumbs to set
	 */
	public void setItemThumbs(ArrayList<String> itemThumbs) {
		this.itemThumbs = itemThumbs;
	}

	/**
	 * @return the fullsizeimg
	 */
	public String getFullsizeimg() {
		return fullsizeimg;
	}

	/**
	 * @param fullsizeimg the fullsizeimg to set
	 */
	public void setFullsizeimg(String fullsizeimg) {
		this.fullsizeimg = fullsizeimg;
	}
}
