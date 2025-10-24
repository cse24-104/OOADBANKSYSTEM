package com.example.thesystem;

import com.example.thesystem.BankDatabase;
import com.example.thesystem.boundary.DashboardView;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.Scene;
import com.example.thesystem.controller.SceneController;

public class LoginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Button loginButton;
    @FXML private Button registerButton;

    @FXML
    private void initialize() {
        loginButton.setOnAction(e -> login());
        registerButton.setOnAction(e -> openRegister());
    }

    private void login() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (BankDatabase.login(username, password)) {
            SceneController.setScene(new Scene(new DashboardView(), 600, 400));
        } else {
            showAlert("Login failed. Check username or password.");
        }
    }

    private void openRegister() {
        SceneController.setScene(new Scene(new RegisterCustomerView(), 600, 400));
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
