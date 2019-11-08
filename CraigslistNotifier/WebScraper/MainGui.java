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

public class MainGui implements ActionListener{
	private int clicks = 0;
	private JLabel label = new JLabel("Click to search: ");
	private JFrame frame = new JFrame();
	public JTextField textfield;
	
	public MainGui() {
		JButton button = new JButton("Search");
		button.addActionListener(this);
		
		textfield = new JTextField(20);
		//textfield.addActionListener();
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
	
	public static void main(String[] args) {
        new MainGui();
    }

}
