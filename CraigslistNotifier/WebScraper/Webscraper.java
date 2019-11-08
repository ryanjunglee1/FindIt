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

public class Webscraper {
	Document website;
	Search search;
	String keyword;

	public Webscraper(Search search) {
		this.search = search;
		this.keyword = keyword;

		try {
			if (search instanceof Topic) {
				website = Jsoup.connect(search.getWebsite().location() + "/search/" + search.getTopicMap().get(search.getTopic())).get();
			}
			else if (search instanceof Category)
				website = Jsoup.connect(search.getWebsite().location() + "/search/" + search.getCategoryMap().get(search.getCategory())).get();
			else
				throw new Exception("Not a topic or category");
		}
		catch(IOException e) {
			website = null;
			System.out.println("Website not found.");
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Override
	public String toString() {
		if (website == null)
			return "No such website";

		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);

		printWriter.printf("Website URL: %s\nWebsite Name: %s\nState: %s\nArea: %s\nSub Area: %s\nTopic: %s\nCategory: %s\n", website.location(), website.title(), search.getState(), search.getArea(), search.getSubArea(), search.getTopic(), search.getCategory());

		return stringWriter.toString();
	}
}