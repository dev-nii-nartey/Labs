package taas_tech.librarymanagementsystem.library.guiController;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import taas_tech.librarymanagementsystem.Main;
import taas_tech.librarymanagementsystem.library.database.DatabaseHelper;
import taas_tech.librarymanagementsystem.library.models.Patron;


import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegisterPatronController {
    @FXML
    private TextField nameField;
    @FXML
    private TextField emailField;
    @FXML
    private TableView<Patron> tableView;
    @FXML
    private TableColumn<Patron, Integer> idColumn;
    @FXML
    private TableColumn<Patron, String> nameColumn;
    @FXML
    private TableColumn<Patron, String> emailColumn;

    private ObservableList<Patron> patronList;

    @FXML
    public void initialize() {
        patronList = FXCollections.observableArrayList();

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        loadPatronData();
    }

    public void registerPatron() {
        String name = nameField.getText();
        String email = emailField.getText();

        if (name.isEmpty() || email.isEmpty()) {
            showAlert("Error", "Name and Email fields cannot be empty.", Alert.AlertType.ERROR);
            return;
        }

        String sql = "INSERT INTO patrons(name, email) VALUES(?, ?)";

        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, email);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                showAlert("Success", "Patron registered successfully.", Alert.AlertType.INFORMATION);
                loadPatronData();  // Reload the data after a successful registration
            } else {
                showAlert("Error", "Failed to register patron.", Alert.AlertType.ERROR);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "An error occurred: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void loadPatronData() {
        patronList.clear();
        String sql = "SELECT * FROM patrons";

        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");

                Patron patron = new Patron(id, name, email);
                patronList.add(patron);
            }
            tableView.setItems(patronList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void goToaddBook() throws IOException {
        Main.showAddBookScreen();
    }

    @FXML
    public void goToReturnBook() throws IOException {
        Main.showReturnBookScreen();
    }

    @FXML
    public void goToissueBook() throws IOException {
        Main.showIssueBookScreen();
    }

    @FXML
    public void Exit() {
        Platform.exit();
    }
}
