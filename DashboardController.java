package com.example.thesystem;

import com.example.thesystem.BankDatabase;
import com.example.thesystem.boundary.AccountView;
import com.example.thesystem.boundary.CreateAccountView;
import com.example.thesystem.boundary.CustomerProfileView;
import com.example.thesystem.boundary.LoginView;
import com.example.thesystem.Account;
import com.example.thesystem.Customer;
import com.example.thesystem.*;
import com.example.thesystem.controller.SceneController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.Scene;

public class DashboardController {

    @FXML private Label welcomeLabel;
    @FXML private ListView<Account> accountListView;
    @FXML private Button createAccountButton;
    @FXML private Button profileButton;
    @FXML private Button logoutButton;

    private Customer currentCustomer;

    @FXML
    private void initialize() {
        currentCustomer = BankDatabase.getLoggedInCustomer();
        if (currentCustomer == null) return;

        welcomeLabel.setText("Welcome, " + currentCustomer.getFirstName() + " " + currentCustomer.getLastName());

        accountListView.getItems().addAll(currentCustomer.getAccounts());

        accountListView.setOnMouseClicked(e -> {
            Account selected = accountListView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                SceneController.setScene(new Scene(new AccountView(selected), 600, 400));
            }
        });

        createAccountButton.setOnAction(e -> SceneController.setScene(new Scene(new CreateAccountView(), 600, 400)));
        profileButton.setOnAction(e -> SceneController.setScene(new Scene(new CustomerProfileView(currentCustomer), 600, 400)));
        logoutButton.setOnAction(e -> {
            BankDatabase.setLoggedInCustomer(null);
            SceneController.setScene(new Scene(new LoginView(), 600, 400));
        });
    }
}
