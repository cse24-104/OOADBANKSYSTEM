package com.example.thesystem.boundary;

import com.example.thesystem.Customer;
import com.example.thesystem.Account;
import com.example.thesystem.SavingsAccount;
import com.example.thesystem.ChequeAccount;
import com.example.thesystem.InvestmentAccount;
import com.example.thesystem.InterestBearing;

import com.example.thesystem.boundary.DashboardView;
import com.example.thesystem.*;
import com.example.thesystem.controller.SceneController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class CreateAccountView extends VBox {
    private ComboBox<String> accountTypeBox;
    private TextField branchField;
    private TextField initialDepositField;
    private TextField employerNameField;
    private TextField employerAddressField;
    private Button createButton;
    private Button backButton;

    public CreateAccountView() {
        setSpacing(10);
        setPadding(new Insets(20));

        accountTypeBox = new ComboBox<>();
        accountTypeBox.getItems().addAll("Savings", "Cheque", "Investment");
        accountTypeBox.setPromptText("Select Account Type");

        branchField = new TextField();
        branchField.setPromptText("Branch");

        initialDepositField = new TextField();
        initialDepositField.setPromptText("Initial Deposit");

        employerNameField = new TextField();
        employerNameField.setPromptText("Employer Name (for Cheque)");

        employerAddressField = new TextField();
        employerAddressField.setPromptText("Employer Address (for Cheque)");

        createButton = new Button("Create Account");
        backButton = new Button("Back to Dashboard");

        getChildren().addAll(accountTypeBox, branchField, initialDepositField,
                employerNameField, employerAddressField, createButton, backButton);

        // Handle create account
        createButton.setOnAction(e -> {
            Customer currentCustomer = BankDatabase.getLoggedInCustomer();
            if (currentCustomer == null) return;

            String type = accountTypeBox.getValue();
            String branch = branchField.getText();
            double deposit = 0;
            try {
                deposit = Double.parseDouble(initialDepositField.getText());
            } catch (NumberFormatException ex) {
                showAlert("Enter a valid deposit amount");
                return;
            }

            Account newAccount;

            try {
                switch (type) {
                    case "Savings":
                        newAccount = new SavingsAccount(generateAccountNumber(), branch, currentCustomer);
                        newAccount.deposit(deposit);
                        break;
                    case "Cheque":
                        String employerName = employerNameField.getText();
                        String employerAddress = employerAddressField.getText();
                        newAccount = new ChequeAccount(generateAccountNumber(), branch, currentCustomer, employerName, employerAddress);
                        newAccount.deposit(deposit);
                        break;
                    case "Investment":
                        if (deposit < 500) {
                            showAlert("Minimum deposit for Investment Account is BWP500.00");
                            return;
                        }
                        newAccount = new InvestmentAccount(generateAccountNumber(), branch, currentCustomer, deposit);
                        break;
                    default:
                        showAlert("Select a valid account type");
                        return;
                }

                currentCustomer.addAccount(newAccount);
                showAlert("Account created successfully!", Alert.AlertType.INFORMATION);

                // Return to dashboard
                SceneController.setScene(new Scene(new DashboardView(), 400, 400));

            } catch (Exception ex) {
                showAlert("Error creating account: " + ex.getMessage());
            }
        });

        backButton.setOnAction(e -> {
            SceneController.setScene(new Scene(new DashboardView(), 400, 400));
        });
    }

    private String generateAccountNumber() {
        return "ACCT" + (int) (Math.random() * 1000000);
    }

    private void showAlert(String message) {
        showAlert(message, Alert.AlertType.ERROR);
    }

    private void showAlert(String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
