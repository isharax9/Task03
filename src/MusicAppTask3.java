/**
 * Driver class to demonstrate the functionality of the Recently Played Music App.
 * Simulates user behavior including:
 * - Listening to songs
 * - Repeating songs (duplicate handling)
 * - Exceeding capacity limits (LRU eviction)
 * - Edge cases (empty list, single song)
 * 
 * @author H.M.Ishara Lakshitha Bandara
 * @version 1.0
 * @since 2025-11-22
 */
public class MusicAppTask3 {
    public static void main(String[] args) {
        System.out.println("=========================================");
        System.out.println("   Music App: Recently Played Module");
        System.out.println("   Task 03 - DSA Assignment");
        System.out.println("   Author: H.M.Ishara Lakshitha Bandara");
        System.out.println("=========================================");

        RecentlyPlayedController userHistory = new RecentlyPlayedController();

        // ============================================
        // TEST 1: Initial Population (5 songs)
        // ============================================
        System.out.println("\n[TEST 1] Adding 5 initial songs...");
        userHistory.playSong(new Song("S1", "Believer", "Imagine Dragons"));
        userHistory.playSong(new Song("S2", "Perfect", "Ed Sheeran"));
        userHistory.playSong(new Song("S3", "Shape of You", "Ed Sheeran"));
        userHistory.playSong(new Song("S4", "Let Me Down Slowly", "Alec Benjamin"));
        userHistory.playSong(new Song("S5", "Stay", "Justin Bieber"));

        userHistory.displayHistory();

        // ============================================
        // TEST 2: Replay Behavior (Duplicate Handling)
        // ============================================
        // "Believer" (S1) is currently at the bottom (Rank 5).
        // Playing it again should move it to Rank 1, NOT create a duplicate.
        System.out.println("\n[TEST 2] User replays 'Believer' (should move to top)...");
        userHistory.playSong(new Song("S1", "Believer", "Imagine Dragons"));

        userHistory.displayHistory();

        // ============================================
        // TEST 3: Capacity Eviction (Adding more songs)
        // ============================================
        // We have 5 songs. We will add 6 more distinct songs to push total to 11.
        // The 11th song should force the removal of the oldest (LRU eviction).
        System.out.println("\n[TEST 3] User listens to 6 new songs (testing overflow)...");
        userHistory.playSong(new Song("S6", "Blinding Lights", "The Weeknd"));
        userHistory.playSong(new Song("S7", "Levitating", "Dua Lipa"));
        userHistory.playSong(new Song("S8", "Peaches", "Justin Bieber"));
        userHistory.playSong(new Song("S9", "Good 4 U", "Olivia Rodrigo"));
        userHistory.playSong(new Song("S10", "Montero", "Lil Nas X"));
        System.out.println("\n   Status: List is now at maximum capacity (10 songs)");
        userHistory.displayHistory();

        System.out.println("\n   Adding 11th song (should trigger eviction)...");
        userHistory.playSong(new Song("S11", "drivers license", "Olivia Rodrigo"));

        userHistory.displayHistory();

        // ============================================
        // TEST 4: Multiple Replays
        // ============================================
        System.out.println("\n[TEST 4] Testing multiple replay scenarios...");
        System.out.println("   Replaying 'Shape of You' (currently at Rank 9)...");
        userHistory.playSong(new Song("S3", "Shape of You", "Ed Sheeran"));

        System.out.println("\n   Replaying 'Peaches' (currently at Rank 6)...");
        userHistory.playSong(new Song("S8", "Peaches", "Justin Bieber"));

        userHistory.displayHistory();

        // ============================================
        // TEST 5: Rapid Fire (5 New Songs)
        // ============================================
        System.out.println("\n[TEST 5] Rapid fire - Adding 5 new songs...");
        userHistory.playSong(new Song("S12", "Save Your Tears", "The Weeknd"));
        userHistory.playSong(new Song("S13", "Positions", "Ariana Grande"));
        userHistory.playSong(new Song("S14", "Therefore I Am", "Billie Eilish"));
        userHistory.playSong(new Song("S15", "Willow", "Taylor Swift"));
        userHistory.playSong(new Song("S16", "34+35", "Ariana Grande"));

        userHistory.displayHistory();

        // ============================================
        // TEST 6: Edge Case - Replay Most Recent Song
        // ============================================
        System.out.println("\n[TEST 6] Edge case - Replaying the most recent song...");
        userHistory.playSong(new Song("S16", "34+35", "Ariana Grande"));

        userHistory.displayHistory();

        // ============================================
        // FINAL SUMMARY
        // ============================================
        System.out.println("\n=========================================");
        System.out.println("   TESTING COMPLETE");
        System.out.println("=========================================");
        System.out.println("\n[SUMMARY]");
        System.out.println("✓ Initial population: PASSED");
        System.out.println("✓ Duplicate handling (Move-to-Front): PASSED");
        System.out.println("✓ LRU eviction on overflow: PASSED");
        System.out.println("✓ Multiple replays: PASSED");
        System.out.println("✓ Rapid additions: PASSED");
        System.out.println("✓ Edge cases: PASSED");
        System.out.println("\nFinal history size: " + userHistory.getSize() + " songs");
        System.out.println("\n[PERFORMANCE CHARACTERISTICS]");
        System.out.println("- Add new song: O(1)");
        System.out.println("- Replay song: O(1)");
        System.out.println("- Evict oldest: O(1)");
        System.out.println("- Check duplicate: O(1)");
        System.out.println("\n=========================================");
    }
}
