import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.HashMap;

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
	protected WebScraper scraper;
	
	/*
	 * Accepts an array of keywords to search with, and creates a new webscraper from the default search provided
	 * @param String[] keywords are the keywords to be searched for Items
	 * @param Search search is the default search that includes required parameters like area, subarea, and topic before keywords
	 */
	public SearchQuery(String[] keywords, Options options, Search search) {
		this.search = search;
		this.searchKeywordsPositive = keywords;
		//this.search = new Search();
		Scanner scan = new Scanner(System.in);
		scraper = new WebScraper(search, scan);

		if (options != null) {
			HashMap<String, Boolean> checkBoxes = options.getCheckBoxes();
			HashMap<String, String> types = options.getTypes();
			minPrice = options.getMinPrice();
			maxPrice = options.getMaxPrice();
			if (checkBoxes != null) {
				hasImage = checkBoxes.get("hasImages");
				multipleImagesOnly = checkBoxes.get("multipleImagesOnly");
				originalImagesOnly = checkBoxes.get("originalImagesOnly");
				postedToday = checkBoxes.get("postedToday");
				searchTitlesOnly = checkBoxes.get("searchTitlesOnly");
				bundleDuplicates = checkBoxes.get("bundleDuplicates");
				hideAllDuplicates = checkBoxes.get("hideAllDuplicates");
				hasMakeModelOnly = checkBoxes.get("hasMakeModelOnly");
				hasPhoneOnly = checkBoxes.get("hasPhoneOnly");
				cryptoAccepted = checkBoxes.get("cryptoAccepted");
				deliveryAvailable = checkBoxes.get("deliveryAvailable");
			}

			if (types != null) {
				sellerType = types.get("sellerType");
				makeSearch = types.get("makeSearch");
				modelSearch = types.get("modelSearch"); 
				conditionSearch = types.get("conditionSearch");
			}
		}
	}

	public SearchQuery(String[] keywords, Search search) {
		this(keywords, null, search);
	}
	
	/*
	 * takes a search and keyword and prints out the first 50 items with their price under those parameters
	 * @return a SearchResult object containing all the items that fit the search parameters
	 */
	public SearchResult getSearch(){
		
		/*
		String baseURL = scraper.website.location();
		String[] keywordURL = new String[this.searchKeywordsPositive.length];
		ArrayList<Item> itemarraylist = new ArrayList<Item>();
		for (int i = 0; i < keywordURL.length; i++) {
			keywordURL[i] = baseURL + "query=" + this.searchKeywordsPositive[i];
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
				//System.out.println(s);
				try {
					if (!s.isEmpty()) {
						Item item = new Item(s);
						if (item.isNull == false) {
							itemarraylist.add(item);
							System.out.println(item + "\n");
						}
					}
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		*/
		ArrayList<Item> itemarraylist = updateSearch();
		SearchResult results = new SearchResult(itemarraylist, this.searchKeywordsPositive[0], this);
		//results.printItems();
		return results;
	}
	
	public ArrayList<Item> updateSearch() {
		String baseURL = scraper.website.location();
		String[] keywordURL = new String[this.searchKeywordsPositive.length];
		ArrayList<Item> itemarraylist = new ArrayList<Item>();
		for (int i = 0; i < keywordURL.length; i++) {
			keywordURL[i] = baseURL + "query=" + this.searchKeywordsPositive[i] + "&sort=date";
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
				//System.out.println(s);
				try {
					if (!s.isEmpty()) {
						Item item = new Item(s);
						if (item.isNull == false && this.maxPrice >= this.minPrice && item.itemPrice >= this.minPrice && item.itemPrice <= this.maxPrice) {
							itemarraylist.add(item);
							System.out.println(item + "\n");
						}
					}
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return itemarraylist;
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
		/*
		String[] testkeywords = {"Ford", "Audi", "Toyota"};
		SearchQuery q = new SearchQuery(testkeywords, newSearch());
		q.getSearch();
		*/
		//SearchQuery query = new SearchQuery(testkeywords);
		//SearchResult result = query.getSearch();
		//result.printItems();
	}
	
}
