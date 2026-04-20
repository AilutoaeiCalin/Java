package com.Kapa;

import com.Kapa.dao.ActorDAO;
import com.Kapa.dao.GenreDAO;
import com.Kapa.dao.MovieActorDAO;
import com.Kapa.dao.MovieDAO;
import com.Kapa.database.DatabasePool;
import com.Kapa.model.Actor;
import com.Kapa.model.Genre;
import com.Kapa.model.Movie;
import com.Kapa.report.HtmlReportGenerator;

import java.sql.Date;

public class Main {
    public static void main(String[] args) {
        try {
            GenreDAO genreDAO = new GenreDAO();
            MovieDAO movieDAO = new MovieDAO();
            ActorDAO actorDAO = new ActorDAO();
            MovieActorDAO movieActorDAO = new MovieActorDAO();

            // Genres
            genreDAO.create("Thriller");
            genreDAO.create("Romance");

            Genre action = genreDAO.findByName("Action");
            System.out.println("Genre gasit: " + action);

            if (action != null) {
                // Movie
                Movie movie = new Movie(
                        "Transformers",
                        Date.valueOf("2014-10-24"),
                        101,
                        8.5,
                        action.getId()
                );
                movieDAO.create(movie);

                Movie foundMovie = movieDAO.findByTitle("Transformers");
                System.out.println("Film gasit: " + foundMovie);

                // Actor
                actorDAO.create("Tom Hanks");
                Actor actor = actorDAO.findByName("Tom Hanks");
                System.out.println("Actor gasit: " + actor);
                if (foundMovie != null && actor != null) {
                    movieActorDAO.addActorToMovie(foundMovie.getId(), actor.getId());
                }
            }


            HtmlReportGenerator generator = new HtmlReportGenerator();
            generator.generate("report.html");
            System.out.println("Raportul HTML a fost generat.");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DatabasePool.close();
        }
    }
}