package com.onleetosh.pluralsight;

import java.sql.*;

public class UsingPreparedStatement {

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
                    fetchProductsFromDatabase(username, password, url);
                    Console.PromptForString("\nPress ENTER to return to previous screen");
                }
                case 2 -> {
                    fetchCustomersFromDatabase(username, password, url);
                    Console.PromptForString("\nPress ENTER to return to previous screen");
                }
                case 3 -> {
                    fetchCategoriesFromDatabase(username, password, url);
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

    public static void fetchCustomersFromDatabase(String username, String password, String url) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet results = null;

        try {
            // Load the MySQL Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish a connection to the database
            connection = DriverManager.getConnection( url, username, password);

            System.out.println("Connected to the database.");

            String query = "SELECT * FROM customers WHERE Country IS NOT NULL ORDER BY Country";
            ps = connection.prepareStatement(query);
            results = ps.executeQuery();

            printCustomerHeader();
            // Process the results
            boolean hasResults = false;
            while (results.next()) {
                hasResults = true;
                printCustomerInformation(results.getString("ContactName"),
                       results.getString("CompanyName"),
                       results.getString("City"),
                       results.getString("Country"),
                       results.getString("Phone"));
            }
            if (!hasResults) {
                System.out.println("No customers found..");
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found. Please ensure the JDBC driver is in your classpath.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("An error occurred while accessing the database:");
            e.printStackTrace();
        } finally {
            try {
                if (results != null) results.close();
                if (ps != null) ps.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void fetchProductsFromDatabase(String username, String password, String url){
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet results = null;

        try {
            // Load the MySQL Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish a connection to the database
            connection = DriverManager.getConnection( url, username, password);

            System.out.println("Connected to the database.");

            // Define the query
            String query = "SELECT * FROM products;";
            ps = connection.prepareStatement(query);
            // 2. Execute your query
            results = ps.executeQuery(query);
            // Print header
            printProductHeader();

            // Process the results
            boolean hasResults = false;
            while (results.next()) {
                hasResults = true;
                printProductInformation(results.getInt("ProductID"),
                        results.getString("ProductName"),
                        results.getDouble("UnitPrice"),
                        results.getInt("UnitsInStock"));

            }
            if (!hasResults) {
                System.out.println("No Products found matching the criteria.");
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found. Please ensure the JDBC driver is in your classpath.");
            e.printStackTrace();
            System.exit(1);
        } catch (SQLException e) {
            System.out.println("An error occurred while accessing the database:");
            e.printStackTrace();
            System.exit(1);
        } finally {
            try {
                if (results != null) results.close();
                if (ps != null) ps.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
    }

    /**
     * Using try-with-resources to manage connection, statement, and result set
     */

    public static void fetchCategoriesFromDatabase(String username, String password, String url)  {

        // Load MySQL driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Cannot load SQL driver...");
            System.exit(1);
        }

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM categories;");
             ResultSet results = ps.executeQuery()
        ) {
            System.out.println("Connected to the database.");

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
        catch (SQLException e) {
            System.out.println("An error occurred while accessing the database:");
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static void returnProductsByCategoryID(String username, String password, String url) {

        int input = Console.PromptForInt("Enter Category ID: ");

        // Load MySQL driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Cannot load SQL driver...");
            System.exit(1);        }

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM products WHERE CategoryID = ?")
        )
        {
            System.out.println("Connected to the database.");

            // Set the parameter for the query
            ps.setInt(1, input);

            // Execute the query and process results
            try (ResultSet results = ps.executeQuery()) {

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
