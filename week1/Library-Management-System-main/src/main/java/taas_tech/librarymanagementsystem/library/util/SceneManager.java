package taas_tech.librarymanagementsystem.library.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneManager {
    public static void loadScene(Stage stage, String fxmlPath) throws IOException {
        Parent root = FXMLLoader.load(SceneManager.class.getResource(fxmlPath));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}