package com.Kapa.dao;

import com.Kapa.database.DatabasePool;
import com.Kapa.model.Genre;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GenreDAO {

    public void create(String name) throws SQLException {
        String sql = "INSERT INTO genres (id, name) VALUES (genres_seq.NEXTVAL, ?)";
        try (Connection connection = DatabasePool.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.executeUpdate();
        }
    }

    public Genre findById(int id) throws SQLException {
        String sql = "SELECT id, name FROM genres WHERE id = ?";
        try (Connection connection = DatabasePool.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Genre(rs.getInt("id"), rs.getString("name"));
                }
            }
        }
        return null;
    }

    public Genre findByName(String name) throws SQLException {
        String sql = "SELECT id, name FROM genres WHERE name = ?";
        try (Connection connection = DatabasePool.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, name);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Genre(rs.getInt("id"), rs.getString("name"));
                }
            }
        }
        return null;
    }
}