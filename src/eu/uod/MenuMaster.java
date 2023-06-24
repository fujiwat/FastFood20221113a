package eu.uod;

import static java.lang.Math.min;

public class MenuMaster {
    static private String[] menuItemBurgerList = {
            "Bacon Cheese Burger",
            "Big Meat Burger",
            "Cheese Burger",
            "Double Big Burger",
            "Double Cheese Burger",
            "Double Pork Burger",
            "Fried Chicken Burger",
            "Grilled Beef Burger",
            "Grilled Pork Burger",
            "Health Burger",
            "Light Burger",
            "Special Burger",
            "Special Veggie Burger",
            "Veggie Burger",
    };
    static private double singlePriceBurgerList[] = {
            10000000, 1001, 1002, 1003, 1004, 1005, 1006, 1007, 1008, 1009, 1010, 1011, 1012, 1013
    };
    static private String[] menuItemDrinkList = {
            "Coca Cola 330ml",
            "Coca Cola Zero 330ml",
            "Sprite 330ml",
            "Sprite Zero 330ml",
            "Fanta Orange 330ml",
    };
    static private double singlePriceDrinkList[] = {
            500, 501, 502, 503, 504
    };
    static private String[] menuItemSpecialList = {
            "Crispy Fried Chicken",
            "Fried Chicken 2 pcs",
            "French Fries Potato (L)",
            "French Fries Potato (S)"
    };
    static private double singlePriceSpecialList[] = {
            601, 602, 603, 604
    };
// -----------------------------------
    static public int menuItemBurgerLength() {
        return min(menuItemBurgerList.length, singlePriceBurgerList.length);
    }

    static public double getSinglePriceBurger(String menuItem) {
        for (int i = 0; i<min(menuItemBurgerList.length, singlePriceBurgerList.length); i++ ) {
            if ( menuItemBurgerList[i].equals(menuItem) ) {
                return singlePriceBurgerList[i];
            }
        }
        return 0;
    }

    static public String getMenuItemBurger(int idx) {
        if ( idx < menuItemBurgerList.length ) {
            return menuItemBurgerList[idx];
        } else {
            return null;
        }
    }

    // -----------------------------------
    static public int menuItemDrinkLength() {
        return min(menuItemDrinkList.length, singlePriceDrinkList.length);
    }

    static public double getSinglePriceDrink(String menuItem) {
        for (int i = 0; i<min(menuItemDrinkList.length, singlePriceDrinkList.length); i++ ) {
            if ( menuItemDrinkList[i].equals(menuItem) ) {
                return singlePriceDrinkList[i];
            }
        }
        return 0;
    }

    static public String getMenuItemDrink(int idx) {
        if ( idx < menuItemDrinkList.length ) {
            return menuItemDrinkList[idx];
        } else {
            return null;
        }
    }

// -----------------------------------
    static public int menuItemSpecialLength() {
        return min(menuItemSpecialList.length, singlePriceSpecialList.length);
    }

    static public double getSinglePriceSpecial(String menuItem) {
        for (int i = 0; i<min(menuItemSpecialList.length, singlePriceSpecialList.length); i++ ) {
            if ( menuItemSpecialList[i].equals(menuItem) ) {
                return singlePriceSpecialList[i];
            }
        }
        return 0;
    }

    static public String getMenuItemSpecial(int idx) {
        if ( idx < menuItemSpecialList.length ) {
            return menuItemSpecialList[idx];
        } else {
            return null;
        }
    }
// ---------------------------
    static public double getSinglePrice(String menuItem) {
        double price = 0;
        price = getSinglePriceBurger(menuItem);
        if ( price == 0 ) {
            price = getSinglePriceDrink(menuItem);
        }
        if ( price == 0 ) {
            price = getSinglePriceSpecial(menuItem);
        }
        return price;
    }

}
