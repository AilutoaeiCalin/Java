package com.kapa.lab7.service;
import com.kapa.lab7.dao.MovieDAO;
import com.kapa.lab7.model.Movie;
import org.springframework.stereotype.Service;
import com.kapa.lab7.dto.MovieRequest;
import com.kapa.lab7.dto.ScorePatchRequest;
import com.kapa.lab7.exception.ResourceNotFoundException;


import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

@Service
public class MovieService {

    private final MovieDAO movieDAO;

    public MovieService(MovieDAO movieDAO) {
        this.movieDAO = movieDAO;
    }

    public List<Movie> getAllMovies() throws SQLException {
        return movieDAO.findAll();
    }

    public Movie getMovieById(int id) throws SQLException {
        Movie movie = movieDAO.findById(id);
        if (movie == null) {
            throw new ResourceNotFoundException("Movie with id " + id + " was not found.");
        }
        return movie;
    }

    public void addMovie(MovieRequest request) throws SQLException {
        Movie movie = new Movie(
                request.getTitle(),
                Date.valueOf(request.getReleaseDate()),
                request.getDuration(),
                request.getScore(),
                request.getGenreId()
        );

        movieDAO.create(movie);
    }

    public void updateMovie(int id, MovieRequest request) throws SQLException {
        Movie existing = movieDAO.findById(id);
        if (existing == null) {
            throw new ResourceNotFoundException("Movie with id " + id + " was not found.");
        }

        Movie movie = new Movie(
                request.getTitle(),
                Date.valueOf(request.getReleaseDate()),
                request.getDuration(),
                request.getScore(),
                request.getGenreId()
        );

        boolean updated = movieDAO.update(id, movie);
        if (!updated) {
            throw new ResourceNotFoundException("Movie with id " + id + " was not updated.");
        }
    }

    public void updateScore(int id, ScorePatchRequest request) throws SQLException {
        Movie existing = movieDAO.findById(id);
        if (existing == null) {
            throw new ResourceNotFoundException("Movie with id " + id + " was not found.");
        }

        boolean updated = movieDAO.updateScore(id, request.getScore());
        if (!updated) {
            throw new ResourceNotFoundException("Score for movie with id " + id + " was not updated.");
        }
    }

    public void deleteMovie(int id) throws SQLException {
        Movie existing = movieDAO.findById(id);
        if (existing == null) {
            throw new ResourceNotFoundException("Movie with id " + id + " was not found.");
        }

        boolean deleted = movieDAO.delete(id);
        if (!deleted) {
            throw new ResourceNotFoundException("Movie with id " + id + " was not deleted.");
        }
    }
}