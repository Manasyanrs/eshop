package dataAccessObjects.util;

public interface EditProductCommands {
    String EXIT = "0";
    String CHANGE_PRODUCT_NAME = "1";
    String CHANGE_PRODUCT_DESCRIPTION = "2";
    String CHANGE_PRODUCT_PRICE = "3";
    String CHANGE_PRODUCT_QUANTITY = "4";
    String CHANGE_PRODUCT_CATEGORY = "5";

    static void printCommands() {
        System.out.println("Please input " + EXIT + " for EXIT");
        System.out.println("Please input " + CHANGE_PRODUCT_NAME + " for CHANGE_PRODUCT_NAME");
        System.out.println("Please input " + CHANGE_PRODUCT_DESCRIPTION + " for CHANGE_PRODUCT_DESCRIPTION");
        System.out.println("Please input " + CHANGE_PRODUCT_PRICE + " for CHANGE_PRODUCT_PRICE");
        System.out.println("Please input " + CHANGE_PRODUCT_QUANTITY + " for CHANGE_PRODUCT_QUANTITY");
        System.out.println("Please input " + CHANGE_PRODUCT_CATEGORY + " for CHANGE_PRODUCT_CATEGORY");
    }
}
