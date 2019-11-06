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

public class State extends Search {

	public State(String state) {
		this.state = state;
		website = this.stateMap.get(state);
		Elements areaItems = this.website.getElementsByClass("geo-site-list-container");
		if (areaItems.size() > 0) {
			areaItems = areaItems.first().children().get(1).children();
			try {
				hasArea = true;
				for (Element areaItem: areaItems) {
					Element myItem = areaItem.children().first();
					areaMap.put(myItem.html(), Jsoup.connect(myItem.attributes().get("href")).get());
				}
			}
			catch (IOException e) {
				System.out.println("Website not found.");
			}
		}
	}
}