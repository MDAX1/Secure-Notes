package com.securenotes.service;

import com.securenotes.model.User;
import com.securenotes.repository.UserRepository;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.SQLException;
import java.util.Optional;

public class AuthService {

    private final UserRepository userRepository = new UserRepository();

    public void register(String username, String password) throws SQLException {
        // Kolla om användarnamnet redan finns – VG felhantering
        if (userRepository.usernameExists(username)) {
            throw new IllegalArgumentException(
                    "Username '" + username + "' is already taken. Please choose another."
            );
        }

        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        userRepository.save(username, hashedPassword);
        System.out.println("Account created successfully! You can log in now.");
    }

    public Optional<User> login(String username, String password) throws SQLException {
        Optional<User> found = userRepository.findByUsername(username);

        if (found.isEmpty()) {
            return Optional.empty();
        }

        User user = found.get();

        boolean correctPassword = BCrypt.checkpw(password, user.getPassword());

        if (correctPassword) {
            return Optional.of(user);
        }

        return Optional.empty();
    }

    // Ändra lösenord – verifierar gamla lösenordet innan vi tillåter ändring
    public void changePassword(User user, String oldPassword, String newPassword)
            throws SQLException {

        if (!BCrypt.checkpw(oldPassword, user.getPassword())) {
            throw new IllegalArgumentException("Incorrect current password.");
        }

        String newHashed = BCrypt.hashpw(newPassword, BCrypt.gensalt());
        userRepository.updatePassword(user.getId(), newHashed);
        System.out.println("Password changed successfully!");
    }
}