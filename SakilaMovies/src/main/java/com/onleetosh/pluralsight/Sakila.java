package com.onleetosh.pluralsight;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Sakila {

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

        createDataSourceForActors(username, password, url);

        createDataSourceForActor2(username, password, url);


    }


    public static void createDataSourceForActors(String username, String password, String url){

        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        returnActorsByLastname(dataSource);
    }


    public static void returnActorsByLastname(DataSource dataSource)   {

        String input = Console.PromptForString("Enter last name of an actor/actress ");

        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM actor WHERE Last_Name = ?")
        )
        {
            System.out.println("Connected to the database.");

            // Set the parameter for the query
            ps.setString(1, input);

            // Execute the query and process results
            try (ResultSet results = ps.executeQuery()) {
                // Print header
                System.out.printf("%-15s %-15s %-15s %10s\n",
                        "Actor ID", "First Name", "Last Name", "Last Update");

                // Track results
                boolean hasResults = false;

                // Loop through and print results
                while (results.next()) {
                    hasResults = true;
                    String actorID = results.getString(1);
                    String firstName = results.getString(2);
                    String lastName = results.getString(3);
                    String lastUpdate = results.getString(4);

                    System.out.printf("%-15s %-15s %-15s %-15s\n",
                            actorID, firstName, lastName, lastUpdate);
                }

                if (!hasResults) {
                    System.out.println("No results found matching the given entry.");
                }
            }
        } catch (SQLException e) {
            System.out.println("An error occurred while accessing the database:");
            e.printStackTrace();
        }
    }



    public static void createDataSourceForActor2(String username, String password, String url){

        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        returnActorsByFirstLastname(dataSource);
    }

    /**
     * Method created to prompt user for a first and last name of an actor and returns a list of films that
     * actor/actress appeared in
     */

    public static void returnActorsByFirstLastname(DataSource dataSource)   {

        String input = Console.PromptForString("Enter first and last name of an actor/actress ");

        // Split the input into first name and last name
        String[] nameParts = input.split(" ", 2);
        if (nameParts.length != 2) {
            System.out.println("Please provide both a first and last name.");
            return;
        }
        String firstName = nameParts[0];
        String lastName = nameParts[1];

        // Using text block for a multi-line SQL query
        String sql = """
        SELECT film.title, film.description, film.release_year, actor.first_name, actor.last_name
        FROM film
        JOIN film_actor ON film.film_id = film_actor.film_id
        JOIN actor ON film_actor.actor_id = actor.actor_id
        WHERE actor.first_name = ? AND actor.last_name = ?;
        """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            System.out.println("Connected to the database.");

            // Set the parameters for the query
            ps.setString(1, firstName);
            ps.setString(2, lastName);

            // Execute the query and process results
            try (ResultSet results = ps.executeQuery()) {
                // Print header
                System.out.printf("%-30s %-110s %-20s %-15s %-10s\n",
                        "Title", "Description", "Release Year", "First Name", "Last Name");

                // Track results
                boolean hasResults = false;

                // Loop through and print results
                while (results.next()) {
                    hasResults = true;
                    String title = results.getString("title");
                    String description = results.getString("description");
                    int releaseYear = results.getInt("release_year");
                    String first = results.getString("first_name");
                    String last = results.getString("last_name");

                    System.out.printf("%-30s %-120s %-15d %-15s %-10s\n",
                            title, description, releaseYear, first, last);
                }

                if (!hasResults) {
                    System.out.println("No films found for the given actor/actress.");
                }
            }
        } catch (SQLException e) {
            System.out.println("An error occurred while accessing the database:");
            e.printStackTrace();
        }
    }







}
