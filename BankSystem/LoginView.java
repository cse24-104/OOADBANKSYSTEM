package com.example.banksystem;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class LoginView {

    private Stage stage;
    private TextField customerIdField = new TextField();
    private Button loginButton = new Button("Login");
    private Button registerButton = new Button("Register Customer");
    private Label messageLabel = new Label();

    public void showLogin(LoginController loginController) {
        stage = new Stage();
        stage.setTitle("Banking System - Login");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setVgap(10);
        grid.setHgap(10);

        grid.add(new Label("Customer ID:"), 0, 0);
        grid.add(customerIdField, 1, 0);
        grid.add(loginButton, 1, 1);
        grid.add(registerButton, 1, 2);
        grid.add(messageLabel, 1, 3);

        loginButton.setOnAction(e -> {
            String id = customerIdField.getText().trim();
            if (id.isEmpty()) {
                setMessage("Please enter a Customer ID", true);
            } else {
                loginController.login(id);
            }
        });

        registerButton.setOnAction(e -> loginController.openRegisterForm());

        Scene scene = new Scene(grid, 400, 200);
        stage.setScene(scene);
        stage.show();
    }

    public void close() {
        if (stage != null) stage.close();
    }

    public TextField getCustomerIdField() { return customerIdField; }
    public Button getLoginButton() { return loginButton; }
    public Label getMessageLabel() { return messageLabel; }
    public Button getRegisterButton() { return registerButton; }
    public Stage getStage() { return stage; }

    public void setMessage(String msg, boolean error) {
        messageLabel.setText(msg);
        messageLabel.setStyle(error ? "-fx-text-fill: red; -fx-font-weight: bold;" : "-fx-text-fill: green;");
    }
}
