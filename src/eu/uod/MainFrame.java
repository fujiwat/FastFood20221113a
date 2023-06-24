package eu.uod;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.function.Function;

import static java.lang.Math.min;

public class MainFrame extends JFrame {
    private JButton button1;
    private JPanel panelMain;
    private JTabbedPane tabbedPane1;
    private JTree tree1;
    private JList listBurger;
    private JTable table1;
    private JLabel labelTotalPrice;
    private JButton buttonOrderClear;
    private JList listDrink;
    private JPanel panelRight;
    private JPanel PanelLeft;
    private JList listSpecial;
    private OrderTable orderTable;
    private Double totalPrice;

    public MainFrame() {
        // 1. Initialize value
        totalPrice = 0.;

        // 1. initialize orderTable
        table1.setEnabled(false);
        Object[] columnHeader = { "  Menu Item  ", "<html><center>Qty</center></html>", "<html><center>Single<br>Price</center></html>", "<html><center>Line<br>Total</center></html>" };
        DefaultTableModel tableModel = new DefaultTableModel(columnHeader, 0);
        table1.setModel(tableModel);

        // 1.1. set Header Height
        Dimension d = table1.getPreferredSize();
        d.height = CONST.headerHeight;
        table1.getTableHeader().setPreferredSize( d );

        // 1.2. set Header Column width
        table1.getColumnModel().getColumn(0).setPreferredWidth(CONST.columnWidthMenuItem);
        table1.getColumnModel().getColumn(1).setPreferredWidth(CONST.columnWidthQty);
        table1.getColumnModel().getColumn(2).setPreferredWidth(CONST.columnWidthSinglePrice);
        table1.getColumnModel().getColumn(3).setPreferredWidth(CONST.columnWidthLineTotal);

        // 1.3. Set column alignment( CENTER, RIGHT), set column background color, alternatively
        orderTable = new OrderTable(table1);
        CustomTableCellRenderer cellRenderer = new CustomTableCellRenderer();
        table1.setDefaultRenderer( Object.class, cellRenderer );

        // 2. initialize tab icons
        setMenuIcon(CONST.tabName_Burger);
        setMenuIcon(CONST.tabName_Drink);
        setMenuIcon(CONST.tabName_Special);

        // 3. Display initial total price as the correct currency.
        labelTotalPrice.setText("Total Price: " + NumberFormat.getPriceStringWithCurrency(0.));

        // 9. main Window


        // Listener
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if ( totalPrice == 0. ) {
                    JOptionPane.showMessageDialog((Frame) (MainFrame.getFrames()[0]),
                            "Please choose your favorite food from a picture panel.",
                            "Ordering Information",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    int result = JOptionPane.showConfirmDialog((Frame) (MainFrame.getFrames()[0]),
                            "You are going to order: " + NumberFormat.getPriceStringWithCurrency(totalPrice) + "\n" +
                                    "(You can change order by click 'Cancel'" +
                                    "\n\n" +
                                    "Are you sure to order this?",
                            "Ordering Question",
                            JOptionPane.OK_CANCEL_OPTION,
                            JOptionPane.QUESTION_MESSAGE);
                    if ( result == JOptionPane.OK_OPTION ) {
                        JOptionPane.showMessageDialog((Frame) (MainFrame.getFrames()[0]),
                                "Your order is transferred to the cook." +
                                "\n\n" +
                                        "Please wait.",
                                "Ordering Information",
                                JOptionPane.INFORMATION_MESSAGE);
                        totalPrice = 0.;
                        orderTable.clearTable();
                        tabbedPane1.setSelectedIndex(0);        // set tab page to the top page.
                        labelTotalPrice.setText("Total Price: " + NumberFormat.getPriceStringWithCurrency(0.));
                    }
                }
            }
        });

        listBurger.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                String menuItem;
                menuItem = (String) listBurger.getSelectedValue();
                showConfirmDialog(menuItem);
                listBurger.clearSelection();
            }
        });

        listDrink.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                String menuItem;
                menuItem = (String) listDrink.getSelectedValue();
                showConfirmDialog(menuItem);
                listDrink.clearSelection();
            }
        });

        listSpecial.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                String menuItem;
                menuItem = (String) listSpecial.getSelectedValue();
                showConfirmDialog(menuItem);
                listSpecial.clearSelection();
            }
        });

        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                JTable table1 = (JTable)e.getSource();
                table1.clearSelection();
                int row = table1.rowAtPoint( e.getPoint() );
                String menuItem = (String)table1.getValueAt(row, OrderTable.column_menuItem);
                showConfirmDialog(menuItem);
                table1.clearSelection();
            }
        });

        buttonOrderClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if ( totalPrice != 0 ) {
                    int result = JOptionPane.showConfirmDialog((Frame) (MainFrame.getFrames()[0]),
                            "Clear the Order List:" + "\n\n"+
                                    "If you want to change order, click 'Cancel'" +
                                    "\n\n" +
                                    "Are you sure to Clear the Order?",
                            "Ordering Question",
                            JOptionPane.OK_CANCEL_OPTION,
                            JOptionPane.QUESTION_MESSAGE);
                    if (result == JOptionPane.OK_OPTION) {
                        // cancel order - all clear
                        totalPrice = 0.;
                        orderTable.clearTable();
                        tabbedPane1.setSelectedIndex(0);            // set tab page to the top
                        labelTotalPrice.setText("Total Price: " + NumberFormat.getPriceStringWithCurrency(0.));
                    }
                }
            }
        });
    }

    private void setMenuIcon(String tabName) {
        int menuItemLength;
        Function<Integer, String> getMenuItem;
        JList list = null;

        switch (tabName) {
            case CONST.tabName_Burger:
                list = listBurger;
                menuItemLength = MenuMaster.menuItemBurgerLength();
                getMenuItem = idx -> MenuMaster.getMenuItemBurger(idx);
                break;
            case CONST.tabName_Drink:
                list = listDrink;
                menuItemLength = MenuMaster.menuItemDrinkLength();
                getMenuItem = idx -> MenuMaster.getMenuItemDrink(idx);
                break;
            case CONST.tabName_Special:
                list = listSpecial;
                menuItemLength = MenuMaster.menuItemSpecialLength();
                getMenuItem = idx -> MenuMaster.getMenuItemSpecial(idx);
                break;
            default:
                throw new IndexOutOfBoundsException();
        }
        // 1. initialize tab icons
        DefaultListModel listModel;
        listModel = new DefaultListModel();
        for (int i = 0; i<menuItemLength; i++ ) {
            listModel.addElement(getMenuItem.apply(i));
        }
        list.setModel(listModel);

        // 2.  Initialize right side ICONs (photo)
        list.setFixedCellHeight(CONST.iconH);
        list.setFixedCellWidth(CONST.iconW);
        list.setCellRenderer(new theListCellRenderer(tabName));
        list.setVisibleRowCount(0);

    }

    public void showConfirmDialog(String menuItem) {
        int qty = 0;
        double singlePrice;
        double lineTotal;
        if ( menuItem != null ) {
            MenuConfirm dialog = new MenuConfirm();
            int rowIdx = orderTable.getIndex(menuItem);
            if (0 <= rowIdx) {  // found in the orderTable
                qty = orderTable.getQty(rowIdx);
            }
            singlePrice = new MenuMaster().getSinglePrice(menuItem);
            lineTotal = singlePrice * qty;
            dialog.setQty(qty);
            dialog.setSinglePrice(singlePrice);
            dialog.setLineTotal(lineTotal);
            dialog.setImage(menuItem);
            dialog.showDialog((Frame) (MainFrame.getFrames()[0]));      // show the photo dialog
            qty = dialog.getQty();
            singlePrice = dialog.getSinglePrice();
            lineTotal = dialog.getLineTotal();
            orderTable.addOrUpdateRow(menuItem, qty, singlePrice, qty * singlePrice);
        }
        totalPrice = 0.;
        for (int i=0; i<table1.getRowCount(); i++ ) {
            totalPrice += NumberFormat.getDoublePriceFromPriceString((String)table1.getValueAt(i, OrderTable.column_lineTotal));
        }
        labelTotalPrice.setText("Total Price: " + NumberFormat.getPriceStringWithCurrency(totalPrice));
    }

    // constructor
    public MainFrame(String title){
        super();
        this.setTitle(title);
    }

    public String getOrderTable() {
        return this.orderTable.toString();
    }

    public static void main(String[] args) {
        MainFrame frame = new MainFrame("Programming 3 Project");
        frame.setContentPane(new MainFrame().panelMain);
        frame.setDefaultCloseOperation((JFrame.EXIT_ON_CLOSE));

        frame.pack();

        frame.setVisible(true);
//        frame.setSize(CONST.windowSizeW, CONST.windowSizeH);
        frame.setLocationRelativeTo(null);      // to the center in the screen
    }
    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}

