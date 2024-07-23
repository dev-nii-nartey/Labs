package taas_tech.librarymanagementsystem.library.service;

import static org.junit.jupiter.api.Assertions.*;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import taas_tech.librarymanagementsystem.library.models.Book;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BookServiceTest {

    private BookService bookService;
    private Connection connection;

    @BeforeEach
    void setUp() throws SQLException {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
        HikariDataSource dataSource = new HikariDataSource(config);
        connection = dataSource.getConnection();

        // Create tables
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("DROP TABLE IF EXISTS books");
            stmt.execute("DROP TABLE IF EXISTS transactions");
            stmt.execute("CREATE TABLE books (id INT AUTO_INCREMENT PRIMARY KEY, title VARCHAR(255), author VARCHAR(255), genre VARCHAR(255), publisher VARCHAR(255), year_published INT, isIssued BOOLEAN)");
            stmt.execute("CREATE TABLE transactions (id INT AUTO_INCREMENT PRIMARY KEY, bookId INT, returnDate DATE)");
        }

        bookService = new BookService(connection);
    }

//    @AfterEach
//    void tearDown() throws SQLException {
//        if (connection != null && !connection.isClosed()) {
//            connection.close();
//        }
//    }

    @Test
    void testAddBook() {
        Book book = new Book(1, "Test Title", "Test Author", "Test Genre", "Test Publisher", 2021, false);
        bookService.addBook(book);

        List<Book> books = bookService.getAllBooks();
        assertEquals(1, books.size());
        assertEquals("Test Title", books.get(0).getTitle());
    }

    @Test
    void testGetAllBooks() {
        Book book1 = new Book(1, "Test Title 1", "Test Author 1", "Test Genre 1", "Test Publisher 1", 2021, false);
        Book book2 = new Book(2, "Test Title 2", "Test Author 2", "Test Genre 2", "Test Publisher 2", 2022, true);
        bookService.addBook(book1);
        bookService.addBook(book2);

        List<Book> books = bookService.getAllBooks();
        assertEquals(2, books.size());
    }

    @Test
    void testGetAllBooks_EmptyDatabase() {
        List<Book> books = bookService.getAllBooks();
        assertTrue(books.isEmpty());
    }

    @Test
    void testIsBookIssued() {
        Book book = new Book(1, "Test Title", "Test Author", "Test Genre", "Test Publisher", 2021, true);
        bookService.addBook(book);

        boolean isIssued = bookService.isBookIssued(1);
        assertTrue(isIssued);
    }

    @Test
    void testIsBookIssued_NonExistentBook() {
        boolean isIssued = bookService.isBookIssued(999);
        assertFalse(isIssued);
    }

    @Test
    void testReturnBook() throws SQLException {
        Book book = new Book(1, "Test Title", "Test Author", "Test Genre", "Test Publisher", 2021, true);
        bookService.addBook(book);

        try (Statement stmt = connection.createStatement()) {
            stmt.execute("INSERT INTO transactions (bookId) VALUES (1)");
        }

        boolean isReturned = bookService.returnBook(1);
        assertTrue(isReturned);
        assertFalse(bookService.isBookIssued(1));
    }

    @Test
    void testAddBook_NullBook() {
        assertThrows(RuntimeException.class, () -> bookService.addBook(null));
    }
}