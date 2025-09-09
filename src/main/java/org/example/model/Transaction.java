package org.example.model;
import java.sql.Timestamp;

public class Transaction {
    private int transactionId;
    private int accountId; // Foreign key from Account
    private String type;   // Deposit, Withdrawal, Transfer
    private double amount;
    private Timestamp timestamp;

    // Constructors
    public Transaction() {}

    public Transaction(int accountId, String type, double amount, Timestamp timestamp) {
        this.accountId = accountId;
        this.type = type;
        this.amount = amount;
        this.timestamp = timestamp;
    }

    // Getters and Setters
    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
