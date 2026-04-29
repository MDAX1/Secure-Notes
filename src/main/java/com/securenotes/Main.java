package com.securenotes;

import com.securenotes.model.User;
import com.securenotes.ui.MainMenu;
import com.securenotes.ui.UserMenu;

import java.util.Optional;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        MainMenu mainMenu = new MainMenu(scanner);

        boolean running = true;

        while (running) {
            mainMenu.show();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> mainMenu.handleRegister();

                case "2" -> {
                    Optional<User> loggedInUser = mainMenu.handleLogin();

                    if (loggedInUser.isPresent()) {
                        new UserMenu(scanner, loggedInUser.get()).run();
                    }
                }

                case "3" -> {
                    System.out.println("Goodbye!");
                    running = false;
                }

                default -> System.out.println("Invalid selection, please try again.");
            }
        }

        scanner.close();
    }
}
