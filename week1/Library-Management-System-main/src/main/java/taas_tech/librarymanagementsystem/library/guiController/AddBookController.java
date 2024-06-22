package taas_tech.librarymanagementsystem.library.guiController;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.application.Platform;
import taas_tech.librarymanagementsystem.Main;
import taas_tech.librarymanagementsystem.library.database.DatabaseHelper;
import taas_tech.librarymanagementsystem.library.models.Book;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddBookController {
    @FXML
    private TextField titleField;
    @FXML
    private TextField authorField;
    @FXML
    private TextField genreField;
    @FXML
    private TextField publisherField;
    @FXML
    private TextField yearField;

    @FXML
    private TableView<Book> tableView;

    @FXML
    private TableColumn<Book, Integer> idColumn;
    @FXML
    private TableColumn<Book, String> titleColumn;
    @FXML
    private TableColumn<Book, String> authorColumn;
    @FXML
    private TableColumn<Book, String> genreColumn;
    @FXML
    private TableColumn<Book, String> publisherColumn;
    @FXML
    private TableColumn<Book, Integer> yearColumn;
    @FXML
    private TableColumn<Book, Boolean> isIssuedColumn;

    @FXML
    public void addBook() {
        String title = titleField.getText();
        String author = authorField.getText();
        String genre = genreField.getText();
        String publisher = publisherField.getText();
        String yearText = yearField.getText();
        int year;

        try {
            year = Integer.parseInt(yearText);
        } catch (NumberFormatException e) {
            showAlert("Invalid Input", "Year must be a number.");
            return;
        }

        String sql = "INSERT INTO books(title, author, genre, publisher, year_published, isIssued) VALUES(?, ?, ?, ?," +
                " ?, ?)";

        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, title);
            pstmt.setString(2, author);
            pstmt.setString(3, genre);
            pstmt.setString(4, publisher);
            pstmt.setInt(5, year);
            pstmt.setBoolean(6, false);
            pstmt.executeUpdate();
            showAlert("Success", "Book added successfully.");
            loadBooks();  // Refresh the table view after adding a book
        } catch (SQLException e) {
            showAlert("Database Error", "Error occurred while adding the book.");
            System.out.println(e.getMessage());
        }
    }

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
       genreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));
        publisherColumn.setCellValueFactory(new PropertyValueFactory<>("publisher"));
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
        isIssuedColumn.setCellValueFactory(new PropertyValueFactory<>("issued"));

        loadBooks();
    }

    private void loadBooks() {
        tableView.setItems(FXCollections.observableArrayList(DatabaseHelper.getBooks()));
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
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
