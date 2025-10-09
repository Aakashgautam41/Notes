Let’s go **deep into Functional Interfaces** in Java:

---

## What is a Functional Interface?

A **Functional Interface** in Java is simply an **interface** that contains **exactly one abstract method**.

- It **may** also contain **default methods** and **static methods** (with a body), but **only one abstract method** is allowed.
    

In short:

> **Functional Interface = Interface with only one abstract method** (SAM - _Single Abstract Method_).

---

## Why Functional Interfaces?

They were introduced mainly to enable the use of **Lambda Expressions**.  
Lambda expressions can be used wherever a **functional interface** is expected — meaning they provide the implementation of the abstract method **on the fly**.

---

## Example of a Simple Functional Interface:

```java
@FunctionalInterface
interface GreetingService {
    void sayHello(String message);
}
```

You can use it with a lambda like:

```java
GreetingService greet = message -> System.out.println("Hello " + message);
greet.sayHello("World");
```

---

## The `@FunctionalInterface` Annotation

- It’s **optional**, but **recommended**.
    
- It tells the compiler:
    
    > "This interface must have exactly **one** abstract method. If not, throw a compile-time error."
    

**Example:**

```java
@FunctionalInterface
interface MyFunction {
    int operate(int a, int b);
}
```

If you mistakenly add a second abstract method, the compiler will complain.

---

## Functional Interface Rules

|Rule|Description|
|:--|:--|
|Only 1 abstract method|Must have exactly **one** abstract method.|
|Can have default/static methods|Can include **default** and **static** methods with a body.|
|Object class methods don’t count|Methods like `equals()`, `hashCode()`, `toString()` from `java.lang.Object` are **not** considered.|

---

## Some Predefined Functional Interfaces (Java 8)

Java 8 provides many **built-in** functional interfaces in the `java.util.function` package.

|Interface|Purpose|Example|
|:--|:--|:--|
|**Predicate**|Takes one argument, returns boolean|`x -> x > 10`|
|**Function<T, R>**|Takes one argument, returns another type|`x -> x.length()`|
|**Consumer**|Takes one argument, returns nothing (void)|`x -> System.out.println(x)`|
|**Supplier**|Takes no argument, returns a value|`() -> Math.random()`|
|**BinaryOperator**|Two inputs of same type, returns same type|`(a, b) -> a + b`|
|**UnaryOperator**|One input, returns same type|`s -> s.toUpperCase()`|

---

## Example: Custom Functional Interface with Default and Static Methods

```java
@FunctionalInterface
interface Calculator {
    int calculate(int a, int b);

    // default method
    default void show() {
        System.out.println("Calculator functional interface");
    }

    // static method
    static void info() {
        System.out.println("Static method inside interface");
    }
}
```

**Usage:**

```java
Calculator add = (a, b) -> a + b;
System.out.println(add.calculate(5, 3)); // Output: 8

add.show();            // Output: Calculator functional interface
Calculator.info();     // Output: Static method inside interface
```

---

## Common Real-world Use Cases

- **Thread creation** using `Runnable`
    
- **Event handling** in GUI programming (listeners)
    
- **Collections sorting** with `Comparator`
    
- **Stream API** operations like `filter`, `map`, `forEach`
    

---

## Quick Visual Summary:

```
@FunctionalInterface
      |
      V
Single Abstract Method (SAM)
      |
Optional: default methods, static methods
      |
Used in: Lambdas, Streams, Asynchronous tasks
```

---

## Important Note:

- **Inheritance**: If a functional interface extends another interface, it must also ensure that only **one abstract method** remains.
    
- **Method Overloading**: You cannot overload abstract methods within a functional interface.
    

---

# Final Example - Runnable

**Runnable** is a classic functional interface:

```java
@FunctionalInterface
public interface Runnable {
    void run();
}
```

Lambda usage:

```java
Runnable r = () -> System.out.println("Running in a thread");
new Thread(r).start();
```

---
