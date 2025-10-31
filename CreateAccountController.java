package com.example.thesystem;

import com.example.thesystem.boundary.DashboardView;
import com.example.thesystem.Customer;
import com.example.thesystem.Account;
import com.example.thesystem.SavingsAccount;
import com.example.thesystem.ChequeAccount;
import com.example.thesystem.InvestmentAccount;
import com.example.thesystem.*;
import com.example.thesystem.controller.SceneController;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.Scene;

public class CreateAccountController {

    @FXML private ComboBox<String> accountTypeBox;
    @FXML private TextField branchField;
    @FXML private TextField initialDepositField;
    @FXML private TextField employerNameField;
    @FXML private TextField employerAddressField;
    @FXML private Button createButton;
    @FXML private Button backButton;

    @FXML
    private void initialize() {
        accountTypeBox.getItems().addAll("Savings", "Cheque", "Investment");

        createButton.setOnAction(e -> createAccount());
        backButton.setOnAction(e -> SceneController.setScene(new Scene(new DashboardView(), 600, 400)));
    }

    private void createAccount() {
        Customer currentCustomer = BankDatabase.getLoggedInCustomer();
        if (currentCustomer == null) return;

        String type = accountTypeBox.getValue();
        String branch = branchField.getText();
        double deposit;

        try {
            deposit = Double.parseDouble(initialDepositField.getText());
        } catch (NumberFormatException e) {
            showAlert("Enter a valid deposit amount");
            return;
        }

        Account newAccount;

        try {
            switch (type) {
                case "Savings" -> {
                    newAccount = new SavingsAccount(generateAccountNumber(), branch, currentCustomer);
                    newAccount.deposit(deposit);
                }
                case "Cheque" -> {
                    String empName = employerNameField.getText();
                    String empAddress = employerAddressField.getText();
                    newAccount = new ChequeAccount(generateAccountNumber(), branch, currentCustomer, empName, empAddress);
                    newAccount.deposit(deposit);
                }
                case "Investment" -> {
                    if (deposit < 500) {
                        showAlert("Minimum deposit for Investment Account is BWP500.00");
                        return;
                    }
                    newAccount = new InvestmentAccount(generateAccountNumber(), branch, currentCustomer, deposit);
                }
                default -> {
                    showAlert("Select a valid account type");
                    return;
                }
            }

            currentCustomer.addAccount(newAccount);
            showAlert("Account created successfully!", Alert.AlertType.INFORMATION);
            SceneController.setScene(new Scene(new DashboardView(), 600, 400));

        } catch (Exception ex) {
            showAlert("Error creating account: " + ex.getMessage());
        }
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
