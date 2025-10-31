package com.example.thesystem.boundary;

import  com.example.thesystem.boundary.DashboardView;
import com.example.thesystem.*;
import com.example.thesystem.BankDatabase;
import com.example.thesystem.RegisterCustomerView;
import com.example.thesystem.controller.SceneController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class LoginView extends VBox {
    private final TextField usernameField = new TextField();
    private final PasswordField passwordField = new PasswordField();
    private final Label messageLabel = new Label();

    public LoginView() {
        setSpacing(10);
        setPadding(new Insets(20));

        Label title = new Label("Banking System - Login");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        usernameField.setPromptText("Username");
        passwordField.setPromptText("Password");

        Button loginBtn = new Button("Login");
        Button registerBtn = new Button("Register");

        loginBtn.setOnAction(e -> handleLogin());
        registerBtn.setOnAction(e -> SceneController.setScene(new Scene(new RegisterCustomerView(), 500, 420)));

        getChildren().addAll(title, usernameField, passwordField, loginBtn, registerBtn, messageLabel);
    }

    private void handleLogin() {
        String u = usernameField.getText().trim();
        String p = passwordField.getText().trim();
        if (u.isEmpty() || p.isEmpty()) {
            messageLabel.setText("Enter both username and password");
            return;
        }
        boolean ok = BankDatabase.login(u, p);
        if (ok) {
            SceneController.setScene(new Scene(new DashboardView(), 600, 420));
        } else {
            messageLabel.setText("Invalid username or password");
        }
    }
}
