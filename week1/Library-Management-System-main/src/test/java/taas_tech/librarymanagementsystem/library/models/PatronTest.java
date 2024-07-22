package taas_tech.librarymanagementsystem.library.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PatronTest {

    @Test
    public void testPatronConstructor() {
        Patron patron = new Patron(1, "John Doe", "john.doe@example.com");

        assertEquals(1, patron.getId());
        assertEquals("John Doe", patron.getName());
        assertEquals("john.doe@example.com", patron.getEmail());
    }

    @Test
    public void testSetAndGetId() {
        Patron patron = new Patron(0, "", "");
        patron.setId(2);
        assertEquals(2, patron.getId());
    }

    @Test
    public void testSetAndGetName() {
        Patron patron = new Patron(0, "", "");
        patron.setName("Jane Smith");
        assertEquals("Jane Smith", patron.getName());
    }

    @Test
    public void testSetAndGetEmail() {
        Patron patron = new Patron(0, "", "");
        patron.setEmail("jane.smith@example.com");
        assertEquals("jane.smith@example.com", patron.getEmail());
    }
}