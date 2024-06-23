package taas_tech.librarymanagementsystem.library.database;

import taas_tech.librarymanagementsystem.library.models.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper {
    private static final String URL = "jdbc:mysql://localhost:3306/library_management";
    private static final String USER = "root";
    private static final String PASSWORD = "password";

    public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connection to the database established successfully.");
        } catch (SQLException e) {
            System.out.println("Failed to connect to the database.");
            e.printStackTrace();
        }
        return conn;
    }

    public static void createTables() {
        String createBooksTable = "CREATE TABLE IF NOT EXISTS books (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "title VARCHAR(255) NOT NULL," +
                "author VARCHAR(255) NOT NULL," +
                "genre VARCHAR(255)," +
                "publisher VARCHAR(255)," +
                "year_published INT," +
                "isIssued BOOLEAN NOT NULL DEFAULT FALSE" +
                ");";

        String createPatronsTable = "CREATE TABLE IF NOT EXISTS patrons (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "name VARCHAR(255) NOT NULL," +
                "email VARCHAR(255) NOT NULL" +
                ");";

        String createTransactionsTable = "CREATE TABLE IF NOT EXISTS transactions (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "bookId INT," +
                "patronId INT," +
                "issueDate DATE," +
                "returnDate DATE," +
                "FOREIGN KEY (bookId) REFERENCES books(id)," +
                "FOREIGN KEY (patronId) REFERENCES patrons(id)" +
                ");";

        String createUsersTable = "CREATE TABLE IF NOT EXISTS users (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "username VARCHAR(255) NOT NULL UNIQUE," +
                "password VARCHAR(255) NOT NULL" +
                ");";

        try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
            stmt.execute(createBooksTable);
            stmt.execute(createPatronsTable);
            stmt.execute(createTransactionsTable);
            stmt.execute(createUsersTable);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static boolean registerUser(String username, String password) {
        String sql = "INSERT INTO users(username, password) VALUES(?, ?)";

        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static boolean authenticateUser(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }


    public static List<Book> getBooks() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
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
            System.out.println(e.getMessage());
        }
        return books;
    }

    // Method to check if a book with the given ID is currently issued
    public static boolean isBookIssued(int bookId) {
        String sql = "SELECT isIssued FROM books WHERE id = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, bookId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getBoolean("isIssued");
            } else {
                // No book found with the specified ID
                return false;
            }

        } catch (SQLException e) {
            System.out.println("Error checking if book is issued: " + e.getMessage());
            return false;
        }
    }
}
