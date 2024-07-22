package taas_tech.librarymanagementsystem.library.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BookTest {

    @Test
    public void testBookConstructor() {
        Book book = new Book(1, "The Great Gatsby", "F. Scott Fitzgerald", "Novel", "Scribner", 1925, false);

        assertEquals(1, book.getId());
        assertEquals("The Great Gatsby", book.getTitle());
        assertEquals("F. Scott Fitzgerald", book.getAuthor());
        assertEquals("Novel", book.getGenre());
        assertEquals("Scribner", book.getPublisher());
        assertEquals(1925, book.getYear());
        assertFalse(book.isIssued());
    }

    @Test
    public void testSetAndGetId() {
        Book book = new Book(0, "", "", "", "", 0, false);
        book.setId(2);
        assertEquals(2, book.getId());
    }

    @Test
    public void testSetAndGetTitle() {
        Book book = new Book(0, "", "", "", "", 0, false);
        book.setTitle("To Kill a Mockingbird");
        assertEquals("To Kill a Mockingbird", book.getTitle());
    }

    @Test
    public void testSetAndGetAuthor() {
        Book book = new Book(0, "", "", "", "", 0, false);
        book.setAuthor("Harper Lee");
        assertEquals("Harper Lee", book.getAuthor());
    }

    @Test
    public void testSetAndGetGenre() {
        Book book = new Book(0, "", "", "", "", 0, false);
        book.setGenre("Southern Gothic");
        assertEquals("Southern Gothic", book.getGenre());
    }

    @Test
    public void testSetAndGetPublisher() {
        Book book = new Book(0, "", "", "", "", 0, false);
        book.setPublisher("J. B. Lippincott & Co.");
        assertEquals("J. B. Lippincott & Co.", book.getPublisher());
    }

    @Test
    public void testSetAndGetYear() {
        Book book = new Book(0, "", "", "", "", 0, false);
        book.setYear(1960);
        assertEquals(1960, book.getYear());
    }

    @Test
    public void testSetAndIsIssued() {
        Book book = new Book(0, "", "", "", "", 0, false);
        book.setIssued(true);
        assertTrue(book.isIssued());
    }
}