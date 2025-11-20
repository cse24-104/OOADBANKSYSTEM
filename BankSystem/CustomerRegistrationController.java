package com.example.banksystem;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CustomerRegistrationController {

    private Stage stage;
    private List<Customer> customers;

    private ComboBox<String> typeCombo, accountTypeCombo;
    private TextField idField, nameField, emailField, phoneField;
    private TextField nationalIdField, registrationNumberField, contactPersonField;
    private DatePicker dobPicker;
    private CheckBox employedCheck;
    private Button registerButton, backButton;
    private Label messageLabel;

    public CustomerRegistrationController(Stage stage, List<Customer> customers) {
        this.stage = stage;
        this.customers = new ArrayList<>(customers); // mutable copy
        stage.setTitle("Register New Customer");
        setupUI();
    }

    private void setupUI() {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(12));
        grid.setHgap(8);
        grid.setVgap(8);

        // Customer type
        typeCombo = new ComboBox<>(FXCollections.observableArrayList("Individual", "Company"));
        typeCombo.setValue("Individual");

        idField = new TextField();
        nameField = new TextField();
        emailField = new TextField();
        phoneField = new TextField();
        nationalIdField = new TextField();
        dobPicker = new DatePicker();
        registrationNumberField = new TextField();
        contactPersonField = new TextField();
        employedCheck = new CheckBox("Employed");

        accountTypeCombo = new ComboBox<>(FXCollections.observableArrayList("Savings", "Cheque", "Investment"));
        accountTypeCombo.setValue("Savings");

        messageLabel = new Label();
        registerButton = new Button("Register");
        backButton = new Button("Back");

        // Add to grid
        grid.add(new Label("Customer Type:"), 0, 0); grid.add(typeCombo, 1, 0);
        grid.add(new Label("ID:"), 0, 1); grid.add(idField, 1, 1);
        grid.add(new Label("Name:"), 0, 2); grid.add(nameField, 1, 2);
        grid.add(new Label("Email:"), 0, 3); grid.add(emailField, 1, 3);
        grid.add(new Label("Phone:"), 0, 4); grid.add(phoneField, 1, 4);

        grid.add(new Label("National ID:"), 0, 5); grid.add(nationalIdField, 1, 5);
        grid.add(new Label("DOB:"), 0, 6); grid.add(dobPicker, 1, 6);

        grid.add(new Label("Registration No:"), 0, 7); grid.add(registrationNumberField, 1, 7);
        grid.add(new Label("Contact Person:"), 0, 8); grid.add(contactPersonField, 1, 8);
        grid.add(employedCheck, 1, 9);

        grid.add(new Label("Account Type:"), 0, 10); grid.add(accountTypeCombo, 1, 10);

        grid.add(registerButton, 0, 11); grid.add(backButton, 1, 11);
        grid.add(messageLabel, 1, 12);

        updateFormFields();
        typeCombo.setOnAction(e -> updateFormFields());

        registerButton.setOnAction(e -> registerCustomer());
        backButton.setOnAction(e -> stage.close());

        stage.setScene(new Scene(grid, 480, 550));
        stage.show();
    }

    private void updateFormFields() {
        boolean individual = typeCombo.getValue().equals("Individual");

        nationalIdField.setVisible(individual);
        dobPicker.setVisible(individual);

        registrationNumberField.setVisible(!individual);
        contactPersonField.setVisible(!individual);
        employedCheck.setVisible(!individual);
    }

    private void registerCustomer() {
        String id = idField.getText().trim();
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String phone = phoneField.getText().trim();

        if (id.isEmpty() || name.isEmpty() || email.isEmpty() || phone.isEmpty()) {
            messageLabel.setText("Fill required fields");
            return;
        }

        Customer newCustomer;

        if (typeCombo.getValue().equals("Individual")) {
            String nid = nationalIdField.getText().trim();
            LocalDate dob = dobPicker.getValue();
            if (nid.isEmpty() || dob == null) {
                messageLabel.setText("Fill Individual fields");
                return;
            }
            newCustomer = new IndividualCustomer(id, name, email, phone, nid, dob);
        } else {
            String reg = registrationNumberField.getText().trim();
            String contact = contactPersonField.getText().trim();
            boolean employed = employedCheck.isSelected();
            if (reg.isEmpty() || contact.isEmpty()) {
                messageLabel.setText("Fill Company fields");
                return;
            }
            newCustomer = new CompanyCustomer(id, name, email, phone, reg, contact, employed);
        }

        // Create first account
        String acctType = accountTypeCombo.getValue();
        if (acctType.equals("Cheque") && newCustomer instanceof CompanyCustomer comp && !comp.isEmployed()) {
            messageLabel.setText("Cheque account requires employed company customer");
            return;
        }

        Account account;
        switch (acctType) {
            case "Savings" -> account = new SavingsAccount(newCustomer, "Main Branch", 0);
            case "Cheque" -> account = new ChequeAccount(newCustomer, "Main Branch", 0);
            case "Investment" -> account = new InvestmentAccount(newCustomer, "Main Branch", 0);
            default -> account = null;
        }
        if (account != null) newCustomer.addAccount(account);

        customers.add(newCustomer);
        DataStorage.saveCustomers(customers);

        messageLabel.setText("Customer registered with " + acctType + " account!");
        clearForm();
    }

    private void clearForm() {
        idField.clear(); nameField.clear(); emailField.clear(); phoneField.clear();
        nationalIdField.clear(); dobPicker.setValue(null);
        registrationNumberField.clear(); contactPersonField.clear();
        employedCheck.setSelected(false);
        accountTypeCombo.setValue("Savings");
    }
}
