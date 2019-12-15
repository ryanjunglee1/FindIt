package guielements;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.application.Application;
import javafx.beans.property.Property;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Callback;
import searchelements.Options;
import searchelements.Search;
import searchelements.SearchQuery;
import searchelements.Sorts;

/**
 * The main GUI that accepts a search parameter (i.e., TV, camera, etc.) from a user and creates a searchQuery
 * for the desired item on Craigslist. Then software will display the search results in a simple yet meaningful
 * way to the user. 
 * @author Updated to JavaFX: Weezha Yahyapoor, Original: Arti Shala and Ryan Lee 
 * @version 1.0
 */
public class UpdatedGUI extends Application {

	Search search = new Search();
	String[] statechoices = search.getStateMap().keySet().toArray(new String[0]);
	
	private ComboBox<String> stateselect = new ComboBox<>();
	private ComboBox<String> areaselect = new ComboBox<>();
	private ComboBox<String> subareaselect = new ComboBox<>();
	private ComboBox<String> topicselect = new ComboBox<>();
	private ComboBox<String> categoryselect = new ComboBox<>();
	private TextField keywordfield = new TextField();
	private TextField minPriceField = new TextField();
	private TextField maxPriceField = new TextField();
	private Button button = new Button("Search");
	private HashMap<String,Boolean> checkBoxMap = new HashMap<String,Boolean>();
	private Boolean hasImage, multipleImagesOnly, postedToday, searchTitlesOnly, bundleDuplicates, 
	hideAllDuplicates, hasMakeModelOnly, hasPhoneOnly, cryptoAccepted, deliveryAvailable = false; 
	private ArrayList<String> searchKeywordsPositive = new ArrayList<String>();
	private ArrayList<String> searchKeywordsNegative = new ArrayList<String>();
	private String lastquery = "";
	
	@Override
	public void start(Stage primaryStage) {
		
		// Create a scene and place it in the stage.
		Scene scene = new Scene(guiLayout(), 850, 600);
		
		primaryStage.setTitle("Web Scraper"); 	// set stage title
		primaryStage.setScene(scene); 			// Place the scene in the stage
		primaryStage.setResizable(true);
		primaryStage.show(); 					// Display the stage
		
	}


	public static void main(String[] args) {
		
		launch(args);
	}
	
