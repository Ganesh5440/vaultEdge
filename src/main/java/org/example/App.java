package org.example;

import java.sql.SQLException;
import java.util.*;
import org.example.service.AccountService;
import org.example.service.PasswordUtil;
import org.example.service.CustomerService;
import org.example.service.CustomerService;
import org.example.service.TransactionService;
import org.example.auth.AuthService;
import org.example.dao.AccountDAO;
import org.example.dao.CustomerDAO;
import org.example.dao.TransactionDAO;
import org.example.dao.UserDAO;
import org.example.model.Account;
import org.example.model.Customer;
import org.example.model.Transaction;
import org.example.model.User;
/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) throws SQLException {
        Scanner sc = new Scanner(System.in);
        AuthService authService = new AuthService();
        CustomerService customerService = new CustomerService();
        AccountService accountService = new AccountService();
        TransactionService transactionService = new TransactionService();
        int finalId=0;

        System.out.println("Welcome to VaultEdge 💼");

        while (true) {
            System.out.println("\n1. Register\n2. Login\n3. Exit");
            System.out.print("Choose: ");
            int choice = sc.nextInt();
            sc.nextLine();

            if (choice == 1) {
                System.out.print("Username: ");
                String username = sc.nextLine();
                System.out.print("Password: ");
                String password = sc.nextLine();
                boolean registered = authService.register(username, password, "customer");
                System.out.println(registered ? "Registered successfully!" : "Registration failed.");
            }

            else if (choice == 2) {
                System.out.print("Username: ");
                String username = sc.nextLine();
                System.out.print("Password: ");
                String password = sc.nextLine();
                boolean loggedIn = authService.login(username, password);

                if (loggedIn) {
                    int userId = authService.getUserId(username);
                    System.out.println("Login successful!");

                    Customer customer = customerService.getCustomerProfile(userId);
                    if (customer == null) {
                        System.out.println("No customer profile found. Let's create one.");
                        System.out.print("Name: ");
                        String name = sc.nextLine();
                        System.out.print("Email: ");
                        String email = sc.nextLine();
                        System.out.print("Phone: ");
                        String phone = sc.nextLine();
                        System.out.print("Address: ");
                        String address = sc.nextLine();
                        customerService.createCustomerProfile(userId, name, email, phone, address);
                        System.out.println("Profile creation successful!");
                    } else {
                        System.out.println("Welcome back, " + customer.getName());
                    }

                    // Account dashboard
                    while (true) {
                        System.out.println("\n--- Account Menu ---");
                        System.out.println("User id is : "+userId);
                        System.out.println("Customer id is : "+customerService.getCustomerIdByUserId(userId));
                        Account account1=accountService.getAccountsByCustomerId(userId);
                        if(account1!=null) {
                            System.out.println("Account id is : " + accountService.getAccountIdByUserId(userId));
                        }
                        System.out.println("1. Open Account");

                        System.out.println("2. Deposit");
                        System.out.println("3. Withdraw");
                        System.out.println("4. View Transactions");
                        System.out.println("5. Logout");
                        System.out.println("6.  Check Balance");
                        System.out.print("Choose: ");
                        int action = sc.nextInt();
                        sc.nextLine();

                        if (action == 1) {
                            System.out.println("Enter the user id");
                            int userID=sc.nextInt();
                            System.out.print("Account Type (Savings/Current) select 1/2: ");
                            int accountType=sc.nextInt();
                            String type = accountType==1?"Savings":"Current";
                            System.out.print("Initial Deposit: ");
                            double deposit = sc.nextDouble();
                            sc.nextLine();
                            Customer customer1=customerService.getCustomerProfile(userID);
                            Account account2=accountService.getAccountsByCustomerId(userID);
                            if(account2!=null && account2.getAccountType().equals(type)) {

                                System.out.println("Account already exists! cannot create new account with same type");
                            }else{
                                accountService.openAccount(userID,type,deposit);
                            }
                        }

                        else if (action == 2) {
                            System.out.print("Account ID: ");
                            int accId = sc.nextInt();
                            System.out.print("Amount: ");
                            double amount = sc.nextDouble();
                            sc.nextLine();
                            accountService.deposit(accId, amount);
                        }

                        else if (action == 3) {
                            System.out.print("Account ID: ");
                            int accId = sc.nextInt();
                            System.out.print("Amount: ");
                            double amount = sc.nextDouble();
                            sc.nextLine();
                            accountService.withdraw(accId, amount);
                        }

                        else if (action == 4) {
                            System.out.print("Account ID: ");
                            int accId = sc.nextInt();
                            sc.nextLine();
                            List<Transaction> transactions = transactionService.getTransactionHistory(accId);
                            transactionService.printTransactionSummary(transactions);
                        }
                        else if(action==6){
                            double balance=accountService.getAccountBalance(userId);
                            System.out.println("Balance : "+balance);
                        }

                        else if (action == 5) {
                            System.out.println("Logged out.");
                            break;
                        }

                        else {
                            System.out.println("Invalid option.");
                        }
                    }
                } else {
                    System.out.println("Login failed.");
                }
            }

            else if (choice == 3) {
                System.out.println("Thanks for using VaultEdge. Goodbye!");
                break;
            }

            else {
                System.out.println("Invalid choice.");
            }
        }
    }
}
