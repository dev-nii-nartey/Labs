package taas_tech.librarymanagementsystem.library.service;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import taas_tech.librarymanagementsystem.library.models.Patron;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PatronServiceTest {

    private PatronService patronService;
    private Connection connection;

    @BeforeEach
    public void setUp() throws SQLException {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
        HikariDataSource dataSource = new HikariDataSource(config);
        connection = dataSource.getConnection();

        connection.createStatement().execute("DROP TABLE IF EXISTS patrons");
        connection.createStatement().execute("CREATE TABLE patrons (id INT AUTO_INCREMENT, name VARCHAR(255), email VARCHAR(255))");

        patronService = new PatronService(connection);
    }

    @Test
    public void testRegisterPatron() throws SQLException {
        assertTrue(patronService.registerPatron("John Doe", "john.doe@example.com"));
    }

    @Test
    public void testGetAllPatrons() throws SQLException {
        patronService.registerPatron("John Doe", "john.doe@example.com");
        patronService.registerPatron("Jane Doe", "jane.doe@example.com");

        List<Patron> patrons = patronService.getAllPatrons();
        assertEquals(2, patrons.size());
    }

    @Test
    public void testGetAllPatrons_EmptyDatabase() throws SQLException {
        List<Patron> patrons = patronService.getAllPatrons();
        assertTrue(patrons.isEmpty());
    }

    @Test
    public void testGetAllPatrons_LargeNumberOfPatrons() throws SQLException {
        for (int i = 0; i < 1000; i++) {
            patronService.registerPatron("Patron " + i, "patron" + i + "@example.com");
        }
        List<Patron> patrons = patronService.getAllPatrons();
        assertEquals(1000, patrons.size());
    }


    @Test
    public void testRegisterPatron_LongNameAndEmail() throws SQLException {
        String longName = "A".repeat(256);
        String longEmail = "a".repeat(245) + "@example.com";
        assertThrows(RuntimeException.class, () -> patronService.registerPatron(longName, longEmail));
    }
}
