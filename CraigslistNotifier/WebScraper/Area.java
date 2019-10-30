public class Area extends WebScraper{
	private HashMap<String, String> subAreaMap;

	public Area(String area) {
		super(area);
		subAreaMap = new HashMap<String, String>();

		//set subAreaMap
		Elements subAreaItems = getArea().getElementsByClass("sublinks").first();
		for (Element item: subAreaItems) {
			Attributes myAttributes = item.children().first().attributes();
			String abbr = myAttributes.get("href");
			subAreaMap.put(myAttributes.get("title"), abbr.substring(1, abbr));
		}
	}

	public Area() {
		this("");
	}
}