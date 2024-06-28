package com.textprocessingtool.textprocessingtool;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/textprocessingtool/textprocessingtool/MainLayout.fxml"));
        primaryStage.setTitle("Text Processing and Data Management Tool");
        primaryStage.setScene(new Scene(loader.load(), 800, 600));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
