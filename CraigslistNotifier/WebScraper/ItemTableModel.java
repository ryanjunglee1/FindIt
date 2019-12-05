import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

public class ItemTableModel extends AbstractTableModel {
		private static final long serialVersionUID = 1L;
		private static final String[] COLUMN_NAMES = new String[] {"Item Name", "Item Price", "Show in CL"};
        private static final Class<?>[] COLUMN_TYPES = new Class<?>[] {String.class, String.class, JButton.class};
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
                case 2: final JButton button = new JButton(COLUMN_NAMES[columnIndex]);
                        button.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent arg0) {
                                result.getItem(rowIndex).openPage();
                            }
                        });
                        return button;
                default: return "Error";
            }
        }
        
        
    }