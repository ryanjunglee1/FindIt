import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Attributes;

/*
 * An object that represents a user requested search based on parameters entered from the GUI, uses a 
 * search object to contain the default search conditions provided by CL and additional parameters 
 * based on custom search functions provided in the GUI. Uses a webscraper object to get the base URL
 * from search object 
 * @author Arti Shala
 * @version 1.0
 */
public class SearchQuery {
	protected Search search;
	protected String[] searchKeywordsPositive, searchKeywordsNegative;
	protected String sellerType, makeSearch, modelSearch, conditionSearch; //Define sellerType and conditionSearch enum for dropdown list;
	protected Boolean hasImage , multipleImagesOnly , originalImagesOnly, postedToday , searchTitlesOnly, bundleDuplicates ,
	hideAllDuplicates, hasMakeModelOnly, hasPhoneOnly, cryptoAccepted, deliveryAvailable;
	protected int milerange, zipcode, descriptionLengthMin, descriptionLengthMax;
	protected float minPrice, maxPrice;
	protected LocalTime startDate,endDate;
	protected Webscraper scraper;
	
	/*
	 * Accepts an array of keywords to search with, and creates a new webscraper from the default search provided
	 * @param String[] keywords are the keywords to be searched for Items
	 * @param Search search is the default search that includes required parameters like area, subarea, and topic before keywords
	 */
	public SearchQuery(String[] keywords, Search search) {
		this.search = search;
		this.searchKeywordsPositive = keywords;
		scraper = new Webscraper(search);
	}
	
	/*
	 * 
	 * @return a SearchResult object containing all the items that fit the search parameters
	 */
	public SearchResult getSearch(){
		//Block of code that simulates webscraper returning an arraylist of items based on required conditions
		String baseURL = scraper.website.location();
		String[] keywordURL = new String[this.searchKeywordsPositive.length];
		for (int i = 0; i < keywordURL.length; i++) {
			keywordURL[i] = baseURL + "?query=" + this.searchKeywordsPositive[i];
			System.out.println(keywordURL[i]);
		}
		ArrayList<String> itemURL = new ArrayList<String>();
		Document[] documents = new Document[keywordURL.length];
		for (int i = 0; i < documents.length; i++) {
			try {
				documents[i] = Jsoup.connect(keywordURL[i]).get();
			} catch (IOException e) {
				System.out.println(e.getMessage());
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			Elements rows = documents[i].getElementsByClass("rows");
			Elements resultrows = rows.get(0).children();
			for (Element resultrow : resultrows) {
				itemURL.add(resultrow.children().get(0).attributes().get("href"));
			}
			for (String s : itemURL) {
				System.out.println(s);
			}
		}
		
		/*
		ArrayList<Item> testList = new ArrayList<Item>();
		Item testItem = new Item("Fridge","brand new fridge");
		Item testItem2 = new Item("TV","slightly used TV");
		Item testItem3 = new Item("Motorcycle","For parts only broken motorcycle");
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
		 
		Iterator listIterator = testList.iterator();
		while (listIterator.hasNext()) {
			Item i = (Item) listIterator.next();
			for (String s : this.searchKeywordsPositive) {
				if (i.itemName.contains(s) || i.description.contains(s)) {
					result.add(i);
				}
			}
		}
		result.printItems();
		return result;
		*/
		return null;
	}
	
	
	public static Search newSearch() {
		Scanner scan = new Scanner(System.in);
    	Search search = new Search();

    	System.out.println("Choose from a list of States:");
        for (String state: search.getStateMap().keySet()) {
        	System.out.print(state + "\t");
        }
        String stateInput = scan.nextLine();
        search = new State(stateInput);

        if (search.hasArea()) {
        	System.out.println("Choose from a list of Areas:");
        	for (String area: search.getAreaMap().keySet()) {
        		System.out.print(area + "\t");
        	}
        	String areaInput = scan.nextLine();
        	search = new Area(search, areaInput);
        }

        if (search.hasSubArea()) {
        	System.out.println("Choose from a list of Sub Areas:");
        	for (String subArea: search.getSubAreaMap().keySet()) {
        		System.out.print(subArea + "\t");
        	}
        	String subAreaInput = scan.nextLine();
        	search = new SubArea(search, subAreaInput);
        }

        System.out.println("Choose from a list of Topics:");
        for (String topic: search.getTopicMap().keySet()) {
        	System.out.print(topic + "\t");
        }
       	String topicInput = scan.nextLine();
       	search = new Topic(search, topicInput);

       	System.out.println("Choose from a list of Categories:");
       	for (String category: search.getCategoryMap().keySet()) {
        	System.out.print(category + "\t");
        }
       	String categoryInput = scan.nextLine();
       	search = new Category(search, categoryInput);
       	return search;
	}
	
	
	public static void main(String[] args) {
		String[] testkeywords = {"Ford", "Audi", "Toyota"};
		SearchQuery q = new SearchQuery(testkeywords, newSearch());
		q.getSearch();
		
		//SearchQuery query = new SearchQuery(testkeywords);
		//SearchResult result = query.getSearch();
		//result.printItems();
	}
	
}
