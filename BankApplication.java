import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.time.LocalDate;

public class BankApplication extends Application {
    private Account account; // stores the current customer’s account
    private TextArea displayArea = new TextArea();

    @Override
    public void start(Stage stage) {
        stage.setTitle("🏦 SmartBank System");

        // --- Create Tabs for Navigation ---
        TabPane tabs = new TabPane();
        Tab openAccountTab = new Tab("Open Account");
        Tab transactionsTab = new Tab("Transactions");
        Tab detailsTab = new Tab("Account Details");
        tabs.getTabs().addAll(openAccountTab, transactionsTab, detailsTab);
        tabs.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        // ========== OPEN ACCOUNT TAB ==========
        VBox openBox = new VBox(10);
        openBox.setPadding(new Insets(20));

        ComboBox<String> custType = new ComboBox<>();
        custType.getItems().addAll("Individual", "Company");
        custType.setPromptText("Select Customer Type");

        TextField nameField = new TextField();
        nameField.setPromptText("Full Name or Company Name");

        TextField idField = new TextField();
        idField.setPromptText("ID or Registration Number");

        DatePicker datePicker = new DatePicker();
        datePicker.setPromptText("Date of Birth / Registration Date");

        TextField addressField = new TextField();
        addressField.setPromptText("Address");

        TextField phoneField = new TextField();
        phoneField.setPromptText("Phone");

        ComboBox<String> accType = new ComboBox<>();
        accType.getItems().addAll("Savings Account", "Cheque Account", "Investment Account");
        accType.setPromptText("Select Account Type");

        TextField accNumField = new TextField();
        accNumField.setPromptText("Account Number");

        TextField branchField = new TextField();
        branchField.setPromptText("Branch Name");

        TextField initialDeposit = new TextField();
        initialDeposit.setPromptText("Initial Deposit (Minimum BW500)");

        Button createBtn = new Button("Open Account");

        createBtn.setOnAction(e -> {
            try {
                double initial = Double.parseDouble(initialDeposit.getText());
                if (initial < 500) {
                    displayArea.setText("❌ Minimum opening deposit is BW500.00");
                    return;
                }

                String type = custType.getValue();
                if (type == null) {
                    displayArea.setText("Please select a customer type.");
                    return;
                }

                Customer customer;
                if (type.equals("Individual")) {
                    customer = new Customer(
                            nameField.getText(),
                            idField.getText(),
                            datePicker.getValue(),
                            addressField.getText(),
                            phoneField.getText()
                    );
                } else {
                    customer = new Customer(
                            nameField.getText(),
                            idField.getText(),
                            datePicker.getValue(),
                            addressField.getText(),
                            phoneField.getText()
                    );
                }

                String selected = accType.getValue();
                if (selected == null) {
                    displayArea.setText("Please select an account type.");
                    return;
                }

                switch (selected) {
                    case "Savings Account":
                        account = new SavingsAccount(initial, accNumField.getText(), customer, branchField.getText());
                        break;
                    case "Cheque Account":
                        account = new ChequeAccount(initial, accNumField.getText(), customer, branchField.getText(), "Employer", "Company Address");
                        break;
                    case "Investment Account":
                        account = new InvestmentAccount(initial, accNumField.getText(), customer, branchField.getText());
                        break;
                }

                displayArea.setText("🎉 Account successfully created!\n\n" + account);
            } catch (Exception ex) {
                displayArea.setText("⚠️ Error: " + ex.getMessage());
            }
        });

        openBox.getChildren().addAll(
                custType, nameField, idField, datePicker, addressField,
                phoneField, accType, accNumField, branchField,
                initialDeposit, createBtn
        );
        openAccountTab.setContent(openBox);

        // ========== TRANSACTIONS TAB ==========
        VBox transBox = new VBox(10);
        transBox.setPadding(new Insets(20));

        TextField amountField = new TextField();
        amountField.setPromptText("Enter Amount");

        Button depositBtn = new Button("Deposit");
        Button withdrawBtn = new Button("Withdraw");
        Button interestBtn = new Button("Apply Interest");

        depositBtn.setOnAction(e -> {
            if (account == null) {
                displayArea.setText("❌ No account available. Please open one first.");
                return;
            }
            double amt = Double.parseDouble(amountField.getText());
            new Deposit(amt).performDeposit(account);
            displayArea.setText("✅ Deposit complete.\nNew Balance: BW" + account.getBalance());
        });

        withdrawBtn.setOnAction(e -> {
            if (account == null) {
                displayArea.setText("❌ No account available. Please open one first.");
                return;
            }
            double amt = Double.parseDouble(amountField.getText());
            new Withdraw(amt).performWithdraw(account);
            displayArea.setText("✅ Withdrawal complete.\nNew Balance: BW" + account.getBalance());
        });

        interestBtn.setOnAction(e -> {
            if (account instanceof InterestBearing) {
                ((InterestBearing) account).applyInterest();
                displayArea.setText("💰 Interest applied.\nNew Balance: BW" + account.getBalance());
            } else {
                displayArea.setText("❌ This account type does not earn interest.");
            }
        });

        transBox.getChildren().addAll(amountField, depositBtn, withdrawBtn, interestBtn);
        transactionsTab.setContent(transBox);

        // ========== ACCOUNT DETAILS TAB ==========
        VBox detailsBox = new VBox(10);
        detailsBox.setPadding(new Insets(20));
        Button showDetails = new Button("Show Account Details");
        showDetails.setOnAction(e -> {
            if (account == null) {
                displayArea.setText("No account found.");
            } else {
                displayArea.setText(account.toString());
            }
        });
        detailsBox.getChildren().addAll(showDetails);
        detailsTab.setContent(detailsBox);

        // ========== MAIN LAYOUT ==========
        VBox layout = new VBox(15, tabs, displayArea);
        layout.setPadding(new Insets(10));
        displayArea.setEditable(false);
        displayArea.setPrefHeight(250);

        Scene scene = new Scene(layout, 600, 600);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}