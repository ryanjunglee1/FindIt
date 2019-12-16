package searchelements;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Attributes;

import java.util.ArrayList;
import java.io.IOException;
import java.io.StringWriter;
import java.io.PrintWriter;
import java.util.HashMap;

/**
 * Used to represent an Area in Craigslist when building a search
 * @author Ryan Lee
 *
 */
public class Area extends State {

	public Area(Search search, String area) {
		super(search.getState());
		try {
			if (!this.areaMap.containsKey(area))
				throw new Exception("Area does not exist in the state.");

			this.area = area;
			this.website = Jsoup.connect(getAreaMap().get(area)).get();
	
			Elements subAreaItems = website.getElementsByClass("sublinks");
			if (subAreaItems.size() > 0) {
				//set subAreaMap
				subAreaItems = subAreaItems.first().children();
				hasSubArea = true;
				for (Element item: subAreaItems) {
					item = item.children().first();
					Attributes myAttributes = item.attributes();
					subAreaMap.put(myAttributes.get("title"), item.html());
				}
			}
			else {
				//set topicMap
				Elements topicItems = website.getElementById("catAbb").getElementsByAttribute("value");
				for (Element item: topicItems) {
					topicMap.put(item.html(), item.attributes().get("value"));
				}
			}
		}
		catch (IOException e) {
			System.out.println("Website not found.");
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}