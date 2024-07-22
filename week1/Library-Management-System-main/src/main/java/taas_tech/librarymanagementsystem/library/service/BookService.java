package taas_tech.librarymanagementsystem.library.service;

import taas_tech.librarymanagementsystem.library.database.DatabaseHelper;
import taas_tech.librarymanagementsystem.library.models.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookService {
    private Connection connection;

    public BookService() {
        this.connection = DatabaseHelper.connect();
    }

    public BookService(Connection connection) {
        this.connection = connection;
    }

    public void addBook(Book book) {
        String sql = "INSERT INTO books(title, author, genre, publisher, year_published, isIssued) VALUES(?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, book.getTitle());
            pstmt.setString(2, book.getAuthor());
            pstmt.setString(3, book.getGenre());
            pstmt.setString(4, book.getPublisher());
            pstmt.setInt(5, book.getYear());
            pstmt.setBoolean(6, book.isIssued());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error adding book: " + e.getMessage(), e);
        }
    }

    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books";
        try (PreparedStatement pstmt = this.connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Book book = new Book(rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("genre"),
                        rs.getString("publisher"),
                        rs.getInt("year_published"),
                        rs.getBoolean("isIssued"));
                books.add(book);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving books: " + e.getMessage(), e);
        }
        return books;
    }

    public boolean isBookIssued(int bookId) {
        String sql = "SELECT isIssued FROM books WHERE id = ?";
        try (PreparedStatement pstmt = this.connection.prepareStatement(sql)) {
            pstmt.setInt(1, bookId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getBoolean("isIssued");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error checking if book is issued: " + e.getMessage(), e);
        }
        return false;
    }

    public boolean returnBook(int bookId) {
        String updateTransactionSql = "UPDATE transactions SET returnDate = CURRENT_DATE WHERE bookId = ? AND returnDate IS NULL";
        String updateBookSql = "UPDATE books SET isIssued = 0 WHERE id = ?";

        try (PreparedStatement pstmtTransaction = connection.prepareStatement(updateTransactionSql);
             PreparedStatement pstmtBook = connection.prepareStatement(updateBookSql)) {
            connection.setAutoCommit(false);

            pstmtTransaction.setInt(1, bookId);
            int transactionRowsAffected = pstmtTransaction.executeUpdate();

            pstmtBook.setInt(1, bookId);
            int bookRowsAffected = pstmtBook.executeUpdate();

            if (transactionRowsAffected > 0 && bookRowsAffected > 0) {
                connection.commit();
                return true;
            } else {
                connection.rollback();
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error returning book: " + e.getMessage(), e);
        }
    }
}
