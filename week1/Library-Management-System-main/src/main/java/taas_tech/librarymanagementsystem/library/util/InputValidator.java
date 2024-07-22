package taas_tech.librarymanagementsystem.library.util;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class InputValidator {
    public static boolean isValidYear(String year) {
        try {
            int yearInt = Integer.parseInt(year);
            return yearInt > 0 && yearInt <= java.time.Year.now().getValue();
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isValidId(String id) {
        try {
            int idInt = Integer.parseInt(id);
            return idInt > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isValidDate(String date) {
        try {
            LocalDate.parse(date);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}