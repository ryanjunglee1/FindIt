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

public class WebScraper {
	private Document website;
	private String area, subArea, topic, category;
	private static final HashMap<String, String> areaMap;

	public WebScraper(String area, String subArea, String topic, String category) {

		try {
			//set subAreaMap
			website = Jsoup.parse("https://" + area + ".craigslist.org");
			Elements subAreaItems = website.getElementsByClass("sublinks").first();
			for (Element item: subAreaItems) {
				Attributes myAttributes = item.children().first().attributes();
				String abbr = myAttributes.get("href");
				subAreaMap.put(myAttributes.get("title"), abbr.substring(1, abbr));
			}


			//set topicMap
			Elements topicItems = website.getElementById("catAbb").children();
			topicItems = topicItems.nextAll();
			for (Element item: topicItems) {
				Attributes myAttributes = item.attributes();
				subAreaMap.put(myAttributes.get("title"), myAttributes.get(topic));
			}
		}
		catch(IOException e) {
			website = null;
		}
	}

	public WebScraper(String area) {
		this(area, "", "");
	}

	public WebScraper(String area, String subArea) {
		this(area, subArea, "");
	}

	public WebScraper() {
		this("", "", "");
	}

	public String getArea() { return area; }

	public String getSubArea() { return subArea; }

	public String getCategory() { return category; }

	public String getWebsite() { return website; }

	public void setArea(String area) { this.area = area; }

	public void setSubArea(String subArea) { this.subArea = subArea; }

	public void setCategory(String category) { this.category = category; }

	public void setWebsite(Document website) { this.website = website; }

	@Override
	public String toString() {
		if (website == null)
			return "No such website"
		if (category.equals(""))
			return "Category Required"

		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);

		printWriter.printf("Website Name: %s\nArea: %s\nSub Area: %s\nCategory: %s\n", website.title(), area, subArea, category);

		return stringWriter.toString();
	}
}