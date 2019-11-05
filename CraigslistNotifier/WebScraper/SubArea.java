public class SubArea extends Area{
	private String subArea;
	private HashMap<String, String> topicMap;

	public subArea(String area, String subArea) {
		super(area, subArea);
		topicMap = new HashMap<String, String>();
		
		//set topicMap
		Elements topicItems = website.getElementById("catAbb").children();
		topicItems = topicItems.nextAll();
		for (Element item: topicItems) {
			Attributes myAttributes = item.attributes();
			topicMap.put(myAttributes.get("title"), myAttributes.get(topic));
		}
	}
}