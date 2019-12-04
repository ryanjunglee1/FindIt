import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
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
	protected LocalTime lastUpdated, updateInterval, nextUpdate;
	protected boolean willUpdate;
	protected String updateEmail;
	
	//GUI elements
	private JFrame frame = new JFrame();
	private JPanel panel = new JPanel();
	private JLabel label = new JLabel("Test");
	private JTable table;
	private JScrollPane scrollpane;
	private String[] columnNames = {"Item Name", "Item Price", "button"};
	
	//initialize the searchresult gui with the list of items provided by searchquery
	public SearchResult(ArrayList<Item> results, String title) {
		this.itemList = results;
		this.resultSize = this.itemList.size();
		this.lastUpdated = LocalTime.now();
		table = new JTable(makeData(), columnNames);
		table.setModel(new ItemTableModel(this));
		table.addMouseListener(new JTableButtonMouseListener(this.table));
		//table.setDefaultEditor(Object.class, null);
		scrollpane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		TableCellRenderer buttonRenderer = new JTableButtonRenderer();
		table.getColumn("button").setCellRenderer(buttonRenderer);
		panel.add(label);
		panel.add(scrollpane);
		frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setTitle(title);
        frame.setSize(500, 500);
        //frame.pack();
        frame.setVisible(true);
		
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
			String myItemPrice = "" + it.itemPrice;
			data[i][1] = "$" + (myItemPrice.indexOf('.') > myItemPrice.length() - 3 ? myItemPrice + "0" : myItemPrice);
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







