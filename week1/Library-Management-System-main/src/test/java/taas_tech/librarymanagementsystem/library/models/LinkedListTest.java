package taas_tech.librarymanagementsystem.library.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LinkedListTest {

    @Test
    public void testAddAndGet() {
        LinkedList<String> list = new LinkedList<>();
        list.add("First");
        list.add("Second");
        list.add("Third");

        assertEquals("First", list.get(0));
        assertEquals("Second", list.get(1));
        assertEquals("Third", list.get(2));
    }

    @Test
    public void testSize() {
        LinkedList<Integer> list = new LinkedList<>();
        assertEquals(0, list.size());

        list.add(1);
        list.add(2);
        assertEquals(2, list.size());
    }

    @Test
    public void testIsEmpty() {
        LinkedList<Double> list = new LinkedList<>();
        assertTrue(list.isEmpty());

        list.add(1.0);
        assertFalse(list.isEmpty());
    }

    @Test
    public void testGetOutOfBounds() {
        LinkedList<String> list = new LinkedList<>();
        list.add("Test");

        assertThrows(IndexOutOfBoundsException.class, () -> list.get(1));
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(-1));
    }
}