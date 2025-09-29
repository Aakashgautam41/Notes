---
tags:
  - Java
  - Concurrency
---

---

## ==Java ExecutorService and Thread Pools==

### I. Running Tasks Asynchronously (Traditional Threads)

- In Java, it has always been easy to run a method or piece of code **asynchronously**.
- To achieve this traditionally:
    1. Create a class (e.g., `Task`) that implements `Runnable`.
    2. Override the `run` method, containing the code you want to run asynchronously.
    3. Create a `new Thread` instance using the task instance.
    4. Call `thread.start()`.
- **Visualization:** The main thread performs its operations, and at a certain point, `thread.start` is executed. Java creates a separate thread (e.g., `thread-0`) that runs its own operations. Once those operations are done, Java kills that thread, and the main thread continues.

### II. The Problem with Creating Many Threads

- If you want to run ten tasks, you can use a `for` loop to create ten threads (T0 to T9), each with a new instance of the task. Java will kill these threads once their operations are complete.
- The problem escalates when running a large number of tasks (e.g., 1,000 tasks) asynchronously.
- **Cost of Threads:** Creating a thread in Java is an **expensive operation**.
- **OS Correspondence:** In Java, **one Java thread corresponds to one operating system (OS) thread**. Running 1,000 tasks via traditional methods would create 1,000 OS threads (T0 to T999).

### III. Introduction to Thread Pools (ExecutorService)

- To solve the problem of expensive thread creation, a **thread pool** is used.
- A thread pool is a **fixed number of threads** that are created upfront.
- You submit tasks (e.g., 1,000 tasks) to the pool.
- The threads pick up and complete tasks sequentially (e.g., Thread T0 picks up Task 1, completes it, and immediately starts with Task 2).

### IV. How the ExecutorService Works

- **Creation:** You create a new fixed thread pool using the **static method** of the `Executors` class, specifying the desired number of threads (e.g., 10). This returns the instance variable for the **`ExecutorService`**.
- **Task Submission:** Instead of starting a new thread, you use the `ExecutorService` instance to **submit** or **execute** new tasks.
- **Internal Mechanism:**
    - The thread pool `ExecutorService` internally uses a **blocking queue**.
    - All submitted tasks are stored in this queue.
    - The fixed number of threads (e.g., T0 to T9) perform the same two steps repeatedly:
        1. Fetch the next task from the queue.
        2. Execute the task.
    - **Thread Safety:** Since all threads attempt to take the task from the queue concurrently, the queue must be thread-safe. This is why the thread pool uses a **blocking queue**.

### V. Determining Ideal Thread Pool Size

The ideal pool size is not simple; it **depends on the type of tasks** being executed.

#### A. CPU Intensive Operations

- **Definition:** Tasks that require a lot of CPU time, such as cryptographic functions or algorithms to create a hash.
- **Challenge:** If your CPU has, for instance, four cores, you can only have four threads running at a time. If you have more threads than cores, the CPU will use **time-split scheduling**, switching execution time between threads, which is not ideal for CPU-intensive tasks.
- **Ideal Pool Size:** The size should generally be the **same number as the number of cores** available in the CPU (e.g., four threads: T0, T1, T2, T3). This allows all cores to run these threads concurrently in an ideal scenario.
- **Implementation:** You can get the CPU core count programmatically using `Runtime.getRuntime().availableProcessors()` and pass this count when creating the thread pool.
- **Consideration:** If the CPU is also running multiple other applications (e.g., on a server), your application might not get access to all the cores, which must be factored into the ideal size.

#### B. I/O Intensive Operations

- **Definition:** Tasks that involve waiting for operations, such as getting data from a **database call** or fetching data from an **HTTP URL/network call**.
- **Challenge:** When these requests are made, the threads wait for the OS to provide a response and enter a **waiting state**. If the pool size is too small (e.g., only four threads), and all threads are waiting, there are no threads left to fetch and execute the next task.
- **Ideal Pool Size:** If tasks are I/O intensive, you can have a **higher pool size** (e.g., 100 threads, T0 to T99). This allows other threads to remain ready to fetch and execute tasks even if many threads are currently in a waiting state.
- **Factors for Sizing (Trade-off):** The higher pool size depends on:
    1. The **rate of task submissions** (how fast you want to submit new tasks).
    2. The **average wait time** for the I/O operation to complete for each task.
- **Memory Consideration:** A trade-off is necessary because having **too many threads increases memory consumption**. You do not want too many threads blocking, nor do you want too many tasks waiting to be fetched by the threads.

