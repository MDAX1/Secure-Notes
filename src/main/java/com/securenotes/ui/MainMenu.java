package com.securenotes.ui;

import com.securenotes.model.User;
import com.securenotes.service.AuthService;

import java.sql.SQLException;
import java.util.Optional;
import java.util.Scanner;

public class MainMenu {

    private final Scanner scanner;
    private final AuthService authService;

    public MainMenu(Scanner scanner) {
        this.scanner = scanner;
        this.authService = new AuthService();
    }

    public void show() {
        System.out.println("*** Welcome to Secure Notes application!*** ");
        System.out.println("Please choose one of the options below:");
        System.out.println("1. Register");
        System.out.println("2. Sign in");
        System.out.println("3. End");
        System.out.print("Choose an option: ");
    }

    public void handleRegister() {
        System.out.print("Choose a username: ");
        String username = scanner.nextLine().trim();

        System.out.print("Choose a password: ");
        String password = scanner.nextLine().trim();

        try {
            authService.register(username, password);
        } catch (SQLException e) {
            System.out.println("Something went wrong during registration: " + e.getMessage());
        }
    }

    public Optional<User> handleLogin() {
        System.out.print("User name: ");
        String username = scanner.nextLine().trim();

        System.out.print("Password: ");
        String password = scanner.nextLine().trim();

        try {
            Optional<User> user = authService.login(username, password);

            if (user.isEmpty()) {
                System.out.println("Incorrect username or password. Please try again.");
            } else {
                System.out.println("Welcome, " + user.get().getUsername() + "!");
            }
            return user;

        } catch (SQLException e) {
            System.out.println("Något gick fel vid inloggning: " + e.getMessage());
            return Optional.empty();
        }
    }
}
