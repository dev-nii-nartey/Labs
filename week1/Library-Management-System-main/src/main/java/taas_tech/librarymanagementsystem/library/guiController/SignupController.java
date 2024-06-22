package taas_tech.librarymanagementsystem.library.guiController;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import taas_tech.librarymanagementsystem.Main;
import taas_tech.librarymanagementsystem.library.database.DatabaseHelper;

import java.io.IOException;

public class SignupController {
        @FXML
        private TextField usernameField;

        @FXML
        private PasswordField passwordField;

        @FXML
        public void handleSignUp() {
            String username = usernameField.getText();
            String password = passwordField.getText();

            if (DatabaseHelper.registerUser(username, password)) {
                System.out.println("User registered successfully");
                // Optionally, navigate to the login screen
            } else {
                System.out.println("Registration failed");
            }
        }
        @FXML
        private void goToLogin() throws IOException {
            Main.showLoginScreen();
        }
    }

