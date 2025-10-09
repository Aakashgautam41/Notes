Great question! Understanding **predicates** is key to using streams effectively in Java.

---

## 🔹 What is a Predicate in Java Streams?

A **predicate** is a **function that takes one input and returns a boolean** (`true` or `false`).

In Java, it's defined in the functional interface:

```java
@FunctionalInterface
public interface Predicate<T> {
    boolean test(T t);
}
```

---

## 🔸 Use in Streams

Predicates are **mainly used in filtering operations**. For example, in:

```java
.stream().filter(predicate)
```

You pass a predicate that says **"keep this element if this condition is true."**

---

### ✅ Example 1: Filter Even Numbers (using a Predicate)

```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

List<Integer> evenNumbers = numbers.stream()
    .filter(n -> n % 2 == 0)  // <--- this is the predicate
    .collect(Collectors.toList());

System.out.println(evenNumbers); // Output: [2, 4]
```

- `n -> n % 2 == 0` is a **lambda expression** that implements `Predicate<Integer>`.
    
- It returns `true` for even numbers.
    

---

### ✅ Example 2: Using a Predicate Variable

You can also define the predicate separately:

```java
Predicate<String> startsWithA = s -> s.startsWith("A");

List<String> names = Arrays.asList("Alice", "Bob", "Angela", "Charlie");

List<String> filtered = names.stream()
    .filter(startsWithA)
    .collect(Collectors.toList());

System.out.println(filtered); // Output: [Alice, Angela]
```

---

## 🔸 Summary

|Concept|Meaning|
|---|---|
|Predicate|A function that returns true or false|
|Used with|`.filter()`, `.removeIf()`, etc.|
|Syntax|`x -> condition`, or `Predicate<T>`|

---

Let's explore how you can **combine predicates** in Java using `.and()`, `.or()`, and `.negate()`.

These are super helpful when you want to apply **multiple conditions**.

---

## ✅ Setup

```java
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
```

---

## 🔹 1. `.and()` — Combine Conditions (AND)

### Example: Names that start with "A" **and** have length > 3

```java
List<String> names = Arrays.asList("Alice", "Ann", "Amy", "Alex", "Bob");

Predicate<String> startsWithA = s -> s.startsWith("A");
Predicate<String> longerThan3 = s -> s.length() > 3;

List<String> result = names.stream()
    .filter(startsWithA.and(longerThan3))
    .collect(Collectors.toList());

System.out.println(result);  // Output: [Alice, Alex]
```

---

## 🔹 2. `.or()` — Combine Conditions (OR)

### Example: Names that start with "A" **or** end with "y"

```java
Predicate<String> endsWithY = s -> s.endsWith("y");

List<String> result = names.stream()
    .filter(startsWithA.or(endsWithY))
    .collect(Collectors.toList());

System.out.println(result);  // Output: [Alice, Ann, Amy, Alex]
```

---

## 🔹 3. `.negate()` — Reverse the Condition

### Example: Names that **do not** start with "A"

```java
List<String> result = names.stream()
    .filter(startsWithA.negate())
    .collect(Collectors.toList());

System.out.println(result);  // Output: [Bob]
```

---

## 🔸 Bonus: Combine All

### Names that start with "A" and (end with "y" or length > 4)

```java
Predicate<String> complex = startsWithA.and(endsWithY.or(s -> s.length() > 4));

List<String> result = names.stream()
    .filter(complex)
    .collect(Collectors.toList());

System.out.println(result);  // Output: [Alice, Amy]
```
