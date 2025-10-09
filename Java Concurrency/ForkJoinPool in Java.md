---
tags:
  - Java
  - Concurrency
---

---
### **Definition**

A **ForkJoinPool** is a special implementation of `ExecutorService` introduced in **Java 7** designed for **parallelism**.

- It uses a **work-stealing algorithm** to efficiently balance tasks between worker threads.
    
- Best suited for **divide-and-conquer** problems (e.g., recursive algorithms like merge sort, parallel sums, tree traversals).
    

---

# 📌 How ForkJoinPool Works

### 1. **Task Model**

- Works with **ForkJoinTask** (instead of `Runnable` or `Callable`):
    
    - `RecursiveTask<V>` → returns a value.
        
    - `RecursiveAction` → no return value.
        
- A big task is **split (forked)** into smaller subtasks.
    
- Subtasks may be **executed in parallel**.
    
- Results are **combined (joined)**.
    

---

### 2. **Work-Stealing Algorithm**

- Each worker thread has a **double-ended queue (deque)**.
    
- A worker:
    
    1. Pushes new subtasks onto its deque.
        
    2. Executes tasks from the top of its deque.
        
    3. If it runs out of tasks, it **steals** from the _bottom_ of another worker’s deque.
        
- This ensures **better CPU utilization** (idle threads steal work).
    

---

### 3. **ForkJoinPool vs ThreadPoolExecutor**

|Feature|ThreadPoolExecutor|ForkJoinPool|
|---|---|---|
|Task Model|Runnable/Callable|ForkJoinTask (RecursiveTask/Action)|
|Scheduling|FIFO queue|Work-stealing, per-thread deque|
|Use Case|Independent tasks|Divide & conquer parallelism|
|Parallelism|Usually fixed threads|Uses all CPU cores by default|

---

# 📌 Example 1: Parallel Sum with ForkJoinPool

```java
import java.util.concurrent.*;

class SumTask extends RecursiveTask<Long> {
    private final int[] arr;
    private final int start, end;
    private static final int THRESHOLD = 5;

    SumTask(int[] arr, int start, int end) {
        this.arr = arr; this.start = start; this.end = end;
    }

    @Override
    protected Long compute() {
        if (end - start <= THRESHOLD) { // base case
            long sum = 0;
            for (int i = start; i < end; i++) sum += arr[i];
            return sum;
        } else {
            int mid = (start + end) / 2;
            SumTask left = new SumTask(arr, start, mid);
            SumTask right = new SumTask(arr, mid, end);

            left.fork();               // async execute left
            long rightResult = right.compute(); // compute right
            long leftResult = left.join();      // wait for left

            return leftResult + rightResult;
        }
    }
}

public class ForkJoinExample {
    public static void main(String[] args) {
        int[] arr = new int[20];
        for (int i = 0; i < arr.length; i++) arr[i] = i + 1;

        ForkJoinPool pool = new ForkJoinPool(); // default = #CPU cores
        long result = pool.invoke(new SumTask(arr, 0, arr.length));

        System.out.println("Sum = " + result); // 210
    }
}
```

👉 How it works:

- The array sum task is split recursively until chunks of ≤5 elements.
    
- Each chunk is computed directly.
    
- Results are combined using `join()`.
    

---

# 📌 Example 2: RecursiveAction (no return value)

```java
class PrintTask extends RecursiveAction {
    private final int start, end;
    private static final int THRESHOLD = 3;

    PrintTask(int start, int end) {
        this.start = start; this.end = end;
    }

    @Override
    protected void compute() {
        if (end - start <= THRESHOLD) {
            for (int i = start; i < end; i++) {
                System.out.println(Thread.currentThread().getName() + " prints " + i);
            }
        } else {
            int mid = (start + end) / 2;
            invokeAll(new PrintTask(start, mid), new PrintTask(mid, end));
        }
    }
}
```

---

# 📌 Key Features / Interview Points

1. **Parallelism level**
    
    - By default: number of CPU cores.
        
    - Can be set: `new ForkJoinPool(8)`.
        
2. **Work Stealing**
    
    - Increases CPU utilization, minimizes idle threads.
        
3. **Difference with `parallelStream()`**
    
    - Streams internally use **common ForkJoinPool**.
        
    - ForkJoinPool is the underlying mechanism of `parallelStream()`.
        
4. **When NOT to use**
    
    - Not good for I/O-bound tasks.
        
    - Best for CPU-intensive recursive problems.
        

---

# 📌 Interview-Style Answer

> **ForkJoinPool** is a special `ExecutorService` introduced in Java 7 that implements the **work-stealing algorithm**. It’s optimized for **divide-and-conquer tasks**, where large problems are recursively split into smaller tasks (`fork`), executed in parallel, and then combined (`join`). Each worker has its own deque of tasks, and idle workers **steal tasks** from others to balance workload. It’s the engine behind Java’s `parallelStream()`.

---

⚡ Mnemonic: **Fork → Split work, Join → Combine results.**

---

Would you like me to also draw a **diagram of the work-stealing process** (workers, deques, stealing from bottom) so you can visualize it in interviews?