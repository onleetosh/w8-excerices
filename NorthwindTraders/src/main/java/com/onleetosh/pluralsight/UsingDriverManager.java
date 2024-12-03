package com.onleetosh.pluralsight;

import java.sql.*;
public class UsingDriverManager {



        public static void main(String[] args){

            if (args.length != 3) {
                System.out.println(
                        "Application needs two arguments to run: " +
                                "java com.pluralsight.wb8demo2 <username> <password> <server>");
                System.exit(1);
            }

            // get the user name and password from the command line args
            String username = args[0];
            String password = args[1];
            String sqlServerAddress = args[2];


            try {
                doDatabaseStuff(username, password, sqlServerAddress);
            } catch (Exception e) {
                System.out.println("Catch in main...");
                e.printStackTrace();
            }

            System.out.println("Very end of application...");


        }

        public static void doDatabaseStuff(String username, String password, String sqlServerAddress) throws SQLException, ClassNotFoundException {


            try{

                // load the MySQL Driver
                Class.forName("com.mysql.cj.jdbc.Driver");

                // 1. open a connection to the database
                // use the database URL to point to the correct database

                try (
                        Connection connection = DriverManager.getConnection(sqlServerAddress, username, password);
                        PreparedStatement pStatement = connection.prepareStatement("SELECT * FROM sakila.city WHERE country_id = ?;");
                )
                {

                    int country_id = 103;

                    pStatement.setInt(1, country_id);

                    try(ResultSet results = pStatement.executeQuery();){

                        // process the results
                        while (results.next()) {
                            int cityId = results.getInt("city_id");
                            String city = results.getString("city");
                            System.out.println(cityId);
                            System.out.println(city);
                        }
                    }

                }

            }
            catch(Exception e){
                //collect additional information about the problem...
                //log e, and additional information....
                System.out.println("Catch inside doDatabaseStuff...");
                log(sqlServerAddress);
                log(username);
                log(e);
                throw(e);
            }

        }

        public static void log(String data){
            System.out.println("Logging: " + data);
        }

        public static void log(Object data){
            System.out.println("Logging" + data.toString());
        }

    }
