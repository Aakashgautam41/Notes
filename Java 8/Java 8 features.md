Here’s a full, clear list of the **major Java 8 features** you should know:

---

### 1. **[[Lambda Expression]]**

- Introduced a new way to represent instances of **functional interfaces** using a clean and concise syntax.
    
- **Example:**
    
    ```java
    (a, b) -> a + b
    ```
---

### 2. **[[Functional Interfaces]]**

- An interface with exactly **one abstract method**.
    
- Java 8 introduced the `@FunctionalInterface` annotation to enforce this.
    
- **Examples:** `Runnable`, `Callable`, `Comparator`, `Function`, `Consumer`.
    

---

### 3. **Streams API**

- Provides a new abstraction to process collections of objects **functionally**.
    
- Allows operations like **map**, **filter**, **reduce**, **collect**, etc.
    
- **Example:**
    
    ```java
    List<String> names = Arrays.asList("John", "Jane", "Doe");
    names.stream()
         .filter(s -> s.startsWith("J"))
         .forEach(System.out::println);
    ```
    

---

### 4. **Default Methods in Interfaces**

- Interfaces can now have **default implementations** for methods.
    
- Solves the problem of adding new methods to interfaces without breaking existing code.
    
- **Example:**
    
    ```java
    interface Vehicle {
        default void start() {
            System.out.println("Vehicle is starting");
        }
    }
    ```
    

---

### 5. **Method References**

- Shorthand for calling a method via a lambda expression.
    
- **Example:**
    
    ```java
    list.forEach(System.out::println);
    ```
    

---

### 6. **Optional Class**

- A container to **avoid `NullPointerException`**.
    
- Represents a value that **might or might not be present**.
    
- **Example:**
    
    ```java
    Optional<String> optional = Optional.ofNullable(getName());
    optional.ifPresent(System.out::println);
    ```
    

---

### 7. **New Date and Time API (java.time package)**

- A new, powerful, immutable date and time API, inspired by Joda-Time.
    
- **Key classes:** `LocalDate`, `LocalTime`, `LocalDateTime`, `Period`, `Duration`, `ZonedDateTime`.
    
- **Example:**
    
    ```java
    LocalDate today = LocalDate.now();
    LocalDate birthday = LocalDate.of(1995, 5, 23);
    Period age = Period.between(birthday, today);
    System.out.println(age.getYears());
    ```
    

---

### 8. **Collectors**

- Used with **Streams** to collect the output into a list, set, map, etc.
    
- **Example:**
    
    ```java
    List<String> namesStartingWithJ = names.stream()
                                           .filter(s -> s.startsWith("J"))
                                           .collect(Collectors.toList());
    ```
    

---

### 9. **Nashorn JavaScript Engine**

- Java 8 introduced **Nashorn**, a much-improved JavaScript engine integrated into the JVM.
    
- Allows embedding JavaScript code inside Java applications.
    

---

### 10. **Parallel Streams**

- Easily process data in parallel to leverage **multi-core processors**.
    
- **Example:**
    
    ```java
    list.parallelStream()
        .filter(e -> e.startsWith("A"))
        .forEach(System.out::println);
    ```
    

---

### 11. **CompletableFuture and Asynchronous Programming**

- Added to `java.util.concurrent` for **better support of async tasks**.
    
- **Example:**
    
    ```java
    CompletableFuture.supplyAsync(() -> "Hello")
                     .thenAccept(System.out::println);
    ```
    

---

### 12. **Other Enhancements**

- **PermGen** memory space removed; replaced with **Metaspace**.
    
- **Repeating Annotations** — You can apply the same annotation multiple times.
    
- **Type Annotations** — More places where annotations can be applied (e.g., generics).
    

---

### Quick Summary Table

| Feature               | Purpose                                 |
| :-------------------- | :-------------------------------------- |
| Lambda Expressions    | Anonymous functions                     |
| Functional Interfaces | Single abstract method interfaces       |
| Streams API           | Functional-style data processing        |
| Default Methods       | Methods with default body in interfaces |
| Method References     | Shorthand for lambda expressions        |
| Optional Class        | Avoid null pointer issues               |
| Date and Time API     | Better date/time handling               |
| Collectors            | Gather stream results                   |
| Nashorn Engine        | JavaScript execution in Java            |
| Parallel Streams      | Multi-threaded streams                  |
| CompletableFuture     | Async programming                       |

---

![Java 8 Features Mind Map](https://chatgpt.com/s/m_680fc13b4b988191953d86b34df8f6d0)