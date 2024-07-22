package taas_tech.librarymanagementsystem.library.guiController;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import taas_tech.librarymanagementsystem.library.service.UserService;
import taas_tech.librarymanagementsystem.library.util.AlertUtil;
import taas_tech.librarymanagementsystem.library.util.SceneManager;

import java.io.IOException;

public class LoginController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;

    private UserService userService;

    public LoginController() {
        this.userService = new UserService();
    }

    @FXML
    public void login() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            AlertUtil.showError("Login Error", "Username and password cannot be empty.");
            return;
        }

        try {
            if (userService.authenticateUser(username, password)) {
                navigateToAddBookScreen();
            } else {
                AlertUtil.showError("Login Failed", "Invalid username or password.");
            }
        } catch (Exception e) {
            AlertUtil.showError("Login Error", "An error occurred during login: " + e.getMessage());
            System.out.println(e.getMessage());
        }
    }

    private void navigateToAddBookScreen() {
        try {
            Stage stage = (Stage) usernameField.getScene().getWindow();
            SceneManager.loadScene(stage, "/taas_tech/librarymanagementsystem/add_book.fxml");
        } catch (IOException e) {
            AlertUtil.showError("Navigation Error", "Failed to load the Add Book screen.");
            System.out.println(e.getMessage());
        }
    }
}