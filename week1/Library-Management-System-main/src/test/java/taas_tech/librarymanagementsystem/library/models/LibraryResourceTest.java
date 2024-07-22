package taas_tech.librarymanagementsystem.library.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LibraryResourceTest {

    private class ConcreteLibraryResource extends LibraryResource {
        // Concrete class for testing abstract LibraryResource
    }

    @Test
    public void testSetAndGetId() {
        LibraryResource resource = new ConcreteLibraryResource();
        resource.setId(1);
        assertEquals(1, resource.getId());
    }
}