package com.securenotes.repository;

import com.securenotes.database.DatabaseConnection;
import com.securenotes.model.User;

import java.sql.*;
import java.util.Optional;

public class UserRepository {

    // Sparar en ny användare i databasen
    public void save(String username, String hashedPassword) throws SQLException {
        String sql = "INSERT INTO users (username, password, role) VALUES (?, ?, 'USER')";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, hashedPassword);
            stmt.executeUpdate();
        }
    }

    // Hämtar en användare med ett visst användarnamn
    public Optional<User> findByUsername(String username) throws SQLException {
        String sql = "SELECT * FROM users WHERE username = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("role")
                ));
            }
        }
        // Returnerar tomt om ingen användare hittades
        return Optional.empty();
    }
}