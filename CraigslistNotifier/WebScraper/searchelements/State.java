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
 * An object representing a state for a search
 * @author Ryan Lee
 */
public class State extends Search {

	public State(String state) {
		this.state = state;
		try {
			website = Jsoup.connect(this.stateMap.get(state)).get();
			Elements areaItems = this.website.getElementsByClass("geo-site-list-container");
			if (areaItems.size() > 0) {
				areaItems = areaItems.first().children().get(1).children();
				hasArea = true;
				for (Element areaItem: areaItems) {
					Element myItem = areaItem.children().first();
					areaMap.put(myItem.html(), myItem.attributes().get("href"));
				}
			}
		}
		catch (IOException e) {
			System.out.println("Website not Found.");
		}
	}
}