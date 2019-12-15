package searchelements;
import java.util.HashMap;

public class Options {
	HashMap<String, Boolean> checkBoxes;
	HashMap<String, String> types;
	float minPrice, maxPrice;
	
	public Options(HashMap<String, Boolean> checkBoxes, HashMap<String, String> types, float[] priceRange) {
		this.checkBoxes = checkBoxes;
		this.types = types;
		this.minPrice = priceRange[0];
		this.maxPrice = priceRange[1];
	}

	public HashMap<String, Boolean> getCheckBoxes() {
		return checkBoxes;
	}

	public void setCheckBoxes(HashMap<String, Boolean> checkBoxes) {
		this.checkBoxes = checkBoxes;
	}

	public HashMap<String, String> getTypes() {
		return types;
	}

	public void setTypes(HashMap<String, String> types) {
		this.types = types;
	}

	public float getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(float minPrice) {
		this.minPrice = minPrice;
	}

	public float getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(float maxPrice) {
		this.maxPrice = maxPrice;
	}
}
