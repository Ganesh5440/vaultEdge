package org.example.service;


import org.example.dao.AccountDAO;
import org.example.dao.TransactionDAO;
import org.example.dao.UserDAO;
import org.example.model.Account;
import org.example.model.Transaction;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

 public class AccountService {
    private final AccountDAO accountDAO;
    private final TransactionDAO transactionDAO;

    public AccountService() {
        this.accountDAO = new AccountDAO();
        this.transactionDAO = new TransactionDAO();
    }

    // Open a new account
    public boolean openAccount(int customerId, String accountType, double initialDeposit) {
        Account account = new Account(customerId, accountType, initialDeposit, "Active");
        boolean success = accountDAO.openAccount(account);
        transactionDAO.recordTransaction(new Transaction(customerId,accountType,initialDeposit, new Timestamp(System.currentTimeMillis())));
        System.out.println("Account creation Successfull");

//        if (success) {
//            // Record initial deposit as a transaction
//            deposit(customerId, initialDeposit);
//            System.out.println("Deposit done successfully");
//        }

        return success;
    }

    // Deposit money
    public boolean deposit(int accountId, double amount) {
      if(accountDAO.getAccountById(accountId) == null) {
          System.out.println("Account does not exist Deposit fixed");
          return false;
      }
        boolean success = accountDAO.deposit(accountId, amount);

        if (success) {
            Transaction transaction = new Transaction(accountId, "Deposit", amount, new Timestamp(System.currentTimeMillis()));
            transactionDAO.recordTransaction(transaction);
            System.out.println("Deposit done successfully");
        }
        return success;
    }

    // Withdraw money
    public boolean withdraw(int accountId, double amount) {
        boolean success = accountDAO.withdraw(accountId, amount);
        if (success) {
            Transaction transaction = new Transaction(accountId, "Withdrawal", amount, new Timestamp(System.currentTimeMillis()));
            transactionDAO.recordTransaction(transaction);

        }
        return success;
    }

    // Close account
    public boolean closeAccount(int accountId) {
        return accountDAO.closeAccount(accountId);
    }

    // Get account details
    public Account getAccountById(int accountId) {
        return accountDAO.getAccountById(accountId);
    }
    public int getAccountIdByUserId(int userId) {
        Account acc=accountDAO.getAccountByUserId(userId);

        return acc.getAccountId();
    }
    public double getAccountBalance(int userId) throws SQLException {
        return accountDAO.getAccountBalance(userId);
    }

    // Get all accounts for a customer
    public Account getAccountsByCustomerId(int customerId) {
        return accountDAO.getAccountById(customerId);
    }
}
