import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
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
	private JLabel label = new JLabel("Click to search: ");
	private JFrame frame = new JFrame();
	public JTextField textfield;
	
	/*
	 * Creates a new instance of the main GUI, creates a JPanel with the textfield,  label and button,
	 * adds an actionListener to the button, adds the panel to the GUI and sets the size and visibility
	 * @see actionPerformed
	 */
	public MainGui() {
		JButton button = new JButton("Search");
		button.addActionListener(this);
		
		textfield = new JTextField(20);
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createEmptyBorder(30,30,10,10));
		panel.setLayout(new GridLayout(1, 3));
        panel.add(label);
        panel.add(button);
        panel.add(textfield);
        // set up the frame and display it
        frame.add(panel, BorderLayout.CENTER);
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
		String labeltext = this.textfield.getText();
		String[] guiTest = {labeltext};
		Search s = SearchQuery.newSearch();
		SearchQuery q = new SearchQuery(guiTest,s);
		q.getSearch();
        label.setText(labeltext);
		
		
	}
	
	/*
	 * creates a new GUI
	 */
	public static void main(String[] args) {
        new MainGui();
    }

}
