package taas_tech.librarymanagementsystem.library.guiController;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import taas_tech.librarymanagementsystem.Main;
import taas_tech.librarymanagementsystem.library.models.Patron;
import taas_tech.librarymanagementsystem.library.service.PatronService;
import taas_tech.librarymanagementsystem.library.util.AlertUtil;

import java.io.IOException;

public class RegisterPatronController {
    @FXML private TextField nameField;
    @FXML private TextField emailField;
    @FXML private TableView<Patron> tableView;
    @FXML private TableColumn<Patron, Integer> idColumn;
    @FXML private TableColumn<Patron, String> nameColumn;
    @FXML private TableColumn<Patron, String> emailColumn;

    private ObservableList<Patron> patronList;
    private PatronService patronService;

    public RegisterPatronController() {
        this.patronService = new PatronService();
    }

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
            AlertUtil.showError("Error", "Name and Email fields cannot be empty.");
            return;
        }

        try {
            boolean success = patronService.registerPatron(name, email);
            if (success) {
                AlertUtil.showInfo("Success", "Patron registered successfully.");
                loadPatronData();
            } else {
                AlertUtil.showError("Error", "Failed to register patron.");
            }
        } catch (Exception e) {
            AlertUtil.showError("Error", "An error occurred: " + e.getMessage());
        }
    }

    private void loadPatronData() {
        try {
            patronList.clear();
            patronList.addAll(patronService.getAllPatrons());
            tableView.setItems(patronList);
        } catch (Exception e) {
            AlertUtil.showError("Error", "Failed to load patron data: " + e.getMessage());
        }
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