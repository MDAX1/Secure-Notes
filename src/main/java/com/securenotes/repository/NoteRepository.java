package com.securenotes.repository;

import com.securenotes.database.DatabaseConnection;
import com.securenotes.model.Note;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NoteRepository {

    // Sparar en helt ny note i databasen
    public void save(int userId, String title, String content) throws SQLException {
        String sql = "INSERT INTO notes (user_id, title, content) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            stmt.setString(2, title);
            stmt.setString(3, content);
            stmt.executeUpdate();
        }
    }

    // Hämtar alla notes som tillhör en viss användare
    public List<Note> findByUserId(int userId) throws SQLException {
        List<Note> notes = new ArrayList<>();
        String sql = "SELECT * FROM notes WHERE user_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                notes.add(new Note(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getString("title"),
                        rs.getString("content")
                ));
            }
        }
        return notes;
    }
    // Hämtar alla notes från alla användare (för admin)
    public List<Note> findAll() throws SQLException {
        List<Note> notes = new ArrayList<>();
        String sql = "SELECT n.id, n.user_id, n.title, n.content, u.username " +
                "FROM notes n JOIN users u ON n.user_id = u.id";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String titleWithOwner = rs.getString("title")
                        + " (av: " + rs.getString("username") + ")";

                notes.add(new Note(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        titleWithOwner,
                        rs.getString("content")
                ));
            }
        }
        return notes;
    }

    // Raderar en note oavsett ägare (för admin)
    public void deleteById(int noteId) throws SQLException {
        String sql = "DELETE FROM notes WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, noteId);
            stmt.executeUpdate();
        }
    }
}
