package com.example.thesystem;

import com.example.thesystem.boundary.LoginView;
import com.example.thesystem.controller.SceneController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        SceneController.setStage(primaryStage);

        LoginView loginView = new LoginView();
        Scene loginScene = new Scene(loginView, 400, 300);

        SceneController.setScene(loginScene);
        primaryStage.setTitle("Banking System");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
