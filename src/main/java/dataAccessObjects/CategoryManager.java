package dataAccessObjects;

import dbConnection.DBConnectionProvider;
import models.Category;

import java.sql.*;
import java.util.Scanner;

public class CategoryManager {
    private final Connection CONNECTION = DBConnectionProvider.getINSTANCE().getConnection();

    public void addCategory(Scanner scanner) {
        Category category = createCategory(scanner);
        boolean categoryNameDB = isCategoryNameDB(category.getName());
        if (!categoryNameDB) {
            String sql = "insert into category(name) values(?)";
            try (PreparedStatement preparedStatement = CONNECTION.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, category.getName());
                preparedStatement.executeUpdate();
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    category.setId(generatedKeys.getInt(1));
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println(category.getName() + " in data base.");
        }
    }

    public void editCategoryById(Scanner scanner) {
        System.out.print("Please input category id: ");
        String categoryId = scanner.nextLine();
        if (isCategoryId(Integer.parseInt(categoryId))) {
            System.out.print("Please input new name for changed category. ");
            String editName = scanner.nextLine();
            String sql = "update category set name=? where id=" + categoryId;

            try (PreparedStatement preparedStatement = CONNECTION.prepareStatement(sql)) {
                preparedStatement.setString(1, editName);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Category by id " + categoryId + " does not exist.");
        }
    }

    public void deleteCategoryById(Scanner scanner) {
        System.out.print("Please input category id: ");
        String deleteCategoryId = scanner.nextLine();
        if (isCategoryId(Integer.parseInt(deleteCategoryId))) {
            String sql = "delete from category where id=" + deleteCategoryId;

            try (Statement statement = CONNECTION.createStatement()) {
                statement.executeUpdate(sql);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Category by id " + deleteCategoryId + " does not exist to delete");
        }

    }

    public int returnCategoryIdByName(String categoryName) {
        int categoryId = 0;
        if (isCategoryNameDB(categoryName)) {
            String sql = "select id from category where name like ?";
            try (PreparedStatement preparedStatement = CONNECTION.prepareStatement(sql)) {
                preparedStatement.setString(1, categoryName);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    categoryId = resultSet.getInt("id");
                    return categoryId;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Category by name " + categoryName + " does not exist.");
        }
        return categoryId;
    }

    public String returnCategoryNameById(int categoryId) {
        String categoryName = "";
        if (isCategoryId(categoryId)) {
            String sql = "select name from category where id=" + categoryId;
            try (Statement statement = CONNECTION.createStatement()) {
                ResultSet resultSet = statement.executeQuery(sql);
                if (resultSet.next()) {
                    categoryName = resultSet.getString("name");
                    return categoryName;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Category by id " + categoryId + " does not exist.");

        }
        return categoryName;
    }

    public boolean isCategoryNameDB(String categoryName) {
        String sql = "select * from category where name like ?";

        try (PreparedStatement preparedStatement = CONNECTION.prepareStatement(sql)) {
            preparedStatement.setString(1, categoryName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isCategoryId(int id) {
        String sql = "select id from category where id=?";

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

    public void showCategories() {
        String sql = "select * from category";

        try (Statement statement = CONNECTION.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("Name");
                System.out.println("id = " + id + ", name = " + name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Category createCategory(Scanner scanner) {
        Category category = new Category();

        System.out.print("Please input category name: ");
        String categoryName = scanner.nextLine();
        category.setName(categoryName);
        return category;
    }
}
