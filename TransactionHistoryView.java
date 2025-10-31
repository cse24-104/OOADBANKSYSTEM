package com.example.thesystem.boundary;

import  com.example.thesystem.boundary.AccountView;
import com.example.thesystem.Account;
import com.example.thesystem.controller.SceneController;
import com.example.thesystem.database.DataStorage;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.util.List;

public class TransactionHistoryView extends VBox {
    public TransactionHistoryView(Account account) {
        setSpacing(10);
        setPadding(new Insets(20));

        Label title = new Label("Transactions for " + account.getAccountNumber());
        title.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        List<String> raw = DataStorage.getTransactions(account.getAccountNumber());
        ListView<String> list = new ListView<>();
        if (raw.isEmpty()) {
            list.getItems().add("No transactions yet.");
        } else {
            for (String r : raw) {
                // human-friendly formatting: use DataStorage helper formatting by re-parsing
                String[] p = r.split(",", 5);
                if (p.length >= 5) {
                    String type = p[1].replace("\\,", ",");
                    String amt = p[2];
                    String date = p[3];
                    String desc = p[4].replace("\\,", ",");
                    list.getItems().add(type + " | BWP " + amt + " | " + date + " | " + desc);
                } else {
                    list.getItems().add(r);
                }
            }
        }

        Button back = new Button("Back");
        back.setOnAction(e -> SceneController.setScene(new Scene(new AccountView(account), 600, 420)));

        getChildren().addAll(title, list, back);
    }
}
