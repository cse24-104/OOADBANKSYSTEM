import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class LoginView extends Application {

    @Override
    public void start(Stage stage) {
        stage.setTitle("🏦 SmartBank Login");

        // --- UI Components ---
        Label title = new Label("SmartBank System");
        title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");

        Label userLabel = new Label("Username:");
        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter username");

        Label passLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter password");

        Button loginBtn = new Button("Login");
        Label message = new Label();

        // --- Layout ---
        VBox box = new VBox(10, title, userLabel, usernameField, passLabel, passwordField, loginBtn, message);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(20));
        box.setStyle("-fx-background-color: linear-gradient(to bottom right, #e0f7fa, #ffffff);");

        Scene loginScene = new Scene(box, 400, 350);
        stage.setScene(loginScene);
        stage.show();

        // --- Login Logic ---
        loginBtn.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            if (username.equals("admin") && password.equals("12345")) {
                message.setText("✅ Login successful!");
                BankApp bankApp = new BankApp();
                try {
                    bankApp.start(stage); // load main banking GUI
                } catch (Exception ex) {
                    message.setText("⚠️ Failed to open dashboard: " + ex.getMessage());
                }
            } else {
                message.setText("❌ Invalid username or password!");
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