	/**
	 * Creates a Scene for the WebScraper root Node and place's it on the Stage.
	 * @param guiLayout(), width, and height.
	 * This guiLayout() guide includes all UI components for the WebScraper program. 
	 * @author Weezha Yahyapoor
	 */
	public BorderPane guiLayout() {
		
		// Create the main BorderPane to host all UI controls
		BorderPane pane = new BorderPane();
		
		//================================== Left Pane ================================	
		/**
		 * Create UI control for choosing a state.
		 */
		stateselect.setPrefWidth(150);
		stateselect.setValue("Choose state");
		//stateselect.getItems().add("Choose a state");
		Sorts.quickSort(statechoices, 0, statechoices.length - 1);
		ObservableList<String> items = FXCollections.observableArrayList(statechoices);
		stateselect.getItems().addAll(items);
		
		Label stateLable = new Label("Choose state:");
		stateLable.setPadding(new Insets(5));
		BorderPane label_boxState = new BorderPane();
		label_boxState.setLeft(stateLable);
		label_boxState.setRight(stateselect);
		
		/**
		 * Create UI control for choosing a city in the selected state.
		 * Not all states have specific localities like Northern VA when VA is selected
		 * as a state.
		 */
		areaselect.setValue("Choose area");
		areaselect.setPrefWidth(stateselect.getPrefWidth());
		Label areaLable = new Label("Choose area:");
		areaLable.setPadding(new Insets(5));
		BorderPane label_boxArea = new BorderPane();
		label_boxArea.setLeft(areaLable);
		label_boxArea.setRight(areaselect);
		
		/**
		 * Create UI control for choosing a locality in the selected state or city.
		 * Not all states have sub localities.
		 */
		subareaselect.setValue("Choose sub area");
		subareaselect.setPrefWidth(stateselect.getPrefWidth());
		Label subareaselectLabel = new Label("Choose sub area:");
		subareaselectLabel.setPadding(new Insets(5));
		BorderPane label_boxSubArea = new BorderPane();
		label_boxSubArea.setLeft(subareaselectLabel);
		label_boxSubArea.setRight(subareaselect);
		
		topicselect.setValue("Choose topic");
		topicselect.setPrefWidth(stateselect.getPrefWidth());
		Label topiclabel = new Label("Choose topic:");
		topiclabel.setPadding(new Insets(5));
		BorderPane label_boxTopic = new BorderPane();
		label_boxTopic.setLeft(topiclabel);
		label_boxTopic.setRight(topicselect);
		
		/**
		 * Create UI control for selecting an activity such as events etc.
		 */
		categoryselect.setValue("Choose category");
		categoryselect.setPrefWidth(stateselect.getPrefWidth());
		Label categorylabel = new Label("Choose category:");
		categorylabel.setPadding(new Insets(5));
		BorderPane label_boxCategory = new BorderPane();
		label_boxCategory.setLeft(categorylabel);
		label_boxCategory.setRight(categoryselect);
		
		/**
		 * Create UI control for capturing a user's desired price range, which is
		 * used to filter the list of available options. 
		 */
		Label priceRange = new Label("(optional) Price Range:");
		priceRange.setPadding(new Insets(5));
		Label to = new Label("to");
		to.setPadding(new Insets(5));
		Label dollarSign = new Label("$");
		dollarSign.setPadding(new Insets(5));
		String minPriceText = "min";
		minPriceField.setPromptText(minPriceText);
		minPriceField.setPrefWidth(minPriceText.length() * 24);
		minPriceField.setPadding(new Insets(5));

		String maxPriceText = "max";
		maxPriceField.setPromptText(maxPriceText);
		maxPriceField.setPrefWidth(maxPriceText.length() * 24);
		maxPriceField.setPadding(new Insets(5));
		//FlowPane textPricePane = new FlowPane(dollarSign, minPriceField, to, maxPriceField);
		//textPricePane.setPrefWidth(182);
		HBox hboxPricePane = new HBox(dollarSign,minPriceField, to, maxPriceField);
		BorderPane priceRangePane = new BorderPane();
		priceRangePane.setLeft(priceRange);
		//priceRangePane.setRight(textPricePane);
		priceRangePane.setRight(hboxPricePane);
	
		Region regionComboBoxes = new Region();
		regionComboBoxes.setPadding(new Insets(5));
		priceRangePane.setPadding(new Insets(20,0,0,0));
		
		
		VBox comboboxes = new VBox(regionComboBoxes, label_boxState, label_boxArea, label_boxSubArea, 
										label_boxTopic, label_boxCategory, priceRangePane, setFiltersCheckBoxes());
		
		comboboxes.setStyle("-fx-border-color: red; -fx-background-color: lightgray;");
		
		/**
		 * Set the GUI to display selection, price range, and filter check boxes UI controls.
		 */
		pane.setLeft(comboboxes);
		// ============================================================================
		
		// ============================ Top Pane =========================================
		/**
		 * Create the tile of the GUI
		 */
		Region titleRTopPane = new Region();
		Label titleLable = new Label("Craigslist Web Scraper");
		titleLable.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 30));
		BorderPane titleBox = new BorderPane();
		titleBox.setLeft(titleRTopPane);
		titleBox.setCenter(titleLable);
		titleBox.setStyle("-fx-border-color: gray; -fx-background-color: orange;");
		titleBox.setPadding(new Insets(10));
		
		pane.setTop(titleBox);
		//===============================================================================
		
		// ================================ Bottom Pane ======================================
		/**
		 * Create a search field and a button to capture a customer's desired search term like a 
		 * 'cellphone' etc.. 
		 */
		String field = "Search for an item on Craigslist here ...";		
		keywordfield.setPromptText("Search for an item on Craigslist here ...");
		keywordfield.setPrefWidth(field.length() * 6);
		keywordfield.setPadding(new Insets(5));
		//Button button = new Button("Search");
		button.setPadding(new Insets(5));
		BorderPane keywordButton = new BorderPane();
		keywordButton.setLeft(keywordfield);
		keywordButton.setRight(button);
		//HBox keywordButton = new HBox(keywordfield, button);
		keywordButton.setPadding(new Insets(5));
		keywordButton.setStyle("-fx-border-color: gray; -fx-background-color: orange;");
		pane.setBottom(keywordButton);
		// ====================================================================================
		
		// ===================================== Right Pane ===================================
		/**
		 * Create UI tables and text fields to capture a customer's keyword for inclusion or 
		 * exclusion in the search request. 
		 */
 		pane.setRight(setRightPane());
 		// ====================================================================================
 		
		//========================== Event handlers ====================================
		/**
		 * Event listener for the area combobox, triggered whenever a change to the state of the areaselect 
		 * object occurs. If the areaselect is not empty and the item selected is not N/A, then set the area 
		 * of the search to the selected area and execute the updateSubAreas method.
		 */ 
		areaselect.setOnAction((e) -> {
			if(!areaselect.getItems().isEmpty()) {
				
				if(!areaselect.getSelectionModel().getSelectedItem().toString().contentEquals("N/A")) {
					//String s = (String) areaselect.getSelectedItem();
					String s = areaselect.getSelectionModel().getSelectedItem().toString();
					System.out.println(s);
					search.setArea(s);
					if (search.setSubAreaMap()) {
						updateSubAreas();
					}
				}
			}
		});
		
		/**
		 * Event listener for the subareaselect object. If subareaselect is not empty and not set to "N/A", then
		 * set the search subarea to the selected subarea. 
		 */
		subareaselect.setOnAction((e) -> {
			
			if (!subareaselect.getItems().isEmpty()) {
				
				if (!subareaselect.getSelectionModel().getSelectedItem().toString().contentEquals("N/A")) {
					
					String s = subareaselect.getSelectionModel().getSelectedItem().toString();
					System.out.println(s);
					search.setSubArea(s);
				}
			}
		});
		
		/**
		 * Event listener for the topic combobox. If the topicselect object is not empty and not set to "N/A", then
		 * set the search topic to the selected topic and execute the update categories commmand by calling
		 * the updateCategories() method. 
		 */		
		topicselect.setOnAction((e) -> {
			
			if (!topicselect.getItems().isEmpty()) {
				
				if (!topicselect.getSelectionModel().getSelectedItem().toString().contentEquals("N/A")) {
					
					String s = topicselect.getSelectionModel().getSelectedItem().toString();
					System.out.println(s);
					search.setTopic(s);
					categoryselect.getItems().clear();
					updateCategories();
				}
			}
		});
		
		/**
		 * Event listener for categoryselect. If categoryselect is not empty and is not "N/A", then
		 * set the category to selected category.
		 */		
		categoryselect.setOnAction((e) -> {
			
			if (!categoryselect.getItems().isEmpty()) {
				
				if (!categoryselect.getSelectionModel().getSelectedItem().toString().contentEquals("N/A")) {
					
					String s = categoryselect.getSelectionModel().getSelectedItem().toString();
					System.out.println(s);
					System.out.println(s);
					search.setCategory(s);
					//updateCategories();
				}
			}
			
		});
		
		
		/**
		 * Event listener for stateselect. If "Choose a state" is selected, then reset the state of the GUI
		 * Otherwise, reset the search and set the state to the new state selected
		 * and run the updateAreas() method.
		 */
		stateselect.setOnAction((e) -> {
					
			System.out.println(stateselect.getSelectionModel().getSelectedItem().toString());
			search = new Search();
			search.setState(stateselect.getSelectionModel().getSelectedItem().toString());
			System.out.println("Search state: " + search.getState());
			System.out.println("setAreaMap success: " + search.setAreaMap());
			areaselect.getItems().clear(); 
			subareaselect.getItems().clear(); 
			subareaselect.setValue("N/A");
			updateAreas();
	
		});
		
		/**
		 * Event listener for the Search button. This event listener executes a search query 
		 * for a customer's desired search item while also considering selected filters.  
		 */
		button.setOnAction((e) -> {
			
			if (!this.lastquery.isBlank())
				this.searchKeywordsPositive.remove(lastquery);
			String labeltext = this.keywordfield.getText();
			this.lastquery = labeltext;
			if (!this.searchKeywordsPositive.contains(labeltext)) {
				this.searchKeywordsPositive.add(labeltext);
			}
			String[] poswords = this.searchKeywordsPositive.toArray(new String[0]);
			String[] negwords = this.searchKeywordsNegative.toArray(new String[0]);
			this.checkBoxMap.put("hasImages", (this.hasImage == null) ? false : this.hasImage);
			this.checkBoxMap.put("multipleImagesOnly", (this.multipleImagesOnly == null) ? false : this.multipleImagesOnly);
			this.checkBoxMap.put("postedToday", (this.postedToday == null) ? false : this.postedToday);
			this.checkBoxMap.put("searchTitlesOnly",(this.searchTitlesOnly == null) ? false : this.searchTitlesOnly);
			this.checkBoxMap.put("bundleDuplicates", (this.bundleDuplicates == null) ? false : this.bundleDuplicates);
			this.checkBoxMap.put("hideAllDuplicates",(this.hideAllDuplicates == null) ? false : this.hideAllDuplicates);
			this.checkBoxMap.put("hasMakeModelOnly", (this.hasMakeModelOnly == null) ? false : this.hasMakeModelOnly);
			this.checkBoxMap.put("hasPhoneOnly",false);
			this.checkBoxMap.put("cryptoAccepted",false);
			this.checkBoxMap.put("deliveryAvailable",false);
			Options options = new Options(null, null, new float[2]);
			try {
				options = new Options(this.checkBoxMap, null, new float[]{minPriceField.getText().equals("") ? 0 : Float.parseFloat(minPriceField.getText()), maxPriceField.getText().equals("") ? Float.MAX_VALUE : Float.parseFloat(maxPriceField.getText())});
			}
			catch (NumberFormatException ie) {
				
			}
			String str = "";
			
			if (search.hasCategory()) {
				System.out.println("keyword: "  + labeltext + " search state: " + search.getState() + 
						" search area: " + search.getArea() + " search subarea: " + search.getSubArea() + 
						" search topic: " + search.getTopic() + " search category: " + search.getCategory());
						if (options.getMinPrice() != 0)
							str += " min price: $" + options.getMinPrice(); 
						if (options.getMaxPrice() != Float.MAX_VALUE)
							str += " max price: $" + options.getMaxPrice();
						System.out.println(str);

						SearchQuery q = new SearchQuery(poswords, negwords, options, search);
				try {
					q.getSearch();
				} catch (NullPointerException z) {
					z.printStackTrace();
				}
			} else {
				System.out.println("incomplete search");
			}
			
		});
		
		return pane;
	}
	// ========================================================================================================
	
	
	// =============================================== Helper methods =========================================
	/**
	 * If a selected state has cities and localities, then update the areaselect selection menu.
	 * @author Arti Shala, minor updates by Weezha Yahyapoor for converting it to JavaFX
	 */
	protected void updateAreas() {
		
		if (search.setAreaMap() == false) {
			areaselect.setValue("N/A");
			System.out.println("No areas, Search state: " + search.getState());
		} else {
			areaselect.getItems().clear();
			System.out.println("State has areas, state: " + search.getState());
			System.out.println("Has areas for state: " + search.setAreaMap() + " Areas: ");
			String[] areachoices = search.getAreaMap().keySet().toArray(new String[0]);
			Sorts.quickSort(areachoices, 0, areachoices.length - 1);
			for (String s : areachoices) {
				System.out.println(s);
				if (!s.equals("")) {
					areaselect.getItems().add(s);
				}
			}
			
			areaselect.getSelectionModel().select(0);
			search.setArea(areaselect.getSelectionModel().getSelectedItem().toString());
			System.out.println("Search area set: " + areaselect.getSelectionModel().getSelectedItem().toString());
			
		}
		
		if (search.setSubAreaMap()) {
			System.out.println(search.getState() + " " + search.getArea() + " has subareas, updating selector");
			updateSubAreas();
		} else {
			System.out.println("No subareas");
			subareaselect.getItems().clear(); 
			subareaselect.setValue("N/A"); 
		}
		search.setTopicMap();
		String[] topicchoices = search.getTopicMap().keySet().toArray(new String[0]);
		Sorts.quickSort(topicchoices, 0, topicchoices.length - 1);
		topicselect.getItems().clear(); 
		
		for (String topic : topicchoices) {
			System.out.println(topic);
			topicselect.getItems().add(topic); 
		}
			topicselect.setValue(topicselect.getItems().get(0).toString());
	}
	
	/**
	 * If a selected state has localities, then update the subareaselect selection menu.
	 * @author Arti Shala, minor updates by Weezha Yahyapoor for converting it to JavaFX
	 */
	protected void updateSubAreas() {
		String area = search.getArea();
		System.out.println();
		System.out.println("Has area: " + search.hasArea() + " Search area: " + area);
		String[] subareachoices = search.getSubAreaMap().keySet().toArray(new String[0]);
		Sorts.quickSort(subareachoices, 0, subareachoices.length - 1);
		subareaselect.getItems().clear(); 
		System.out.println("Choose from a list of sub areas for: " + search.getState() + " " + search.getArea());
		for (String subArea: subareachoices) {
			System.out.print(subArea + "     ");
			if (!subArea.contentEquals("")) {
				subareaselect.getItems().add(subArea);
			}
		}
		subareaselect.getSelectionModel().select(0);
		search.setSubArea(subareaselect.getSelectionModel().getSelectedItem().toString());
		
		//search.setSubArea(scan.nextLine());
		
	}

	/**
	 * If the selected customer topic is found, then update the cateogoryselect selection menu.
	 * @author Arti Shala, minor updates by Weezha Yahyapoor for converting it to JavaFX
	 */
	protected void updateCategories() {
		if (search.setCategoryMap() == false) {
			topicselect = new ComboBox<String>();
			topicselect.getItems().addAll(new String[] {"N/A"});
			System.out.println("setCategoryMap failed");
		} else {
			search.setCategoryMap();
			categoryselect.getItems().clear(); 
			String[] categorychoices = search.getCategoryMap().keySet().toArray(new String[0]);
			Sorts.quickSort(categorychoices, 0, categorychoices.length - 1);
			for (String s : categorychoices) {
				System.out.println(s);
				categoryselect.getItems().add(s); 
			}
			categoryselect.setValue(categoryselect.getItems().get(0).toString());
		}
	}
	
	
	/**
	 * This VBox contains the UI components to create check box filters and their associated event listeners. 
	 * @return filterBoxes, a VBox container.
	 * @author Weezha Yahyapoor
	 */
	protected VBox setFiltersCheckBoxes() {

 		//Insets(double top, double right, double bottom, double left)
 		// ============================= Adding filter check boxes =====================================
 		Region filterCheckBoxes = new Region();
 		filterCheckBoxes.setPadding(new Insets(5));


 		Label filterLabel = new Label("Apply a Filter:");
 		filterLabel.setPadding(new Insets(5));
 		filterLabel.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.REGULAR, 12));

 		// label = has images only
 		Label hasImagesOnly = new Label("Has images only:");
 		CheckBox cHasImagesOnly = new CheckBox();
 		BorderPane bHasImagesOnly = new BorderPane();
 		bHasImagesOnly.setPadding(new Insets(2,5,0,5));
 		bHasImagesOnly.setLeft(hasImagesOnly);
 		bHasImagesOnly.setRight(cHasImagesOnly);

 		// multiple images only
 		Label hasMultImagesOnly = new Label("Multiple images only:");
 		CheckBox cHasMultImagesOnly = new CheckBox();
 		BorderPane bHasMultImagesOnly = new BorderPane();
 		bHasMultImagesOnly.setPadding(new Insets(2,5,0,5));
 		bHasMultImagesOnly.setLeft(hasMultImagesOnly);
 		bHasMultImagesOnly.setRight(cHasMultImagesOnly);

 		// posted today
 		Label postedToday = new Label("Posted today:");
 		CheckBox cPostedToday = new CheckBox();
 		BorderPane bPostedToday = new BorderPane();
 		bPostedToday.setPadding(new Insets(2,5,0,5));
 		bPostedToday.setLeft(postedToday);
 		bPostedToday.setRight(cPostedToday);

 		// search titles only
 		Label searchTitleOnly = new Label("Search title only:");
 		CheckBox cSearchTitleOnly = new CheckBox();
 		BorderPane bSearchTitleOnly = new BorderPane();
 		bSearchTitleOnly.setPadding(new Insets(2,5,0,5));
 		bSearchTitleOnly.setLeft(searchTitleOnly);
 		bSearchTitleOnly.setRight(cSearchTitleOnly);

 		// bundle duplicates
 		Label bundleDuplicates = new Label("Bundle duplicates:");
 		CheckBox cBundleDuplicates = new CheckBox();
 		BorderPane bBundleDuplicates = new BorderPane();
 		bBundleDuplicates.setPadding(new Insets(2,5,0,5));
 		bBundleDuplicates.setLeft(bundleDuplicates);
 		bBundleDuplicates.setRight(cBundleDuplicates);


 		// hide all duplicates
 		Label hideAllDuplicates = new Label("Hide all duplicates:");
 		CheckBox cHideAllDuplicates = new CheckBox();
 		BorderPane bHideAllDuplicates = new BorderPane();
 		bHideAllDuplicates.setPadding(new Insets(2,5,0,5));
 		bHideAllDuplicates.setLeft(hideAllDuplicates);
 		bHideAllDuplicates.setRight(cHideAllDuplicates);

 		// has make/model only
 		Label hasMakeModelOnly = new Label("Has make/model only:");
 		CheckBox chasMakeModelOnly = new CheckBox();
 		BorderPane bHasMakeModelOnly = new BorderPane();
 		bHasMakeModelOnly.setPadding(new Insets(2,5,0,5));
 		bHasMakeModelOnly.setLeft(hasMakeModelOnly);
 		bHasMakeModelOnly.setRight(chasMakeModelOnly);

 		VBox filterBoxes = new VBox(filterCheckBoxes, filterLabel, bHasImagesOnly, bHasMultImagesOnly,
 				                   	bSearchTitleOnly, bPostedToday, bBundleDuplicates, 
 				                    bHideAllDuplicates, bHasMakeModelOnly);
 		//filterBoxes.setStyle("-fx-border-color: red; -fx-background-color: lightgray;");


 		//========================== Event handlers ========================================================
 		/**
 		 * Event listener for the "has images only CheckBox", triggered whenever a change to the state of the check box 
 		 * object changes.
 		 */ 
 		cHasImagesOnly.setOnAction((e) -> {

 			// add functionality here
 			if (cHasImagesOnly.isSelected()) {
 				this.hasImage = true;
 				System.out.println("Has images only was selected");

 			} else {
 				this.hasImage = false;
 				// do nothing
 			}

 		});

 		/**
 		 * Event listener for the "Multiple images only CheckBox", triggered whenever a change to the state of the check box 
 		 * object changes.
 		 */ 
 		cHasMultImagesOnly.setOnAction((e) -> {

 			// add functionality here
 			if (cHasMultImagesOnly.isSelected()) {
 				this.multipleImagesOnly = true;
 				System.out.println("Multiple images only was selected");

 			} else {
 				this.multipleImagesOnly = false;
 				// do nothing
 			}
 		});


 		/**
 		 * Event listener for the "Posted today CheckBox", triggered whenever a change to the state of the check box 
 		 * object changes.
 		 */ 
 		cPostedToday.setOnAction((e) -> {

 			// add functionality here
 			if (cPostedToday.isSelected()) {

 				System.out.println("Post today was selected");
 				this.postedToday = true;

 			} else {
 				this.postedToday = false;
 				// do nothing
 			}
 		});

 		/**
 		 * Event listener for the "Search title only CheckBox", triggered whenever a change to the state of the check box 
 		 * object changes.
 		 */ 
 		cSearchTitleOnly.setOnAction((e) -> {

 			// add functionality here
 			if (cSearchTitleOnly.isSelected()) {
 				this.searchTitlesOnly = true;
 				System.out.println("Search title only was selected");

 			} else {
 				this.searchTitlesOnly = false;
 				// do nothing
 			}
 		});

 		/**
 		 * Event listener for the "Bundle duplicates CheckBox", triggered whenever a change to the state of the check box 
 		 * object changes.
 		 */ 
 		cBundleDuplicates.setOnAction((e) -> {

 			// add functionality here
 			if (cBundleDuplicates.isSelected()) {
 				this.bundleDuplicates = true;
 				System.out.println("Bundle duplicates was selected");

 			} else {
 				this.bundleDuplicates = false;
 				// do nothing
 			}
 		});

 		/**
 		 * Event listener for the "Hide all duplicates CheckBox", triggered whenever a change to the state of the check box 
 		 * object changes.
 		 */ 
 		cHideAllDuplicates.setOnAction((e) -> {

 			// add functionality here
 			if (cHideAllDuplicates.isSelected()) {
 				this.hideAllDuplicates = true;
 				System.out.println("Hide all duplicates was selected");

 			} else {
 				this.hideAllDuplicates = false;
 				// do nothing
 			}
 		});

 		/**
 		 * Event listener for the "Has make/model only CheckBox", triggered whenever a change to the state of the check box 
 		 * object changes.
 		 */ 
 		chasMakeModelOnly.setOnAction((e) -> {

 			// add functionality here
 			if (chasMakeModelOnly.isSelected()) {
 				this.hasMakeModelOnly = true;
 				System.out.println("Has make/model was selected");

 			} else {
 				this.hasMakeModelOnly = false;
 				// do nothing
 			}
 		});

 		// return the VBox that contains all filter check boxes
 		return filterBoxes;

 	}
	
	/**
	 * Class used to represent a positive or negative keyword in the GUI table
	 * @author Weezha Yahyapoor
	 */
	class ObjectTable {

 		public String keyword = "empty";


 	    public ObjectTable() {
 	    	
 	    }

 	    public ObjectTable(String s) {
 	        this.keyword = s;
 	    }
 	    
 	    public String getKeyword() {
 	    	return this.keyword;
 	    }


 	}
	
	/**
	 * This BorderPane contains the UI components to create TableView objects and they keyword text fields for
	 * capturing a user's input. 
	 * @return pane, a BorderPane container.
	 * @author Weezha Yahyapoor and Arti Shala
	 */
	protected BorderPane setRightPane() {

 		BorderPane right = new BorderPane();

 		TextField aField = new TextField();
 		aField.setPromptText("Include these keywords");
 		aField.setPadding(new Insets(5));
 		aField.setPrefWidth(150);
 		Button aButton = new Button("Add");
 		aButton.setPadding(new Insets(5));
 		Button aClear = new Button("Clear");
 		aClear.setPadding(new Insets(5));
 		HBox aBox = new HBox(aField, aButton, aClear);
 		aBox.setPadding(new Insets(5));

 		TableView<ObjectTable> aTable = new TableView<ObjectTable>();
 		aTable.setPrefSize(250, 450);
 		aTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

 		TableView<ObjectTable> bTable = new TableView<ObjectTable>();
 		bTable.setPrefSize(250, 450);
 		bTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
 		// The input type to the TableColumns can be set to other objects type, 
 		// even user defined classes etc. 
         TableColumn<ObjectTable, String> col1a = new TableColumn<>("Include these keywords");
         col1a.setCellValueFactory(new Callback<CellDataFeatures<ObjectTable, String>, ObservableValue<String>>() {
             public ObservableValue<String> call(CellDataFeatures<ObjectTable, String> p) {
                 return new ReadOnlyObjectWrapper(p.getValue().getKeyword());
             }
          });
         //col2.setCellValueFactory(new PropertyValueFactory<>("sendCol"));

         aTable.getColumns().add(col1a);
         //aTable.getItems().add(new ObjectTable("Test"));
 		VBox aVBox = new VBox(aBox, aTable);
 		aVBox.setPadding(new Insets(5));
 		right.setLeft(aVBox);

 		//----
 		TextField bField = new TextField();
 		bField.setPromptText("Exclude these keywords");
 		bField.setPadding(new Insets(5));
 		bField.setPrefWidth(150);
 		Button bButton = new Button("Add");
 		bButton.setPadding(new Insets(5));
 		Button bClear = new Button("Clear");
 		bClear.setPadding(new Insets(5));
 		HBox bBox = new HBox(bField, bButton,bClear);
 		bBox.setPadding(new Insets(5));


 		// The input type to the TableColumns can be set to other objects type, 
 		// even user defined classes etc. 
         TableColumn<ObjectTable, String> col1b = new TableColumn<ObjectTable, String>("Exclude these keywords");
         col1b.setCellValueFactory(new Callback<CellDataFeatures<ObjectTable, String>, ObservableValue<String>>() {
             public ObservableValue<String> call(CellDataFeatures<ObjectTable, String> p) {
                 return new ReadOnlyObjectWrapper(p.getValue().getKeyword());
             }
          });
         //col2.setCellValueFactory(new PropertyValueFactory<>("sendCol"));

         bTable.getColumns().add(col1b);

         /**
          * Event handlers to capture a user's input for including or excluding a keyword in/from a search query.
          */
         aButton.setOnAction((e) -> {
        	 if (aField.getText().isBlank()) {
        		 //do nothing
        	 }
        	 else {
        		 aTable.getItems().add(new ObjectTable(aField.getText()));
        		 this.searchKeywordsPositive.add(aField.getText());
        	 }
         });
         bButton.setOnAction((e) -> {
        	 if (bField.getText().isBlank()) {
        		 //do nothing
        	 } else {
        		 bTable.getItems().add(new ObjectTable(bField.getText()));
        		 this.searchKeywordsNegative.add(bField.getText());
        	 }
         });
         aClear.setOnAction((e) -> {
        	 aTable.getItems().clear();
        	 this.searchKeywordsPositive.clear();
         });
         bClear.setOnAction((e) -> {
        	 bTable.getItems().clear();
        	 this.searchKeywordsNegative.clear();
         });
         VBox bVBox = new VBox(bBox, bTable);
         bVBox.setPadding(new Insets(5,5,0,0));
         right.setRight(bVBox);
 		return right;
 	}
}

