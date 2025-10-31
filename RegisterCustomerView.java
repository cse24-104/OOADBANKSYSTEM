package com.example.thesystem;

import com.example.thesystem.boundary.LoginView;
import com.example.thesystem.Account;
import com.example.thesystem.BankDatabase;
import com.example.thesystem.Customer;
import com.example.thesystem.Individual;
import com.example.thesystem.controller.SceneController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class RegisterCustomerView extends VBox {
    private final TextField firstName = new TextField();
    private final TextField lastName = new TextField();
    private final TextField address = new TextField();
    private final TextField phone = new TextField();
    private final TextField email = new TextField();
    private final TextField username = new TextField();
    private final PasswordField password = new PasswordField();
    private final Label message = new Label();

    public RegisterCustomerView() {
        setSpacing(10);
        setPadding(new Insets(20));

        Label title = new Label("Register New Individual");
        title.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        firstName.setPromptText("First Name");
        lastName.setPromptText("Last Name");
        address.setPromptText("Address");
        phone.setPromptText("Phone");
        email.setPromptText("Email");
        username.setPromptText("Username");
        password.setPromptText("Password");

        Button registerBtn = new Button("Register");
        Button backBtn = new Button("Back to Login");

        registerBtn.setOnAction(e -> handleRegister());
        backBtn.setOnAction(e -> SceneController.setScene(new Scene(new LoginView(), 500, 380)));

        getChildren().addAll(title, firstName, lastName, address, phone, email, username, password, registerBtn, backBtn, message);
    }

    private void handleRegister() {
        String f = firstName.getText().trim();
        String l = lastName.getText().trim();
        String addr = address.getText().trim();
        String ph = phone.getText().trim();
        String em = email.getText().trim();
        String u = username.getText().trim();
        String pw = password.getText().trim();

        if (f.isEmpty() || l.isEmpty() || u.isEmpty() || pw.isEmpty()) {
            message.setText("Fill required fields (first, last, username, password).");
            return;
        }

        // check username uniqueness
        for (Customer c : BankDatabase.getCustomers()) {
            if (c instanceof Individual ind && ind.getUsername().equals(u)) {
                message.setText("Username already exists. Choose another.");
                return;
            }
        }

        int cid = BankDatabase.getCustomers().size() + 1;
        Individual ind = new Individual(f, l, addr, ph, em, cid, "1990-01-01", "NotSpecified", "Single", u, pw);
        BankDatabase.addCustomer(ind);
        message.setText("Registration successful. Go back and login.");

        // return to login
        SceneController.setScene(new Scene(new LoginView(), 500, 380));
    }
}
