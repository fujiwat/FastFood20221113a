package eu.uod;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class CustomTableCellRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (isSelected) {
            setForeground(table.getSelectionForeground());
            setBackground(table.getSelectionBackground());
        } else {
            setForeground(table.getForeground());
            setBackground((row % 2 == 1) ? Color.LIGHT_GRAY : table.getBackground());
        }
        switch ( column ){
            case OrderTable.column_menuItem:
                setHorizontalAlignment(JLabel.LEFT);
                break;
            case OrderTable.column_qty:
                setHorizontalAlignment(JLabel.CENTER);
                break;
            case OrderTable.column_singlePrice:
                setHorizontalAlignment(JLabel.RIGHT);
                break;
            case OrderTable.column_lineTotal:
                setHorizontalAlignment(JLabel.RIGHT);
                break;
            default:
        }
        return this;
    };
}