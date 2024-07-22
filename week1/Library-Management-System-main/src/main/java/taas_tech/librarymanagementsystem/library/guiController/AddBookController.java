package taas_tech.librarymanagementsystem.library.guiController;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.application.Platform;
import taas_tech.librarymanagementsystem.Main;
import taas_tech.librarymanagementsystem.library.database.DatabaseHelper;
import taas_tech.librarymanagementsystem.library.models.Book;
import taas_tech.librarymanagementsystem.library.service.BookService;
import taas_tech.librarymanagementsystem.library.util.AlertUtil;
import taas_tech.librarymanagementsystem.library.util.InputValidator;

import java.io.IOException;
import java.sql.SQLException;

public class AddBookController {
    @FXML private TextField titleField;
    @FXML private TextField authorField;
    @FXML private TextField genreField;
    @FXML private TextField publisherField;
    @FXML private TextField yearField;
    @FXML private TableView<Book> tableView;
    @FXML private TableColumn<Book, Integer> idColumn;
    @FXML private TableColumn<Book, String> titleColumn;
    @FXML private TableColumn<Book, String> authorColumn;
    @FXML private TableColumn<Book, String> genreColumn;
    @FXML private TableColumn<Book, String> publisherColumn;
    @FXML private TableColumn<Book, Integer> yearColumn;
    @FXML private TableColumn<Book, Boolean> isIssuedColumn;

    private BookService bookService;

    public AddBookController() {
        this.bookService = new BookService();
    }

    @FXML
    public void initialize() {
        setupTableColumns();
        loadBooks();
    }

    @FXML
    public void addBook() {
        if (!validateInputs()) {
            return;
        }

        Book book = createBookFromInputs();
        bookService.addBook(book);
        AlertUtil.showInfo("Success", "Book added successfully.");
        clearInputFields();
        loadBooks();
    }

    private boolean validateInputs() {
        if (titleField.getText().isEmpty() || authorField.getText().isEmpty()) {
            AlertUtil.showError("Invalid Input", "Title and Author are required.");
            return false;
        }

        if (!InputValidator.isValidYear(yearField.getText())) {
            AlertUtil.showError("Invalid Input", "Year must be a valid number.");
            return false;
        }

        return true;
    }

    private Book createBookFromInputs() {
        return new Book(
                0, // ID will be set by the database
                titleField.getText(),
                authorField.getText(),
                genreField.getText(),
                publisherField.getText(),
                Integer.parseInt(yearField.getText()),
                false // New books are not issued
        );
    }

    private void clearInputFields() {
        titleField.clear();
        authorField.clear();
        genreField.clear();
        publisherField.clear();
        yearField.clear();
    }

    private void setupTableColumns() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        genreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));
        publisherColumn.setCellValueFactory(new PropertyValueFactory<>("publisher"));
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
        isIssuedColumn.setCellValueFactory(new PropertyValueFactory<>("issued"));
    }

    private void loadBooks() {
        tableView.setItems(FXCollections.observableArrayList(bookService.getAllBooks()));
    }

    @FXML
    public void goToIssueBook() throws IOException {
        Main.showIssueBookScreen();
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