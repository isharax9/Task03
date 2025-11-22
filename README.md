# Task 03: Music App - Recently Played Songs

[![Java](https://img.shields.io/badge/Java-ED8B00?style=flat&logo=java&logoColor=white)](https://www.java.com/)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![DSA](https://img.shields.io/badge/DSA-Data%20Structures-blue)](https://github.com/isharax9/Task03)

## 📋 Table of Contents
- [Overview](#overview)
- [Problem Statement](#problem-statement)
- [Solution Architecture](#solution-architecture)
- [Features](#features)
- [Project Structure](#project-structure)
- [Implementation Details](#implementation-details)
- [How to Run](#how-to-run)
- [Sample Output](#sample-output)
- [Complexity Analysis](#complexity-analysis)
- [Author](#author)
- [License](#license)

## 🎯 Overview

This project implements a **Recently Played Songs** feature for a music streaming application, similar to those found in Spotify, Apple Music, and YouTube Music. The implementation uses a **Hybrid LRU (Least Recently Used) Cache** architecture combining a **Doubly Linked List** and a **Custom Hash Map** to achieve **O(1) time complexity** for all critical operations.

This is Task 03 of the Final Year Data Structures and Algorithms (DSA) module assignment.

## 📖 Problem Statement

Design and implement a music player's "Recently Played" history system with the following requirements:

1. **Fixed Capacity**: Maintain exactly the last 10 played songs
2. **Chronological Order**: Display songs from newest to oldest
3. **Automatic Eviction**: Remove the oldest song when adding the 11th song
4. **Duplicate Handling**: When replaying a song, move it to the top (no duplicates)
5. **Performance**: All operations must be O(1) constant time

### Real-World Context
This problem mirrors the "Spotify Bug" where duplicate songs appear in history due to poor data structure choices. Our implementation solves this using industry-standard LRU cache patterns.

## 🏗️ Solution Architecture

### Hybrid Data Structure: LRU Cache

Our solution combines two fundamental data structures:

```
┌─────────────────────────────────────────────────────────┐
│                  LRU CACHE ARCHITECTURE                 │
├─────────────────────────────────────────────────────────┤
│                                                         │
│  HashMap (Song ID → Node)         Doubly Linked List   │
│  ┌──────────────────┐              ┌─────────────┐    │
│  │ "S1" → Node1     │              │    HEAD     │    │
│  │ "S2" → Node2     │              │ ┌─────────┐ │    │
│  │ "S3" → Node3     │              │ │  Node3  │←┼──┐ │
│  │ ...              │              │ └────┬────┘ │  │ │
│  └──────────────────┘              │      ↓      │  │ │
│        O(1) Lookup                 │ ┌────┴────┐ │  │ │
│                                    │ │  Node2  │←┼──┤ │
│                                    │ └────┬────┘ │  │ │
│                                    │      ↓      │  │ │
│                                    │ ┌────┴────┐ │  │ │
│                                    │ │  Node1  │←┼──┘ │
│                                    │ └─────────┘ │    │
│                                    │    TAIL     │    │
│                                    └─────────────┘    │
│                                    O(1) Operations    │
└─────────────────────────────────────────────────────────┘
```

**Why This Hybrid Approach?**
- **Doubly Linked List**: Maintains order, allows O(1) head/tail operations
- **Hash Map**: Provides O(1) duplicate detection and node access

### Algorithm Flow

```
┌─────────────────┐
│  Play Song(S)   │
└────────┬────────┘
         │
         ▼
    ┌────────────────┐
    │ S in HashMap?  │
    └────┬───────┬───┘
         │       │
     YES │       │ NO
         │       │
         ▼       ▼
   ┌─────────┐ ┌──────────────┐
   │ Move to │ │ Size == 10?  │
   │  Head   │ └──────┬───────┘
   │  O(1)   │        │
   └─────────┘    YES │ NO
                      │
                      ▼
              ┌───────────────┐
              │ Remove Tail   │
              │ from Map      │
              │    O(1)       │
              └───────┬───────┘
                      │
                      ▼
              ┌───────────────┐
              │ Add to Head   │
              │ Add to Map    │
              │    O(1)       │
              └───────────────┘
```

## ✨ Features

✅ **Pure Java Implementation** - No external libraries (built from scratch)
✅ **O(1) Time Complexity** - All operations in constant time
✅ **Custom Data Structures** - Hand-crafted HashMap and Doubly Linked List
✅ **Thread-Safe** - Synchronized methods for concurrent access
✅ **Duplicate Prevention** - Move-to-front strategy
✅ **LRU Eviction** - Automatic removal of least recently used
✅ **Memory Efficient** - Fixed-size pool with proper garbage collection

## 📁 Project Structure

```
Task03/
│
├── src/
│   ├── Song.java                      # Song entity (ID, title, artist)
│   ├── Node.java                      # Doubly linked list node
│   ├── SimpleHashMap.java             # Custom HashMap implementation
│   ├── DoublyLinkedList.java          # Custom DLL with LRU operations
│   ├── RecentlyPlayedController.java  # Main orchestrator
│   └── MusicAppTask3.java             # Driver class with tests
│
├── LICENSE                             # MIT License
├── README.md                           # This file
└── .gitignore                          # Git ignore rules
```

## 🔧 Implementation Details

### 1. Song Entity (`Song.java`)
```java
public class Song {
    private final String id;      // Unique identifier
    private final String title;   // Song title
    private final String artist;  // Artist name
    
    // Equality based on ID for HashMap
    @Override
    public boolean equals(Object obj) { ... }
    
    @Override
    public int hashCode() { ... }
}
```

### 2. Doubly Linked List Node (`Node.java`)
```java
public class Node {
    public Song data;
    public Node next;  // Points to older song
    public Node prev;  // Points to newer song
}
```

### 3. Custom Hash Map (`SimpleHashMap.java`)
- **Bucket Size**: 16 (with separate chaining)
- **Collision Resolution**: Linked list chains
- **Operations**: `put()`, `get()`, `remove()`, `containsKey()`
- **Time Complexity**: O(1) average case

### 4. Doubly Linked List (`DoublyLinkedList.java`)
**Key Operations**:
- `addFirst(Node)` - Add to head (most recent)
- `removeLast()` - Remove tail (least recent)
- `removeNode(Node)` - Remove from middle
- `moveToHead(Node)` - Reposition on replay

### 5. Controller (`RecentlyPlayedController.java`)
**Business Rules**:
- Maximum capacity: 10 songs
- Thread-safe with `synchronized` methods
- Combines HashMap + DLL for LRU logic

## 🚀 How to Run

### Prerequisites
- Java Development Kit (JDK) 8 or higher
- Command line or IDE (IntelliJ IDEA, Eclipse, VS Code)

### Compilation
```bash
# Navigate to src directory
cd src

# Compile all Java files
javac *.java

# Run the main driver class
java MusicAppTask3
```

### Using an IDE
1. Clone the repository
2. Open the project in your IDE
3. Run `MusicAppTask3.java`

## 📊 Sample Output

```
=========================================
   Music App: Recently Played Module
   Task 03 - DSA Assignment
   Author: H.M.Ishara Lakshitha Bandara
=========================================

[TEST 1] Adding 5 initial songs...
>> Playing: Believer
   [Logic] New song detected.
>> Playing: Perfect
   [Logic] New song detected.
>> Playing: Shape of You
   [Logic] New song detected.
>> Playing: Let Me Down Slowly
   [Logic] New song detected.
>> Playing: Stay
   [Logic] New song detected.

--- Current History (Newest First) ---
1. ['Stay' by Justin Bieber]
2. ['Let Me Down Slowly' by Alec Benjamin]
3. ['Shape of You' by Ed Sheeran]
4. ['Perfect' by Ed Sheeran]
5. ['Believer' by Imagine Dragons]
--------------------------------------

[TEST 2] User replays 'Believer' (should move to top)...
>> Playing: Believer
   [Logic] Song exists. Moving to top.

--- Current History (Newest First) ---
1. ['Believer' by Imagine Dragons]
2. ['Stay' by Justin Bieber]
3. ['Let Me Down Slowly' by Alec Benjamin]
4. ['Shape of You' by Ed Sheeran]
5. ['Perfect' by Ed Sheeran]
--------------------------------------

[TEST 3] User listens to 6 new songs (testing overflow)...
>> Playing: Blinding Lights
   [Logic] New song detected.
>> Playing: Levitating
   [Logic] New song detected.
>> Playing: Peaches
   [Logic] New song detected.
>> Playing: Good 4 U
   [Logic] New song detected.
>> Playing: Montero
   [Logic] New song detected.

   Status: List is now at maximum capacity (10 songs)

--- Current History (Newest First) ---
1. ['Montero' by Lil Nas X]
2. ['Good 4 U' by Olivia Rodrigo]
3. ['Peaches' by Justin Bieber]
4. ['Levitating' by Dua Lipa]
5. ['Blinding Lights' by The Weeknd]
6. ['Believer' by Imagine Dragons]
7. ['Stay' by Justin Bieber]
8. ['Let Me Down Slowly' by Alec Benjamin]
9. ['Shape of You' by Ed Sheeran]
10. ['Perfect' by Ed Sheeran]
--------------------------------------

   Adding 11th song (should trigger eviction)...
>> Playing: drivers license
   [Logic] New song detected.
   [Eviction] Capacity full. Removed: Perfect

--- Current History (Newest First) ---
1. ['drivers license' by Olivia Rodrigo]
2. ['Montero' by Lil Nas X]
3. ['Good 4 U' by Olivia Rodrigo]
4. ['Peaches' by Justin Bieber]
5. ['Levitating' by Dua Lipa]
6. ['Blinding Lights' by The Weeknd]
7. ['Believer' by Imagine Dragons]
8. ['Stay' by Justin Bieber]
9. ['Let Me Down Slowly' by Alec Benjamin]
10. ['Shape of You' by Ed Sheeran]
--------------------------------------

[TEST 4] Testing multiple replay scenarios...
   Replaying 'Shape of You' (currently at Rank 9)...
>> Playing: Shape of You
   [Logic] Song exists. Moving to top.

   Replaying 'Peaches' (currently at Rank 6)...
>> Playing: Peaches
   [Logic] Song exists. Moving to top.

--- Current History (Newest First) ---
1. ['Peaches' by Justin Bieber]
2. ['Shape of You' by Ed Sheeran]
3. ['drivers license' by Olivia Rodrigo]
4. ['Montero' by Lil Nas X]
5. ['Good 4 U' by Olivia Rodrigo]
6. ['Levitating' by Dua Lipa]
7. ['Blinding Lights' by The Weeknd]
8. ['Believer' by Imagine Dragons]
9. ['Stay' by Justin Bieber]
10. ['Let Me Down Slowly' by Alec Benjamin]
--------------------------------------

=========================================
   TESTING COMPLETE
=========================================

[SUMMARY]
✓ Initial population: PASSED
✓ Duplicate handling (Move-to-Front): PASSED
✓ LRU eviction on overflow: PASSED
✓ Multiple replays: PASSED
✓ Rapid additions: PASSED
✓ Edge cases: PASSED

Final history size: 10 songs

[PERFORMANCE CHARACTERISTICS]
- Add new song: O(1)
- Replay song: O(1)
- Evict oldest: O(1)
- Check duplicate: O(1)

=========================================
```

## 📈 Complexity Analysis

### Time Complexity

| Operation | Array | Singly Linked List | Circular Queue | **DLL + HashMap (Ours)** |
|-----------|-------|-------------------|----------------|-------------------------|
| Add New Song | O(N) | O(1) | O(1) | **O(1)** |
| Remove Oldest | O(1) | O(N) | O(1) | **O(1)** |
| Check Duplicate | O(N) | O(N) | O(N) | **O(1)** |
| Replay (Move) | O(N) | O(N) | O(N) | **O(1)** |

### Space Complexity
- **HashMap**: O(N) where N = capacity (10)
- **Doubly Linked List**: O(N) where N = capacity (10)
- **Total**: O(2N) = O(N) = O(10) = **O(1)** for fixed size

### Why Our Solution is Optimal

✅ **Constant Time Operations**: All critical operations are O(1)
✅ **No Linear Scans**: HashMap eliminates the need to traverse
✅ **Bidirectional Access**: DLL allows efficient head/tail operations
✅ **Memory Efficient**: Fixed-size pool with no memory leaks

## 🎓 Key Learnings

### Data Structure Selection
1. **Arrays** - Fast random access but slow insertion/deletion
2. **Singly Linked Lists** - Fast insertion but can't traverse backward
3. **Circular Queues** - Good for FIFO but can't handle random access
4. **Doubly Linked Lists** - Perfect for bidirectional operations
5. **Hash Maps** - Essential for O(1) lookups

### Industry Applications
- **Operating Systems**: Page replacement algorithms (LRU)
- **Databases**: Query result caching
- **Web Browsers**: Browser history management
- **CDNs**: Content delivery caching
- **Music Apps**: Spotify, Apple Music, YouTube Music

## 👨‍💻 Author

**H.M.Ishara Lakshitha Bandara**
- Software Engineering Student
- Data Structures & Algorithms Specialization
- Final Year Project - Task 03

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

## 🔗 References

1. LRU Cache Design Patterns
2. Doubly Linked List Applications
3. Hash Map Implementation Techniques
4. Spotify API Documentation
5. Operating System Memory Management

---

**⭐ If you found this helpful, please star the repository!**

```
┌────────────────────────────────────────┐
│  Built with ❤️ using Pure Java        │
│  No external dependencies              │
│  100% from scratch implementation      │
└────────────────────────────────────────┘
```
