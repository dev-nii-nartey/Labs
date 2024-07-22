package taas_tech.librarymanagementsystem.library.guiController;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import taas_tech.librarymanagementsystem.Main;
import taas_tech.librarymanagementsystem.library.service.UserService;
import taas_tech.librarymanagementsystem.library.util.AlertUtil;

import java.io.IOException;
import java.sql.SQLException;

public class SignupController {
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    private UserService userService;

    public SignupController() {
        this.userService = new UserService();
    }

    @FXML
    public void handleSignUp() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            AlertUtil.showError("Error", "Username and password cannot be empty.");
            return;
        }

        try {
            if (userService.registerUser(username, password)) {
                AlertUtil.showInfo("Success", "User registered successfully");
                goToLogin();
            } else {
                AlertUtil.showError("Error", "Registration failed. Please try again.");
            }
        } catch (SQLException e) {
            AlertUtil.showError("Database Error", "An error occurred during registration: " + e.getMessage());
        }
    }

    @FXML
    private void goToLogin() {
        try {
            Main.showLoginScreen();
        } catch (IOException e) {
            AlertUtil.showError("Navigation Error", "Failed to load the login screen: " + e.getMessage());
        }
    }
}