class theListCellRenderer extends DefaultListCellRenderer {

    public theListCellRenderer(String name) {
        this.setText(name);

        // テキストを下に表示する
        setVerticalTextPosition(BOTTOM);
        // テキスト中央揃え
        setHorizontalTextPosition(CENTER);
        // アイコン中央揃え
        setVerticalAlignment(CENTER);
        setHorizontalAlignment(CENTER);
    }

    public Component getListCellRendererComponent(JList list, Object value,
                                                  int index, boolean isSelected, boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected,
                cellHasFocus);

        Icon icon = null;
        Image img = null;
        BufferedImage bImg = null;
        try {
            bImg = ImageIO.read(new File("images\\" + value + ".jpg"));
        } catch (IOException e) {
        }
        if ( bImg == null ) { // try again with png file
            try {
                bImg = ImageIO.read(new File("images\\" + value + ".png"));
            } catch (IOException e) {
            }
        }

        if ( bImg != null ) {
            double scaleFactorX = (double)CONST.imageW / bImg.getWidth();
            double scaleFactorY = (double)CONST.imageH / bImg.getHeight();
            double scaleFactor = min( scaleFactorX, scaleFactorY);
            int scaledX = (int)(bImg.getWidth() * scaleFactor + 0.5);
            int scaledY = (int)(bImg.getHeight() * scaleFactor + 0.5);
            img = bImg.getScaledInstance(scaledX, scaledY, Image.SCALE_SMOOTH);
            if (img != null) {
                icon = new ImageIcon(img);
            }
        }
        setIcon(icon);
        return this;
    }
}
