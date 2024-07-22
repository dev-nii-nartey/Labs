package taas_tech.librarymanagementsystem.library.service;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import taas_tech.librarymanagementsystem.library.models.Transaction;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TransactionServiceTest {

    private TransactionService transactionService;
    private Connection connection;

    @BeforeEach
    public void setUp() throws SQLException {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
        HikariDataSource dataSource = new HikariDataSource(config);
        connection = dataSource.getConnection();

        connection.createStatement().execute("DROP TABLE IF EXISTS transactions");
        connection.createStatement().execute("DROP TABLE IF EXISTS books");
        connection.createStatement().execute("CREATE TABLE transactions (id INT AUTO_INCREMENT, bookId INT, patronId INT, issueDate DATE, returnDate DATE)");
        connection.createStatement().execute("CREATE TABLE books (id INT AUTO_INCREMENT, isIssued BOOLEAN)");

        transactionService = new TransactionService(connection);
    }

    @Test
    public void testIssueBook() throws SQLException {
        connection.createStatement().execute("INSERT INTO books (isIssued) VALUES (false)");

        transactionService.issueBook(1, 1, LocalDate.now().plusDays(14));
        List<Transaction> transactions = transactionService.getAllTransactions();
        assertEquals(1, transactions.size());
    }

    @Test
    public void testGetAllTransactions() throws SQLException {
        connection.createStatement().execute("INSERT INTO books (isIssued) VALUES (false)");

        transactionService.issueBook(1, 1, LocalDate.now().plusDays(14));
        transactionService.issueBook(1, 2, LocalDate.now().plusDays(14));

        List<Transaction> transactions = transactionService.getAllTransactions();
        assertEquals(2, transactions.size());
    }
}
