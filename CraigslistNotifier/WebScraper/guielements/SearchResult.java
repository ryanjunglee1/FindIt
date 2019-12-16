package guielements;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
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
import javax.swing.table.TableColumnModel;

import scraping.Item;
import searchelements.SearchQuery;

/**
 * An object that displays all the items generated through a SearchQuery in a table and allows the user to set a time
 * interval to update the items from that SearchQuery and be notified via email when new items are posted that fit the 
 * SearchQuery
 * @author Arti Shala
 * @version 1.0
 * @see SearchQuery
 */
public class SearchResult {
	//data fields
	private ArrayList<Item> itemList;
	private int resultSize;
	private SearchQuery query;
	private int updateInterval;
	private LocalDateTime lastUpdated, nextUpdate;
	private boolean willUpdate;
	private String updateEmail;
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
	
	/**
	 * Creates a SearchResult with the given items in an ArrayList and a searchquery used to get updates
	 * @param results all the items generated via the searchquery object
	 * @param title title to be set (first positive keyword)
	 * @param query the searchquery object that generated the results
	 */
	public SearchResult(ArrayList<Item> results, String title, SearchQuery query) {
		this.query = query;
		//itemlist set by constructor args, JTable populated with makedata method of searchresult and tablemodel set with mouselistener
		this.itemList = results;
		this.resultSize = this.itemList.size();
		this.lastUpdated = LocalDateTime.now();
		table = new JTable(makeData(), columnNames);
		table.setModel(new ItemTableModel(this));
		table.addMouseListener(new JTableButtonMouseListener(this.table));
		TableColumnModel tcmodel = table.getColumnModel();
		tcmodel.getColumn(0).setPreferredWidth(250);
		tcmodel.getColumn(1).setPreferredWidth(75);
		tcmodel.getColumn(2).setPreferredWidth(350);
		tcmodel.getColumn(3).setPreferredWidth(100);
		tcmodel.getColumn(4).setPreferredWidth(600);
		tcmodel.getColumn(5).setPreferredWidth(200);
		table.setRowHeight(35);
		
		//scrollpanel created and table added to scroll panel with button cell renderer
		scrollpane = new JScrollPane(table,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		table.setFillsViewportHeight(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		TableCellRenderer buttonRenderer = new JTableButtonRenderer();
		table.getColumn("Show in CL").setCellRenderer(buttonRenderer);
		table.getColumn("Image Preview").setCellRenderer(buttonRenderer);
		
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
		/**
		 * Creates a new thread and updates the table by calling the updateTable method
		 */
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
        frame.setSize(600, 650);
        //frame.pack();
        frame.setVisible(true);
		
	}
	
	/**
	 * Executes the updateSearch method of the searchquery and compares the resulting items with the items in the table currently
	 * and emails the user if a new item is added
	 * @param items the items of this searchresult to be compared with for new results
	 */
	public void updateTable(ArrayList<Item> items) {
		ArrayList<Item> originalList = this.itemList;
		LocalDateTime originalUpdateTime = this.lastUpdated;
		ArrayList<Item> newItems = new ArrayList<Item>();
		this.itemList = items;
		table.removeAll();
		table.setModel(new ItemTableModel(this));
		TableColumnModel tcmodel = table.getColumnModel();
		tcmodel.getColumn(0).setPreferredWidth(250);
		tcmodel.getColumn(1).setPreferredWidth(75);
		tcmodel.getColumn(2).setPreferredWidth(350);
		tcmodel.getColumn(3).setPreferredWidth(100);
		tcmodel.getColumn(4).setPreferredWidth(600);
		tcmodel.getColumn(5).setPreferredWidth(200);
		table.addMouseListener(new JTableButtonMouseListener(this.table));
		TableCellRenderer buttonRenderer = new JTableButtonRenderer();
		table.getColumn("Show in CL").setCellRenderer(buttonRenderer);
		table.getColumn("Image Preview").setCellRenderer(buttonRenderer);
		this.lastUpdated = LocalDateTime.now();
		System.out.println("Last updated: " + this.lastUpdated.toString());
		for (Item  i : this.itemList) {
			boolean foundequal = false;
			for (Item j : originalList) {
				if (i.equals(j))
					foundequal = true;
			}
			if (!foundequal)
				newItems.add(i);
		}
		System.out.println("--------NEW ITEMS----------");
		String emailsubject = "Your " + this.query.getSearchObject().getState() + " " + this.query.getSearchObject().getCategory() + " search has new items!";
		String emailbody = "";
		for (Item i : newItems) {
			System.out.println("-----------");
			System.out.println("Item: " + i.toString() + " post date: " + i.getDateTimePosted() + " update date: " + i.getDateTimeUpdated());
			System.out.println("original time: " + originalUpdateTime.toString());
			System.out.println("-----------");
			try {
			emailbody = emailbody + i.getItemName() + " $" + i.getItemPrice() + "\n Posted: " + i.getDateTimePosted().toString() + "\n" +
					i.getItemURL() + "\n" + "\n";
			} catch (NullPointerException z) {
				z.printStackTrace();
			}
			// TODO Send email containing new items and fix new item condition
		}
		if (!newItems.isEmpty()) {
	    	MailSender sender = new MailSender(updateEmail, emailsubject, emailbody);
	    	System.out.println("Email sent: " + sender.sendMail());
		}
			
		//table = new JTable(makeData(), columnNames);
		//table.setModel(new ItemTableModel(this));
		
	}
	
	/**
	 * A method that uses regex to check if a string fits the expected format of an email
	 * @param email the string to check
	 * @return true if the string is valid format for an email, false otherwise
	 */
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
	
	/**
	 * Adds an item to the results
	 * @param i item to be added
	 */
	public void add(Item i) {
		this.itemList.add(i);
		this.resultSize++;
	}
	
	/**
	 * Prints all the items in the results
	 */
	public void printItems() {
		Iterator itemIterator = itemList.iterator();
		while (itemIterator.hasNext()) {
			Item i = (Item) itemIterator.next();
			System.out.println(i);
		}
	}
	
	/**
	 * Generates a two dimensional Object Array that represents all the items in the results
	 * Used to properly format the JTable using itemtablemodel
	 * @return a two dimensional Object array containing all the items with formatted data for the table
	 * @see ItemTableModel
	 */
	public Object[][] makeData() {
		Object[][] data = new Object[this.itemList.size()][7];
		for (int i = 0; i < this.itemList.size(); i++) {
			Item it = this.itemList.get(i);
			data[i][0] = it.getItemName();
			String myItemPrice = "" + it.getItemPrice();
			data[i][1] = "$" + (myItemPrice.indexOf('.') > myItemPrice.length() - 3 ? myItemPrice + "0" : myItemPrice);
			data[i][2] = (it.getMake().contentEquals("") ? "" : "Make: " + it.getMake() + "/") + 
					(it.getModel().contentEquals("") ? "" : "Model: " + it.getModel() + "/") + 
					(it.getCondition().contentEquals("") ? "" : "Condition: " + it.getCondition()); 
			data[i][3] = new JButton();
			try {
				data[i][4] = it.getDescription().substring(0,100);
			} catch (Exception e) {
				data[i][4] = it.getDescription();
			}
			try {
			data[i][5] = it.getItemThumbs().get(0);
			data[i][6] = it.getFullsizeimg();
			} catch (Exception e) {
				data[i][5] = "";
				data[i][6]= "";
			}
					
		}
		return data;
	}
	
	/**
	 * if possible, opens a web page in the default browser using a given URI
	 * @param uri the website to open
	 * @return true if successful, false if otherwise
	 */
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
	
	/**
	 * Class used to allow the JTable to display buttons correctly and respond to clicks
	 * @author Arti Shala
	 *
	 */
	private static class JTableButtonRenderer implements TableCellRenderer {        
	    @Override public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
	        JButton button = (JButton)value;
	        //button.addMouseListener(new JTableButtonMouseListener(table));
	        return button;  
	    }
	}

	/**
	 * 
	 * @return the table object
	 */
	public JTable getTable() {
		// TODO Auto-generated method stub
		return this.table;
	}
	
	/**
	 * Listens for mouseclicks on a specific table cell and engages the button action of that cell
	 * @author Arti Shala
	 *
	 */
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
	
	/**
	 * @return the item at the specified index
	 */
	public Item getItem(int i) {
		return this.itemList.get(i);
	}
}








