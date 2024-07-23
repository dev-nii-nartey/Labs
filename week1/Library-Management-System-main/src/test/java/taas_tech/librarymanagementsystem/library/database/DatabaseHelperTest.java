package taas_tech.librarymanagementsystem.library.database;

import org.junit.jupiter.api.*;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

public class DatabaseHelperTest {

    private static final String URL = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    @BeforeAll
    public static void setUpClass() {
        // Replace the database URL for tests
        DatabaseHelper.setDatabaseURL(URL, USER, PASSWORD);
    }

    @BeforeEach
    public void setUp() {
        // Recreate tables before each test
        DatabaseHelper.createTables();
    }

    @AfterEach
    public void tearDown() {
        // Clean up database after each test
        try (Connection conn = DatabaseHelper.connect(); Statement stmt = conn.createStatement()) {
            stmt.execute("SET REFERENTIAL_INTEGRITY FALSE");
            stmt.execute("DROP TABLE IF EXISTS transactions");
            stmt.execute("DROP TABLE IF EXISTS books");
            stmt.execute("DROP TABLE IF EXISTS patrons");
            stmt.execute("DROP TABLE IF EXISTS users");
            stmt.execute("SET REFERENTIAL_INTEGRITY TRUE");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testRegisterUser() {
        boolean result = DatabaseHelper.registerUser("testuser", "password");
        assertTrue(result);
    }

    @Test
    public void testAuthenticateUser() {
        DatabaseHelper.registerUser("testuser", "password");
        boolean result = DatabaseHelper.authenticateUser("testuser", "password");
        assertTrue(result);
    }

    @Test
    public void testIsBookIssued() {
        // Setup: Add a book
        String sql = "INSERT INTO books(title, author, isIssued) VALUES('Test Book', 'Test Author', false)";
        try (Connection conn = DatabaseHelper.connect(); Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Test: Check if the book is issued
        boolean isIssued = DatabaseHelper.isBookIssued(1);
        assertFalse(isIssued);
    }

    // Add more tests as needed
}
