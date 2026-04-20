package com.Kapa.dao;

import com.Kapa.database.DatabasePool;
import com.Kapa.model.Movie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MovieDAO {

    public void create(Movie movie) throws SQLException {
        String sql = """
                INSERT INTO movies (id, title, release_date, duration, score, genre_id)
                VALUES (movies_seq.NEXTVAL, ?, ?, ?, ?, ?)
                """;

        try (Connection connection = DatabasePool.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, movie.getTitle());
            stmt.setDate(2, movie.getReleaseDate());
            stmt.setInt(3, movie.getDuration());
            stmt.setDouble(4, movie.getScore());
            stmt.setInt(5, movie.getGenreId());

            stmt.executeUpdate();
        }
    }

    public Movie findById(int id) throws SQLException {
        String sql = "SELECT id, title, release_date, duration, score, genre_id FROM movies WHERE id = ?";

        try (Connection connection = DatabasePool.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Movie(
                            rs.getInt("id"),
                            rs.getString("title"),
                            rs.getDate("release_date"),
                            rs.getInt("duration"),
                            rs.getDouble("score"),
                            rs.getInt("genre_id")
                    );
                }
            }
        }
        return null;
    }

    public Movie findByTitle(String title) throws SQLException {
        String sql = "SELECT id, title, release_date, duration, score, genre_id FROM movies WHERE title = ?";

        try (Connection connection = DatabasePool.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, title);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Movie(
                            rs.getInt("id"),
                            rs.getString("title"),
                            rs.getDate("release_date"),
                            rs.getInt("duration"),
                            rs.getDouble("score"),
                            rs.getInt("genre_id")
                    );
                }
            }
        }
        return null;
    }

    public List<Movie> findAll() throws SQLException {
        List<Movie> movies = new ArrayList<>();
        String sql = "SELECT id, title, release_date, duration, score, genre_id FROM movies ORDER BY id";

        try (Connection connection = DatabasePool.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                movies.add(new Movie(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getDate("release_date"),
                        rs.getInt("duration"),
                        rs.getDouble("score"),
                        rs.getInt("genre_id")
                ));
            }
        }
        return movies;
    }
}