package com.example.thesystem;

import com.example.thesystem.boundary.DashboardView;
import com.example.thesystem.AccountListView;
import com.example.thesystem.boundary.AccountView;
import com.example.thesystem.Account;
import com.example.thesystem.controller.SceneController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.util.List;

public class AccountListView extends VBox {
    public AccountListView(List<Account> accounts) {
        setSpacing(10);
        setPadding(new Insets(20));

        Label title = new Label("My Accounts");
        title.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        ListView<Account> list = new ListView<>();
        list.getItems().addAll(accounts);

        Button open = new Button("Open Selected Account");
        Button back = new Button("Back");

        open.setOnAction(e -> {
            Account sel = list.getSelectionModel().getSelectedItem();
            if (sel != null) {
                SceneController.setScene(new Scene(new AccountView(sel), 600, 420));
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Select an account first").showAndWait();
            }
        });

        back.setOnAction(e -> SceneController.setScene(new Scene(new DashboardView(), 600, 420)));

        getChildren().addAll(title, list, open, back);
    }
}
