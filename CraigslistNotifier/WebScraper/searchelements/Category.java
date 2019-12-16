package searchelements;
/**
 * Used to reoresent a category when building a search
 * @author Ryan Lee
 *
 */
public class Category extends Topic{
	public Category(Search topic, String category) {
		super(topic, topic.getTopic());
		this.category = category;
	}
}