

### What is a Lambda Expression?

A **Lambda Expression** in Java is essentially a **shortcut** for writing **anonymous functions** — functions that don't belong to any class and don't need a name. Lambdas were introduced in **Java 8** to support functional programming concepts and to make code more concise, especially when working with **functional interfaces** (interfaces with a single abstract method).

In simple terms:

> **Lambda expression = a way to pass behavior (functionality) as data.**

---

### Syntax of a Lambda Expression

```java
(parameters) -> { body }
```

**Examples:**

```java
// A lambda that adds two numbers
(a, b) -> a + b

// A lambda that prints a string
s -> System.out.println(s)

// A lambda with no parameters
() -> System.out.println("Hello World")
```

---

### Parts of a Lambda:

1. **Parameters**:  
    Inside parentheses `()`. Like method parameters.
    
2. **Arrow operator**:  
    `->` separates the parameters and the body.
    
3. **Body**:  
    A single expression or a block `{}` with multiple statements.
    

---

### Key Requirements:

- **Lambda can only be used where a functional interface is expected.**
    
- A **functional interface** is an interface with exactly **one abstract method**.  
    (Examples: `Runnable`, `Comparator<T>`, `Callable<V>`, `Consumer<T>`, `Function<T,R>`, etc.)
    

Example of a functional interface:

```java
@FunctionalInterface
interface MyFunctionalInterface {
    void doSomething();
}
```

Now you can use a lambda:

```java
MyFunctionalInterface func = () -> System.out.println("Doing something");
func.doSomething(); // Output: Doing something
```

---

### Why Use Lambda Expressions?

- **Conciseness**: Reduces boilerplate code.
    
- **Readability**: Code becomes more expressive.
    
- **Better for Functional-style Programming**: Especially useful with Streams API.
    

---

### Different Forms of Lambda Expressions

|Form|Example|Description|
|:--|:--|:--|
|No parameters|`() -> System.out.println("Hi")`|No input, just run code|
|Single parameter|`x -> x * x`|One parameter, no parentheses needed|
|Multiple parameters|`(a, b) -> a + b`|Must use parentheses|
|Block body|`(a, b) -> { int sum = a+b; return sum; }`|Use `{}` for multiple lines|

---

### Examples in Action

**Using with Runnable:**

```java
Runnable r = () -> System.out.println("Running thread");
new Thread(r).start();
```

**Using with Comparator:**

```java
List<String> names = Arrays.asList("John", "Jane", "Doe");

Collections.sort(names, (a, b) -> a.compareTo(b));
```

**Using with Streams:**

```java
List<String> names = Arrays.asList("John", "Jane", "Doe");

names.stream()
    .filter(name -> name.startsWith("J"))
    .forEach(name -> System.out.println(name));
```

---

### How Lambdas Relate to Functional Interfaces Internally

When you write:

```java
Comparator<String> comp = (a, b) -> a.compareTo(b);
```

The compiler automatically turns it into an **anonymous class** equivalent behind the scenes.

---

### Important Notes

- **Type inference**: Java can often infer parameter types, so you can omit them.
    
    ```java
    (String a, String b) -> a.compareTo(b) // full form
    (a, b) -> a.compareTo(b)               // inferred
    ```
    
- **Final or Effectively Final Variables**:  
    Lambdas can capture variables from surrounding context, but those variables must be **final** or **effectively final**.
    
    Example:
    
    ```java
    int x = 10;
    Runnable r = () -> System.out.println(x); // OK
    ```
    
- **No checked exceptions unless handled**:  
    If your lambda body throws a checked exception, it must be handled inside.