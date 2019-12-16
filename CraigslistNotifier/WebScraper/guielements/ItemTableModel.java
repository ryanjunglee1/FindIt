package guielements;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

import javafx.scene.image.Image;

/**
 * A table model that is used to set the appearance of the table in results and display Items accurately 
 * @author Arti Shala
 *
 */
public class ItemTableModel extends AbstractTableModel {
		//data fields
		private static final long serialVersionUID = 1L;
		//Column headers
		private static final String[] COLUMN_NAMES = new String[] {"Item Name", "Item Price", "Make/Model/Condition", "Show in CL","Description (100 char preview)","Image Preview"};
		//Column display datatypes
        private static final Class<?>[] COLUMN_TYPES = new Class<?>[] {String.class, String.class, String.class, JButton.class, String.class, JButton.class};
        private Object[][] data;
        private SearchResult result;
        
        /**
         * Creates the itemtablemodel based on the data given in the SearchResult object
         * @param result the serachresult to base the table on
         */
        public ItemTableModel(SearchResult result) {
        	this.data = result.makeData();
        	this.result = result;
        }
        
        /**
         * @return the amount of columns in the table based on how many column headers there are
         */
        @Override public int getColumnCount() {
            return COLUMN_NAMES.length;
        }
        /**
         * @return the amount of rows based on how many items are in the data
         */
        @Override public int getRowCount() {
            return this.data.length;
        }
        
        /**
         * @return column names based on column headers above
         */
        @Override public String getColumnName(int columnIndex) {
            return COLUMN_NAMES[columnIndex];
        }
        
        /**
         * @return the datatype of the display in a column
         */
        @Override public Class<?> getColumnClass(int columnIndex) {
            return COLUMN_TYPES[columnIndex];
        }
        
        /**
         * @return the value to be displayed in each cell, based on row and column index
         */
        @Override public Object getValueAt(final int rowIndex, final int columnIndex) {
                /*Adding components*/
            switch (columnIndex) {
                case 0: return this.data[rowIndex][0];
                case 1: return this.data[rowIndex][1];
                case 2: return this.data[rowIndex][2];
                case 3: final JButton button = new JButton(COLUMN_NAMES[columnIndex]);
                        button.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent arg0) {
                                result.getItem(rowIndex).openPage();
                            }
                        });
                        return button;
                case 4: return this.data[rowIndex][4];
                case 5: final JButton imgbutton = new JButton("Click for Full Size");
	                	try {
								ImageIcon imgico = new ImageIcon(new URL((String) this.data[rowIndex][5]));
								imgbutton.setIcon(imgico);
								imgbutton.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent arg0) {
										try {
											result.getItem(rowIndex).openFullImg();
										} catch (URISyntaxException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
										
									}
								});
								return imgbutton;
							} catch (MalformedURLException e) {
								// TODO Auto-generated catch block
								return null;
							}
                default: return "Error";
            }
        }
        
        
    }