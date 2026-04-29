package com.securenotes.service;

import com.securenotes.model.User;
import com.securenotes.repository.UserRepository;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.SQLException;
import java.util.Optional;

public class AuthService {

    private final UserRepository userRepository = new UserRepository();

    public void register(String username, String password) throws SQLException {
        // Hasha lösenordet innan vi sparar eftersom man ska aldrig spara lösenord i klartext
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        userRepository.save(username, hashedPassword);
        System.out.println("Account created successfully! You can Loging now.");
    }

    public Optional<User> login(String username, String password) throws SQLException {
        Optional<User> found = userRepository.findByUsername(username);

        // Ingen användare med det användarnamnet
        if (found.isEmpty()) {
            return Optional.empty();
        }

        User user = found.get();

        // Jämför det angivna lösenordet med det hashade i databasen
        boolean correctPassword = BCrypt.checkpw(password, user.getPassword());

        if (correctPassword) {
            return Optional.of(user);
        }

        return Optional.empty();
    }
}
