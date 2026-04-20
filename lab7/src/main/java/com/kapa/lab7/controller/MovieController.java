package com.kapa.lab7.controller;

import com.kapa.lab7.dto.MovieRequest;
import com.kapa.lab7.dto.ScorePatchRequest;
import com.kapa.lab7.model.Movie;
import com.kapa.lab7.service.MovieService;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public List<Movie> getMovies() throws SQLException {
        return movieService.getAllMovies();
    }

    @GetMapping("/{id}")
    public Movie getMovieById(@PathVariable int id) throws SQLException {
        return movieService.getMovieById(id);
    }

    @PostMapping
    public String addMovie(@RequestBody MovieRequest request) throws SQLException {
        movieService.addMovie(request);
        return "Movie added successfully.";
    }

    @PutMapping("/{id}")
    public String updateMovie(@PathVariable int id, @RequestBody MovieRequest request) throws SQLException {
        movieService.updateMovie(id, request);
        return "Movie updated successfully.";
    }

    @PatchMapping("/{id}/score")
    public String updateScore(@PathVariable int id, @RequestBody ScorePatchRequest request) throws SQLException {
        movieService.updateScore(id, request);
        return "Movie score updated successfully.";
    }

    @DeleteMapping("/{id}")
    public String deleteMovie(@PathVariable int id) throws SQLException {
        movieService.deleteMovie(id);
        return "Movie deleted successfully.";
    }
}