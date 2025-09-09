package org.example.service;

import java.security.MessageDigest;

public class PasswordUtil {

    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256"); // Secure hashing algorithm
            byte[] hashBytes = md.digest(password.getBytes());

            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b)); // Convert byte to hex
            }
            return sb.toString();

        } catch (Exception e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }
}