---
tags:
  - Java
  - Concurrency
---
---

## ==I. Data Races==

A **data race** is a condition that occurs when multiple threads are accessing a **shared variable** at the same time without any synchronization or coordination, and **at least one of those threads is writing** to that variable [1, 2]. This often leads to a possibility of memory corruption [3].

### ==A. Data Races in Java (Integers)==

*   **Java Guarantee:** The Java Language Specification (JLS), which all JVMs must implement, **mandates that any writes to variables of type integer should be Atomic** [4].
*   **Result:** If two threads attempt to update a shared integer variable simultaneously (e.g., Thread 1 setting X to 3, Thread 2 setting X to 7), the JVM will ensure that only one thread is able to set the value at a time [3, 4].
*   **Outcome:** Because of the JLS mandate, memory corruptions are generally avoided when threads simultaneously update integers [4].

### ==B. Data Race Exceptions: Longs and Doubles (Word Tearing)==

*   **Lack of Guarantee:** JLS **does not mandate Atomic rights of `long` or `double`** [4].
*   **Structure:** `Longs` and `doubles` are 64 bits and can theoretically be considered as two different variables, each of size 32 bits [4].
*   **Word Tearing:** This is the concept describing how 64-bit updates can be split [4]. It is possible that one thread updates the first 32 bits of that memory while another thread sets the next 32 bits [4].
*   **Corruption:** This can result in **memory corruption**, where the final value is a weird combination of the first 32 bits of one number and the last 32 bits of another number [4].
*   **Read/Write Scenario:** This problem can occur even if only one thread is writing and another is reading [1]. If a writing thread has only updated the first 32 bits, a reading thread might read the first 32 bits of the updated value and the last 32 bits of the *old* value, resulting in the thread reading a **corrupt value** [1].

### ==C. Practical Relevance of Data Races in Java==

*   Data race is **practically not an issue in Java in most of the cases** because the JVM provides certain guarantees to avoid them [2].

---

## ==II. Race Conditions==

A **race condition** occurs when the **output of a certain computation depends on the relative ordering of the threads** [5]. Multiple threads access a shared variable, and the resulting value will be different based on the execution order of the threads [2].

### ==A. Nature of Race Conditions==

*   **Interleaving:** The problem arises when **multiple instructions that are supposed to be executed together (atomically) are actually not run together**; instead, they are interleaved with similar instructions from other threads [5].
*   **Racing:** Threads are "racing to perform a few instructions," and the final output depends on which thread runs first compared to the others [5].

### ==B. Situation 1: Check and Update==

This scenario involves checking a state and then updating it based on that check.

*   **Example (Library System):** Using a hashmap to track loaned books (key: book ID, value: user ID) [6]. Before loaning a book, the program checks if the book is already present [6].
*   **Required Atomic Operation:** The program needs to execute the check (`if` statement) and the update (setting the value in the map) as a single, indivisible unit [6, 7].
*   **Race Scenario:** If thread scheduling is not guaranteed, Thread 1 may run only the `if` statement (finding the book absent) [5]. Then Thread 2 runs its own `if` statement (also finding the book absent) [5]. Both threads then proceed to loan out the same book to two different users, breaking the program's correctness [5].
*   **Note on Concurrent Structures:** Even if the underlying data structure is a `ConcurrentHashMap`, the race condition can still occur if the check and set operations remain two separate instructions that can be interleaved [7, 8].

### ==C. Situation 2: Read and Update==

This scenario involves performing an operation that internally requires multiple steps to read, modify, and write a value.

*   **Example (`count++`):** Incrementing a shared integer variable `count` initialized to zero [8].
*   **Underlying Instructions:** The operation `count++` is three separate instructions [8]:
    1.  Read the existing value of `count`.
    2.  Increment the value by one.
    3.  Write the updated value of `count` [8].
*   **Race Scenario:** If two threads execute `count++` simultaneously, both threads may read the initial value (zero) [9]. Both will update the value to one, and subsequently write one back, resulting in a final value of 1 when the expected correct result is 2 [9].

### ==D. Solutions to Race Conditions==

To ensure that sets of instructions are executed atomically (together):

1.  **Use Locks:** Wrap the instructions within a single shared lock (e.g., `ReentrantLock`) [7]. The JVM guarantees that only one thread can acquire the lock at a time, ensuring that all instructions within the lock are executed automatically before another thread can access them [7, 9].
2.  **Use Compound Operations:** Utilize methods in concurrent data structures that combine multiple actions into a single atomic instruction [8]. Examples include `put if absent` or `compute if absent` methods in a `ConcurrentHashMap` (for check and update scenarios) [8].
3.  **Use Atomic Variables:** For simple read/update scenarios (like incrementing), use Atomic variables (e.g., **`AtomicInteger`**) [9]. Methods like `count.increment and get` ensure that the read, update, and write operations are performed atomically [9].

---

## ==III. Summary and Contrast==

| Feature | Data Race | Race Condition |
| :--- | :--- | :--- |
| **Definition** | Multiple threads access a shared variable without synchronization, with at least one thread writing to it [2]. | Multiple threads access a variable, and the output depends on the execution order (timing) of the threads [2]. |
| **Cause** | Memory corruption, especially concerning 64-bit values like `long` or `double` [3, 4]. | Instructions that should run atomically are interleaved, breaking program correctness [5]. |
| **Java Relevance** | Practically not an issue in most cases because the JVM provides guarantees [2]. | **Very common** in multi-threaded applications [2]. |
| **Responsibility** | JVM provides guarantees to avoid them [2]. | It is **up to the developers to ensure** race conditions do not occur [2]. |

