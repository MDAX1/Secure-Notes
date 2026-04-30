package com.securenotes.ui;

import com.securenotes.model.Note;
import com.securenotes.model.User;
import com.securenotes.service.NoteService;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class AdminMenu {
    private final Scanner scanner;
    private final NoteService noteService;
    private final User currentUser;

    public AdminMenu(Scanner scanner, User user) {
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
                case "1" -> viewAllNotes();
                case "2" -> deleteAnyNote();
                case "3" -> {
                    System.out.println("You are now logged out.");
                    running = false;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private void showMenu() {
        System.out.println("  Admin menu: " + currentUser.getUsername());
        System.out.println("1. See all notes in the system");
        System.out.println("2. Delete any note");
        System.out.println("3. Sign out");
        System.out.print("Please choose: ");
    }

    private void viewAllNotes() {
        try {
            List<Note> notes = noteService.getAllNotes();

            if (notes.isEmpty()) {
                System.out.println("Sorry! there are no notes in the system.");
            } else {
                System.out.println("\n--- All notes in the system ---");
                for (Note note : notes) {
                    System.out.println(note);
                    System.out.println();
                }
            }
        } catch (SQLException e) {
            System.out.println("Could not get any notes: " + e.getMessage());
        }
    }

    private void deleteAnyNote() {
        viewAllNotes();

        System.out.print("Enter the ID of the note to delete: ");
        try {
            int id = Integer.parseInt(scanner.nextLine().trim());
            noteService.deleteAnyNote(id);

        } catch (NumberFormatException e) {
            System.out.println("Invalid ID, please enter an integer.");
        } catch (SQLException e) {
            System.out.println("Could not delete note: " + e.getMessage());
        }
    }
}
