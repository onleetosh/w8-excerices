package com.onleetosh.pluralsight;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;
import java.sql.*;

public class UsingDataSource {
    public static void main(String[] args) {

        if (args.length != 3) {
            System.out.println(
                    "Application needs three arguments to run: " +
                            " <username> <password> <url>");
            System.exit(1);
        }

        // Get the username, password, and URL from Program arguments in configuration
        String username = args[0];
        String password = args[1];
        String url = args[2];

        boolean exit = false;
        int command;

        while (!exit) {
            System.out.println("\nWhat do you want to do?\n" +
                    "1) Display all products\n" +
                    "2) Display all customers\n" +
                    "3) Display all categories\n" +
                    "0) Exit");

            try {
                command = Console.PromptForInt("Select [0-2]: ");

                switch (command) {
                    case 1 -> {
                        useDataSourceToReturnProducts(username, password, url);
                        Console.PromptForString("\nPress ENTER to return to previous screen");
                    }
                    case 2 -> {
                        useDataSourceToReturnCustomerInformation(username, password, url);
                        Console.PromptForString("\nPress ENTER to return to previous screen");
                    }
                    case 3 -> {
                        useDataSourceToReturnCategories(username, password, url);
                        returnProductsByCategoryID(username, password, url);
                        Console.PromptForString("\nPress ENTER to return to previous screen");
                    }
                    case 0 -> {
                        System.out.println("Exiting the application. Goodbye!");
                        exit = true;
                    }
                    default -> System.out.println("Invalid entry. Please enter 0, 1, or 2.");
                }
            }
            catch (Exception e) {
                System.out.println("Invalid entry, enter [0-2]");
            }
        }
    }
    /**
     * Using try-with-resources to manage connection, statement, and result set
     */
    public static void useDataSourceToReturnCustomerInformation(String username, String password, String url) {
        // Load MySQL driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Cannot connect to driver");
            System.exit(1);
        }

        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        fetchCustomerData(dataSource);

    }

    public static void fetchCustomerData(DataSource dataSource){
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM customers;")
        )
        {
            System.out.println("Connected to the database.");

            // Execute the query and process results
            try (ResultSet results = ps.executeQuery()) {

                // Print header
                printCustomerHeader();
                // Track results
                boolean hasResults = false;

                // Loop through and print results
                while (results.next()) {
                    hasResults = true;
                    printCustomerInformation(results.getString("ContactName"),
                            results.getString("CompanyName"),
                            results.getString("City"),
                            results.getString("Country"),
                            results.getString("Phone"));
                }

                if (!hasResults) {
                    System.out.println("No customers found");
                }
            }
        } catch (SQLException e) {
            System.out.println("An error occurred while accessing the database:");
            e.printStackTrace();
        }
    }

    public static void useDataSourceToReturnProducts(String username, String password, String url){
        // Load MySQL driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Cannot connect to driver");
            System.exit(1);
        }

        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        fetchProductData(dataSource);
    }
    private static void fetchProductData(DataSource dataSource){
        try ( Connection connection = dataSource.getConnection();
              PreparedStatement ps = connection.prepareStatement("SELECT * FROM products;")
        )
        {
            System.out.println("Connected to the database.");

            // Execute the query and process results
            try (ResultSet results = ps.executeQuery()) {
                // Print header
                printProductHeader();
                // Track results
                boolean hasResults = false;
                // Loop through and print results
                while (results.next()) {
                    hasResults = true;
                    printProductInformation(results.getInt("ProductID"),
                            results.getString("ProductName"),
                            results.getDouble("UnitPrice"),
                            results.getInt("UnitsInStock"));
                }

                if (!hasResults) {
                    System.out.println("No categories found");
                }
            }
        } catch (SQLException e) {
            System.out.println("An error occurred while accessing the database:");
            e.printStackTrace();
        }
    }


    public static void useDataSourceToReturnCategories(String username, String password, String url){

        // Load MySQL driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Cannot connect to driver");
            System.exit(1);
        }

        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        fetchCategoryData(dataSource);
    }
    private static void fetchCategoryData(DataSource dataSource){
        try ( Connection connection = dataSource.getConnection();
              PreparedStatement ps = connection.prepareStatement("SELECT * FROM categories;")
        )
        {
            System.out.println("Connected to the database.");

            // Execute the query and process results
            try (ResultSet results = ps.executeQuery()) {

                // Print header
                printCategoryHeader();

                // Track results
                boolean hasResults = false;

                // Loop through and print results
                while (results.next()) {
                    hasResults = true;
                    printCategoryInformation(results.getInt("CategoryID"),
                            results.getString("CategoryName"),
                            results.getString("Description"));
                }
                if (!hasResults) {
                    System.out.println("No categories found");
                }
            }
        } catch (SQLException e) {
            System.out.println("An error occurred while accessing the database:");
            e.printStackTrace();
        }
    }

    public static void returnProductsByCategoryID(String username, String password, String url)   {

        int input = Console.PromptForInt("Enter Category ID: ");

        // Load MySQL driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Cannot connect to driver");
            System.exit(1);
        }

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM products WHERE CategoryID = ?")
        )
        {
            System.out.println("Connected to the database.");

            // Set the parameter for the query
            ps.setInt(1, input);

            // Execute the query and process results
            try (ResultSet results = ps.executeQuery()) {
                // Print header
                System.out.printf("%-15s %-35s %-15s %-25s\n",
                        "Product ID", "Product Name", "Unit Price", "Products in Stock");

                // Track results
                boolean hasResults = false;

                // Loop through and print results
                while (results.next()) {
                    hasResults = true;
                    int productID = results.getInt("ProductID");
                    String productName = results.getString("ProductName");
                    double unitPrice = results.getDouble("UnitPrice");
                    int productStock = results.getInt("UnitsInStock");

                    System.out.printf("%-15d %-35s $%-25.2f %-15d\n",
                            productID, productName, unitPrice, productStock);
                }

                if (!hasResults) {
                    System.out.println("No Products found matching the given Category ID.");
                }
            }
        } catch (SQLException e) {
            System.out.println("An error occurred while accessing the database:");
            e.printStackTrace();
        }
    }


    /**
     * Helper methods used to print headers
     */

    public static void printProductHeader(){
        System.out.printf("%-15s %-35s %-15s %-15s\n",
                "Product ID", "Product Name", "Unit Price", "Products in Stock");
    }
    public static void printCustomerHeader(){
        System.out.printf("%-25s %-40s %-25s %-25s %-15s\n",
                "Contact Name", "Company Name", "City", "Country", "Phone Number");
    }
    public static void printCategoryHeader(){
        System.out.printf("%-15s %-35s %-15s \n", "Category ID", "Category Name", "Description");
    }

    /**
     * Helper methods used to print information in the database
     */

    public static void printProductInformation(int productID,String productName, double unitPrice, int productStock){
        System.out.printf("%-15d %-35s $%-25.2f %-15d\n",productID, productName, unitPrice, productStock );
    }
    public static void printCustomerInformation(String contactName, String companyName, String city, String country, String phoneNumber){
        System.out.printf("%-25s %-40s %-25s %-25s %-15s\n", contactName, companyName, city, country, phoneNumber);
    }
    public static void printCategoryInformation(int categoryID, String categoryName, String description ){
        System.out.printf("%-15s %-35s %-15s\n",
                categoryID, categoryName, description );

    }


}
