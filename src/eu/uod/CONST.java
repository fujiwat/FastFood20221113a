package eu.uod;

public class CONST {

    // 1. Window
    static final int windowSizeW = 700;
    static final int windowSizeH = 600;

    // 2. Left Pane
    // 2.1. JTable
    static final int headerHeight = 40;
    static final int columnWidthMenuItem = 150;
    static final int columnWidthQty = 30;
    static final int columnWidthSinglePrice = 90;
    static final int columnWidthLineTotal = 90;

    // 3. Right Pane
    static final int imageW = 100;
    static final int imageH = 100;
    static final int iconW = 120;
    static final int iconH = 130;

    // 4. confirmation dialog panel
    static final int confirmImageW = (int)(imageW * 3.5);
    static final int confirmImageH = (int)(imageH * 3.5);
    static final int confirmIconW = (int)(iconW * 3.5);
    static final int confirmIconH = (int)(iconH * 3.5);

    // 5. Currency
    static final String currency = "HUF";               // currency name EUR, HUF, ...
    static final int afterTheDecimalPoint = 0;          // support decimal point.   in case of EUR: 2, HUF: 0, JPY: 0

    // 6. Tab name
    static final String tabName_Burger = "Burger";
    static final String tabName_Drink = "Drink";
    static final String tabName_Special = "Special";

}
