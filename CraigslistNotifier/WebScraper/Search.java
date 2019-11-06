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

public class Search {
	protected Document website;
	protected HashMap<String, String> stateMap, areaMap, subAreaMap, topicMap, categoryMap;
	protected String state, area, subArea, topic, category;
	protected boolean hasArea, hasSubArea;

	public Search(String state, String area, String subArea, String topic, String category) {
		this.state = state;
		this.area = area;
		this.subArea = subArea;
		this.topic = topic;
		this.category = category;
		this.stateMap = new HashMap<String, String>();
		this.areaMap = new HashMap<String, String>();
		this.subAreaMap = new HashMap<String, String>();
		this.topicMap = new HashMap<String, String>();
		this.categoryMap = new HashMap<String, String>();

		try {
			website = Jsoup.connect("https://washingtondc.craigslist.org").get();
			Elements acItems = website.getElementsByClass("acitem");
			Elements areaItems = acItems.get(2).children();
			for (Element areaItem: areaItems) {
				Element myItem = areaItem.children().first();
				stateMap.put(myItem.html(), "https:" + myItem.attributes().get("href"));
			}
		}
		catch(IOException e) {
			website = null;
			System.out.println("Website Not Found");
		}
	}

	public Search() {
		this("", "", "", "", "");
	}

	public Document getWebsite() { return website; }

	public void setWebsite(Document website) { this.website = website; }

	public String getState() { return state; }

	public void setState(String state) { this.state = state; }

	public String getArea() { return area; }

	public void setArea(String area) { this.area = area; }

	public String getSubArea() { return subArea; }

	public void setsubArea(String subArea) { this.subArea = subArea; }

	public String getTopic() { return topic; }

	public void setTopic(String topic) { this.topic = topic; }

	public String getCategory() { return category; }

	public void setCategory(String category) { this.category = category; }

	public HashMap<String, String> getStateMap() { return stateMap; }

	public void setStateMap(HashMap<String, String> states) { this.stateMap = stateMap; }

	public HashMap<String, String> getAreaMap() { return areaMap; }

	public HashMap<String, String> getSubAreaMap() { return subAreaMap; }

	public HashMap<String, String> getTopicMap() { return topicMap; }

	public HashMap<String, String> getCategoryMap() { return categoryMap; }

	public boolean hasArea() { return hasArea; }

	public boolean hasSubArea() { return hasSubArea; }
}