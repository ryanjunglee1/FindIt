import java.time.LocalTime;
import java.util.ArrayList;

//Class that represents an item in craigslist
public class Item {
	protected String itemName;
	protected Category category;
	protected float itemPrice;
	protected LocalTime datePosted;
	protected LocalTime dateUpdated;
	protected String itemURL; //change later when URL class is defined
	protected ArrayList<String> itemImages; // change later to arraylist of URL 
	protected String condition; // change to condition class once defined
	protected String make;
	protected String model;
	protected Area itemArea;
	protected SubArea itemSubArea;
	protected String description;
	protected String contactInfo;
	
	
}