---

# 📌 Interview Questions: Data Race vs Race Condition in Java

---

### **Conceptual Questions**

1. **What is the difference between a data race and a race condition in Java?**
    
    - _Expected Answer:_
        
        - **Data Race:** Unsynchronized conflicting accesses (at least one write). Leads to visibility/stale value issues.
            
        - **Race Condition:** Logic correctness depends on execution order (e.g., `count++`).
            
        - JVM prevents memory corruption but not logical errors.
            

---

2. **Does Java guarantee atomic access to `long` and `double` variables?**
    
    - _Expected Answer:_
        
        - Before Java 5: not guaranteed (word tearing possible).
            
        - Since Java 5: guaranteed atomic reads/writes for all primitives, including `long` and `double`.
            
        - However, **atomicity ≠ visibility** (need `volatile` or synchronization).
            

---

3. **Can a data race occur if all threads are only reading a shared variable?**
    
    - _Expected Answer:_
        
        - No. Data race requires at least one write. Multiple readers are safe.
            

---

4. **What happens if you have a data race in Java according to the JMM?**
    
    - _Expected Answer:_
        
        - The program has **undefined behavior**.
            
        - JVM prevents "torn reads," but stale values and missed updates are possible.
            
        - Example: infinite loops due to cached variables in CPU registers.
            

---

5. **Is `count++` thread-safe in Java? Why or why not?**
    
    - _Expected Answer:_
        
        - No. It’s a read-modify-write sequence, not atomic.
            
        - Two threads can read the same value and overwrite each other.
            
        - Fix: use `AtomicInteger.incrementAndGet()` or synchronization.
            

---

6. **Does `ConcurrentHashMap` prevent race conditions?**
    
    - _Expected Answer:_
        
        - It prevents data races on individual operations (thread-safe get/put).
            
        - But composite actions like `if (!map.containsKey(k)) map.put(k, v)` are **not atomic** → still a race condition.
            
        - Use methods like `putIfAbsent()` or `computeIfAbsent()`.
            

---

7. **What role does `volatile` play in preventing data races?**
    
    - _Expected Answer:_
        
        - Ensures **visibility** and prevents reordering of reads/writes.
            
        - Doesn’t make compound operations atomic.
            
        - Example: `volatile boolean flag` works for signaling but not for `count++`.
            

---

---

### **Coding / Scenario Questions**

8. **Code Debugging:** What’s wrong with this code?
    
    ```java
    class Counter {
        private int count = 0;
        public void increment() {
            count++;
        }
        public int getCount() {
            return count;
        }
    }
    ```
    
    - _Answer:_ `count++` is not atomic. Multiple threads can cause lost updates. Use `AtomicInteger` or synchronization.
        

---

9. **Visibility Issue Example:**  
    Why can this program hang forever?
    
    ```java
    class Example {
        private static boolean flag = false;
        public static void main(String[] args) {
            new Thread(() -> {
                while (!flag) { }
                System.out.println("Flag set!");
            }).start();
    
            new Thread(() -> flag = true).start();
        }
    }
    ```
    
    - _Answer:_ Data race on `flag`. Without `volatile`, Thread 1 may cache `flag` and never see the update. Fix: declare `volatile`.
        

---

10. **Atomic Operation Question:**  
    How would you make this thread-safe without using `synchronized`?
    

```java
private int count = 0;
public void increment() {
    count++;
}
```

- _Answer:_ Use `AtomicInteger`:
    
    ```java
    private AtomicInteger count = new AtomicInteger(0);
    public void increment() {
        count.incrementAndGet();
    }
    ```
    

---

### **Trick / Deep-Dive Questions**

11. **Is a race condition always harmful?**
    

- _Answer:_ Not necessarily. Sometimes developers accept non-determinism (e.g., logging order). But if correctness depends on order, it’s harmful.
    

---

12. **How does the Java Memory Model define "happens-before"? Why is it relevant to data races?**
    

- _Answer:_
    
    - "Happens-before" defines visibility guarantees between threads.
        
    - If no happens-before exists between conflicting accesses, it’s a **data race**.
        
    - Example: lock release → happens-before → lock acquire.
        

---

13. **Explain with an example: Can you have a race condition without a data race?**
    

- _Answer:_ Yes.
    
    - Example: Multiple threads call `if (!map.containsKey(k)) map.put(k, v)`.
        
    - Each individual call is synchronized (no data race).
        
    - But combined logic is not atomic → race condition.
        

---

14. **What tools can detect data races in Java code?**
    

- _Answer:_
    
    - Tools: FindBugs/SpotBugs, IntelliJ inspections, race detectors like Intel Inspector or Thread Sanitizer (with JVM builds).
        
    - But most detection is through careful code reviews/testing.
        

---

15. **How do you prevent race conditions in high-performance systems without locks?**
    

- _Answer:_
    
    - Use **lock-free** or **CAS-based atomic structures** (`AtomicInteger`, `ConcurrentLinkedQueue`).
        
    - Leverage **immutable objects**.
        
    - Use **functional programming style** to avoid shared mutable state.
        

---

👉 These are **real-world interview-level questions**. They range from **junior (what is count++)** → **senior (happens-before & lock-free design)**.

Would you like me to also create a **cheat sheet of "short answers"** (like flashcards) so you can revise these quickly before interviews?