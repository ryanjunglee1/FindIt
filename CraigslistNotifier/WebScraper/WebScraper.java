import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class WebScraper {
	protected Document website;
	protected String area;
	protected String region;
	protected String topic;

	public WebScraper(String area, String region, String topic) {
		this.area = area;
		this.region = region;
		this.topic = topic;
		try {
			website = Jsoup.connect("https://" + area + ".craigslist.org/" + region + "/" + topic).get();
		}
		catch(Exception IOException) {
			website = null;
		}
	}

	public WebScraper(String area) {
		this(area, "", "");
	}

	public WebScraper(String area, String region) {
		this(area, region, "");
	}

	public String getArea() { return area; }

	public String getRegion() { return region; }

	public String getTopic() { return topic; }
}