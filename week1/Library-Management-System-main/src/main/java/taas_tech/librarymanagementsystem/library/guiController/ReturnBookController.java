package taas_tech.librarymanagementsystem.library.guiController;

import javafx.application.Platform;
import javafx.fxml.FXML;
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

    @FXML
    public void returnBook() {
        int bookId = Integer.parseInt(bookIdField.getText());

        String sql = "UPDATE transactions SET returnDate = CURRENT_DATE WHERE bookId = ? AND returnDate IS NULL";

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
            System.out.println(e.getMessage());
        }
    }
    @FXML
    public void goToaddBook() throws IOException {
        Main.showIssueBookScreen();
    }
    @FXML
    public void goToRegisterPatron() throws IOException {
        Main.showRegisterPatronScreen();
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
