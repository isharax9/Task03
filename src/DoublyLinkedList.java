/**
 * Custom Doubly Linked List implementation.
 * Specialized for LRU operations: Add to Head, Remove Tail, Unlink Node.
 * 
 * All operations maintain O(1) time complexity by using head and tail pointers.
 * 
 * @author H.M.Ishara Lakshitha Bandara
 * @version 1.0
 * @since 2025-11-22
 */
public class DoublyLinkedList {
    public Node head;    // Points to the most recently played song
    public Node tail;    // Points to the least recently played song
    public int size;     // Current number of nodes in the list

    /**
     * Constructs an empty doubly linked list.
     */
    public DoublyLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    /**
     * Adds a node to the absolute front of the list.
     * This represents the "Most Recently Played" position.
     * 
     * Time Complexity: O(1)
     * 
     * @param node The node to add
     */
    public void addFirst(Node node) {
        if (head == null) {
            // List is empty
            head = node;
            tail = node;
        } else {
            // Add to front
            node.next = head;
            head.prev = node;
            head = node;
        }
        size++;
    }

    /**
     * Removes the last node (Least Recently Played).
     * Used when capacity is exceeded.
     * 
     * Time Complexity: O(1) - No traversal required thanks to 'tail' pointer.
     * 
     * @return The removed node, or null if list is empty
     */
    public Node removeLast() {
        if (tail == null) return null;

        Node removed = tail;
        if (head == tail) {
            // Only one node in the list
            head = null;
            tail = null;
        } else {
            // Update tail pointer
            tail = tail.prev;
            tail.next = null;
        }

        // Nullify pointers for garbage collection
        removed.prev = null;
        removed.next = null;
        size--;
        return removed;
    }

    /**
     * Removes a specific node from anywhere in the list.
     * Used when a song is replayed and needs to be moved.
     * 
     * Time Complexity: O(1)
     * 
     * @param node The node to remove
     */
    public void removeNode(Node node) {
        if (node == head) {
            // Removing the head
            head = node.next;
            if (head != null) head.prev = null;
        } else if (node == tail) {
            // Removing the tail
            tail = tail.prev;
            if (tail != null) tail.next = null;
        } else {
            // Node is in the middle. Stitch the neighbors together.
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }

        // Cleanup pointers
        node.next = null;
        node.prev = null;
        size--;
    }

    /**
     * Moves an existing node to the head (most recent position).
     * This is used when a song is replayed.
     * 
     * Time Complexity: O(1)
     * 
     * @param node The node to move
     */
    public void moveToHead(Node node) {
        // If already at head, do nothing
        if (node == head) return;

        // 1. Remove from current position
        if (node == tail) {
            tail = tail.prev;
            tail.next = null;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }

        // 2. Attach to front
        node.next = head;
        node.prev = null;
        if (head != null) {
            head.prev = node;
        }
        head = node;
    }

    /**
     * Prints the list from head to tail (newest to oldest).
     * Used for displaying the recently played history.
     */
    public void printForward() {
        Node current = head;
        int rank = 1;
        System.out.println("\n--- Current History (Newest First) ---");
        if (current == null) {
            System.out.println("   (Empty)");
        }
        while (current != null) {
            System.out.println(rank + ". " + current.data.toString());
            current = current.next;
            rank++;
        }
        System.out.println("--------------------------------------");
    }
}
