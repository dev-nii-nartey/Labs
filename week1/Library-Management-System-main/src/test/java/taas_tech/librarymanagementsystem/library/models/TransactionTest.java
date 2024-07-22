package taas_tech.librarymanagementsystem.library.models;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

public class TransactionTest {

    @Test
    public void testSetAndGetId() {
        Transaction transaction = new Transaction();
        transaction.setId(1);
        assertEquals(1, transaction.getId());
    }

    @Test
    public void testSetAndGetBookId() {
        Transaction transaction = new Transaction();
        transaction.setBookId(100);
        assertEquals(100, transaction.getBookId());
    }

    @Test
    public void testSetAndGetPatronId() {
        Transaction transaction = new Transaction();
        transaction.setPatronId(200);
        assertEquals(200, transaction.getPatronId());
    }

    @Test
    public void testSetAndGetIssueDate() {
        Transaction transaction = new Transaction();
        LocalDate issueDate = LocalDate.of(2024, 7, 22);
        transaction.setIssueDate(issueDate);
        assertEquals(issueDate, transaction.getIssueDate());
    }

    @Test
    public void testSetAndGetReturnDate() {
        Transaction transaction = new Transaction();
        LocalDate returnDate = LocalDate.of(2024, 8, 5);
        transaction.setReturnDate(returnDate);
        assertEquals(returnDate, transaction.getReturnDate());
    }
}