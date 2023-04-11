package dataAccessObjects;

import dataAccessObjects.util.EditProductCommands;
import dbConnection.DBConnectionProvider;
import models.Product;

import java.sql.*;
import java.util.Scanner;


public class ProductManager implements EditProductCommands {
    private final Connection CONNECTION = DBConnectionProvider.getINSTANCE().getConnection();
    private final Scanner SCANNER = new Scanner(System.in);
    private final CategoryManager CATEGORY = new CategoryManager();

    private Product createProduct() {
        System.out.print("Please input product name: ");
        String name = SCANNER.nextLine();

        System.out.print("Please input product description: ");
        String description = SCANNER.nextLine();

        System.out.print("Please input product price: ");
        double price = Double.parseDouble(SCANNER.nextLine());

        System.out.print("Please input product quantity: ");
        int quantity = Integer.parseInt(SCANNER.nextLine());

        System.out.print("Please input product category: ");
        String categoryName = SCANNER.nextLine();

        int categoryId = CATEGORY.returnCategoryIdByName(categoryName);
        return new Product(name, description, price, quantity, categoryId);
    }

    public void addProduct() {
        Product product = createProduct();
        String sql = "insert into products(name, description, price, quantity, category) " + "values(?,?,?,?,?)";
        try (PreparedStatement preparedStatement = CONNECTION.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getDescription());
            preparedStatement.setDouble(3, product.getPrice());
            preparedStatement.setInt(4, product.getQuantity());
            preparedStatement.setInt(5, product.getCategory());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                product.setId(generatedKeys.getInt(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void editProductById() {
        showProducts();
        System.out.print("Please input product id: ");
        String productId = SCANNER.nextLine();
        if (isProductId(Integer.parseInt(productId))) {
            editProductCommands(Integer.parseInt(productId));

        } else {
            System.out.println("Product by id " + productId + " does not exist.");
        }
    }

    public void deleteProductById() {
        showProducts();
        System.out.print("Please input product id: ");
        String deleteProductId = SCANNER.nextLine();
        if (isProductId(Integer.parseInt(deleteProductId))) {
            String sql = "delete from products where id=" + deleteProductId;

            try (Statement statement = CONNECTION.createStatement()) {
                statement.executeUpdate(sql);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Product by id " + deleteProductId + " does not exist to delete");
        }

    }

    public void printTotalProductsCount() {
        String sql = "select count(id) from products";

        try (Statement statement = CONNECTION.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                System.out.println("Total products count = " + resultSet.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void printMaxOfPriceProducts() {
        String sql = "select max(price) from products";

        try (Statement statement = CONNECTION.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                System.out.println("Max price in products = " + resultSet.getDouble(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void printMinOfPriceProducts() {
        String sql = "select min(price) from products";

        try (Statement statement = CONNECTION.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                System.out.println("Min price in products = " + resultSet.getDouble(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void printAVGOfPriceProducts() {
        String sql = "select avg(price) from products";

        try (Statement statement = CONNECTION.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                System.out.println("AVG price in products = " + resultSet.getDouble(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void showProducts() {
        String sql = "select * from products";
        try (Statement statement = CONNECTION.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                double price = resultSet.getDouble("price");
                int quantity = resultSet.getInt("quantity");
                int category = resultSet.getInt("category");
                String categoryName = CATEGORY.returnCategoryNameById(category);
                System.out.println("id = " + id + ", name = " + name + ", description = " + description + ", price = "
                        + price + ", quantity = " + quantity + ", category = " + categoryName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void changeProductName(int id) {
        System.out.print("Please input new name for changed product. ");
        String editName = SCANNER.nextLine();
        String sql = "update products set name=? where id=" + id;

        try (PreparedStatement preparedStatement = CONNECTION.prepareStatement(sql)) {
            preparedStatement.setString(1, editName);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void changeProductDescription(int id) {
        System.out.print("Please input new description for changed product. ");
        String editDescription = SCANNER.nextLine();
        String sql = "update products set description=? where id=" + id;

        try (PreparedStatement preparedStatement = CONNECTION.prepareStatement(sql)) {
            preparedStatement.setString(1, editDescription);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void changeProductPrice(int id) {
        System.out.print("Please input new price for changed product. ");
        String editPrice = SCANNER.nextLine();
        String sql = "update products set price=? where id=" + id;

        try (PreparedStatement preparedStatement = CONNECTION.prepareStatement(sql)) {
            preparedStatement.setString(1, editPrice);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void changeProductCategory(int id) {
        System.out.print("Please input category name for chang product category id. ");
        String editCategoryName = SCANNER.nextLine();
        if (CATEGORY.isCategoryNameDB(editCategoryName.strip())) {
            int categoryId = CATEGORY.returnCategoryIdByName(editCategoryName.trim());

            String sql = "update products set category=? where id=" + id;

            try (PreparedStatement preparedStatement = CONNECTION.prepareStatement(sql)) {
                preparedStatement.setString(1, String.valueOf(categoryId));
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void changeProductQuantity(int id) {
        System.out.print("Please input new quantity for changed product. ");
        String editQuantity = SCANNER.nextLine();
        String sql = "update products set quantity=? where id=" + id;

        try (PreparedStatement preparedStatement = CONNECTION.prepareStatement(sql)) {
            preparedStatement.setString(1, editQuantity);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean isProductId(int id) {
        String sql = "select id from products where id=?";

        try (PreparedStatement preparedStatement = CONNECTION.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void editProductCommands(int id) {
        boolean flag = true;

        while (flag) {
            EditProductCommands.printCommands();
            String editProduct = SCANNER.nextLine();
            switch (editProduct) {
                case EXIT:
                    flag = false;
                    break;
                case CHANGE_PRODUCT_NAME:
                    changeProductName(id);
                    break;
                case CHANGE_PRODUCT_DESCRIPTION:
                    changeProductDescription(id);
                    break;
                case CHANGE_PRODUCT_PRICE:
                    changeProductPrice(id);
                    break;
                case CHANGE_PRODUCT_QUANTITY:
                    changeProductQuantity(id);
                    break;
                case CHANGE_PRODUCT_CATEGORY:
                    changeProductCategory(id);
                    break;
                default:
                    System.out.println("Please choose the correct team:");
            }
        }
    }

}
