package taas_tech.librarymanagementsystem.library.service;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import taas_tech.librarymanagementsystem.library.models.Book;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BookServiceTest {

    private Connection connection;
    private BookService bookService;

    @BeforeEach
    void setUp() throws SQLException {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");
        HikariDataSource dataSource = new HikariDataSource(config);
        connection = dataSource.getConnection();

        bookService = new BookService(connection);

        try (Statement stmt = connection.createStatement()) {
            connection.createStatement().execute("DROP TABLE IF EXISTS books");
            connection.createStatement().execute("DROP TABLE IF EXISTS transactions");

            stmt.execute("CREATE TABLE books (id INT AUTO_INCREMENT PRIMARY KEY, title VARCHAR(255), author VARCHAR(255), genre VARCHAR(255), publisher VARCHAR(255), year_published INT, isIssued BOOLEAN)");
            stmt.execute("CREATE TABLE transactions (id INT AUTO_INCREMENT PRIMARY KEY, bookId INT, returnDate DATE)");
        }
    }

    @AfterEach
    void tearDown() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("DROP TABLE books");
            stmt.execute("DROP TABLE transactions");
        }
        connection.close();
    }

    @Test
    void addBook() throws SQLException {
        Book book = new Book(1, "Test Title", "Test Author", "Test Genre", "Test Publisher", 2021, false);
        bookService.addBook(book);

        List<Book> books = bookService.getAllBooks();
        assertEquals(1, books.size());
        assertEquals("Test Title", books.get(0).getTitle());
    }

    @Test
    void getAllBooks() throws SQLException {
        Book book1 = new Book(1, "Test Title 1", "Test Author 1", "Test Genre 1", "Test Publisher 1", 2021, false);
        Book book2 = new Book(2, "Test Title 2", "Test Author 2", "Test Genre 2", "Test Publisher 2", 2022, true);
        bookService.addBook(book1);
        bookService.addBook(book2);

        List<Book> books = bookService.getAllBooks();
        assertEquals(2, books.size());
    }

    @Test
    void isBookIssued() throws SQLException {
        Book book = new Book(1, "Test Title", "Test Author", "Test Genre", "Test Publisher", 2021, true);
        bookService.addBook(book);

        boolean isIssued = bookService.isBookIssued(1);
        assertTrue(isIssued);
    }

    @Test
    void returnBook() throws SQLException {
        Book book = new Book(1, "Test Title", "Test Author", "Test Genre", "Test Publisher", 2021, true);
        bookService.addBook(book);

        try (Statement stmt = connection.createStatement()) {
            stmt.execute("INSERT INTO transactions (bookId) VALUES (1)");
        }

        boolean isReturned = bookService.returnBook(1);
        assertTrue(isReturned);

        boolean isIssued = bookService.isBookIssued(1);
        assertFalse(isIssued);
    }

    @Test
    void addBook_NullBook() {
        assertThrows(RuntimeException.class, () -> bookService.addBook(null));
    }

    @Test
    void getAllBooks_EmptyDatabase() {
        List<Book> books = bookService.getAllBooks();
        assertTrue(books.isEmpty());
    }

    @Test
    void isBookIssued_NonExistentBook() {
        boolean isIssued = bookService.isBookIssued(999);
        assertFalse(isIssued);
    }

    @Test
    void returnBook_NonExistentBook() {
        boolean isReturned = bookService.returnBook(999);
        assertFalse(isReturned);
    }

    @Test
    void returnBook_AlreadyReturnedBook() throws SQLException {
        Book book = new Book(1, "Test Title", "Test Author", "Test Genre", "Test Publisher", 2021, false);
        bookService.addBook(book);

        try (Statement stmt = connection.createStatement()) {
            stmt.execute("INSERT INTO transactions (bookId, returnDate) VALUES (1, CURRENT_DATE)");
        }

        boolean isReturned = bookService.returnBook(1);
        assertFalse(isReturned);
    }

//    @Test
//    void addBook_DuplicateBook() throws SQLException {
//        Book book = new Book(1, "Test Title", "Test Author", "Test Genre", "Test Publisher", 2021, false);
//        bookService.addBook(book);
//
//        // Attempt to add the same book again
//        assertThrows(RuntimeException.class, () -> bookService.addBook(book));
//    }

    @Test
    void getAllBooks_LargeNumberOfBooks() throws SQLException {
        for (int i = 0; i < 1000; i++) {
            Book book = new Book(i, "Title " + i, "Author", "Genre", "Publisher", 2021, false);
            bookService.addBook(book);
        }

        List<Book> books = bookService.getAllBooks();
        assertEquals(1000, books.size());
    }
}
