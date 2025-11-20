package com.example.banksystem;

import javafx.stage.Stage;
import java.util.List;

public class LoginController {

    private Stage primaryStage;
    private List<Customer> customers;
    private LoginView loginView;

    public LoginController(Stage primaryStage, List<Customer> customers, LoginView loginView) {
        this.primaryStage = primaryStage;
        this.customers = customers;
        this.loginView = loginView;
    }

    public void login(String customerId) {
        if (customerId == null || customerId.isBlank()) {
            loginView.setMessage("Customer ID cannot be empty", true);
            return;
        }

        Customer customer = customers.stream()
                .filter(c -> c.getCustomerId().equalsIgnoreCase(customerId.trim()))
                .findFirst()
                .orElse(null);

        if (customer == null) {
            loginView.setMessage("Customer ID not found.", true);
            return;
        }

        openAccountDashboard(customer);
    }

    private void openAccountDashboard(Customer customer) {
        AccountController accountController = new AccountController(primaryStage, customer, customers);
        accountController.showAccountView();
        loginView.close();
    }

    public void openRegisterForm() {
        // Delegate to the new RegistrationController
        new CustomerRegistrationController(primaryStage, customers);
        // The RegistrationController will handle showing the form and registering the customer
        // After registration, it should open the dashboard automatically (handled inside RegistrationController)
    }
}
