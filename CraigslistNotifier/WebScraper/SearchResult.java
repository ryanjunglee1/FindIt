import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;

/*
 * This class is used to contain an arrayList of items , the SearchQuery used to get those items, and update methods
 * that update the item list based on the update interval from the searchQuery, sends a notification to email upon
 * new items being added to the list
 * @author Arti Shala
 * @version 1.0
 */
public class SearchResult {
	protected ArrayList<Item> itemList;
	protected int resultSize;
	protected SearchQuery query;
	protected LocalTime lastUpdated, updateInterval, nextUpdate;
	protected boolean willUpdate;
	protected String updateEmail;
	
	public SearchResult(SearchQuery q) {
		itemList = new ArrayList<Item>();
		this.query = q;
	}
	
	public void add(Item i) {
		this.itemList.add(i);
	}
	
	public void printItems() {
		Iterator itemIterator = itemList.iterator();
		while (itemIterator.hasNext()) {
			Item i = (Item) itemIterator.next();
			System.out.println(i);
		}
	}
}
