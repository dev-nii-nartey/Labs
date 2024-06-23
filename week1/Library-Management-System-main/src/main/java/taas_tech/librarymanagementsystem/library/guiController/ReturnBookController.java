package taas_tech.librarymanagementsystem.library.guiController;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import taas_tech.librarymanagementsystem.Main;
import taas_tech.librarymanagementsystem.library.database.DatabaseHelper;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ReturnBookController {
    @FXML
    private TextField bookIdField;

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    public void returnBook() {
        String bookIdText = bookIdField.getText();
        int bookId;

        try {
            bookId = Integer.parseInt(bookIdText);
        } catch (NumberFormatException e) {
            showAlert("Invalid Input", "Book ID must be a number.");
            return;
        }

        // Update return date in transactions table
        String sql = "UPDATE transactions SET returnDate = CURRENT_DATE WHERE bookId = ? AND returnDate IS NULL";
        if (DatabaseHelper.isBookIssued(bookId)) {
            showAlert("Success", "Book returned successfully.");

            try (Connection conn = DatabaseHelper.connect();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, bookId);
                pstmt.executeUpdate();

                String updateBookSql = "UPDATE books SET isIssued = 0 WHERE id = ?";
                try (PreparedStatement pstmtUpdate = conn.prepareStatement(updateBookSql)) {
                    pstmtUpdate.setInt(1, bookId);
                    pstmtUpdate.executeUpdate();
                }


            } catch (SQLException e) {
                showAlert("Database Error", "Error occurred while returning the book.");
                System.out.println(e.getMessage());
            }
        } else {
            showAlert("Error", "Book with ID " + bookId + " is not currently issued.");
        }
    }

    @FXML
    public void goToAddBook() throws IOException {
        Main.showAddBookScreen();
    }

    @FXML
    public void goToRegisterPatron() throws IOException {
        Main.showRegisterPatronScreen();
    }

    @FXML
    public void goToIssueBook() throws IOException {
        Main.showIssueBookScreen();
    }

    @FXML
    public void Exit() {
        Platform.exit();
    }
}
