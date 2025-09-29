---
tags:
  - Java
  - Concurrency
---
---

### **Definition**

A **BlockingQueue** is a special kind of **queue** (from `java.util.concurrent`) that **supports operations which wait** if the queue is either:

- **Empty** → consumers wait until elements are available.
    
- **Full** → producers wait until space is available.
    

👉 It’s often used in **Producer–Consumer** scenarios.

---

### **Key Characteristics**

1. **Thread-Safe**
    
    - No need for explicit synchronization (unlike `LinkedList` or `ArrayList` when shared across threads).
        
2. **Blocking Operations**
    
    - Producers can wait when queue is full.
        
    - Consumers can wait when queue is empty.
        
3. **No null elements allowed**
    
    - Adding `null` throws `NullPointerException`.
        

---

### **Important Methods**

BlockingQueue defines methods beyond a normal queue:

|Operation|Throws Exception|Special Value|Blocks|Times Out|
|---|---|---|---|---|
|**Insert**|`add(e)`|`offer(e)` (false if full)|`put(e)` (waits if full)|`offer(e, timeout, unit)`|
|**Remove**|`remove()`|`poll()` (null if empty)|`take()` (waits if empty)|`poll(timeout, unit)`|
|**Examine**|`element()`|`peek()`|N/A|N/A|

---

### **Common Implementations**

1. **`ArrayBlockingQueue`** – bounded, backed by an array (fixed capacity).
    
2. **`LinkedBlockingQueue`** – optionally bounded, backed by linked nodes (often higher throughput than array-based).
    
3. **`PriorityBlockingQueue`** – unbounded, orders elements according to natural ordering or comparator.
    
4. **`DelayQueue`** – elements become available only after a delay.
    
5. **`SynchronousQueue`** – capacity = 0, producer and consumer meet directly (hand-off).
    

---

### **Example: Producer–Consumer with BlockingQueue**

```java
import java.util.concurrent.*;

class ProducerConsumerExample {
    public static void main(String[] args) {
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(5);

        // Producer
        Runnable producer = () -> {
            try {
                for (int i = 1; i <= 10; i++) {
                    System.out.println("Produced: " + i);
                    queue.put(i); // waits if full
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };

        // Consumer
        Runnable consumer = () -> {
            try {
                for (int i = 1; i <= 10; i++) {
                    int val = queue.take(); // waits if empty
                    System.out.println("Consumed: " + val);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };

        new Thread(producer).start();
        new Thread(consumer).start();
    }
}
```

🔎 **What happens here?**

- Producer adds 10 items. If queue is full (capacity = 5), `put()` **blocks** until consumer removes something.
    
- Consumer waits on `take()` if queue is empty.
    

---

### **Where It’s Used**

- **Producer–Consumer pattern** (thread-safe data exchange).
    
- **Thread pools / Work queues** (e.g., in `ExecutorService`).
    
- **Rate limiting** or **task scheduling**.
    

---

### **Interview Tip**

If asked _"Why BlockingQueue over synchronized collections?"_ → Answer:  
👉 Because **BlockingQueue handles both synchronization and blocking elegantly**, avoiding manual `wait()`/`notify()` boilerplate. It’s higher-level and less error-prone.

---

⚡ Quick Mnemonic:  
**BlockingQueue = Queue + Thread-Safety + Waiting**

---

# 📌 **SynchronousQueue in Java**

### **Definition**

A **SynchronousQueue** is a special kind of `BlockingQueue` **with zero capacity**.

- It **cannot hold any element**.
    
- Each `put()` must **wait for a `take()`**, and each `take()` must **wait for a `put()`**.
    

👉 Think of it as a **direct handoff** between producer and consumer.  
That’s why it’s sometimes called a **handoff queue**.

---

### **Key Characteristics**

1. **Capacity = 0**
    
    - Unlike other queues, it never stores elements.
        
2. **Thread-Safe Handoff**
    
    - Producer thread and consumer thread must rendezvous for data transfer.
        
3. **Fairness**
    
    - `SynchronousQueue` can be created in **fair mode** (FIFO) or **unfair mode** (LIFO).
        
    
    ```java
    BlockingQueue<String> queue = new SynchronousQueue<>(true); // fair
    ```
    
4. **Use Case**
    
    - Used when you want threads to exchange data **one-to-one without buffering**.
        

---

### **Example**

```java
import java.util.concurrent.*;

public class SynchronousQueueExample {
    public static void main(String[] args) {
        BlockingQueue<String> queue = new SynchronousQueue<>();

        // Producer thread
        new Thread(() -> {
            try {
                System.out.println("Producer is trying to put an item...");
                queue.put("Hello"); // blocks until consumer takes
                System.out.println("Producer successfully handed off item!");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();

        // Consumer thread
        new Thread(() -> {
            try {
                Thread.sleep(2000); // simulate delay
                System.out.println("Consumer is ready to take...");
                String item = queue.take(); // blocks until producer puts
                System.out.println("Consumer got: " + item);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }
}
```

**Output (timing may vary):**

```
Producer is trying to put an item...
Consumer is ready to take...
Producer successfully handed off item!
Consumer got: Hello
```

👉 Notice how `put()` blocks until the consumer calls `take()`.

---

### **Real-World Uses**

1. **Thread Pools (`Executors.newCachedThreadPool()`)**
    
    - Uses `SynchronousQueue` internally.
        
    - When a new task is submitted:
        
        - If a thread is available → task is handed off immediately.
            
        - If not → new thread is created.
            
    - This is why cached thread pools can grow **unbounded**.
        
2. **Message Passing Between Threads**
    
    - Directly handing data from producer → consumer without buffering.
        
3. **Load Balancing**
    
    - Useful when you want to avoid queues piling up and force consumers to keep up with producers.
        

---

### **Difference from Other BlockingQueues**

|Queue Type|Stores Data?|Example|
|---|---|---|
|**ArrayBlockingQueue**|Yes (fixed capacity)|Bounded producer-consumer|
|**LinkedBlockingQueue**|Yes (optional capacity, usually large)|Task queues|
|**SynchronousQueue**|❌ No|Direct handoff|

---

### **Interview Tip**

If asked _"Why would you ever use a queue with zero capacity?"_ → Answer:  
👉 To enforce **direct synchronization** between producer and consumer. It’s perfect when you want no buffering, immediate processing, and strict handoff semantics (like in `CachedThreadPool`).

---

⚡ Mnemonic:  
**SynchronousQueue = "Queue with no queue" → only handshakes.**
