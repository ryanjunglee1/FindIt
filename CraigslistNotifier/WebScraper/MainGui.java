import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.ItemSelectable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/*
 * The main GUI that will be used to accept parameters from the program user and create a searchQuery, then 
 * display the SearchResult in a meaningful way to the user. 
 * @author Arti Shala
 * @version 1.0
 */
public class MainGui implements ActionListener{
	
	//Define all GUI elements
	private JFrame frame = new JFrame();
	private JPanel basicsearch = new JPanel();
	private JLabel keywordlabel = new JLabel("Enter search keyword: ");
	private JLabel statelabel = new JLabel("Choose state: ");
	private JComboBox<String> stateselect;
	private JLabel arealabel = new JLabel("Choose area: ");
	private JComboBox<String> areaselect;
	private JLabel subarealabel = new JLabel("Choose sub area: ");
	private JComboBox<String> subareaselect;
	private JLabel topiclabel = new JLabel("Choose topic: ");
	private JComboBox<String> topicselect;
	private JLabel categorylabel = new JLabel("Choose category: ");
	private JComboBox<String> categoryselect;
	private JTextField keywordfield = new JTextField(20);
	private JButton button = new JButton("Search");
	public Search search;
	
	/*
	 * Creates a new instance of the main GUI, creates a JPanel with the textfield,  label and button,
	 * adds an actionListener to the button, adds the panel to the GUI and sets the size and visibility
	 * @see actionPerformed
	 */
	public MainGui() {
		
		button.addActionListener(this); //adds the actionPerformed method to the button
		search = new Search(); //reset the search object
		String[] statechoices = search.getStateMap().keySet().toArray(new String[0]); //define options for state choice jcombobox
		String[] testchoices = {"N/A"}; //default jcombobox options
		stateselect = new JComboBox<String>(statechoices); //add states to state jcombobox
		stateselect.addItem("Choose a state");
		stateselect.setSelectedIndex(stateselect.getItemCount() - 1); //sets the default selected item to "Choose a state"
		areaselect = new JComboBox<String>(testchoices);
		subareaselect = new JComboBox<String>(testchoices);
		topicselect = new JComboBox<String>(testchoices);
		categoryselect = new JComboBox<String>(testchoices);
		
		/*
		 * listener for the area jcombobox, triggered whenever a change to the state of the areaselect object occurs
		 * if the areaselect is not empty and the item selected is not N/A, then set the area of the search to the selected
		 * area and if applicable, execute the updateSubAreas method
		 */
		ItemListener arealistener = new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (areaselect.getItemCount() != 0) {
					if (!areaselect.getSelectedItem().toString().equals("N/A")) {
						String s = (String) areaselect.getSelectedItem();
						System.out.println(s);
						search.setArea(s);
						if (search.setSubAreaMap()) {
							updateSubAreas();
						}
					}
				}
				
			}
			
		};
		areaselect.addItemListener(arealistener);
		
		/*
		 * listener for the subareaselect object, if subareaselect is not empty and not set to "N/A" then
		 * set the search subarea to the selected subarea 
		 */
		ItemListener subarealistener = new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (subareaselect.getItemCount() != 0) {
					if (!subareaselect.getSelectedItem().toString().equals("N/A")) {
						String s = (String) subareaselect.getSelectedItem();
						System.out.println(s);
						search.setSubArea(s);
					}
				}
				
			}
			
		};
		subareaselect.addItemListener(subarealistener);
		
		/*
		 * listener for the topic combobox, if the topicselect object is not empty and not set to "N/A" then
		 * set the search topic to the selected topic and execute the update categories commmand
		 */
		ItemListener topiclistener = new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (topicselect.getItemCount() != 0) {
					if (!topicselect.getSelectedItem().toString().equals("N/A")) {
						String s = (String) topicselect.getSelectedItem();
						System.out.println(s);
						search.setTopic(s);
						categoryselect.removeAllItems();
						updateCategories();
					}
				}
				
			}
			
		};
		topicselect.addItemListener(topiclistener);
		
		/*
		 * itemListener for categoryselect, if categoryselect is not empty and is not "N/A" set the category to selected category 
		 */
		ItemListener categorylistener = new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (categoryselect.getItemCount() != 0) {
					if (!categoryselect.getSelectedItem().toString().equals("N/A")) {
						String s = (String) categoryselect.getSelectedItem();
						System.out.println(s);
						search.setCategory(s);
						//updateCategories();
					}
				}
				
			}
			
		};
		categoryselect.addItemListener(categorylistener);
		
		/*
		 * action listener for stateselect, if "Choose a state" is selected, reset the state of the GUI, otherwise
		 * reset the search and set the state to the new state selected and run the updateAreas method
		 */
		stateselect.addActionListener(new AbstractAction("test") {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (stateselect.getSelectedItem() == "Choose a state") {
					areaselect.removeAllItems();
					areaselect.addItem("N/A");
					subareaselect.removeAllItems();
					subareaselect.addItem("N/A");
					topicselect.removeAllItems();
					topicselect.addItem("N/A");
					categoryselect.removeAllItems();
					categoryselect.addItem("N/A");
					search = new Search();
				} else {
					System.out.println("Selected Item: " + (String)stateselect.getSelectedItem());
					search = new Search();
					search.setState((String)stateselect.getSelectedItem());
					System.out.println("Search state: " + search.getState());
					System.out.println("setAreaMap success: " + search.setAreaMap());
					areaselect.removeAllItems();
					subareaselect.removeAllItems();
					subareaselect.addItem("N/A");
					updateAreas();
				}
			}
		});
		//updateSubAreas();
		
		//adds the elements to the panel in grid layout
		basicsearch.setBorder(BorderFactory.createEmptyBorder(30,30,10,10));
		basicsearch.setLayout(new GridLayout(0, 2));
		basicsearch.add(statelabel);
		basicsearch.add(stateselect);
		basicsearch.add(arealabel);
		basicsearch.add(areaselect);
		basicsearch.add(subarealabel);
		basicsearch.add(subareaselect);
		basicsearch.add(topiclabel);
		basicsearch.add(topicselect);
		basicsearch.add(categorylabel);
		basicsearch.add(categoryselect);
		basicsearch.add(keywordlabel);
		basicsearch.add(keywordfield);
        basicsearch.add(button);
        // set up the frame and display it
        frame.add(basicsearch, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("GUI");
        frame.setSize(500, 500);
        frame.pack();
        frame.setVisible(true);
	}
	
	/*
	 * creates a new search from the newSearch method, creates a searchQuery from the search and from the 
	 * text entered by the User, runs the getSearch method of the searchQuery created, and sets the label text 
	 * to the text entered by the User
	 * @param e is the actionEvent performed, in this case, button click
	 * @see SearchQuery
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String labeltext = this.keywordfield.getText();
		String[] guiTest = {labeltext};
		if (search.hasCategory) {
			System.out.println("keyword: "  + labeltext + " search state: " + search.getState() + 
					" search area: " + search.getArea() + " search subarea: " + search.getSubArea() + 
					" search topic: " + search.getTopic() + " search category: " + search.getCategory()); 
			SearchQuery q = new SearchQuery(guiTest,search);
			q.getSearch();
		} else {
			System.out.println("incomplete search");
		}
        //label.setText(labeltext);
		
		
	}
	
	protected void updateAreas() {
		if (search.setAreaMap() == false) {
			areaselect.addItem("N/A");
			System.out.println("No areas, Search state: " + search.getState());
		} else {
			areaselect.removeAllItems();
			//search.setState((String)stateselect.getSelectedItem());
			System.out.println("State has areas, state: " + search.getState());
			System.out.println("Has areas for state: " + search.setAreaMap() + " Areas: ");
			String[] areachoices = search.getAreaMap().keySet().toArray(new String[0]);
			for (String s : areachoices) {
				System.out.println(s);
				if (!s.equals(""))
					areaselect.addItem(s);
			}
			areaselect.setSelectedIndex(0);
			search.setArea(areaselect.getSelectedItem().toString());
			System.out.println("Search area set: " + areaselect.getSelectedItem().toString());
		}
		
		if (search.setSubAreaMap()) {
			System.out.println(search.getState() + " " + search.getArea() + " has subareas, updating selector");
			updateSubAreas();
		} else {
			System.out.println("No subareas");
			subareaselect.removeAllItems();
			subareaselect.addItem("N/A");
		}
		search.setTopicMap();
		String[] topicchoices = search.getTopicMap().keySet().toArray(new String[0]);
		topicselect.removeAllItems();
		for (String topic : topicchoices) {
			//System.out.println(topic);
			System.out.println(topic);
			topicselect.addItem(topic);
		}
	}
	
	protected void updateSubAreas() {
		String area = search.getArea();
		System.out.println();
		System.out.println("Has area: " + search.hasArea + " Search area: " + area);
		String[] subareachoices = search.getSubAreaMap().keySet().toArray(new String[0]);
		subareaselect.removeAllItems();
		System.out.println("Choose from a list of sub areas for: " + search.getState() + " " + search.getArea());
		for (String subArea: subareachoices) {
			System.out.print(subArea + "     ");
			if (!subArea.contentEquals(""))
				subareaselect.addItem(subArea);
		}
		subareaselect.setSelectedIndex(0);
		search.setSubArea(subareaselect.getSelectedItem().toString());
		
		//search.setSubArea(scan.nextLine());
		
	}
	
	protected void updateCategories() {
		if (search.setCategoryMap() == false) {
			topicselect = new JComboBox<String>(new String[]{"N/A"});
			System.out.println("setCategoryMap failed");
		} else {
			search.setCategoryMap();
			categoryselect.removeAllItems();
			String[] categorychoices = search.getCategoryMap().keySet().toArray(new String[0]);
			for (String s : categorychoices) {
				System.out.println(s);
				categoryselect.addItem(s);
			}
		}
	}
	
	/*
	 * creates a new GUI
	 */
	public static void main(String[] args) {
        new MainGui();
    }

}
