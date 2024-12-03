/**
 *  This project encapsulates several exercise on executing query in Java:
 *  1. connect to a database and executes a query to display all products that database
 *  2. modify the code and display all the products with their product ID, Product Name, Unit Price, and Units In Stock
 *  3. update the main method to create a home screen with the option to view all products, customers, or exit; and
 *      add try/catch/finally to address potential errors.
 */

package com.onleetosh.pluralsight;

import java.sql.*;

public class UsingStatement {
    public static void main(String[] args) {

        if (args.length != 2) {
            System.out.println(
                    "Application needs two arguments to run: " +
                            "java com.pluralsight.wb8demo2 <username> <password>");
            System.exit(1);
        }

        // get the username and password from the command line args
        String username = args[0];
        String password = args[1];

        System.out.println("What do you want to do?\n" +
                "1) Display all products\n " +
                "2)Display all customer\n" +
                "0) exit ");

        int command;

        command = Console.PromptForInt("select [0-2]: ");

        switch (command) {
            case 1 -> fetchProductsFromDatabase(username, password);
            case 2 -> fetchCustomersFromDatabase(username, password);
            case 0 -> System.exit(0);
        }
    }


    public static void fetchCustomersFromDatabase(String username, String password){

        Connection connection = null;
        try {
            // load the MySQL Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 1. open a connection to the database
            // use the database URL to point to the correct database and establish a connection
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/northwind",
                    username,
                    password);
            // create statement
            // the statement is tied to the open connection
            Statement statement = connection.createStatement();
            // define your query
            String query = "SELECT * FROM customers ORDER BY country;";

            // 2. Execute your query
            ResultSet results = statement.executeQuery(query);

            System.out.printf("%-25s %-40s %-25s %-25s %-15s\n",
                    "Contact Name", "Company Name", "City", "Country", "Phone Number");


            // process the results
            while (results.next()) {
                String contactName = results.getString("ContactName");
                String companyName = results.getString("CompanyName");
                String city = results.getString("City");
                String country = results.getString("Country");
                String phoneNumber = results.getString("Phone");
                System.out.printf("%-25s %-40s %-25s %-25s %-15s\n",contactName, companyName, city, country, phoneNumber );
            }

            // 3. Close the connection
            //  connection.close();
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found ");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("There was an SQL issue: ");
            e.printStackTrace();
        }

        finally {
            if(connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

            }
        }
    }

    public static void fetchProductsFromDatabase(String username, String password){
        Connection connection = null;
        try {
            // load the MySQL Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // 1. open a connection to the database
            // use the database URL to point to the correct database and establish a connection
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/northwind",
                    username,
                    password);
            // create statement
            // the statement is tied to the open connection
            Statement statement = connection.createStatement();
            // define your query
            String query = "SELECT * FROM products;";

            // 2. Execute your query
            ResultSet results = statement.executeQuery(query);

            System.out.printf("%-15s %-35s %-15s %-15s\n",
                    "Product ID", "Product Name", "Unit Price", "Products in Stock");


            // list product ID, Product Name, Unit Price, Unit In stock
            // process the results
            while (results.next()) {
                int productID = results.getInt("ProductID");
                String productName = results.getString("ProductName");
                double unitPrice = results.getDouble("UnitPrice");
                int productStock = results.getInt("UnitsInStock");
                System.out.printf("%-15d %-35s %-25.2f %-15d\n",productID, productName, unitPrice, productStock );
            }

            // 3. Close the connection
            //  connection.close();
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found ");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("There was an SQL issue: ");
            e.printStackTrace();
        }

        finally {
            if(connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

            }
        }
    }
}