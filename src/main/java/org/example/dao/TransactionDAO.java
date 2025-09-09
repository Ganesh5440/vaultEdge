package org.example.dao;


import org.example.dbConnection.DBConnection;
import org.example.model.Account;
import org.example.model.Customer;
import org.example.model.Transaction;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.example.service.CustomerService;
import org.example.service.AccountService;

public class TransactionDAO {

    // Record a new transaction
    public boolean recordTransaction(Transaction transaction) {

        String sql = "INSERT INTO transactions (customer_id, type, amount, timestamp) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
           //  Account account=accountService.getAccountById(transaction.getAccountId());

            ps.setInt(1, transaction.getAccountId());
            ps.setString(2, transaction.getType());
            ps.setDouble(3, transaction.getAmount());
            ps.setTimestamp(4, transaction.getTimestamp());
            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error recording transaction: " + e.getMessage());
            return false;
        }
    }

    // Get all transactions for an account
    public List<Transaction> getTransactionsByAccountId(int customerId) {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions WHERE customer_id = ? ORDER BY timestamp DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, customerId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Transaction transaction = new Transaction();
                transaction.setTransactionId(rs.getInt("transaction_id"));
                transaction.setAccountId(rs.getInt("customer_id"));
                transaction.setType(rs.getString("type"));
                transaction.setAmount(rs.getDouble("amount"));
                transaction.setTimestamp(rs.getTimestamp("timestamp"));
                transactions.add(transaction);
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving transactions: " + e.getMessage());
        }
        return transactions;
    }

    // Optional: Filter transactions by type
    public List<Transaction> getTransactionsByType(int accountId, String type) {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions WHERE customer_id = ? AND type = ? ORDER BY timestamp DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, accountId);
            ps.setString(2, type);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Transaction transaction = new Transaction();
                transaction.setTransactionId(rs.getInt("transaction_id"));
                transaction.setAccountId(rs.getInt("account_id"));
                transaction.setType(rs.getString("type"));
                transaction.setAmount(rs.getDouble("amount"));
                transaction.setTimestamp(rs.getTimestamp("timestamp"));
                transactions.add(transaction);
            }

        } catch (SQLException e) {
            System.out.println("Error filtering transactions: " + e.getMessage());
        }
        return transactions;
    }
}
