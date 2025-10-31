package com.example.thesystem;

import com.example.thesystem.Customer;
import com.example.thesystem.controller.SceneController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class CustomerProfileView extends VBox {
    private TextField firstNameField;
    private TextField lastNameField;
    private TextField addressField;
    private TextField phoneField;
    private TextField emailField;
    private Button updateButton;
    private Button backButton;

    public CustomerProfileView(Customer customer) {
        setSpacing(10);
        setPadding(new Insets(20));

        firstNameField = new TextField(customer.getFirstName());
        lastNameField = new TextField(customer.getLastName());
        addressField = new TextField(customer.getAddress());
        phoneField = new TextField(String.valueOf(customer.getPhoneNumber()));
        emailField = new TextField(customer.getEmail());

        updateButton = new Button("Update Profile");

        backButton = new Button("Back to Dashboard");
        backButton.setOnAction(e -> {
            com.example.thesystem.boundary.DashboardView dashboard = new com.example.thesystem.boundary.DashboardView();
            Scene dashboardScene = new Scene(dashboard, 400, 300);
            SceneController.setScene(dashboardScene);
        });

        getChildren().addAll(
                new Label("First Name:"), firstNameField,
                new Label("Last Name:"), lastNameField,
                new Label("Address:"), addressField,
                new Label("Phone Number:"), phoneField,
                new Label("Email:"), emailField,
                updateButton, backButton
        );
    }

    public Button getUpdateButton() {
        return updateButton;
    }

    public String getFirstName() {
        return firstNameField.getText();
    }

    public String getLastName() {
        return lastNameField.getText();
    }

    public String getAddress() {
        return addressField.getText();
    }

    public String getPhoneNumber() {
        return phoneField.getText();
    }

    public String getEmail() {
        return emailField.getText();
    }
}
