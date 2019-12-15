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

public class Topic extends SubArea{

	public Topic(Search subArea, String topic) {
		super(subArea, subArea.getSubArea());
		this.topic = topic;

		//set categoryMap
		Elements categoryItems = website.getElementById(getTopicMap().get(topic)).getElementsByClass("cats").first().children();
		for (Element item: categoryItems) {
			for (Element category: item.children()) {
				category = category.children().first();
				String name = category.children().first().html();
				categoryMap.put(name.substring(0, name.indexOf('<')), category.attributes().get("class"));
			}
		}
	}
}