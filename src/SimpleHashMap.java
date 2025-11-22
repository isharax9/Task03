/**
 * A simplified implementation of a Hash Map.
 * Maps a Song ID (String) to a Node object.
 * Uses separate chaining for collision resolution.
 * 
 * Time Complexity:
 * - put(): O(1) average case
 * - get(): O(1) average case
 * - remove(): O(1) average case
 * 
 * @author H.M.Ishara Lakshitha Bandara
 * @version 1.0
 * @since 2025-11-22
 */

/**
 * Represents a key-value pair in the hash map.
 * Forms a linked list (chain) to handle collisions.
 */
class MapEntry {
    String key;          // Song ID
    Node value;          // Reference to the node in the doubly linked list
    MapEntry next;       // Next entry in the chain (for collision handling)

    /**
     * Constructs a new MapEntry.
     * 
     * @param key   Song ID
     * @param value Reference to the node
     */
    public MapEntry(String key, Node value) {
        this.key = key;
        this.value = value;
        this.next = null;
    }
}

public class SimpleHashMap {
    private final int SIZE = 16;    // Initial bucket size
    private MapEntry[] buckets;     // Array of buckets (chains)

    /**
     * Constructs a new SimpleHashMap with default bucket size.
     */
    public SimpleHashMap() {
        buckets = new MapEntry[SIZE];
    }

    /**
     * Computes the bucket index based on the key's hash code.
     * Uses modulo operation to ensure index is within bounds.
     * 
     * @param key The key to hash
     * @return Bucket index (0 to SIZE-1)
     */
    private int getBucketIndex(String key) {
        int hashCode = key.hashCode();
        return Math.abs(hashCode) % SIZE;
    }

    /**
     * Inserts a Key-Value pair into the map.
     * If the key exists, updates the value.
     * 
     * Time Complexity: O(1) average case
     * 
     * @param key   Song ID
     * @param value Reference to the node
     */
    public void put(String key, Node value) {
        int index = getBucketIndex(key);
        MapEntry head = buckets[index];

        // Check if key exists in the chain
        while (head != null) {
            if (head.key.equals(key)) {
                head.value = value; // Update existing
                return;
            }
            head = head.next;
        }

        // Key not found, insert new entry at the head of the chain
        MapEntry newEntry = new MapEntry(key, value);
        newEntry.next = buckets[index];
        buckets[index] = newEntry;
    }

    /**
     * Retrieves the Node associated with a specific Song ID.
     * 
     * Time Complexity: O(1) average case
     * 
     * @param key Song ID
     * @return Node reference if found, null otherwise
     */
    public Node get(String key) {
        int index = getBucketIndex(key);
        MapEntry head = buckets[index];

        while (head != null) {
            if (head.key.equals(key)) {
                return head.value;
            }
            head = head.next;
        }
        return null; // Not found
    }

    /**
     * Removes a key from the map.
     * 
     * Time Complexity: O(1) average case
     * 
     * @param key Song ID to remove
     */
    public void remove(String key) {
        int index = getBucketIndex(key);
        MapEntry head = buckets[index];
        MapEntry prev = null;

        while (head != null) {
            if (head.key.equals(key)) {
                if (prev == null) {
                    buckets[index] = head.next; // Remove first in chain
                } else {
                    prev.next = head.next; // Bypass the node
                }
                return;
            }
            prev = head;
            head = head.next;
        }
    }

    /**
     * Checks if a key exists in the map.
     * 
     * @param key Song ID to check
     * @return true if key exists, false otherwise
     */
    public boolean containsKey(String key) {
        return get(key) != null;
    }
}
