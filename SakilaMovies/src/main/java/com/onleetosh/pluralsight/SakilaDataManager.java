package com.onleetosh.pluralsight;

import com.onleetosh.pluralsight.models.*;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SakilaDataManager {

    private final BasicDataSource dataSource;

    public SakilaDataManager(BasicDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Actor> fetchActorByLastName(String lastname) {
        ArrayList<Actor> actors = new ArrayList<>();


        //do the stuff with the datasource here...

        try(Connection connection = dataSource.getConnection();){
            try(PreparedStatement ps = connection.prepareStatement(
                    "SELECT * FROM Actor WHERE last_name = ?");)
            {
                ps.setString(1, lastname);
                try(ResultSet results = ps.executeQuery())
                {
                    while(results.next()){
                        actors.add( new Actor(
                                results.getInt(1),
                                results.getString(2),
                                results.getString(3)
                        ));
                    }
                }
            }
            return actors;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Film> fetchFilmsByActorName(String firstname, String lastname) {

        ArrayList<Film> films = new ArrayList<>();

        try (Connection connect = dataSource.getConnection()) {
            try (PreparedStatement ps = connect.prepareStatement("""
        SELECT film.film_id, film.title, film.description, film.release_year, film.length
        FROM film
        JOIN film_actor ON film.film_id = film_actor.film_id
        JOIN actor ON film_actor.actor_id = actor.actor_id
        WHERE actor.first_name = ? AND actor.last_name = ?;
        """)) {
                // Set the parameters for the query
                ps.setString(1, firstname);
                ps.setString(2, lastname);

                try (ResultSet results = ps.executeQuery()) {
                    while (results.next()) {
                        films.add(new Film(
                                results.getInt("film_id"),
                                results.getString("title"),
                                results.getString("description"),
                                results.getInt("release_year"),
                                results.getInt("length")
                        ));
                    }
                }
            }
            return films;
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching films for the actor: " + firstname + " " + lastname, e);
        }

    }

}
