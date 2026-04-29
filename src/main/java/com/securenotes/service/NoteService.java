package com.securenotes.service;

import com.securenotes.model.Note;
import com.securenotes.model.User;
import com.securenotes.repository.NoteRepository;

import java.sql.SQLException;
import java.util.List;

public class NoteService {
    private final NoteRepository noteRepository = new NoteRepository();

    public void createNote(User user, String title, String content) throws SQLException {
        noteRepository.save(user.getId(), title, content);
        System.out.println("Note skapad!");
    }

    public List<Note> getMyNotes(User user) throws SQLException {
        return noteRepository.findByUserId(user.getId());
    }

    // Hämtar alla notes (admin-funktion)
    public List<Note> getAllNotes() throws SQLException {
        return noteRepository.findAll();
    }

    // Raderar valfri note (admin-funktion)
    public void deleteAnyNote(int noteId) throws SQLException {
        noteRepository.deleteById(noteId);
        System.out.println("Note raderad!");
    }
}
