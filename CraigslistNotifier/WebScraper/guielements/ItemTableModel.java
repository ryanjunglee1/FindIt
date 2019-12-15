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

public class ItemTableModel extends AbstractTableModel {
		private static final long serialVersionUID = 1L;
		private static final String[] COLUMN_NAMES = new String[] {"Item Name", "Item Price", "Make/Model/Condition", "Show in CL","Description (100 char preview)","Image Preview"};
        private static final Class<?>[] COLUMN_TYPES = new Class<?>[] {String.class, String.class, String.class, JButton.class, String.class, JButton.class};
        private Object[][] data;
        private SearchResult result;
        
        public ItemTableModel(SearchResult result) {
        	this.data = result.makeData();
        	this.result = result;
        }
        
        @Override public int getColumnCount() {
            return COLUMN_NAMES.length;
        }

        @Override public int getRowCount() {
            return this.data.length;
        }

        @Override public String getColumnName(int columnIndex) {
            return COLUMN_NAMES[columnIndex];
        }

        @Override public Class<?> getColumnClass(int columnIndex) {
            return COLUMN_TYPES[columnIndex];
        }

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