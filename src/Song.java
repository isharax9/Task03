/**
 * Represents a single track in the music application.
 * Acts as the data payload for our linked list nodes.
 * 
 * @author H.M.Ishara Lakshitha Bandara
 * @version 1.0
 * @since 2025-11-22
 */
public class Song {
    private final String id;      // Unique ID (e.g., "S001")
    private final String title;   // Human-readable title
    private final String artist;  // Artist name

    /**
     * Constructs a new Song object.
     * 
     * @param id     Unique identifier for the song
     * @param title  Human-readable song title
     * @param artist Artist name
     */
    public Song(String id, String title, String artist) {
        this.id = id;
        this.title = title;
        this.artist = artist;
    }

    /**
     * Gets the unique identifier of the song.
     * 
     * @return Song ID
     */
    public String getId() { 
        return id; 
    }

    /**
     * Gets the title of the song.
     * 
     * @return Song title
     */
    public String getTitle() { 
        return title; 
    }

    /**
     * Gets the artist name.
     * 
     * @return Artist name
     */
    public String getArtist() { 
        return artist; 
    }

    /**
     * Returns a string representation of the song.
     * 
     * @return Formatted string with title and artist
     */
    @Override
    public String toString() {
        return String.format("['%s' by %s]", title, artist);
    }

    /**
     * Equality check based on ID is crucial for accurate HashMap lookups.
     * Two songs are considered equal if they have the same ID.
     * 
     * @param obj Object to compare
     * @return true if objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Song other = (Song) obj;
        return this.id.equals(other.id);
    }

    /**
     * Generates hash code based on the song ID.
     * 
     * @return Hash code
     */
    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
