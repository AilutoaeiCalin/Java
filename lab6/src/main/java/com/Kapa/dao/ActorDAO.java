package com.Kapa.dao;

import com.Kapa.database.DatabasePool;
import com.Kapa.model.Actor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ActorDAO {

    public void create(String name) throws SQLException {
        String sql = "INSERT INTO actors (id, name) VALUES (actors_seq.NEXTVAL, ?)";
        try (Connection connection = DatabasePool.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.executeUpdate();
        }
    }

    public Actor findById(int id) throws SQLException {
        String sql = "SELECT id, name FROM actors WHERE id = ?";
        try (Connection connection = DatabasePool.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Actor(rs.getInt("id"), rs.getString("name"));
                }
            }
        }
        return null;
    }

    public Actor findByName(String name) throws SQLException {
        String sql = "SELECT id, name FROM actors WHERE name = ?";
        try (Connection connection = DatabasePool.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, name);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Actor(rs.getInt("id"), rs.getString("name"));
                }
            }
        }
        return null;
    }
}