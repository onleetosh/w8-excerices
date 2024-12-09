/**
 *  Refactored Sakila  use SakilaDataManager and constructors
 */

package com.onleetosh.pluralsight;

import com.onleetosh.pluralsight.models.*;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;
import java.util.List;


public class Main {
    public static void main(String[] args) {


        String lastName = Console.PromptForString("What is the last name of an actor you like? ");

        try (BasicDataSource dataSource = getConfiguredDataSource(args);) {

            SakilaDataManager dm = new SakilaDataManager(dataSource);
            List<Actor> actors = dm.fetchActorByLastName(lastName);
            if (!actors.isEmpty()) {
                for (Actor a : actors) {
                    System.out.println(a);
                }

                String input = Console.PromptForString("Enter first and last name of an actor/actress ");

                // Split the input into first name and last name
                String[] nameParts = input.split(" ", 2);
                if (nameParts.length != 2) {
                    System.out.println("Please provide both a first and last name.");
                    return;
                }
                String first = nameParts[0];
                String last = nameParts[1];

                List<Film> films = dm.fetchFilmsByActorName(first, last);
                for(Film f : films){
                    System.out.println(f);
                }
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

 public static BasicDataSource getConfiguredDataSource(String[] args){
        String username = args[0];
        String password = args[1];
        String sqlServerUrl = args[2];

        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl(sqlServerUrl);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;

    }
}