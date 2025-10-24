package com.example.thesystem;

import com.example.thesystem.boundary.LoginView;
import com.example.thesystem.BankDatabase;
import com.example.thesystem.Individual;
import com.example.thesystem.controller.SceneController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class RegisterCustomerView extends VBox {
    private TextField firstNameField;
    private TextField lastNameField;
    private TextField addressField;
    private TextField phoneField;
    private TextField emailField;
    private TextField usernameField;
    private PasswordField passwordField;
    private Button registerButton;
    private Button backButton;

    public RegisterCustomerView() {
        setSpacing(10);
        setPadding(new Insets(20));

        firstNameField = new TextField();
        firstNameField.setPromptText("First Name");

        lastNameField = new TextField();
        lastNameField.setPromptText("Last Name");

        addressField = new TextField();
        addressField.setPromptText("Address");

        phoneField = new TextField();
        phoneField.setPromptText("Phone Number");

        emailField = new TextField();
        emailField.setPromptText("Email");

        usernameField = new TextField();
        usernameField.setPromptText("Username");

        passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        registerButton = new Button("Register");
        backButton = new Button("Back to Login");

        getChildren().addAll(
                new Label("Register New Individual Customer"),
                firstNameField, lastNameField, addressField,
                phoneField, emailField, usernameField, passwordField,
                registerButton, backButton
        );

        // Register action
        registerButton.setOnAction(e -> {
            try {
                String firstName = firstNameField.getText();
                String lastName = lastNameField.getText();
                String address = addressField.getText();
                int phone = Integer.parseInt(phoneField.getText());
                String email = emailField.getText();
                String username = usernameField.getText();
                String password = passwordField.getText();

                if (firstName.isEmpty() || lastName.isEmpty() || username.isEmpty() || password.isEmpty()) {
                    showAlert("Please fill all required fields");
                    return;
                }

                // Auto-generate customer ID
                int customerID = BankDatabase.getCustomers().size() + 1;

                Individual newCustomer = new Individual(
                        firstName, lastName, address, phone, email,
                        customerID, "2000-01-01", "Not Specified", "Single", // default DOB, gender, marital
                        username, password
                );

                BankDatabase.addCustomer(newCustomer);
                showAlert("Registration successful!", Alert.AlertType.INFORMATION);

                // Automatically go to login screen
                SceneController.setScene(new Scene(new LoginView(), 400, 300));

            } catch (NumberFormatException ex) {
                showAlert("Invalid phone number");
            }
        });

        backButton.setOnAction(e -> SceneController.setScene(new Scene(new LoginView(), 400, 300)));
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
