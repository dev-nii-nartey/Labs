package taas_tech.librarymanagementsystem.library.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test
    public void testSetAndGetId() {
        User user = new User();
        user.setId(1);
        assertEquals(1, user.getId());
    }

    @Test
    public void testSetAndGetUsername() {
        User user = new User();
        user.setUsername("johndoe");
        assertEquals("johndoe", user.getUsername());
    }

    @Test
    public void testSetAndGetPassword() {
        User user = new User();
        user.setPassword("password123");
        assertEquals("password123", user.getPassword());
    }
}