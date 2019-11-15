import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
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
		String[] testchoices = {"TEST", "TEST2", "TEST3"};
		stateselect = new JComboBox<String>(statechoices);
		stateselect.addItem("Choose a state");
		stateselect.setSelectedIndex(stateselect.getItemCount() - 1);
		stateselect.addActionListener(new AbstractAction("test") {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Selected Item: " + (String)stateselect.getSelectedItem());
				search = new Search();
				search.setState((String)stateselect.getSelectedItem());
				System.out.println("Search state: " + search.getState());
				System.out.println("setAreaMap success: " + search.setAreaMap());
				//String[] areachoices = search.getAreaMap().keySet().toArray(new String[0]);
				//System.out.println(areachoices[0]);
				//search.setAreaMap();
				//System.out.println(search.hasArea());
				//updateAreas();
			}
		});
		updateAreas();
		subareaselect = new JComboBox<String>(testchoices);
		topicselect = new JComboBox<String>(testchoices);
		categoryselect = new JComboBox<String>(testchoices);
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
		if (stateselect.getSelectedIndex() == stateselect.getItemCount() - 1) {
			areaselect = new JComboBox<String>(new String[]{"N/A"});
		} else {
			//search.setState((String)stateselect.getSelectedItem());
			System.out.println(search.getState());
			System.out.println(search.setAreaMap());
			String[] areachoices = search.getAreaMap().keySet().toArray(new String[0]);
			areaselect = new JComboBox<String>(areachoices);
		}
	}
	
	protected void updateSubAreas() {
		String area = (String) areaselect.getSelectedItem();
		System.out.println(area);
		search.setArea(area);
		System.out.println("Has area: " + search.hasArea);
		if (search.setSubAreaMap() == false) {
			subareaselect = new JComboBox<String>(new String[]{"N/A"});
		} else {
			System.out.println(search.setSubAreaMap());
			String[] subareachoices = search.getSubAreaMap().keySet().toArray(new String[0]);
			subareaselect = new JComboBox<String>(subareachoices);
		}
		search.setTopicMap();
		String[] topics = search.getTopicMap().keySet().toArray(new String[0]);
		topicselect = new JComboBox<String>(topics);
		updateCategories();
		
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
