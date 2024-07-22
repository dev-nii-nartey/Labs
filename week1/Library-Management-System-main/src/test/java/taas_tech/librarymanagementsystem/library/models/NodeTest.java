package taas_tech.librarymanagementsystem.library.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class NodeTest {

    @Test
    public void testNodeCreation() {
        Node<String> node = new Node<>("Test");
        assertEquals("Test", node.data);
        assertNull(node.next);
    }

    @Test
    public void testNodeLinking() {
        Node<Integer> node1 = new Node<>(1);
        Node<Integer> node2 = new Node<>(2);
        node1.next = node2;

        assertEquals(1, node1.data);
        assertEquals(2, node1.next.data);
        assertNull(node2.next);
    }
}