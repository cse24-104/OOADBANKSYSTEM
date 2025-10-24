package com.example.thesystem.boundary;

import com.example.thesystem.Account;
import com.example.thesystem.controller.SceneController;
import com.example.thesystem.boundary.DashboardView;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class AccountView extends VBox {
    private Label accountNumberLabel;
    private Label balanceLabel;
    private TextField amountField;
    private Button depositButton;
    private Button withdrawButton;
    private Button backButton;
    private ListView<String> transactionList;

    public AccountView(Account account) {
        setSpacing(10);
        setPadding(new Insets(20));

        accountNumberLabel = new Label("Account Number: " + account.getAccountNumber());
        balanceLabel = new Label("Balance: BWP" + account.getBalance());

        amountField = new TextField();
        amountField.setPromptText("Enter amount");

        depositButton = new Button("Deposit");
        withdrawButton = new Button("Withdraw");

        // Transaction history
        transactionList = new ListView<>();
        transactionList.getItems().addAll(account.getTransactions());

        // Deposit action
        depositButton.setOnAction(e -> {
            try {
                double amt = Double.parseDouble(amountField.getText());
                account.deposit(amt);
                updateBalance(account.getBalance());
                transactionList.getItems().setAll(account.getTransactions());
                amountField.clear();
            } catch (NumberFormatException ex) {
                showAlert("Invalid amount");
            }
        });

        // Withdraw action
        withdrawButton.setOnAction(e -> {
            try {
                double amt = Double.parseDouble(amountField.getText());
                account.withdraw(amt);
                updateBalance(account.getBalance());
                transactionList.getItems().setAll(account.getTransactions());
                amountField.clear();
            } catch (NumberFormatException ex) {
                showAlert("Invalid amount");
            }
        });

        backButton = new Button("Back to Dashboard");
        backButton.setOnAction(e -> {
            SceneController.setScene(new Scene(new DashboardView(), 400, 400));
        });

        getChildren().addAll(accountNumberLabel, balanceLabel, amountField, depositButton, withdrawButton,
                new Label("Transaction History:"), transactionList, backButton);
    }

    private void updateBalance(double newBalance) {
        balanceLabel.setText("Balance: BWP" + newBalance);
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
