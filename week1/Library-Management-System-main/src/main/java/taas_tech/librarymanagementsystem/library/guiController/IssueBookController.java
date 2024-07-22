package taas_tech.librarymanagementsystem.library.guiController;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import taas_tech.librarymanagementsystem.Main;
import taas_tech.librarymanagementsystem.library.models.Transaction;
import taas_tech.librarymanagementsystem.library.service.TransactionService;
import taas_tech.librarymanagementsystem.library.util.AlertUtil;
import taas_tech.librarymanagementsystem.library.util.InputValidator;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

public class IssueBookController {
    @FXML private TextField bookIdField;
    @FXML private TextField patronIdField;
    @FXML private TextField returnDateField;
    @FXML private TableView<Transaction> tableView;
    @FXML private TableColumn<Transaction, Integer> bookIdColumn;
    @FXML private TableColumn<Transaction, Integer> patronIdColumn;
    @FXML private TableColumn<Transaction, String> issueDateColumn;
    @FXML private TableColumn<Transaction, String> returnDateColumn;

    private TransactionService transactionService;

    public IssueBookController() {
        this.transactionService = new TransactionService();
    }

    @FXML
    public void initialize() {
        setupTableColumns();
        loadTransactions();
    }

    @FXML
    public void issueBook() {
        if (!validateInputs()) {
            return;
        }

        int bookId = Integer.parseInt(bookIdField.getText());
        int patronId = Integer.parseInt(patronIdField.getText());
        LocalDate returnDate = LocalDate.parse(returnDateField.getText());

        try {
            transactionService.issueBook(bookId, patronId, returnDate);
            AlertUtil.showInfo("Success", "Book issued successfully.");
            clearInputFields();
            loadTransactions();
        } catch (SQLException e) {
            AlertUtil.showError("Database Error", "Error occurred while issuing the book.");
            System.out.println(e.getMessage());
        }
    }

    private boolean validateInputs() {
        if (!InputValidator.isValidId(bookIdField.getText())) {
            AlertUtil.showError("Invalid Input", "Book ID must be a number.");
            return false;
        }

        if (!InputValidator.isValidId(patronIdField.getText())) {
            AlertUtil.showError("Invalid Input", "Patron ID must be a number.");
            return false;
        }

        if (!InputValidator.isValidDate(returnDateField.getText())) {
            AlertUtil.showError("Invalid Date", "Return date must be in yyyy-MM-dd format.");
            return false;
        }

        return true;
    }

    private void clearInputFields() {
        bookIdField.clear();
        patronIdField.clear();
        returnDateField.clear();
    }

    private void setupTableColumns() {
        bookIdColumn.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        patronIdColumn.setCellValueFactory(new PropertyValueFactory<>("patronId"));
        issueDateColumn.setCellValueFactory(new PropertyValueFactory<>("issueDate"));
        returnDateColumn.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
    }

    private void loadTransactions() {
        tableView.setItems(FXCollections.observableArrayList(transactionService.getAllTransactions()));
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
    public void goToRegisterPatron() throws IOException {
        Main.showRegisterPatronScreen();
    }

    @FXML
    public void Exit() {
        Platform.exit();
    }
}