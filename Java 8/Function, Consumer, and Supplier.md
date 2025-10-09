Perfect! Let’s explore **Function**, **Consumer**, and **Supplier** — three powerful functional interfaces in Java.

These are used in **lambda expressions**, **Streams**, and **method references**.

---

## 🧠 At a Glance

|Interface|Input|Output|Usage Example|
|---|---|---|---|
|`Function<T, R>`|✅ Yes|✅ Yes|Transforms a value|
|`Consumer<T>`|✅ Yes|❌ No|Performs an action|
|`Supplier<T>`|❌ No|✅ Yes|Supplies a value|

---

## 🔹 1. `Function<T, R>` – Transform One Value into Another

> Accepts one input, returns a result.

### Example: Convert a String to its length

```java
import java.util.function.Function;

Function<String, Integer> stringLength = s -> s.length();

int length = stringLength.apply("Hello");
System.out.println(length);  // Output: 5
```

### ✅ Use in Streams

```java
List<String> words = Arrays.asList("Java", "Stream", "API");

List<Integer> lengths = words.stream()
    .map(stringLength)
    .collect(Collectors.toList());

System.out.println(lengths);  // Output: [4, 6, 3]
```

---

## 🔹 2. `Consumer<T>` – Perform an Action, No Return

> Accepts one input, performs an action (e.g. printing), doesn't return anything.

### Example: Print each element

```java
import java.util.function.Consumer;

Consumer<String> printer = s -> System.out.println("Name: " + s);

printer.accept("Alice");  // Output: Name: Alice
```

### ✅ Use in forEach

```java
List<String> names = Arrays.asList("Alice", "Bob", "Charlie");

names.forEach(printer);

// Output:
// Name: Alice
// Name: Bob
// Name: Charlie
```

---

## 🔹 3. `Supplier<T>` – Provide a Value Without Input

> Doesn’t take input, just returns a value.

### Example: Generate a random number

```java
import java.util.function.Supplier;
import java.util.Random;

Random random = new Random();

Supplier<Integer> randomNumber = () -> random.nextInt(100);

System.out.println(randomNumber.get());  // e.g., Output: 42
```

### ✅ Use to populate a list

```java
List<Integer> randomNumbers = Stream.generate(randomNumber)
    .limit(5)
    .collect(Collectors.toList());

System.out.println(randomNumbers);  // e.g., [23, 77, 8, 92, 61]
```

---

## 🔸 Summary

|Interface|Use Case Example|
|---|---|
|`Function<T, R>`|Transform a value (`String -> Integer`)|
|`Consumer<T>`|Print/log/do something with a value|
|`Supplier<T>`|Generate data without input|
