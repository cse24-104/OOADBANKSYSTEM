package com.example.thesystem;

import com.example.thesystem.boundary.LoginView;
import com.example.thesystem.controller.SceneController;
import com.example.thesystem.database.DataStorage;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        SceneController.setStage(primaryStage);

        // Ensure files exist / load mechanisms are ready
        DataStorage.ensureFilesExist();

        LoginView loginView = new LoginView();
        Scene loginScene = new Scene(loginView, 500, 380);

        SceneController.setScene(loginScene);
        primaryStage.setTitle("Banking System (File Storage)");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
