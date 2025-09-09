package org.example.dao;

import org.example.dbConnection.DBConnection;
import org.example.model.Account;

import java.sql.*;

public class AccountDAO {

    // Open a new account
    public boolean openAccount(Account account) {
        String sql = "INSERT INTO accounts (customer_id, account_type, balance, status) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, account.getCustomerId());
            ps.setString(2, account.getAccountType());
            ps.setDouble(3, account.getBalance());
            ps.setString(4, account.getStatus());
            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error opening account: " + e.getMessage());
            return false;
        }
    }

    // Get account by ID
    public Account getAccountById(int accountId) {
        String sql = "SELECT * FROM accounts WHERE customer_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, accountId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Account account = new Account();
                account.setAccountId(rs.getInt("account_id"));
                account.setCustomerId(rs.getInt("customer_id"));
                account.setAccountType(rs.getString("account_type"));
                account.setBalance(rs.getDouble("balance"));
                account.setStatus(rs.getString("status"));
                return account;
            }

        } catch (SQLException e) {
            System.out.println("Error fetching account: " + e.getMessage());
        }
        return null;
    }
    public Account getAccountByUserId(int userId) {
        String sql = "SELECT * FROM accounts WHERE customer_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Account account = new Account();
                account.setAccountId(rs.getInt("account_id"));
                account.setCustomerId(rs.getInt("customer_id"));
                account.setAccountType(rs.getString("account_type"));
                account.setBalance(rs.getDouble("balance"));
                account.setStatus(rs.getString("status"));
                return account;
            }

        } catch (SQLException e) {
            System.out.println("Error fetching account: " + e.getMessage());
        }
        return null;
    }


    // Deposit money
    public boolean deposit(int accountId, double amount) {

        String sql = "UPDATE accounts SET balance = balance + ? WHERE  customer_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDouble(1, amount);
            ps.setInt(2, accountId);
            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error depositing: " + e.getMessage());
            return false;
        }
    }

    // Withdraw money
    public boolean withdraw(int accountId, double amount) {
        String sql = "UPDATE accounts SET balance = balance - ? WHERE customer_id = ? AND balance >= ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDouble(1, amount);
            ps.setInt(2, accountId);
            ps.setDouble(3, amount);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Error withdrawing: " + e.getMessage());
            return false;
        }
    }

    // Close account
    public boolean closeAccount(int accountId) {
        String sql = "UPDATE accounts SET status = 'Inactive' WHERE account_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, accountId);
            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error closing account: " + e.getMessage());
            return false;
        }
    }
    public double getAccountBalance(int accountId) throws SQLException {
        Connection conn = DBConnection.getConnection();
        String query = "SELECT balance FROM accounts WHERE customer_id = ? AND status = 'Active'";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, accountId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("balance");
                } else {
                    System.out.println("Error Fetching in the balance");
                    return 0.0; // Account not found or inactive
                }
            }
        }
    }

}

// Get all accounts for a customer