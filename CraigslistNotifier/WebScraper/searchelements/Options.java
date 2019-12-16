package searchelements;
import java.util.HashMap;
/**
 * used to configure the SearchQuery based on input from the User in the GUI
 * @author Ryan Lee
 *
 */
public class Options {
	//data fields
	HashMap<String, Boolean> checkBoxes;
	HashMap<String, String> types;
	float minPrice, maxPrice;
	
	/**
	 * Generates an Options object based on booleans passed in through the GUI checkboxes and text boxes for price range
	 * @param checkBoxes boolean hashmap of conditions from GUI checkboxes 
	 * @param types for future use
	 * @param priceRange range of prices from min to max
	 * @see UpdatedGUI
	 */
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
