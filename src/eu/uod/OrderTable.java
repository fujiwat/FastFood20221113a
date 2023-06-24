package eu.uod;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class OrderTable {
    private JTable jTable;
    public static final int column_menuItem = 0;
    public static final int column_qty = 1;
    public static final int column_singlePrice = 2;
    public static final int column_lineTotal = 3;
    public static final int column_count = 4;

    public OrderTable(JTable jTable) {
        this.jTable = jTable;
    }

    public void clearTable() {
        for (int i=jTable.getRowCount()-1; 0<=i; --i) {
            DefaultTableModel tableModel = (DefaultTableModel)(jTable.getModel());
            tableModel.removeRow(i);
        }
    }

    public int addOrUpdateRow(String menuItem, int qty, double singlePrice, double lineTotal) {

        // step 1.  find the row, if there is same menuItem
        int rowCount = jTable.getRowCount();
        int rowWalk;
        DefaultTableModel tableModel = (DefaultTableModel)(jTable.getModel());

        for (rowWalk = 0; rowWalk < rowCount; rowWalk++) {
            if ( jTable.getValueAt(rowWalk, column_menuItem).equals(menuItem) ) {
                break;
            }
        }
        if ( rowWalk != rowCount ) {
            // exist - edit it
            if ( qty == 0 ) {
                tableModel.removeRow(rowWalk);
            } else {
                jTable.setValueAt(Integer.toString(qty), rowWalk, column_qty);
                jTable.setValueAt(NumberFormat.getPriceStringWithCurrency(lineTotal),
                        rowWalk, column_lineTotal);
            }
        } else {
            // not exist - add it
            if (qty != 0) {
                // add
                tableModel.addRow(new Object[]{
                        menuItem,
                        Integer.toString(qty),
                        NumberFormat.getPriceStringWithCurrency(singlePrice),
                        NumberFormat.getPriceStringWithCurrency(lineTotal)
                });
                rowWalk = jTable.getRowCount() - 1;
            } else {
                // not exist and do not need to add it.
                rowWalk = -1;
            }
        }
        return rowWalk;
    }

    public int getIndex(String menuItem) {
        int rowCount = jTable.getRowCount();
        int rowWalk;
        for (rowWalk = 0; rowWalk < rowCount; rowWalk++) {
            if ( jTable.getValueAt(rowWalk, column_menuItem).equals(menuItem) ) {
                break;
            }
        }
        if ( rowWalk  != rowCount ) {
            // found - return the index
            return rowWalk;
        }
        return -1;     // not found.
    }

    public int getQty(int idx) {
        String str = jTable.getValueAt(idx, column_qty).toString();
        return Integer.valueOf(str);
    }

    public double getSinglePrice(int idx) {
        return NumberFormat.getDoublePriceFromPriceString(jTable.getValueAt(idx, column_singlePrice).toString());
    }

    public double getLineTotal(int idx) {
        return NumberFormat.getDoublePriceFromPriceString(jTable.getValueAt(idx, column_lineTotal).toString());
    }
}
