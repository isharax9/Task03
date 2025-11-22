/**
 * The Orchestrator Class.
 * Combines the HashMap and DoublyLinkedList to enforce business rules.
 * 
 * Business Rules:
 * - Fixed Size: 10 songs maximum
 * - Deduplication: No duplicate songs in history
 * - LRU Eviction: Oldest song is removed when capacity is exceeded
 * - Move-to-Front: Replaying a song moves it to the top
 * 
 * All operations are thread-safe to prevent race conditions.
 * 
 * @author H.M.Ishara Lakshitha Bandara
 * @version 1.0
 * @since 2025-11-22
 */
public class RecentlyPlayedController {
    private final int CAPACITY = 10;                       // Maximum history size
    private final SimpleHashMap lookupMap;                 // For O(1) duplicate checking
    private final DoublyLinkedList playHistory;            // For maintaining order

    /**
     * Constructs a new RecentlyPlayedController.
     * Initializes the hash map and doubly linked list.
     */
    public RecentlyPlayedController() {
        this.lookupMap = new SimpleHashMap();
        this.playHistory = new DoublyLinkedList();
    }

    /**
     * Records a song being played.
     * 
     * Algorithm:
     * 1. Check if song exists in history (using HashMap)
     * 2. If exists: Move existing node to head (O(1))
     * 3. If new: Check capacity, evict if needed, add to head
     * 
     * Thread-safe to prevent race conditions during rapid updates.
     * 
     * Time Complexity: O(1) for all operations
     * 
     * @param song The song being played
     */
    public synchronized void playSong(Song song) {
        System.out.println(">> Playing: " + song.getTitle());

        String key = song.getId();

        // Check if song is already in history
        if (lookupMap.containsKey(key)) {
            System.out.println("   [Logic] Song exists. Moving to top.");
            Node existingNode = lookupMap.get(key);

            // Move existing node to head (most recent position)
            playHistory.moveToHead(existingNode);

        } else {
            System.out.println("   [Logic] New song detected.");

            // Check capacity before adding
            if (playHistory.size >= CAPACITY) {
                // Evict the oldest (least recently played)
                Node oldest = playHistory.removeLast();
                lookupMap.remove(oldest.data.getId()); // Remove from Map
                System.out.println("   [Eviction] Capacity full. Removed: " + oldest.data.getTitle());
            }

            // Create new node and add to head
            Node newNode = new Node(song);
            playHistory.addFirst(newNode);
            lookupMap.put(key, newNode); // Add to Map for fast lookup
        }
    }

    /**
     * Displays the current recently played history.
     * Shows songs from newest to oldest.
     */
    public void displayHistory() {
        playHistory.printForward();
    }

    /**
     * Gets the current size of the history.
     * 
     * @return Number of songs in history
     */
    public int getSize() {
        return playHistory.size;
    }

    /**
     * Checks if a song is in the recently played history.
     * 
     * @param songId The song ID to check
     * @return true if song is in history, false otherwise
     */
    public boolean contains(String songId) {
        return lookupMap.containsKey(songId);
    }
}
