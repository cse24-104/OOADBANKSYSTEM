package com.example.thesystem.boundary;

import com.example.thesystem.Account;
import com.example.thesystem.Customer;
import com.example.thesystem.Individual;
import com.example.thesystem.SavingsAccount;
import com.example.thesystem.boundary.AccountView;
import com.example.thesystem.boundary.CreateAccountView;
import com.example.thesystem.boundary.CustomerProfileView;
import com.example.thesystem.boundary.LoginView;
import com.example.thesystem.controller.SceneController;

import com.example.thesystem.*;
import com.example.thesystem.controller.SceneController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import com.example.thesystem.BankDatabase;
import javafx.scene.control.*;

public class DashboardView extends VBox {

    private Button createAccountButton;
    private Button profileButton;
    private Button logoutButton;

    private Customer currentCustomer;

    public DashboardView() {
        setSpacing(10);
        setPadding(new Insets(20));

        currentCustomer = BankDatabase.getLoggedInCustomer();
        if (currentCustomer == null) return;

        Label welcomeLabel = new Label("Welcome, " + currentCustomer.getFirstName() + " " + currentCustomer.getLastName());

        // List all accounts dynamically
        ListView<Account> accountListView = new ListView<>();
        accountListView.getItems().addAll(currentCustomer.getAccounts());

        accountListView.setOnMouseClicked(event -> {
            Account selectedAccount = accountListView.getSelectionModel().getSelectedItem();
            if (selectedAccount != null) {
                SceneController.setScene(new Scene(new AccountView(selectedAccount), 400, 400));
            }
        });

        createAccountButton = new Button("Open New Account");
        profileButton = new Button("My Profile");
        logoutButton = new Button("Logout");

        getChildren().addAll(welcomeLabel, new Label("Your Accounts:"), accountListView, createAccountButton, profileButton, logoutButton);

        // Create Account action
        createAccountButton.setOnAction(e -> {
            if (currentCustomer instanceof Individual) {
                SceneController.setScene(new Scene(new CreateAccountView(), 400, 400));
            } else {
                showAlert("Account creation is not available for Companies yet.");
            }
        });

        // Profile action
        profileButton.setOnAction(e -> {
            SceneController.setScene(new Scene(new CustomerProfileView(currentCustomer), 400, 400));
        });

        // Logout action
        logoutButton.setOnAction(e -> {
            BankDatabase.setLoggedInCustomer(null);
            SceneController.setScene(new Scene(new LoginView(), 400, 300));
        });
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
