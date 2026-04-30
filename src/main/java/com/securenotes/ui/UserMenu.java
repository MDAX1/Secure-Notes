package com.securenotes.ui;

import com.securenotes.model.Note;
import com.securenotes.model.User;
import com.securenotes.service.AuthService;
import com.securenotes.service.NoteService;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class UserMenu {

    private final Scanner scanner;
    private final NoteService noteService;
    private final AuthService authService;
    private final User currentUser;

    public UserMenu(Scanner scanner, User user) {
        this.scanner = scanner;
        this.currentUser = user;
        this.noteService = new NoteService();
        this.authService = new AuthService();
    }

    public void run() {
        boolean running = true;

        while (running) {
            showMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> createNote();
                case "2" -> viewNotes();
                case "3" -> editNote();
                case "4" -> deleteNote();
                case "5" -> changePassword();
                case "6" -> {
                    System.out.println("You are now logged out.");
                    running = false;
                }
                default -> System.out.println("Invalid selection, please try again.");
            }
        }
    }

    private void showMenu() {
        System.out.println("\n-----------------------------");
        System.out.println("  Logged in as: " + currentUser.getUsername());
        System.out.println("-----------------------------");
        System.out.println("1. Create note");
        System.out.println("2. Show my notes");
        System.out.println("3. Edit note");
        System.out.println("4. Delete note");
        System.out.println("5. Change password");
        System.out.println("6. Sign out");
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
            System.out.println("Could not save note: " + e.getMessage());
        }
    }

    private void viewNotes() {
        try {
            List<Note> notes = noteService.getMyNotes(currentUser);

            if (notes.isEmpty()) {
                System.out.println("You have no notes yet.");
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

    private void editNote() {
        viewNotes();

        System.out.print("Enter ID of note to edit: ");
        try {
            int id = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("New title: ");
            String title = scanner.nextLine().trim();

            System.out.print("New content: ");
            String content = scanner.nextLine().trim();

            noteService.editNote(currentUser, id, title, content);

        } catch (NumberFormatException e) {
            System.out.println("Invalid ID, please enter a number.");
        } catch (SQLException e) {
            System.out.println("Could not edit note: " + e.getMessage());
        }
    }

    private void deleteNote() {
        viewNotes();

        System.out.print("Enter ID of note to delete: ");
        try {
            int id = Integer.parseInt(scanner.nextLine().trim());
            noteService.deleteMyNote(currentUser, id);

        } catch (NumberFormatException e) {
            System.out.println("Invalid ID, please enter a number.");
        } catch (SQLException e) {
            System.out.println("Could not delete note: " + e.getMessage());
        }
    }

    private void changePassword() {
        System.out.print("Current password: ");
        String oldPass = scanner.nextLine().trim();

        System.out.print("New password: ");
        String newPass = scanner.nextLine().trim();

        try {
            authService.changePassword(currentUser, oldPass, newPass);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Could not change password: " + e.getMessage());
        }
    }
}