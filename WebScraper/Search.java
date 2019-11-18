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
	protected boolean hasArea, hasSubArea, hasCategory;

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
			stateMap.remove("more ...");
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

	public void setState(String state) {
		this.state = state;
	}

	public String getArea() { return area; }

	public void setArea(String area) {
		this.area = area;
	}

	public String getSubArea() { return subArea; }

	public void setSubArea(String subArea) {
		this.subArea = subArea;
	}

	public String getTopic() { return topic; }

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getCategory() { return category; }

	public void setCategory(String category) {
		this.category = category;
	}

	public HashMap<String, String> getStateMap() { return stateMap; }

	public void setStateMap(HashMap<String, String> states) { this.stateMap = stateMap; }

	public HashMap<String, String> getAreaMap() { return areaMap; }

	public boolean setAreaMap() {
		try {
			website = Jsoup.connect(this.stateMap.get(state)).get();
			Elements areaItems = this.website.getElementsByClass("geo-site-list-container");
			if (areaItems.size() > 0) {
				areaItems = areaItems.first().children().get(1).children();
				hasArea = true;
				for (Element areaItem: areaItems) {
					Element myItem = areaItem.children().first();
					areaMap.put(removeBold(myItem.html()), myItem.attributes().get("href"));
				}
			}
		}
		catch (IOException e) {
			System.out.println("Website not Found.");
		}

		return hasArea;
	}

	public HashMap<String, String> getSubAreaMap() { return subAreaMap; }

	public boolean setSubAreaMap() {
		try {
			if (hasArea)
				this.website = Jsoup.connect(areaMap.get(area)).get();
	
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
		}
		catch (IOException e) {
			System.out.println("Website not found.");
		}

		return hasSubArea;
	}

	public HashMap<String, String> getTopicMap() { return topicMap; }

	public void setTopicMap() {
		Elements topicItems = website.getElementById("catAbb").getElementsByAttribute("value");
		for (Element item: topicItems) {
			topicMap.put(removeBold(item.html()), item.attributes().get("value"));
		}
		if (this.state.equals("puerto rico")) {
			topicMap.remove("seleccionar categoría");
			topicMap.remove("eventos");
		}
		else {
			topicMap.remove("select category");
			topicMap.remove("events");
		}
	}

	public HashMap<String, String> getCategoryMap() { return categoryMap; }

	public boolean setCategoryMap() {
		System.out.println(getTopicMap().get(topic));
		Elements categoryItems = website.getElementById(getTopicMap().get(topic)).getElementsByClass("cats").first().children();
		if (categoryItems.size() > 0) {
			hasCategory = true;
			for (Element item: categoryItems) {
				for (Element category: item.children()) {
					category = category.children().first();
					String name = category.children().first().html();
					categoryMap.put(name.substring(0, name.indexOf('<')).replaceAll("&nbsp;", " ").replaceAll("amp;", ""), category.attributes().get("class"));
				}
			}
		}
		return hasCategory;
	}

	private String removeBold(String str) {
		return str.replaceAll("<b>", "").replaceAll("</b>", "");
	}

	public boolean hasArea() { return hasArea; }

	public boolean hasSubArea() { return hasSubArea; }
}