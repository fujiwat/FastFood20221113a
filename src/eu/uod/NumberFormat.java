package eu.uod;

import java.text.DecimalFormat;

public class NumberFormat {
    public static String getPriceStringWithCurrency(double price) {
        DecimalFormat formatString;
        if ( CONST.afterTheDecimalPoint == 0) {
            formatString = new DecimalFormat("#,##0");
        } else {
            formatString = new DecimalFormat("#,##0." + "0".repeat(CONST.afterTheDecimalPoint));
        }
        return formatString.format(price) + " " + CONST.currency;
    }

    public static Double getDoublePriceFromPriceString(String str) {
        String withoutComma = str.replaceAll(",", "");
        return Double.valueOf(withoutComma.replace(" " + CONST.currency, ""));
    }
}
