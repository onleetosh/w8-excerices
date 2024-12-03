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

        // Get the username, password, and URL from the command line args
        String username = args[0];
        String password = args[1];
        String url = args[2];

        boolean exit = false;

        while (!exit) {
            System.out.println("\nWhat do you want to do?\n" +
                    "1) Display all products\n" +
                    "2) Display all customers\n" +
                    "0) Exit");

            int command = Console.PromptForInt("Select [0-2]: ");

            switch (command) {
                case 1 -> {
                    fetchProductsFromDatabase(username, password, url);
                    Console.PromptForYesNo("Press Enter to return to the menu...");
                }
                case 2 -> {
                    fetchCustomersFromDatabase(username, password, url);
                    Console.PromptForYesNo("Press Enter to return to the menu...");
                }
                case 0 -> {
                    System.out.println("Exiting the application. Goodbye!");
                    exit = true;
                }
                default -> System.out.println("Invalid option. Please select 0, 1, or 2.");
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
            connection = DriverManager.getConnection(
                    url,
                    username,
                    password);

            System.out.println("Connected to the database.");

            String query = "SELECT * FROM customers ORDER BY Country";
            ps = connection.prepareStatement(query);
            results = ps.executeQuery();

            // Print header
            System.out.printf("%-25s %-40s %-25s %-25s %-15s\n",
                    "Contact Name", "Company Name", "City", "Country", "Phone Number");

            // Process the results
            boolean hasResults = false;
            while (results.next()) {
                hasResults = true;
                String contactName = results.getString("ContactName");
                String companyName = results.getString("CompanyName");
                String city = results.getString("City");
                String country = results.getString("Country");
                String phoneNumber = results.getString("Phone");

                // Print each row
                System.out.printf("%-25s %-40s %-25s %-25s %-15s\n",
                        contactName, companyName, city, country, phoneNumber);
            }
            if (!hasResults) {
                System.out.println("No customers found matching the criteria.");
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found. Please ensure the JDBC driver is in your classpath.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("There was an SQL issue:");
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
            connection = DriverManager.getConnection(
                    url,
                    username,
                    password);

            System.out.println("Connected to the database.");

            // Define the query
            String query = "SELECT * FROM products;";
            ps = connection.prepareStatement(query);

            // 2. Execute your query
            results = ps.executeQuery(query);

            // Print header
            System.out.printf("%-15s %-35s %-15s %-15s\n",
                    "Product ID", "Product Name", "Unit Price", "Products in Stock");


            // Process the results
            boolean hasResults = false;
            while (results.next()) {
                hasResults = true;

                // process the results
                while (results.next()) {
                    int productID = results.getInt("ProductID");
                    String productName = results.getString("ProductName");
                    double unitPrice = results.getDouble("UnitPrice");
                    int productStock = results.getInt("UnitsInStock");
                    // Print each row
                    System.out.printf("%-15d %-35s %-25.2f %-15d\n",
                            productID, productName, unitPrice, productStock );
                }

            }
            if (!hasResults) {
                System.out.println("No Products found matching the criteria.");
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found. Please ensure the JDBC driver is in your classpath.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("There was an SQL issue:");
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

}
