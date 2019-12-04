public class Category extends Topic{
	public Category(Search topic, String category) {
		super(topic, topic.getTopic());
		this.category = category;
	}
}