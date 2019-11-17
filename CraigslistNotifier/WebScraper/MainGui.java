import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
	private int clicks = 0;
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
		
		button.addActionListener(this);
		search = new Search();
		String[] statechoices = search.getStateMap().keySet().toArray(new String[0]);
		String[] topicchoices = search.getTopicMap().keySet().toArray(new String[0]);
		String[] testchoices = {"TEST", "TEST2", "TEST3"};
		stateselect = new JComboBox<String>(statechoices);
		stateselect.addItem("Choose a state");
		stateselect.setSelectedIndex(stateselect.getItemCount() - 1);
		areaselect = new JComboBox<String>(testchoices);
		subareaselect = new JComboBox<String>(testchoices);
		topicselect = new JComboBox<String>(testchoices);
		categoryselect = new JComboBox<String>(testchoices);
		/*
		areaselect.addActionListener(new AbstractAction("areaselect") {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (areaselect.getSelectedItem() == "N/A") {
					//set a topic for the search without any specific area
				} else {
					System.out.println("Selected Item: " + (String)areaselect.getSelectedItem());
					search.setArea((String)areaselect.getSelectedItem());
					System.out.println("Search Area: " + search.getArea());
					System.out.println("setSubAreaMap success: " + search.setSubAreaMap());
					//areaselect.removeAllItems();
					//updateAreas();
				}
			}
		});
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
		clicks++;
		String labeltext = this.keywordfield.getText();
		String[] guiTest = {labeltext};
		String combotest = (String) this.stateselect.getSelectedItem();
		System.out.println(combotest);
		System.out.println(e.getSource().getClass());
		System.out.println(e.getSource());
		//Search s = SearchQuery.newSearch();
		//SearchQuery q = new SearchQuery(guiTest);
		//q.getSearch();
        //label.setText(labeltext);
		
		
	}
	
	protected void updateAreas() {
		if (search.setAreaMap() == false) {
			areaselect.addItem("N/A");
			search = new Search();
			search.setState((String) stateselect.getSelectedItem());
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
			subareaselect.addItem(subArea);
		}
		//search.setSubArea(scan.nextLine());
		
	}
	
	protected void updateCategories() {
		String topic = (String) topicselect.getSelectedItem();
		search.setTopic(topic);
		if (search.setCategoryMap() == false) {
			topicselect = new JComboBox<String>(new String[]{"N/A"});
		} else {
			search.setCategoryMap();
			String[] categorychoices = search.getCategoryMap().keySet().toArray(new String[0]);
			categoryselect = new JComboBox<String>(categorychoices);
		}
	}
	
	/*
	 * creates a new GUI
	 */
	public static void main(String[] args) {
        new MainGui();
    }

}
