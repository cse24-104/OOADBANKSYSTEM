package com.example.thesystem.controller;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneController {
    private static Stage stage;

    public static void setStage(Stage primaryStage) {
        stage = primaryStage;
    }

    public static void setScene(Scene scene) {
        if (stage != null) stage.setScene(scene);
    }
}
