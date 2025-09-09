package org.example.service;


import org.example.dao.TransactionDAO;
import org.example.model.Transaction;

import java.util.List;

public class TransactionService {
    private final TransactionDAO transactionDAO;

    public TransactionService() {
        this.transactionDAO = new TransactionDAO();
    }

    // Get all transactions for an account
    public List<Transaction> getTransactionHistory(int customerId) {
        return transactionDAO.getTransactionsByAccountId(customerId);
    }

    // Filter transactions by type (Deposit, Withdrawal, etc.)
    public List<Transaction> getTransactionsByType(int accountId, String type) {
        return transactionDAO.getTransactionsByType(accountId, type);
    }

    // Optional: Print transaction summary
    public void printTransactionSummary(List<Transaction> transactions) {
        System.out.println("\n--- Transaction History ---");
        for (Transaction tx : transactions) {
            System.out.printf("ID: %d | Type: %s | Amount: %.2f | Date: %s%n",
                    tx.getTransactionId(),
                    tx.getType(),
                    tx.getAmount(),
                    tx.getTimestamp().toString());
        }
    }
}