import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.io.IOException;

public class WebScraper {
	protected Document website;
	protected String area;
	protected String subArea;
	protected String topic;
	protected ArrayList<String> subAreaList;
	protected ArrayList<String> topicList;

	public WebScraper(String area, String subArea, String topic) {
		this.area = area;
		this.subArea = subArea;
		this.topic = topic;
		subAreaList = new ArrayList<>();
		topicList = new ArrayList<>();

		try {
			website = Jsoup.connect("https://" + area + ".craigslist.org/search/" + topic + "?").get();
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

	public String getsubArea() { return subArea; }

	public String getTopic() { return topic; }

	public void setSubAreaList() {
		Element mySubArea = website.getElementById("subArea");
		Elements mySubAreas = mySubArea.getElementsByTag("option");
		for (Element myElement: mySubAreas) {
			subAreaList.add(myElement.attr("value"));
		}
	}

	public void setTopicList() {
		Element mySubArea = website.getElementById("subCatAbb");
		Elements mySubAreas = mySubArea.getElementsByTag("option");
		for (Element myElement: mySubAreas) {
			topicList.add(myElement.attr("value"));
		}
	}
}