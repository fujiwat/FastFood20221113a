package eu.uod;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static java.lang.Math.min;

public class MenuConfirm extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JList list1;
    private JButton buttonMinus;
    private JButton buttonPlus;
    private JLabel labelQty;
    private JLabel labelSinglePrice;

    private int qty;
    private double singlePrice;
    private double lineTotal;
    private int prevQty;

    public MenuConfirm() {
        this.setUndecorated(true);  // remove title bar of the window
        buttonMinus.setMargin(new Insets(0,0,6,0)); // position up for '-' button
        buttonPlus.setMargin(new Insets(8,2,8,2)); // position up for '+' button
        buttonOK.setMargin(new Insets(17,2,17,2)); // position up for '+' button
        buttonCancel.setMargin(new Insets(17,2,17,2)); // position up for '+' button
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        buttonPlus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { onPlus(); }
        });
        buttonMinus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { onMinus(); }
        });
        list1.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                list1.clearSelection();
            }
        });
    }

    public void setImage(String menuName) {
        DefaultListModel listModel;
        listModel = new DefaultListModel();
        listModel.addElement(menuName);
        list1.setModel(listModel);
        list1.setFixedCellHeight(CONST.confirmIconH);
        list1.setFixedCellWidth(CONST.confirmIconW);
        list1.setCellRenderer(new MenuConfirm_theListCellRenderer("confirmPhoto"));
        list1.setVisibleRowCount(0);
    }

    public void setQty(int qty) {
        this.prevQty = qty;
        this.qty = qty;
    }

    public void setSinglePrice(double singlePrice) {
        this.singlePrice = singlePrice;
    }

    public void setLineTotal(double lineTotal) {
        this.lineTotal = lineTotal;
    }

    public int getQty() {
        return this.qty;
    }

    public double getSinglePrice() {
        return this.singlePrice;
    }

    public double getLineTotal() {
        return this.lineTotal;
    }

    public void showDialog(Frame frame) {
        labelQty.setText("Quantity: " + qty);
        labelSinglePrice.setText( "Single Price: "
                + NumberFormat.getPriceStringWithCurrency(singlePrice));
        buttonOK.setEnabled(false);
        this.pack();
        setLocationRelativeTo(frame);
        this.setVisible(true);
    }

    private void onPlus() {
        if ( qty < 10 ) {
            qty++;
            labelQty.setText("Quantity: " + qty);
            buttonOK.setEnabled(true);
        }
    }

    private void onMinus() {
        if ( 0 < qty ) {
            qty--;
            labelQty.setText("Quantity: " + qty);
            buttonOK.setEnabled(true);
        }
    }

    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        qty = prevQty;
        dispose();
    }

    public static void main(String[] args) {
        MenuConfirm dialog = new MenuConfirm();
        dialog.pack();
        dialog.setVisible(true);
        System.out.println("now exit");
        System.exit(0);
    }
}

class MenuConfirm_theListCellRenderer extends DefaultListCellRenderer {

    public MenuConfirm_theListCellRenderer(String name) {
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
        if ( bImg == null ) {   // try again with png file
            try {
                bImg = ImageIO.read(new File("images\\" + value + ".png"));
            } catch (IOException e) {
            }
        }
        if ( bImg != null ) {
            double scaleFactorX = (double)CONST.confirmImageH / bImg.getWidth();
            double scaleFactorY = (double)CONST.confirmImageW / bImg.getHeight();
            double scaleFactor = min( scaleFactorX, scaleFactorY);
            int scaledX = (int)(bImg.getWidth() * scaleFactor);
            int scaledY = (int)(bImg.getHeight() * scaleFactor);
            img = bImg.getScaledInstance(scaledX, scaledY, Image.SCALE_SMOOTH);
            if (img != null) {
                icon = new ImageIcon(img);
            }
        }
        setIcon(icon);
        return this;
    }
}

