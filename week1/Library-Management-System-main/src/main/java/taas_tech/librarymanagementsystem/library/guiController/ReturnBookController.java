package taas_tech.librarymanagementsystem.library.guiController;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import taas_tech.librarymanagementsystem.Main;
import taas_tech.librarymanagementsystem.library.service.BookService;
import taas_tech.librarymanagementsystem.library.database.DatabaseHelper;
import taas_tech.librarymanagementsystem.library.util.AlertUtil;

import java.io.IOException;

public class ReturnBookController {
    @FXML
    private TextField bookIdField;

    private BookService bookService;

    public ReturnBookController() {
        this.bookService = new BookService();
    }

    @FXML
    public void returnBook() {
        String bookIdText = bookIdField.getText();
        int bookId;

        try {
            bookId = Integer.parseInt(bookIdText);
        } catch (NumberFormatException e) {
            AlertUtil.showError("Invalid Input", "Book ID must be a number.");
            return;
        }

        try {
            if (bookService.isBookIssued(bookId)) {
                boolean success = bookService.returnBook(bookId);
                if (success) {
                    AlertUtil.showInfo("Success", "Book returned successfully.");
                } else {
                    AlertUtil.showError("Error", "Failed to return the book.");
                }
            } else {
                AlertUtil.showError("Error", "Book with ID " + bookId + " is not currently issued.");
            }
        } catch (Exception e) {
            AlertUtil.showError("Database Error", "Error occurred while returning the book: " + e.getMessage());
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