import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Pattern;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;

/*
 * This class is used to contain an arrayList of items , the SearchQuery used to get those items, and update methods
 * that update the item list based on the update interval from the searchQuery, sends a notification to email upon
 * new items being added to the list
 * @author Arti Shala
 * @version 1.0
 */
public class SearchResult {
	//data fields
	protected ArrayList<Item> itemList;
	protected int resultSize;
	protected SearchQuery query;
	protected int updateInterval;
	protected LocalDateTime lastUpdated, nextUpdate;
	protected boolean willUpdate;
	protected String updateEmail;
	int count = 0;
	
	//GUI elements
	private JFrame frame = new JFrame();
	private JPanel notificationPanel = new JPanel();
	private JPanel panel = new JPanel();
	private JPanel container = new JPanel();
	private JLabel label = new JLabel("Enter your email for notifications");
	private JTextField email = new JTextField(20);
	private JComboBox updateIntervalField = new JComboBox(new Object[] {"1","5","10","60","120","720","1440"});
	private JLabel updateLabel1 = new JLabel("Check for updates every: ");
	private JLabel updateLabel2 = new JLabel("Minutes");
	private JButton updateButton = new JButton("Enable");
	private JTable table;
	private JScrollPane scrollpane;
	private String[] columnNames = {"Item Name", "Item Price", "button"};
	
	//initialize the searchresult gui with the list of items provided by searchquery
	public SearchResult(ArrayList<Item> results, String title, SearchQuery query) {
		this.query = query;
		//itemlist set by constructor args, JTable populated with makedata method of searchresult and tablemodel set with mouselistener
		this.itemList = results;
		this.resultSize = this.itemList.size();
		this.lastUpdated = LocalDateTime.now();
		table = new JTable(makeData(), columnNames);
		table.setModel(new ItemTableModel(this));
		table.addMouseListener(new JTableButtonMouseListener(this.table));
		
		//scrollpanel created and table added to scroll panel with button cell renderer
		scrollpane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		TableCellRenderer buttonRenderer = new JTableButtonRenderer();
		table.getColumn("button").setCellRenderer(buttonRenderer);
		
		//notification panel elements populated
		notificationPanel.add(label);
		notificationPanel.add(email);
		notificationPanel.add(updateLabel1);
		notificationPanel.add(updateIntervalField);
		notificationPanel.add(updateLabel2);
		notificationPanel.add(updateButton);
		updateButton.setBackground(Color.green);
		updateButton.setOpaque(true);
		updateButton.setBorderPainted(false);
		updateButton.addActionListener(new AbstractAction("Enable") {

			@Override
			public void actionPerformed(ActionEvent e) {
				Timer timer = new Timer();
				if (willUpdate) {
					willUpdate = false;
					System.out.println("disabled");
					updateButton.setBackground(Color.green);
					updateButton.setText("Enable");
					timer.cancel();
				} else {
					willUpdate = true;
					String inputEmail = email.getText();
					updateEmail = email.getText();
					updateButton.setBackground(Color.red);
					updateButton.setText("Disable");
					System.out.println("enabled: " + updateEmail);
					if (isValidEmail(inputEmail)) {
						updateInterval = Integer.parseInt(updateIntervalField.getSelectedItem().toString());
						System.out.println(updateInterval + " minute update interval");
						
						
						timer.schedule( new TimerTask() {
						    public void run() {
						    	if (willUpdate == false) {
						    		timer.cancel();
						    		count = 0;
						    		System.out.println("Updated " + count + " times");
						    	} else {
						    		System.out.println(updateInterval + " minutes have passed"); 
						    		count++;
						    		updateTable(query.updateSearch());
						    		
						    	}
						    }
						 }, 0, updateInterval * 60000);
						
						
						
					} else {
						updateEmail = null;
						willUpdate = false;
						updateButton.setBackground(Color.green);
						updateButton.setText("Enable");
						JOptionPane.showMessageDialog(null, inputEmail + " is not a valid email, please enter a valid email and try again. Updates currently disabled.");
						System.out.println("invalid email, disabled");
					}
					
					
				}
				
			}
			
		});
		notificationPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		
		//panel element (table) populated
		panel.add(scrollpane);
		
		//container layout set and panel + notificatonpanel added, container added to frame and frame initialized
		container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
		container.add(panel);
		container.add(notificationPanel);
		frame.add(container);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setTitle(title);
        frame.setSize(500, 650);
        //frame.pack();
        frame.setVisible(true);
		
	}
	
	public void updateTable(ArrayList<Item> items) {
		ArrayList<Item> originalList = this.itemList;
		this.itemList = items;
		table.removeAll();
		table.setModel(new ItemTableModel(this));
		table.addMouseListener(new JTableButtonMouseListener(this.table));
		TableCellRenderer buttonRenderer = new JTableButtonRenderer();
		table.getColumn("button").setCellRenderer(buttonRenderer);
		//table = new JTable(makeData(), columnNames);
		//table.setModel(new ItemTableModel(this));
		
	}
	
	public static boolean isValidEmail(String email) 
    { 
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+ 
                            "[a-zA-Z0-9_+&*-]+)*@" + 
                            "(?:[a-zA-Z0-9-]+\\.)+[a-z" + 
                            "A-Z]{2,7}$"; 
                              
        Pattern pat = Pattern.compile(emailRegex); 
        if (email == null) 
            return false; 
        return pat.matcher(email).matches(); 
    } 
	
	public void add(Item i) {
		this.itemList.add(i);
		this.resultSize++;
	}
	
	public void printItems() {
		Iterator itemIterator = itemList.iterator();
		while (itemIterator.hasNext()) {
			Item i = (Item) itemIterator.next();
			System.out.println(i);
		}
	}
	
	public Object[][] makeData() {
		Object[][] data = new Object[this.itemList.size()][3];
		for (int i = 0; i < this.itemList.size(); i++) {
			Item it = this.itemList.get(i);
			data[i][0] = it.itemName;
			data[i][1] = "$" + it.itemPrice;
			data[i][2] = new JButton();
		}
		return data;
	}
	
	protected static boolean openWebpage(URI uri) {
	    Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
	    if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
	        try {
	            desktop.browse(uri);
	            return true;
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	    return false;
	}
	
	
	private static class JTableButtonRenderer implements TableCellRenderer {        
	    @Override public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
	        JButton button = (JButton)value;
	        //button.addMouseListener(new JTableButtonMouseListener(table));
	        return button;  
	    }
	}


	public JTable getTable() {
		// TODO Auto-generated method stub
		return this.table;
	}
	
	private static class JTableButtonMouseListener extends MouseAdapter {
        private final JTable table;

        public JTableButtonMouseListener(JTable table) {
            this.table = table;
        }

        public void mouseClicked(MouseEvent e) {
            int column = table.getColumnModel().getColumnIndexAtX(e.getX()); // get the coloum of the button
            int row    = e.getY()/table.getRowHeight(); //get the row of the button

                    /*Checking the row or column is valid or not*/
            if (row < table.getRowCount() && row >= 0 && column < table.getColumnCount() && column >= 0) {
                Object value = table.getValueAt(row, column);
                if (value instanceof JButton) {
                    ((JButton)value).doClick();
                }
            }
        }
    }
	
	public Item getItem(int i) {
		return this.itemList.get(i);
	}
}
/*
 * the next block of code defines classes required to properly render the table onto the GUI
 */







