package taas_tech.librarymanagementsystem.library.service;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {

    private UserService userService;
    private Connection connection;

    @BeforeEach
    public void setUp() throws SQLException {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
        HikariDataSource dataSource = new HikariDataSource(config);
        connection = dataSource.getConnection();

        connection.createStatement().execute("DROP TABLE IF EXISTS users");
        connection.createStatement().execute("CREATE TABLE users (id INT AUTO_INCREMENT, username VARCHAR(255), password VARCHAR(255))");

        userService = new UserService(connection);
    }

    @Test
    public void testRegisterUser() throws SQLException {
        assertTrue(userService.registerUser("johndoe", "password123"));
    }

    @Test
    public void testAuthenticateUser() throws SQLException {
        userService.registerUser("johndoe", "password123");
        assertTrue(userService.authenticateUser("johndoe", "password123"));
    }

    @Test
    public void testAuthenticateUser_NonexistentUser() throws SQLException {
        assertFalse(userService.authenticateUser("nonexistent", "password123"));
    }

    @Test
    public void testAuthenticateUser_WrongPassword() throws SQLException {
        userService.registerUser("johndoe", "password123");
        assertFalse(userService.authenticateUser("johndoe", "wrongpassword"));
    }

    @Test
    public void testAuthenticateUser_CaseSensitiveUsername() throws SQLException {
        userService.registerUser("JohnDoe", "password123");
        assertFalse(userService.authenticateUser("johndoe", "password123"));
    }
}
