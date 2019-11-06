import java.time.LocalTime;
import java.util.ArrayList;

//Class that represents an item in craigslist
public class Item {
	protected String itemName, make, model, description, contactInfo;
	protected Category category;
	protected float itemPrice;
	protected LocalTime datePosted,dateUpdated;
	protected String itemURL; //change later when URL class is defined
	protected ArrayList<String> itemImages; // change later to arraylist of URL 
	protected String condition; // change to condition class once defined
	protected Area itemArea;
	protected SubArea itemSubArea;
	
	public Item() {
		
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
