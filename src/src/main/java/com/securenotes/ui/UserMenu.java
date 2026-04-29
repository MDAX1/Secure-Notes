package com.securenotes.ui;

import com.securenotes.model.Note;
import com.securenotes.model.User;
import com.securenotes.service.NoteService;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class UserMenu {

    private final Scanner scanner;
    private final NoteService noteService;
    private final User currentUser;

    public UserMenu(Scanner scanner, User user) {
        this.scanner = scanner;
        this.currentUser = user;
        this.noteService = new NoteService();
    }

    public void run() {
        boolean running = true;

        while (running) {
            showMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> createNote();
                case "2" -> viewNotes();
                case "3" -> {
                    System.out.println("You are now logged out.");
                    running = false;
                }
                default -> System.out.println("Invalid selection, please try again.");
            }
        }
    }

    private void showMenu() {
        System.out.println(" You are logged in as: " + currentUser.getUsername());
        System.out.println("1. Create note");
        System.out.println("2. Show my notes");
        System.out.println("3. Sign out");
        System.out.print("Select: ");
    }

    private void createNote() {
        System.out.print("Title: ");
        String title = scanner.nextLine().trim();

        System.out.print("Content: ");
        String content = scanner.nextLine().trim();

        try {
            noteService.createNote(currentUser, title, content);
        } catch (SQLException e) {
            System.out.println("Kunde inte spara note: " + e.getMessage());
        }
    }

    private void viewNotes() {
        try {
            List<Note> notes = noteService.getMyNotes(currentUser);

            if (notes.isEmpty()) {
                System.out.println("You don't have any notes yet.");
            } else {
                System.out.println("\n--- Your notes ---");
                for (Note note : notes) {
                    System.out.println(note);
                    System.out.println();
                }
            }
        } catch (SQLException e) {
            System.out.println("Could not retrieve notes: " + e.getMessage());
        }
    }
}
