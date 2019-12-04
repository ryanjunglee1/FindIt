
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
	private Button button = new Button("Search");
	
	@Override
	public void start(Stage primaryStage) {
		
		// Create a scene and place it in the stage.
		Scene scene = new Scene(guiLayout(), 600, 400);
		
		primaryStage.setTitle("Web Scraper"); 	// set stage title
		primaryStage.setScene(scene); 			// Place the scene in the stage
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

	
		Region regionComboBoxes = new Region();
		regionComboBoxes.setPadding(new Insets(5));
		VBox comboboxes = new VBox(regionComboBoxes, label_boxState, label_boxArea, label_boxSubArea, 
																	label_boxTopic, label_boxCategory);
		
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
			
			if (search.hasCategory()) {
				System.out.println("keyword: "  + labeltext + " search state: " + search.getState() + 
						" search area: " + search.getArea() + " search subarea: " + search.getSubArea() + 
						" search topic: " + search.getTopic() + " search category: " + search.getCategory()); 
				SearchQuery q = new SearchQuery(guiTest, search);
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
			for (String s : categorychoices) {
				System.out.println(s);
				categoryselect.getItems().add(s); 
			}
			categoryselect.setValue(categoryselect.getItems().get(0).toString());
		}
	}
}

