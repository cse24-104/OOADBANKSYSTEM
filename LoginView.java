package com.example.thesystem.boundary;

import com.example.thesystem.RegisterCustomerView;
import com.example.thesystem.boundary.DashboardView;
import com.example.thesystem.BankDatabase;
import com.example.thesystem.controller.SceneController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class LoginView extends VBox {
    private TextField usernameField;
    private PasswordField passwordField;
    private Button loginButton;
    private Button registerButton; // new

    public LoginView() {
        setSpacing(10);
        setPadding(new Insets(20));

        Text title = new Text("Login");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        usernameField = new TextField();
        usernameField.setPromptText("Username");

        passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        loginButton = new Button("Login");
        registerButton = new Button("Register"); // new

        getChildren().addAll(title, usernameField, passwordField, loginButton, registerButton);

        // Login action
        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            if (BankDatabase.login(username, password)) {
                Scene dashboardScene = new Scene(new com.example.thesystem.boundary.DashboardView(), 400, 400);
                SceneController.setScene(dashboardScene);
            } else {
                showAlert("Invalid username or password");
            }
        });

        // Register action
        registerButton.setOnAction(e -> {
            Scene registerScene = new Scene(new RegisterCustomerView(), 400, 400);
            SceneController.setScene(registerScene);
        });
    }

    public String getUsername() { return usernameField.getText(); }
    public String getPassword() { return passwordField.getText(); }
    public Button getLoginButton() { return loginButton; }
    public Button getRegisterButton() { return registerButton; }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
