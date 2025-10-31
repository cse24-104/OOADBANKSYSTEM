package com.example.thesystem.boundary;

import  com.example.thesystem.boundary.CreateAccountView;
import  com.example.thesystem.boundary.LoginView;
import com.example.thesystem.BankDatabase;
import com.example.thesystem.Account;
import com.example.thesystem.Customer;
import com.example.thesystem.controller.SceneController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.util.List;

public class DashboardView extends VBox {
    private Button viewAccountsButton;
    private Button createAccountButton;
    private Button balanceButton;
    private Button transactionHistoryButton;
    private Button logoutButton;

    private Customer loggedInCustomer;

    public DashboardView() {
        Customer customer = null;
        this.loggedInCustomer = customer;

        setSpacing(10);
        setPadding(new Insets(20));

        Label welcomeLabel = new Label("Welcome, " + customer.getFirstName() + "!");
        viewAccountsButton = new Button("View My Accounts");
        createAccountButton = new Button("Open New Account");
        balanceButton = new Button("Check Balance");
        transactionHistoryButton = new Button("View Transaction History");
        logoutButton = new Button("Logout");

        getChildren().addAll(
                welcomeLabel,
                viewAccountsButton,
                createAccountButton,
                balanceButton,
                transactionHistoryButton,
                logoutButton
        );

        // 🔹 View Accounts
        viewAccountsButton.setOnAction(e -> {
            List<Account> accounts = BankDatabase.getCustomerAccounts(customer.getUsername());
            if (accounts.isEmpty()) {
                showAlert("No Accounts", "You don't have any accounts yet.");
            } else {
                StringBuilder sb = new StringBuilder("Your Accounts:\n");
                for (Account acc : accounts) {
                    sb.append("- ").append(acc.getAccountNumber())
                            .append(" (").append(acc.getClass().getSimpleName())
                            .append(") | Balance: BWP ").append(acc.getBalance()).append("\n");
                }
                showAlert("Your Accounts", sb.toString());
            }
        });

        // 🔹 Create Account (choose type)
        createAccountButton.setOnAction(e -> {
            CreateAccountView createView = new CreateAccountView(customer);
            Scene createScene = new Scene(createView, 400, 400);
            SceneController.setScene(createScene);
        });

        // 🔹 Check Balance
        balanceButton.setOnAction(e -> {
            List<Account> accounts = BankDatabase.getCustomerAccounts(customer.getUsername());
            if (accounts.isEmpty()) {
                showAlert("No Accounts", "You don't have any accounts.");
                return;
            }

            StringBuilder sb = new StringBuilder("Account Balances:\n");
            for (Account acc : accounts) {
                sb.append(acc.getAccountNumber())
                        .append(" → BWP ").append(acc.getBalance()).append("\n");
            }
            showAlert("Balances", sb.toString());
        });

        // 🔹 View Transaction History
        transactionHistoryButton.setOnAction(e -> {
            List<String> history = BankDatabase.getTransactionHistory(customer.getUsername());
            if (history.isEmpty()) {
                showAlert("No Transactions", "You have no transaction history.");
            } else {
                StringBuilder sb = new StringBuilder("Transaction History:\n");
                for (String record : history) sb.append(record).append("\n");
                showAlert("Transactions", sb.toString());
            }
        });

        // 🔹 Logout
        logoutButton.setOnAction(e -> {
            LoginView loginView = new LoginView();
            Scene loginScene = new Scene(loginView, 400, 300);
            SceneController.setScene(loginScene);
        });
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
