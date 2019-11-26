import java.awt.BorderLayout;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

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
	private String[] columnNames = {"Item Name", "Item Price"};
	
	//initialize the searchresult gui with the list of items provided by searchquery
	public SearchResult(ArrayList<Item> results, String title) {
		this.itemList = results;
		this.lastUpdated = LocalTime.now();
		table = new JTable(makeData(), columnNames);
		scrollpane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		panel.add(label);
		panel.add(scrollpane);
		frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setTitle(title);
        frame.setSize(500, 500);
        //frame.pack();
        frame.setVisible(true);
		//this.query = q;
	}
	
	public void add(Item i) {
		this.itemList.add(i);
	}
	
	public void printItems() {
		Iterator itemIterator = itemList.iterator();
		while (itemIterator.hasNext()) {
			Item i = (Item) itemIterator.next();
			System.out.println(i);
		}
	}
	
	public Object[][] makeData() {
		Object[][] data = new Object[this.itemList.size()][2];
		for (int i = 0; i < this.itemList.size(); i++) {
			Item it = this.itemList.get(i);
			data[i][0] = it.itemName;
			data[i][1] = it.itemPrice;
		}
		return data;
	}
}
