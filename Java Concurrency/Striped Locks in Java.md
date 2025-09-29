---
tags:
  - Java
  - Concurrency
aliases:
---

---

### **Definition**

**Lock striping** is a technique where instead of using **one big lock** for a whole data structure (coarse-grained locking) or **a lock per element** (fine-grained locking), you use a **fixed number of locks ("stripes")**.  
Each element (or bucket) maps to a stripe, and only threads accessing elements within the same stripe contend for the same lock.

👉 It’s a **compromise between concurrency and memory usage**.

---

### **Why Do We Need It?**

- **Single global lock** → simple, but very low concurrency (all threads serialized).
    
- **Per-element locks** → maximum concurrency, but huge memory overhead if there are many elements.
    
- **Striped locks** → balance: limited number of locks, but still allows parallelism.
    

---

### **Example: Without Striped Locks**

```java
class SharedMap {
    private final Map<String, String> map = new HashMap<>();
    private final Object lock = new Object();

    public void put(String key, String value) {
        synchronized (lock) {
            map.put(key, value); // all threads block here
        }
    }
}
```

👉 Only one thread can modify the map at a time (coarse-grained lock).

---

### **With Striped Locks**

```java
import java.util.concurrent.locks.*;
import java.util.*;

class StripedMap {
    private static final int N_LOCKS = 16;
    private final Lock[] locks;
    private final Map<String, String> map;

    public StripedMap() {
        this.map = new HashMap<>();
        this.locks = new ReentrantLock[N_LOCKS];
        for (int i = 0; i < N_LOCKS; i++) {
            locks[i] = new ReentrantLock();
        }
    }

    private Lock getLock(Object key) {
        int hash = key.hashCode();
        return locks[Math.abs(hash % N_LOCKS)];
    }

    public void put(String key, String value) {
        Lock lock = getLock(key);
        lock.lock();
        try {
            map.put(key, value);
        } finally {
            lock.unlock();
        }
    }

    public String get(String key) {
        Lock lock = getLock(key);
        lock.lock();
        try {
            return map.get(key);
        } finally {
            lock.unlock();
        }
    }
}
```

👉 Here:

- Keys are mapped to one of 16 locks.
    
- Two threads can operate **in parallel** if they work on keys that map to different stripes.
    

---

### **Where It’s Used in Java**

- **`ConcurrentHashMap` (Java 7 and before)** → used **segment-based locking** (16 segments by default). Each segment had its own lock = lock striping.
    
- **`Striped` in Guava library (`com.google.common.util.concurrent.Striped`)** → provides a neat utility for striped locks, read/write locks, and semaphores.
    

---

### **Advantages**

✅ Higher concurrency than a single lock.  
✅ Lower memory cost than per-element locks.  
✅ Easy to implement with arrays of `ReentrantLock` or `ReadWriteLock`.

---

### **Disadvantages**

❌ More complex than a single lock.  
❌ Lock striping doesn’t guarantee perfect load distribution (hot keys may still cause contention).  
❌ Operations that span multiple keys (like iterating over the whole map) may still need to acquire **all stripes**, reducing concurrency.

---

### **Interview-Style Example**

**Q: What problem do striped locks solve compared to synchronized blocks?**

- Synchronized block → serializes all operations → low throughput.
    
- Striped locks → partition lock space → multiple threads can safely update different parts of data structure concurrently.
    

**Q: How are striped locks used in `ConcurrentHashMap`?**

- Java 7 → striped locks (segments).
    
- Java 8 → CAS-based + finer-grained per-bucket locks → even more concurrency.
    

---

⚡ Mnemonic:  
**Striped Locks = Divide data into lanes → each lane has its own lock.**
