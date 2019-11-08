import java.util.Scanner;

public class TestWebscraper {

    public static void main(String args[]) {
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
       	
       	Webscraper scrape = new Webscraper(search);

       	System.out.println(scrape);
    }
}
