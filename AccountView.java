package com.example.thesystem.boundary;

import com.example.thesystem.boundary.DashboardView;
import com.example.thesystem.boundary.TransactionHistoryView;
import com.example.thesystem.BankDatabase;
import com.example.thesystem.Account;
import com.example.thesystem.*;
import com.example.thesystem.controller.SceneController;
import com.example.thesystem.database.DataStorage;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class AccountView extends VBox {
    private final Account account;
    private final Label balanceLabel = new Label();
    private final TextField amountField = new TextField();

    public AccountView(Account account) {
        this.account = account;
        setSpacing(10);
        setPadding(new Insets(20));

        Label title = new Label("Account: " + account.getAccountNumber());
        title.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        balanceLabel.setText("Balance: BWP " + String.format("%.2f", account.getBalance()));
        amountField.setPromptText("Amount");

        Button depositBtn = new Button("Deposit");
        Button withdrawBtn = new Button("Withdraw");
        Button txHistory = new Button("View Transactions");
        Button back = new Button("Back");

        depositBtn.setOnAction(e -> doDeposit());
        withdrawBtn.setOnAction(e -> doWithdraw());
        txHistory.setOnAction(e -> SceneController.setScene(new Scene(new TransactionHistoryView(account), 600, 420)));
        back.setOnAction(e -> SceneController.setScene(new Scene(new DashboardView(), 600, 420)));

        getChildren().addAll(title, balanceLabel, amountField, depositBtn, withdrawBtn, txHistory, back);
    }

    private void doDeposit() {
        try {
            double amt = Double.parseDouble(amountField.getText());
            if (amt <= 0) throw new NumberFormatException();

            account.deposit(amt);
            DataStorage.saveTransaction(account.getAccountNumber(), "Deposit", amt, "Deposit");
            BankDatabase.saveAll();
            balanceLabel.setText("Balance: BWP " + String.format("%.2f", account.getBalance()));
            amountField.clear();
        } catch (NumberFormatException ex) {
            new Alert(Alert.AlertType.WARNING, "Enter a valid positive amount").showAndWait();
        }
    }

    private void doWithdraw() {
        try {
            double amt = Double.parseDouble(amountField.getText());
            if (amt <= 0) throw new NumberFormatException();
            if (amt > account.getBalance()) {
                new Alert(Alert.AlertType.WARNING, "Insufficient funds").showAndWait();
                return;
            }

            account.withdraw(amt);
            DataStorage.saveTransaction(account.getAccountNumber(), "Withdraw", amt, "Withdrawal");
            BankDatabase.saveAll();
            balanceLabel.setText("Balance: BWP " + String.format("%.2f", account.getBalance()));
            amountField.clear();
        } catch (NumberFormatException ex) {
            new Alert(Alert.AlertType.WARNING, "Enter a valid positive amount").showAndWait();
        }
    }
}
