package com.example.banksystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class AccountController {

    private Stage stage;
    private Customer customer;
    private List<Customer> allCustomers;
    private AccountView view;

    public AccountController(Stage stage, Customer customer, List<Customer> allCustomers) {
        this.stage = stage;
        this.customer = customer;
        this.allCustomers = allCustomers;
        this.view = new AccountView(stage);
    }

    public void showAccountView() {
        populateAccounts();

        // Open New Account button
        Button openAccountBtn = new Button("Open New Account");
        ((VBox) view.getStage().getScene().getRoot()).getChildren().add(1, openAccountBtn);
        openAccountBtn.setOnAction(e -> showOpenAccountDialog());

        // Button actions
        view.getDepositButton().setOnAction(e -> performDeposit());
        view.getWithdrawButton().setOnAction(e -> performWithdraw());

        // Updated Apply Interest button
        view.getApplyInterestButton().setOnAction(e -> {
            Account selected = getSelectedAccount();
            if (selected == null) {
                view.getMessageLabel().setText("No account selected!");
                return;
            }

            if (selected instanceof InterestBearing ib) {
                ib.applyMonthlyInterest();
                refreshSelectedAccount();
                view.getMessageLabel().setText("Interest applied successfully!");
            } else {
                view.getMessageLabel().setText("Selected account does not bear interest.");
            }
        });

        view.getLogoutButton().setOnAction(e -> logout());
        view.getAccountListView().getSelectionModel().selectedIndexProperty().addListener((obs, o, n) -> refreshSelectedAccount());
        view.getTransactionFilter().setOnAction(e -> refreshSelectedAccount());

        view.show();
    }

    private void populateAccounts() {
        ObservableList<String> items = FXCollections.observableArrayList();
        for (Account a : customer.getAccounts()) {
            items.add(a.getAccountNumber() + " (" + a.getClass().getSimpleName() + ")");
        }
        view.updateAccounts(items);

        if (!customer.getAccounts().isEmpty()) {
            view.getAccountListView().getSelectionModel().select(0);
            refreshSelectedAccount();
        }
    }

    private void showOpenAccountDialog() {
        Stage dialog = new Stage();
        dialog.setTitle("Open New Account");

        ComboBox<String> typeCombo = new ComboBox<>(FXCollections.observableArrayList("Savings", "Cheque", "Investment"));
        typeCombo.setValue("Savings");

        // Disable Cheque if customer is not employed
        if (!customer.isEmployed()) {
            typeCombo.getItems().remove("Cheque");
        }

        TextField branchField = new TextField();
        TextField depositField = new TextField();
        Label message = new Label();
        Button createBtn = new Button("Create Account");
        Button backBtn = new Button("Back");

        // Layout
        javafx.scene.layout.GridPane grid = new javafx.scene.layout.GridPane();
        grid.setVgap(8); grid.setHgap(8);
        grid.setPadding(new javafx.geometry.Insets(12));

        grid.add(new Label("Account Type:"), 0, 0); grid.add(typeCombo, 1, 0);
        grid.add(new Label("Branch:"), 0, 1); grid.add(branchField, 1, 1);
        grid.add(new Label("Initial Deposit:"), 0, 2); grid.add(depositField, 1, 2);
        grid.add(createBtn, 0, 3); grid.add(backBtn, 1, 3);
        grid.add(message, 0, 4, 2, 1);

        createBtn.setOnAction(e -> {
            String type = typeCombo.getValue();
            String branch = branchField.getText().trim();
            String depositStr = depositField.getText().trim();

            if (branch.isEmpty() || depositStr.isEmpty()) {
                message.setText("Fill all fields");
                return;
            }

            try {
                double deposit = Double.parseDouble(depositStr);

                // Prevent creating Cheque account if customer not employed
                if ("Cheque".equals(type) && !customer.isEmployed()) {
                    message.setText("Cheque account requires employed customer");
                    return;
                }

                Account acc = switch (type) {
                    case "Savings" -> new SavingsAccount(customer, branch, deposit);
                    case "Cheque" -> new ChequeAccount(customer, branch, deposit);
                    case "Investment" -> new InvestmentAccount(customer, branch, deposit);
                    default -> null;
                };

                if (acc != null) {
                    customer.addAccount(acc);
                    message.setText("Account created: " + acc.getAccountNumber());
                    populateAccounts();
                    dialog.close();
                }
            } catch (NumberFormatException ex) {
                message.setText("Invalid amount");
            } catch (IllegalArgumentException ex) {
                message.setText(ex.getMessage());
            }
        });

        backBtn.setOnAction(e -> dialog.close());

        dialog.setScene(new javafx.scene.Scene(grid, 400, 250));
        dialog.initOwner(stage);
        dialog.show();
    }


    private Account getSelectedAccount() {
        int idx = view.getAccountListView().getSelectionModel().getSelectedIndex();
        if (idx >= 0 && idx < customer.getAccounts().size()) return customer.getAccounts().get(idx);
        return null;
    }

    private void refreshSelectedAccount() {
        Account selected = getSelectedAccount();
        if (selected == null) {
            view.updateBalance("");
            view.updateTransactions(FXCollections.observableArrayList());
            view.getWithdrawButton().setDisable(true);
            view.getDepositButton().setDisable(true);
            view.getApplyInterestButton().setDisable(true);
            return;
        }

        view.updateBalance(String.format("%.2f BWP", selected.getBalance()));
        view.getWithdrawButton().setDisable(selected instanceof SavingsAccount);
        view.getApplyInterestButton().setDisable(!(selected instanceof InterestBearing));

        String filter = view.getTransactionFilter().getValue();
        ObservableList<String> txItems = FXCollections.observableArrayList();
        for (Transaction t : selected.getTransactions()) {
            if ("All".equals(filter) || t.getType().toString().equalsIgnoreCase(filter)) {
                txItems.add(t.getFormattedTransaction());
            }
        }
        view.updateTransactions(txItems);
        view.getMessageLabel().setText("");
    }

    private void performDeposit() {
        Account acc = getSelectedAccount();
        if (acc == null) { showError("Select an account"); return; }
        String s = view.getAmountField().getText().trim();
        if (s.isEmpty()) { showError("Enter an amount"); return; }
        try {
            double amt = Double.parseDouble(s);
            acc.deposit(amt);
            refreshSelectedAccount();
            showMessage("Deposit successful");
        } catch (NumberFormatException ex) {
            showError("Invalid number");
        } catch (Exception ex) {
            showError(ex.getMessage());
        }
    }

    private void performWithdraw() {
        Account acc = getSelectedAccount();
        if (acc == null) { showError("Select an account"); return; }
        String s = view.getAmountField().getText().trim();
        if (s.isEmpty()) { showError("Enter an amount"); return; }
        try {
            double amt = Double.parseDouble(s);
            acc.withdraw(amt);
            refreshSelectedAccount();
            showMessage("Withdrawal successful");
        } catch (NumberFormatException ex) {
            showError("Invalid number");
        } catch (UnsupportedOperationException ex) {
            showError(ex.getMessage());
        } catch (Exception ex) {
            showError(ex.getMessage());
        }
    }

    private void logout() {
        view.getStage().close();
        LoginView loginView = new LoginView();
        LoginController loginController = new LoginController(stage, allCustomers, loginView);
        loginView.showLogin(loginController);
    }

    private void showError(String msg) {
        view.getMessageLabel().setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
        view.getMessageLabel().setText(msg);
    }

    private void showMessage(String msg) {
        view.getMessageLabel().setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
        view.getMessageLabel().setText(msg);
    }
}
