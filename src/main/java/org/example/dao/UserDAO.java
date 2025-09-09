package org.example.dao;

import org.example.dbConnection.DBConnection;

import org.example.model.User;

import org.example.service.PasswordUtil;

import java.sql.*;

public class UserDAO {

    // Register a new user
    public boolean registerUser(User user) {
        String sql = "INSERT INTO users (username, password_hash, role) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPasswordHash());
            ps.setString(3, user.getRole());
            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Registration failed: " + e.getMessage());
            return false;
        }
    }

    // Login user by verifying password
    public boolean loginUser(String username, String password) {
        String sql = "SELECT password_hash FROM users WHERE username = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String storedHash = rs.getString("password_hash");
                String inputHash = PasswordUtil.hashPassword(password);
                return storedHash.equals(inputHash);
            }

        } catch (SQLException e) {
            System.out.println("Login failed: " + e.getMessage());
        }
        return false;
    }

    // Get user ID by username (for linking with Customer)
    public int getUserId(String username) {
        String sql = "SELECT user_id FROM users WHERE username = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("user_id");
            }

        } catch (SQLException e) {
            System.out.println("Error fetching user ID: " + e.getMessage());
        }
        return -1;
    }
}