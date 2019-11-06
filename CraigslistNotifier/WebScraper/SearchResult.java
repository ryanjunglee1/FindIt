import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;

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
