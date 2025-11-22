/**
 * A fundamental building block for the Doubly Linked List.
 * Contains the data payload and pointers to traverse in both directions.
 * 
 * Reference: Syllabus Section 5.0 (Linked Lists)
 * 
 * @author H.M.Ishara Lakshitha Bandara
 * @version 1.0
 * @since 2025-11-22
 */
public class Node {
    public Song data;      // The song object this node holds
    public Node next;      // Pointer to the next node (towards older songs)
    public Node prev;      // Pointer to the previous node (towards newer songs)

    /**
     * Constructs a new Node with the given song data.
     * Initializes next and prev pointers to null.
     * 
     * @param data The song object to store in this node
     */
    public Node(Song data) {
        this.data = data;
        this.next = null;
        this.prev = null;
    }
}
