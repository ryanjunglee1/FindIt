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
 * an object representing an Area for a search
 * @author Ryan Lee
 *
 */
public class SubArea extends Area{

	public SubArea(Search area, String subArea) {
		super(area, area.getArea());
		this.subArea = subArea;
		
		//set topicMap
		Elements topicItems = website.getElementById("catAbb").getElementsByAttribute("value");
		for (Element item: topicItems) {
			topicMap.put(item.html(), item.attributes().get("value"));
		}
	}
}