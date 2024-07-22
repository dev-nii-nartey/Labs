package taas_tech.librarymanagementsystem.library.util;

import javafx.scene.control.Alert;

public class AlertUtil {
    public static void showError(String title, String content) {
        showAlert(title, content, Alert.AlertType.ERROR);
    }

    public static void showInfo(String title, String content) {
        showAlert(title, content, Alert.AlertType.INFORMATION);
    }

    private static void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}