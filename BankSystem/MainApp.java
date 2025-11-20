package com.example.banksystem;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainApp extends Application {

    private List<Customer> customers = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) {
        // Load saved data
        customers = DataStorage.loadCustomers();
        List<Account> accounts = DataStorage.loadAccounts(customers);
        DataStorage.loadTransactions(accounts);

        // Initialize login view and controller
        LoginView loginView = new LoginView();
        LoginController loginController = new LoginController(primaryStage, customers, loginView);

        // Show login
        loginView.showLogin(loginController);

        // Handle application close: save data
        primaryStage.setOnCloseRequest(e -> {
            DataStorage.saveCustomers(customers);
            List<Account> allAccounts = customers.stream()
                    .flatMap(c -> c.getAccounts().stream())
                    .toList();
            DataStorage.saveAccounts(allAccounts);
            DataStorage.saveTransactions(allAccounts);
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
