---
tags:
  - Java
  - Concurrency
---

A **`ReentrantLock`** is a class in Java's `java.util.concurrent.locks` package that provides a more flexible and feature-rich way to control thread synchronization than the traditional `synchronized` keyword.

It is a mutual exclusion lock, meaning it controls access to a **critical section** of code, ensuring that only one thread can execute that code at a time to prevent **race conditions**.

---

## Key Features

The name breaks down into its two main properties:

1. **Lock:** It is a mutual exclusion mechanism, meaning threads must call the `lock()` method before accessing shared resources and the **`unlock()`** method afterward, typically in a **`finally`** block to guarantee release.
    
2. **Reentrant:** This is the most defining characteristic. It means a thread that has successfully acquired the lock can **acquire it again multiple times** without blocking itself. An internal count (hold count) tracks how many times the owning thread has locked the resource. The lock is only fully released when the thread has called `unlock()` the same number of times it called `lock()`. 1This prevents a single thread from deadlocking itself when a method that holds a lock calls another method that tries to acquire the same lock.
    

---

## Why Use It Over `synchronized`?

While the `synchronized` keyword is simpler to use, a `ReentrantLock` provides extended capabilities, giving you more control over the locking process:

|Feature|`ReentrantLock`|`synchronized` Keyword|
|---|---|---|
|**Basic Control**|**Explicit** methods (`lock()` and `unlock()`)|**Implicit** (controlled by JVM upon entering/exiting block/method)|
|**Interruptible Wait**|**Yes** (`lockInterruptibly()`), a waiting thread can be interrupted.|**No**, a waiting thread will wait indefinitely and cannot be interrupted.|
|**Timed Wait**|**Yes** (`tryLock(timeout)`), allowing a thread to give up waiting after a certain period.|**No**|
|**Try Lock**|**Yes** (`tryLock()`), allows a thread to attempt to acquire the lock without blocking.|**No**|
|**Fairness**|**Optional** (configurable in constructor), ensures the longest-waiting thread gets the lock next.|**No** (unfair by default), any waiting thread may acquire the lock.|
|**Conditions**|**Yes** (via `newCondition()`), allows creation of multiple waiting sets for more complex thread communication.|**No**, only a single `wait()`/`notify()` set per object's intrinsic lock.|

The choice between them depends on complexity: use `synchronized` for simple locking, and use `ReentrantLock` when you need advanced features like interruptible waiting or fairness.

You can get a better understanding of the comparison between ReentrantLock and synchronized in this video: Java ReentrantLock - fairness, tryLock and more.

---
Simple example of a **`ReentrantLock`** in Java. This code shows a basic shared resource (a counter) that multiple threads try to update simultaneously, and how the lock is used to prevent a race condition.

### Simple `ReentrantLock` Example (Java)

Java

```
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockExample {

    private int count = 0;
    // 1. Create a ReentrantLock instance
    private final ReentrantLock lock = new ReentrantLock();

    public void increment() {
        // 2. Acquire the lock (block until it's available)
        lock.lock(); 
        try {
            // This is the CRITICAL SECTION: only one thread can be here at a time.
            count++;
            System.out.println(Thread.currentThread().getName() + " incremented counter to: " + count);
        } finally {
            // 3. MUST release the lock, even if an exception occurs
            lock.unlock(); 
        }
    }

    public int getCount() {
        return count;
    }

    public static void main(String[] args) throws InterruptedException {
        ReentrantLockExample example = new ReentrantLockExample();

        // Create two threads that will both call increment()
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                example.increment();
            }
        }, "Thread-1");

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                example.increment();
            }
        }, "Thread-2");

        t1.start();
        t2.start();

        // Wait for both threads to finish
        t1.join();
        t2.join();

        // The expected result is 2000 (1000 + 1000)
        System.out.println("\nFinal count: " + example.getCount());
    }
}
```

### Explanation of the Steps

1. **Creation:** We initialize a `ReentrantLock` object: `private final ReentrantLock lock = new ReentrantLock();`.
    
2. **Acquiring the Lock:** The `lock.lock()` call ensures that only one thread can proceed past this line. If another thread already holds the lock, the current thread stops and waits.
    
3. **Critical Section:** The shared resource (`count++`) is placed inside the `try` block. This is the code that needs protection from race conditions.
    
4. **Releasing the Lock:** The `lock.unlock()` call is placed inside the **`finally`** block. This is absolutely critical. It ensures that the lock is released (made available for waiting threads) even if an exception occurs during the execution of the critical section. If you forget to release the lock, the program can **deadlock**, and all other waiting threads will be stuck forever.
    

When you run this code, the final count will reliably be **2000**, because the `ReentrantLock` guarantees that the `count++` operation is **atomic** (uninterruptible).