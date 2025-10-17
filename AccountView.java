import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class AccountView {
    private Account account;

    public AccountView(Account account) {
        this.account = account;
    }

    public void show(Stage stage) {
        Label title = new Label("🏦 Account Information");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Label accNum = new Label("Account Number: " + account.getAccountNumber());
        Label accType = new Label("Account Type: " + account.getClass().getSimpleName());
        Label accBalance = new Label("Balance: BW" + account.getBalance());
        Label accBranch = new Label("Branch: " + account.getBranch());
        Label custName = new Label("Customer: " + account.getCustomer().getName());
        Label custContact = new Label("Phone: " + account.getCustomer().getPhone());
        Label custAddress = new Label("Address: " + account.getCustomer().getAddress());

        VBox box = new VBox(10, title, accNum, accType, accBalance, accBranch, custName, custContact, custAddress);
        box.setPadding(new Insets(20));
        Scene scene = new Scene(box, 400, 300);

        stage.setScene(scene);
        stage.show();
    }
}
