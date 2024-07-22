package taas_tech.librarymanagementsystem.library.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class InputValidatorTest {

    @Test
    public void testIsValidYear() {
        assertTrue(InputValidator.isValidYear("2023"));
        assertFalse(InputValidator.isValidYear("0"));
        assertFalse(InputValidator.isValidYear("-1"));
        assertFalse(InputValidator.isValidYear("abc"));
        assertFalse(InputValidator.isValidYear("3000")); // Assuming current year is less than 3000
    }

    @Test
    public void testIsValidId() {
        assertTrue(InputValidator.isValidId("1"));
        assertTrue(InputValidator.isValidId("100"));
        assertFalse(InputValidator.isValidId("0"));
        assertFalse(InputValidator.isValidId("-1"));
        assertFalse(InputValidator.isValidId("abc"));
    }

    @Test
    public void testIsValidDate() {
        assertTrue(InputValidator.isValidDate("2023-07-22"));
        assertFalse(InputValidator.isValidDate("2023-13-01")); // Invalid month
        assertFalse(InputValidator.isValidDate("2023-02-30")); // Invalid day
        assertFalse(InputValidator.isValidDate("abc"));
    }
}