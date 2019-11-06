import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;

public class SearchQuery {
	protected Area[] searchAreas;
	protected SubArea[] subAreas;
	protected Category[] categories;
	protected String[] searchKeywordsPositive, searchKeywordsNegative;
	protected String sellerType, makeSearch, modelSearch, conditionSearch; //Define sellerType and conditionSearch enum for dropdown list;
	protected Boolean hasImage , multipleImagesOnly , originalImagesOnly, postedToday , searchTitlesOnly, bundleDuplicates ,
	hideAllDuplicates, hasMakeModelOnly, hasPhoneOnly, cryptoAccepted, deliveryAvailable;
	protected int milerange, zipcode, descriptionLengthMin, descriptionLengthMax;
	protected float minPrice, maxPrice;
	protected LocalTime startDate,endDate;
	protected WebScraper scraper;
	
	//Current functionality, creates a new searchquery that will be able to get items from webscraper and only show those 
	// that  contain one of the keywords specified
	public SearchQuery(String[] keywords) {
		this.searchKeywordsPositive = keywords;
	}
	
	public SearchResult getSearch() {
		//Block of code that simulates webscraper returning an arraylist of items based on required conditions
		ArrayList<Item> testList = new ArrayList<Item>();
		Item testItem = new Item("word 1","Description");
		Item testItem2 = new Item("word 2","Description");
		Item testItem3 = new Item("word 3","Description");
		testList.add(testItem);
		testList.add(testItem2);
		testList.add(testItem3);
		//
		
		//empty searchresult created with this as the query
		SearchResult result = new SearchResult(this);
		
		/*
		 * goes through the simulated webscraper list return and adds only 
		 * the items that fit the keyword query to the result
		 * currently only supports checking item name and description for any of the keywords
		 */
		Iterator listIterator = testList.iterator();
		while (listIterator.hasNext()) {
			Item i = (Item) listIterator.next();
			for (String s : this.searchKeywordsPositive) {
				if (i.itemName.contains(s) || i.description.contains(s)) {
					result.add(i);
				}
			}
		}
		
		return result;
	}
	
	public static void main(String[] args) {
		String[] testkeywords = {"word 1", "word 5"};
		SearchQuery query = new SearchQuery(testkeywords);
		SearchResult result = query.getSearch();
		result.printItems();
	}
	
}
