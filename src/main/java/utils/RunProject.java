package utils;

import dataAccessObjects.CategoryManager;
import dataAccessObjects.ProductManager;

import java.util.Scanner;


public class RunProject implements Commands {
    private static final RunProject IS_RUN = new RunProject();
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final CategoryManager CATEGORY_MANAGER = new CategoryManager();
    private static final ProductManager PRODUCT_MANAGER = new ProductManager();

    private RunProject() {
    }

    public void getCommands() {
        boolean flag = true;

        while (flag) {
            Commands.printCommands();
            String command = SCANNER.nextLine();
            switch (command) {
                case EXIT:
                    flag = false;
                    break;
                case ADD_CATEGORY:
                    CATEGORY_MANAGER.addCategory(SCANNER);
                    break;
                case EDIT_CATEGORY_BY_ID:
                    CATEGORY_MANAGER.editCategoryById(SCANNER);
                    break;
                case DELETE_CATEGORY_BY_ID:
                    CATEGORY_MANAGER.deleteCategoryById(SCANNER);
                    break;
                case ADD_PRODUCT:
                    PRODUCT_MANAGER.addProduct(SCANNER);
                    break;
                case EDIT_PRODUCT_BY_ID:
                    PRODUCT_MANAGER.editProductById(SCANNER);
                    break;
                case DELETE_PRODUCT_BY_ID:
                    PRODUCT_MANAGER.deleteProductById(SCANNER);
                    break;
                case PRINT_TOTAL_PRODUCTS_COUNT:
                    PRODUCT_MANAGER.printTotalProductsCount();
                    break;
                case PRINT_MAX_OF_PRICE_PRODUCT:
                    PRODUCT_MANAGER.printMaxOfPriceProducts();
                    break;
                case PRINT_MIN_OF_PRICE_PRODUCT:
                    PRODUCT_MANAGER.printMinOfPriceProducts();
                    break;
                case PRINT_AVG_OF_PRICE_PRODUCT:
                    PRODUCT_MANAGER.printAVGOfPriceProducts();
                    break;
                case SHOW_PRODUCTS:
                    PRODUCT_MANAGER.showProducts();
                    break;
                case SHOW_CATEGORIES:
                    CATEGORY_MANAGER.showCategories();
                    break;
                default:
                    System.out.println("Please check correct:");
            }
        }
    }

    public static RunProject getIsRun() {
        return IS_RUN;
    }

}
