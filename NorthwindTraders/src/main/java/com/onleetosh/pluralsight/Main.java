package com.onleetosh.pluralsight;

import java.sql.*;

public class Main {
    public static void main(String[] args) {

        if (args.length != 2) {
            System.out.println(
                    "Application needs two arguments to run: " +
                            "java com.pluralsight.wb8demo2 <username> <password>");
            System.exit(1);
        }

        // get the user name and password from the command line args
        String username = args[0];
        String password = args[1];
        try {
            // load the MySQL Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // 1. open a connection to the database
            // use the database URL to point to the correct database and establish a connection
            Connection connection;
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/northwind",
                    username,
                    password);
            // create statement
            // the statement is tied to the open connection
            Statement statement = connection.createStatement();
            // define your query
            String query = "SELECT * FROM northwind.products;";

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
            connection.close();
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found ");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("There was an SQL issue: ");
            e.printStackTrace();
        }

    }
}