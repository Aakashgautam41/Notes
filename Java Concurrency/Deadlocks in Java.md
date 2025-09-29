---
tags:
  - Java
  - Concurrency
---
---

### **Definition**

A **deadlock** occurs when two or more threads are **permanently blocked**, waiting for each other to release locks (or resources).  
👉 None of them can proceed, so the program stalls.

---

### **Classic Example**

```java
class DeadlockExample {
    private final Object lock1 = new Object();
    private final Object lock2 = new Object();

    public void method1() {
        synchronized (lock1) {
            System.out.println("Thread 1: Holding lock1...");
            try { Thread.sleep(100); } catch (InterruptedException ignored) {}

            System.out.println("Thread 1: Waiting for lock2...");
            synchronized (lock2) {
                System.out.println("Thread 1: Acquired lock2!");
            }
        }
    }

    public void method2() {
        synchronized (lock2) {
            System.out.println("Thread 2: Holding lock2...");
            try { Thread.sleep(100); } catch (InterruptedException ignored) {}

            System.out.println("Thread 2: Waiting for lock1...");
            synchronized (lock1) {
                System.out.println("Thread 2: Acquired lock1!");
            }
        }
    }
}

public class DeadlockDemo {
    public static void main(String[] args) {
        DeadlockExample example = new DeadlockExample();

        new Thread(example::method1).start();
        new Thread(example::method2).start();
    }
}
```

🔎 What happens?

- **Thread 1** locks `lock1` → waits for `lock2`.
    
- **Thread 2** locks `lock2` → waits for `lock1`.
    
- Neither releases → **deadlock**.
    

---

# 📌 How to Detect Deadlocks in Java

### 1. **Thread Dump Analysis**

- Use `jstack <pid>` or `kill -3 <pid>` to generate a **thread dump**.
    
- Deadlocks show up as:
    
    ```
    Found one Java-level deadlock:
    =============================
    "Thread-1":
      waiting to lock monitor 0x000000000f81 (object lock2),
      which is held by "Thread-2"
    "Thread-2":
      waiting to lock monitor 0x000000000f91 (object lock1),
      which is held by "Thread-1"
    ```
    

### 2. **Using `ThreadMXBean` (Java Management Extensions)**

```java
import java.lang.management.*;

public class DeadlockDetector {
    public static void main(String[] args) throws InterruptedException {
        ThreadMXBean bean = ManagementFactory.getThreadMXBean();

        while (true) {
            long[] deadlockedThreads = bean.findDeadlockedThreads();
            if (deadlockedThreads != null) {
                System.out.println("Deadlock detected!");
                ThreadInfo[] infos = bean.getThreadInfo(deadlockedThreads);
                for (ThreadInfo info : infos) {
                    System.out.println(info);
                }
                break;
            }
            Thread.sleep(1000);
        }
    }
}
```

👉 This program detects deadlocked threads at runtime.

### 3. **Monitoring Tools**

- VisualVM, JConsole, JMC (Java Mission Control) → can visualize deadlocks.
    

---

# 📌 How to Resolve Deadlocks in Java

### 1. **Prevent by Lock Ordering**

- Always acquire locks in the **same order** across threads.  
    👉 If every thread acquires `lock1` before `lock2`, deadlocks won’t happen.
    

```java
synchronized (lock1) {
    synchronized (lock2) {
        // safe
    }
}
```

---

### 2. **Use `tryLock()` with Timeout (from `ReentrantLock`)**

```java
import java.util.concurrent.locks.*;

Lock lock1 = new ReentrantLock();
Lock lock2 = new ReentrantLock();

if (lock1.tryLock(100, TimeUnit.MILLISECONDS)) {
    try {
        if (lock2.tryLock(100, TimeUnit.MILLISECONDS)) {
            try {
                // critical section
            } finally {
                lock2.unlock();
            }
        }
    } finally {
        lock1.unlock();
    }
}
```

👉 Prevents permanent blocking → avoids deadlocks.

---

### 3. **Use Higher-Level Concurrency Utilities**

- `BlockingQueue`, `ExecutorService`, `ConcurrentHashMap`, etc.
    
- These abstract away explicit lock management and reduce deadlock risks.
    

---

### 4. **Deadlock Recovery (Rare in Java Apps)**

- Detect deadlock (via `ThreadMXBean`), then **kill/restart affected threads**.
    
- Usually not practical → better to **prevent**.
    

---

# 📌 Interview-Style Summary

**Q: What is a deadlock in Java?**  
A deadlock is a situation where two or more threads are waiting for each other’s resources, resulting in a permanent stall.

**Q: How to detect deadlocks?**

- Take thread dumps (`jstack`).
    
- Use `ThreadMXBean.findDeadlockedThreads()`.
    
- Use monitoring tools like JConsole/VisualVM.
    

**Q: How to prevent or resolve deadlocks?**

- Always acquire locks in a consistent order.
    
- Use `ReentrantLock.tryLock()` with timeout.
    
- Minimize use of low-level locks → use higher-level concurrency utilities.
    

---

⚡ Mnemonic: **D-E-A-D** for Deadlocks:

- **D**etect with dumps/tools.
    
- **E**nforce lock ordering.
    
- **A**void by using concurrent utilities.
    
- **D**on’t forget tryLock with timeout.
    

---

👉 Do you want me to also show you **a real-world scenario** (like in database transaction handling or producer-consumer) where deadlock happens and how to fix it, so you can use that in interviews?