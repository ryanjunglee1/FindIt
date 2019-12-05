
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

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
	
	@Override
	public void start(Stage primaryStage) {
		
		// Create a scene and place it in the stage.
		Scene scene = new Scene(guiLayout(), 800, 600);
		
		primaryStage.setTitle("Web Scraper"); 	// set stage title
		primaryStage.setScene(scene); 			// Place the scene in the stage
		primaryStage.setResizable(true);
		primaryStage.show(); 					// Display the stage
		
	}


	public static void main(String[] args) {
		
		launch(args);
	}
	
	public BorderPane guiLayout() {
		
		// Create the main BorderPane to host all UI controls
		BorderPane pane = new BorderPane();
		
		//================================== Left Pane ================================	
		/*
		 * Creating the GUI components for different selection menu (ComboBoxes)
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
		
		areaselect.setValue("Choose area");
		areaselect.setPrefWidth(stateselect.getPrefWidth());
		Label areaLable = new Label("Choose area:");
		areaLable.setPadding(new Insets(5));
		BorderPane label_boxArea = new BorderPane();
		label_boxArea.setLeft(areaLable);
		label_boxArea.setRight(areaselect);
		
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
		
		categoryselect.setValue("Choose category");
		categoryselect.setPrefWidth(stateselect.getPrefWidth());
		Label categorylabel = new Label("Choose category:");
		categorylabel.setPadding(new Insets(5));
		BorderPane label_boxCategory = new BorderPane();
		label_boxCategory.setLeft(categorylabel);
		label_boxCategory.setRight(categoryselect);
		
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
		FlowPane textPricePane = new FlowPane(dollarSign, minPriceField, to, maxPriceField);
		textPricePane.setPrefWidth(182);
		BorderPane priceRangePane = new BorderPane();
		priceRangePane.setLeft(priceRange);
		priceRangePane.setRight(textPricePane);

	
		Region regionComboBoxes = new Region();
		regionComboBoxes.setPadding(new Insets(5));
		priceRangePane.setPadding(new Insets(20,0,0,0));
		VBox comboboxes = new VBox(regionComboBoxes, label_boxState, label_boxArea, label_boxSubArea, 
																	label_boxTopic, label_boxCategory, priceRangePane, setFiltersCheckBoxes());
		
		comboboxes.setStyle("-fx-border-color: red; -fx-background-color: lightgray;");
		pane.setLeft(comboboxes);
		// ============================================================================
		
		// ============================ Top Pane =========================================
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
 		pane.setRight(setRightPane());
 		// ====================================================================================
 		
		//========================== Event handlers ====================================
		/*
		 * Event listener for the area combobox, triggered whenever a change to the state of the areaselect 
		 * object occurs. If the areaselect is not empty and the item selected is not N/A, then set the area 
		 * of the search to the selected area and if applicable, execute the updateSubAreas method
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
		
		/*
		 * Event listener for the subareaselect object, if subareaselect is not empty and not set to "N/A" then
		 * set the search subarea to the selected subarea 
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
		
		/*
		 * Event listener for the topic combobox, if the topicselect object is not empty and not set to "N/A" then
		 * set the search topic to the selected topic and execute the update categories commmand
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
		
		/*
		 * Event listener for categoryselect, if categoryselect is not empty and is not "N/A" set the category to selected category 
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
		
		
		/*
		 * Event listener for stateselect, if "Choose a state" is selected, reset the state of the GUI, otherwise
		 * reset the search and set the state to the new state selected and run the updateAreas method
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
		
		/*
		 * Event listener for the search button
		 */
		button.setOnAction((e) -> {
			
			String labeltext = this.keywordfield.getText();
			String[] guiTest = {labeltext};
			Options options = new Options(null, null, new float[]{minPriceField.getText().equals("") ? 0 : Float.parseFloat(minPriceField.getText()), maxPriceField.getText().equals("") ? Float.MAX_VALUE : Float.parseFloat(maxPriceField.getText())});
			
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

						SearchQuery q = new SearchQuery(guiTest, options, search);
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
	
	
	//Checkbox VBOX creator
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


 		// original images only
 		Label hasOrigImagesOnly = new Label("Original images only:");
 		CheckBox cHasOrigImagesOnly = new CheckBox();
 		BorderPane bHasOrigImagesOnly = new BorderPane();
 		bHasOrigImagesOnly.setPadding(new Insets(2,5,0,5));
 		bHasOrigImagesOnly.setLeft(hasOrigImagesOnly);
 		bHasOrigImagesOnly.setRight(cHasOrigImagesOnly);

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
 				                    bHasOrigImagesOnly, bSearchTitleOnly, bBundleDuplicates, 
 				                    bHideAllDuplicates, bHasMakeModelOnly);
 		//filterBoxes.setStyle("-fx-border-color: red; -fx-background-color: lightgray;");


 		//========================== Event handlers ========================================================
 		/*
 		 * Event listener for the "has images only CheckBox", triggered whenever a change to the state of the check box 
 		 * object changes.
 		 */ 
 		cHasImagesOnly.setOnAction((e) -> {

 			// add functionality here
 			if (cHasImagesOnly.isSelected()) {

 				System.out.println("Has images only was selected");

 			} else {

 				// do nothing
 			}

 		});

 		/*
 		 * Event listener for the "Multiple images only CheckBox", triggered whenever a change to the state of the check box 
 		 * object changes.
 		 */ 
 		cHasMultImagesOnly.setOnAction((e) -> {

 			// add functionality here
 			if (cHasMultImagesOnly.isSelected()) {

 				System.out.println("Multiple images only was selected");

 			} else {

 				// do nothing
 			}
 		});


 		/*
 		 * Event listener for the "Original images only CheckBox", triggered whenever a change to the state of the check box 
 		 * object changes.
 		 */ 
 		cHasOrigImagesOnly.setOnAction((e) -> {

 			// add functionality here
 			if (cHasOrigImagesOnly.isSelected()) {

 				System.out.println("Original images only was selected");

 			} else {

 				// do nothing
 			}
 		});

 		/*
 		 * Event listener for the "Posted today CheckBox", triggered whenever a change to the state of the check box 
 		 * object changes.
 		 */ 
 		cPostedToday.setOnAction((e) -> {

 			// add functionality here
 			if (cPostedToday.isSelected()) {

 				System.out.println("Post today was selected");

 			} else {

 				// do nothing
 			}
 		});

 		/*
 		 * Event listener for the "Search title only CheckBox", triggered whenever a change to the state of the check box 
 		 * object changes.
 		 */ 
 		cSearchTitleOnly.setOnAction((e) -> {

 			// add functionality here
 			if (cSearchTitleOnly.isSelected()) {

 				System.out.println("Search title only was selected");

 			} else {

 				// do nothing
 			}
 		});

 		/*
 		 * Event listener for the "Bundle duplicates CheckBox", triggered whenever a change to the state of the check box 
 		 * object changes.
 		 */ 
 		cBundleDuplicates.setOnAction((e) -> {

 			// add functionality here
 			if (cBundleDuplicates.isSelected()) {

 				System.out.println("Bundle duplicates was selected");

 			} else {

 				// do nothing
 			}
 		});

 		/*
 		 * Event listener for the "Hide all duplicates CheckBox", triggered whenever a change to the state of the check box 
 		 * object changes.
 		 */ 
 		cHideAllDuplicates.setOnAction((e) -> {

 			// add functionality here
 			if (cHideAllDuplicates.isSelected()) {

 				System.out.println("Hide all duplicates was selected");

 			} else {

 				// do nothing
 			}
 		});

 		/*
 		 * Event listener for the "Has make/model only CheckBox", triggered whenever a change to the state of the check box 
 		 * object changes.
 		 */ 
 		chasMakeModelOnly.setOnAction((e) -> {

 			// add functionality here
 			if (chasMakeModelOnly.isSelected()) {

 				System.out.println("Has make/model was selected");

 			} else {

 				// do nothing
 			}
 		});

 		// return the VBox that contains all filter check boxes
 		return filterBoxes;

 	}
	
	class ObjectTable {

 		private String s1 = "empty";
 	    private double d1 = 0.0;

 	    public ObjectTable() {
 	    }

 	    public ObjectTable(String s1, double d1) {
 	        this.s1 = s1;
 	        this.d1 = d1;
 	    }


 	}
	
	protected BorderPane setRightPane() {

 		BorderPane right = new BorderPane();

 		TextField aField = new TextField();
 		aField.setPromptText("Include these keywords");
 		aField.setPadding(new Insets(5));
 		aField.setPrefWidth(150);
 		Button aButton = new Button("Add");
 		aButton.setPadding(new Insets(5));
 		HBox aBox = new HBox(aField, aButton);
 		aBox.setPadding(new Insets(5));

 		TableView aTable = new TableView();
 		aTable.setPrefSize(250, 450);
 		aTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

 		TableView bTable = new TableView();
 		bTable.setPrefSize(250, 450);
 		bTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
 		// The input type to the TableColumns can be set to other objects type, 
 		// even user defined classes etc. 
         TableColumn<String, ObjectTable> col1a = new TableColumn<>("First Column");
         //col1.setCellValueFactory(new PropertyValueFactory<>("firstCol"));
         TableColumn<String, ObjectTable> col2a = new TableColumn<>("Second Column");
         //col2.setCellValueFactory(new PropertyValueFactory<>("sendCol"));

         aTable.getColumns().add(col1a);
         aTable.getColumns().add(col2a);
         aTable.getItems().add(new ObjectTable("Row1", 10.0));
         aTable.getItems().add(new ObjectTable("Row2", 10.0));
 		VBox aVBox = new VBox(aBox, aTable);
 		aVBox.setPadding(new Insets(5));
 		right.setLeft(aVBox);

 		//----
 		TextField bField = new TextField();
 		bField.setPromptText("Exclude these keywords");
 		bField.setPadding(new Insets(5));
 		bField.setPrefWidth(150);
 		Button bButton = new Button("Add");
 		aButton.setPadding(new Insets(5));
 		HBox bBox = new HBox(bField, bButton);
 		bBox.setPadding(new Insets(5));


 		// The input type to the TableColumns can be set to other objects type, 
 		// even user defined classes etc. 
         TableColumn<String, ObjectTable> col1b = new TableColumn<>("First Column");
         //col1.setCellValueFactory(new PropertyValueFactory<>("firstCol"));
         TableColumn<String, ObjectTable> col2b = new TableColumn<>("Second Column");
         //col2.setCellValueFactory(new PropertyValueFactory<>("sendCol"));

         bTable.getColumns().add(col1b);
         bTable.getColumns().add(col2b);

         bTable.getItems().add(new ObjectTable("Row1", 20.0));
         bTable.getItems().add(new ObjectTable("Row2", 20.0));

         VBox bVBox = new VBox(bBox, bTable);
         bVBox.setPadding(new Insets(5,5,0,0));
         right.setRight(bVBox);
 		return right;
 	}
}

