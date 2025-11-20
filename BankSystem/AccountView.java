package com.example.banksystem;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AccountView {

    private Stage stage;
    private ListView<String> accountListView;
    private Label balanceLabel;
    private ListView<String> transactionListView;
    private TextField amountField;
    private Button depositButton;
    private Button withdrawButton;
    private Button applyInterestButton;
    private ComboBox<String> transactionFilter;
    private Button logoutButton;
    private Label messageLabel;

    public AccountView(Stage stage) {
        this.stage = stage;
        setupUI();
    }

    private void setupUI() {
        stage.setTitle("Banking System - Dashboard");

        VBox root = new VBox(10);
        root.setPadding(new Insets(12));

        accountListView = new ListView<>();
        accountListView.setPrefHeight(100);

        balanceLabel = new Label("Balance:");

        transactionListView = new ListView<>();
        transactionListView.setPrefHeight(180);

        GridPane actionPane = new GridPane();
        actionPane.setHgap(8);
        actionPane.setVgap(8);

        amountField = new TextField();
        depositButton = new Button("Deposit");
        withdrawButton = new Button("Withdraw");
        applyInterestButton = new Button("Apply Interest");
        transactionFilter = new ComboBox<>();
        transactionFilter.getItems().addAll("All", "DEPOSIT", "WITHDRAWAL", "INTEREST");
        transactionFilter.setValue("All");
        logoutButton = new Button("Logout");
        messageLabel = new Label();

        actionPane.add(new Label("Amount:"), 0, 0);
        actionPane.add(amountField, 1, 0);
        actionPane.add(depositButton, 0, 1);
        actionPane.add(withdrawButton, 1, 1);
        actionPane.add(applyInterestButton, 0, 2);
        actionPane.add(new Label("Filter:"), 0, 3);
        actionPane.add(transactionFilter, 1, 3);
        actionPane.add(logoutButton, 0, 4);
        actionPane.add(messageLabel, 0, 5, 2, 1);

        root.getChildren().addAll(new Label("Accounts:"), accountListView, balanceLabel,
                new Label("Transactions:"), transactionListView, actionPane);

        Scene scene = new Scene(root, 600, 550);
        stage.setScene(scene);
    }

    public void show() { stage.show(); }
    public Stage getStage() { return stage; }

    // Getters
    public ListView<String> getAccountListView() { return accountListView; }
    public Label getBalanceLabel() { return balanceLabel; }
    public ListView<String> getTransactionListView() { return transactionListView; }
    public TextField getAmountField() { return amountField; }
    public Button getDepositButton() { return depositButton; }
    public Button getWithdrawButton() { return withdrawButton; }
    public Button getApplyInterestButton() { return applyInterestButton; }
    public ComboBox<String> getTransactionFilter() { return transactionFilter; }
    public Button getLogoutButton() { return logoutButton; }
    public Label getMessageLabel() { return messageLabel; }

    // Update helpers
    public void updateAccounts(javafx.collections.ObservableList<String> accounts) {
        accountListView.setItems(accounts);
    }
    public void updateTransactions(javafx.collections.ObservableList<String> transactions) {
        transactionListView.setItems(transactions);
    }
    public void updateBalance(String text) { balanceLabel.setText("Balance: " + text); }
}
