
### 1. **Methods in Stream API**

The Stream API is used for functional-style operations on collections. Common methods include:

- `filter(Predicate)` – filters elements based on a condition
    
- `map(Function)` – transforms elements
    
- `distinct()` – removes duplicates
    
- `sorted()` – sorts the stream
    
- `collect(Collectors.toList())` – collects results into a list
    
- `forEach(Consumer)` – performs an action on each element
    

**Example:**

```java
List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "Bob");
List<String> unique = names.stream()
                           .distinct()
                           .collect(Collectors.toList());
```

---

### 2. **What is Optional Class?**

`Optional` is a container object that may or may not contain a non-null value. It helps prevent `NullPointerException`.

**Example:**

```java
Optional<String> name = Optional.ofNullable(null);
System.out.println(name.orElse("Default"));
```

**Key methods:** `isPresent()`, `ifPresent()`, `orElse()`, `orElseGet()`, `orElseThrow()`

---

### 3. **What is a Functional Interface?**

A functional interface has exactly one abstract method. It can have multiple default or static methods. It enables usage of lambdas.

**Example:**

```java
@FunctionalInterface
interface MyFunction {
    void execute();
}
```

---

### 4. **Default Scope of Spring Bean**

The default scope is `singleton`, meaning one shared instance per Spring container.

---

### 5. **What is Global Exception?**

A way to handle exceptions globally in Spring Boot using `@ControllerAdvice` and `@ExceptionHandler`.

**Example:**

```java
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAll(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
}
```

---

### 6. **Finally Block**

A `finally` block always executes after `try`/`catch`, regardless of exceptions or `return` statements.

---

### 7. **Finalize Block**

`finalize()` is a method called by the garbage collector before an object is deleted. Deprecated after Java 9 due to unpredictability.

---

### 8. **Return in Catch – Will Finally Run?**

Yes, the `finally` block executes even if there is a return in the `catch` block.

**Example:**

```java
try {
    throw new Exception("Error");
} catch(Exception e) {
    return;
} finally {
    System.out.println("Finally runs");
}
```

---

### 9. **Exception Handling in Spring Boot**

- Use `@ControllerAdvice` and `@ExceptionHandler`
    
- Use `@ResponseStatus`
    
- Use `ResponseStatusException`
    

---

### 10. **Custom Exception Example**

```java
public class EmployeeNotFoundException extends RuntimeException {
    public EmployeeNotFoundException(String message) {
        super(message);
    }
}
```

---

### 11. **@Controller vs @RestController**

|@Controller|@RestController|
|---|---|
|Returns views (JSP, HTML)|Returns JSON/XML|
|Part of MVC|REST APIs|
|Uses `@ResponseBody` explicitly|Has it by default|

---

### 12. **HashMap vs HashSet**

|HashMap|HashSet|
|---|---|
|Stores key-value pairs|Stores unique elements|
|Implements Map|Implements Set|
|One null key allowed|One null element allowed|

---

### 13. **What is TreeSet?**

A `TreeSet` is a sorted set that stores unique elements in natural order (or using a custom comparator).

---

### 14. **DDL vs DML**

|DDL|DML|
|---|---|
|Data Definition Language|Data Manipulation Language|
|CREATE, ALTER, DROP|INSERT, UPDATE, DELETE|
|Affects structure|Affects data|

---

### 15. **2nd Highest Salary Query**

```sql
SELECT MAX(salary) FROM employee
WHERE salary < (SELECT MAX(salary) FROM employee);
```

---

### 16. **Comparable vs Comparator**

|Comparable|Comparator|
|---|---|
|`compareTo()`|`compare()`|
|Natural ordering|Custom ordering|
|Same class|Separate class|

---

### 17. **Common Design Patterns**

- **Creational**: Singleton, Factory, Builder
    
- **Structural**: Adapter, Decorator
    
- **Behavioral**: Strategy, Observer, Command
    

---

### 18. **DROP vs DELETE in SQL**

|DROP|DELETE|
|---|---|
|Removes entire table|Removes rows|
|Irreversible|Can be rolled back|

---

### 19. **Get Distinct Employees**

Override `equals()` and `hashCode()` based on name, dept, and salary.

```java
Set<Employee> unique = new HashSet<>(employees);
```

Or with streams:

```java
List<Employee> unique = employees.stream().distinct().collect(Collectors.toList());
```

---

### 20. **equals() and hashCode()**

- `equals()`: checks logical equality
    
- `hashCode()`: used in hash-based collections
    

If `equals()` is true, `hashCode()` must be the same.

---

### 21. **Java 8 vs 17 vs 21**

|Feature|Java 8|Java 17|Java 21|
|---|---|---|---|
|Lambda & Stream|✅|✅|✅|
|Records|❌|✅|✅|
|Pattern Matching|❌|Preview|✅|
|Virtual Threads|❌|❌|✅|
|Text Blocks|❌|✅|✅|

---

This document will help you revise answers and concepts with clarity. You can ask to expand or quiz on any topic from here!