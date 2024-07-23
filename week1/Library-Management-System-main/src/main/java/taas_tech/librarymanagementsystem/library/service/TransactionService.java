package taas_tech.librarymanagementsystem.library.service;

import taas_tech.librarymanagementsystem.library.database.DatabaseHelper;
import taas_tech.librarymanagementsystem.library.models.Transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TransactionService {
    private Connection conn;

    public TransactionService() {
        this.conn =  DatabaseHelper.connect();
    }

    public TransactionService(Connection connection) {
        this.conn =  connection;
    }

    public void issueBook(int bookId, int patronId, LocalDate returnDate) throws SQLException {
        String sql = "INSERT INTO transactions (bookId, patronId, issueDate, returnDate) VALUES (?, ?, CURRENT_DATE, ?)";

        try {

             PreparedStatement pstmt = conn.prepareStatement(sql); {
            pstmt.setInt(1, bookId);
            pstmt.setInt(2, patronId);
            pstmt.setDate(3, java.sql.Date.valueOf(returnDate));
            pstmt.executeUpdate();

            this.updateBookIssuedStatus(conn, bookId, true);
        }
    } catch (SQLException e) {
            throw new RuntimeException(e);
        }}

        private void updateBookIssuedStatus(Connection conn, int bookId, boolean isIssued) throws SQLException {
        String sql = "UPDATE books SET isIssued = ? WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setBoolean(1, isIssued);
            pstmt.setInt(2, bookId);
            pstmt.executeUpdate();
        }
    }

    public List<Transaction> getAllTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions";

        try {

             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Transaction transaction = new Transaction();
                transaction.setId(rs.getInt("id"));
                transaction.setBookId(rs.getInt("bookId"));
                transaction.setPatronId(rs.getInt("patronId"));
                transaction.setIssueDate(rs.getDate("issueDate").toLocalDate());
                transaction.setReturnDate(rs.getDate("returnDate") != null ? rs.getDate("returnDate").toLocalDate() : null);
                transactions.add(transaction);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return transactions;
    }

    public boolean returnBook(int bookId) {
        String sql = "UPDATE transactions SET returnDate = CURRENT_DATE WHERE bookId = ? AND returnDate IS NULL";

        try {
             PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, bookId);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    }