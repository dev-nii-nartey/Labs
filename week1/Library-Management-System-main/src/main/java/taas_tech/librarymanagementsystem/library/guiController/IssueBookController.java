package taas_tech.librarymanagementsystem.library.guiController;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import taas_tech.librarymanagementsystem.Main;
import taas_tech.librarymanagementsystem.library.database.DatabaseHelper;
import taas_tech.librarymanagementsystem.library.models.Transaction;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class IssueBookController {
    @FXML
    private TextField bookIdField;
    @FXML
    private TextField patronIdField;
    @FXML
    public TextField returnDateField;
    @FXML
    private TableView<Object> tableView;
    @FXML
    private TableColumn<Transaction, Integer> bookIdColumn;
    @FXML
    private TableColumn<Transaction, Integer> patronIdColumn;
    @FXML
    private TableColumn<Transaction, String> issueDateColumn;
    @FXML
    private TableColumn<Transaction, String> returnDateColumn;

    @FXML
    public void issueBook() {
        String bookIdText = bookIdField.getText();
        String patronIdText = patronIdField.getText();
        String returnDateText = returnDateField.getText();
        int bookId;
        int patronId;

        // Log input values for debugging
        System.out.println("Book ID: " + bookIdText);
        System.out.println("Patron ID: " + patronIdText);

        try {
            bookId = Integer.parseInt(bookIdText);
        } catch (NumberFormatException e) {
            showAlert("Invalid Input", "Book ID must be a number.");
            return;
        }

        try {
            patronId = Integer.parseInt(patronIdText);
        } catch (NumberFormatException e) {
            showAlert("Invalid Input", "Patron ID must be a number.");
            return;
        }

        LocalDate returnDate;
        try {
            returnDate = LocalDate.parse(returnDateText); // Parse returnDateText to LocalDate
        } catch (DateTimeParseException e) {
            showAlert("Invalid Date", "Return date must be in yyyy-MM-dd format.");
            return;
        }

        Transaction transaction = new Transaction();
        String sql = "INSERT INTO transactions (bookId, patronId, issueDate, returnDate) VALUES (?, ?, CURRENT_DATE, " +
                "?)";


        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, bookId);
            pstmt.setInt(2, patronId);
            pstmt.setDate(3, java.sql.Date.valueOf(returnDate));
            pstmt.executeUpdate();

            // Update books table
            String updateBookSql = "UPDATE books SET isIssued = 1 WHERE id = ?";
            try (PreparedStatement pstmtUpdate = conn.prepareStatement(updateBookSql)) {
                pstmtUpdate.setInt(1, bookId);
                pstmtUpdate.executeUpdate();
            }

            showAlert("Success", "Book issued successfully.");
            loadTransactions();  // Refresh the table view after issuing a book
        } catch (SQLException e) {
            showAlert("Database Error", "Error occurred while issuing the book.");
            System.out.println(e.getMessage());
        }
    }


    @FXML
    public void initialize() {
        bookIdColumn.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        patronIdColumn.setCellValueFactory(new PropertyValueFactory<>("patronId"));
        issueDateColumn.setCellValueFactory(new PropertyValueFactory<>("issueDate"));
        returnDateColumn.setCellValueFactory(new PropertyValueFactory<>("returnDate"));

        loadTransactions();
    }

    private void loadTransactions() {
        String sql = "SELECT * FROM transactions";

        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            var transactions = FXCollections.observableArrayList();

            while (rs.next()) {
                Transaction transaction = new Transaction();
                transaction.setId(rs.getInt("id"));
                transaction.setBookId(rs.getInt("bookId"));
                transaction.setPatronId(rs.getInt("patronId"));
                transaction.setIssueDate(rs.getDate("issueDate").toLocalDate());
                transaction.setReturnDate(rs.getDate("returnDate") != null ? rs.getDate("returnDate").toLocalDate() : null);
                transactions.add(transaction);
            }

            tableView.setItems(transactions);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
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
    public void goToRegisterPatron() throws IOException {
        Main.showRegisterPatronScreen();
    }

    @FXML
    public void Exit() {
        Platform.exit();
    }
}
