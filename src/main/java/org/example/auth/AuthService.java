package org.example.auth;

import org.example.dao.UserDAO;
import org.example.model.User;
import org.example.service.PasswordUtil;

public class AuthService {
    private final UserDAO userDAO;

    public AuthService() {
        this.userDAO = new UserDAO();
    }

    // Register a new user
    public boolean register(String username, String password, String role) {
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            System.out.println("Username and password cannot be empty.");
            return false;
        }

        String hashedPassword = PasswordUtil.hashPassword(password);
        User user = new User(username, hashedPassword, role != null ? role : "customer");

        boolean success = userDAO.registerUser(user);
        if (success) {
            System.out.println("User registered successfully.");
        } else {
            System.out.println("Registration failed. Username might already exist.");
        }
        return success;
    }

    // Login and verify credentials
    public boolean login(String username, String password) {
        if (username == null || password == null) {
            System.out.println("Username and password are required.");
            return false;
        }

        boolean success = userDAO.loginUser(username, password);
        if (success) {
            System.out.println("Login successful.");
        } else {
            System.out.println("Invalid credentials.");
        }
        return success;
    }

    // Get user ID by username
    public int getUserId(String username) {
        return userDAO.getUserId(username);
    }
}