Here are some **practice problems** involving Java Collections, focusing on **Lists**, **Sets**, **Maps**, and other related concepts. These problems will help you improve your understanding of collections while also practicing frequency counts, sorting, and other key operations.

---

### **Problem Set**

#### **1. Frequency Count**

Write a program to count the frequency of each word in a given string using a `Map`.  
**Input:** `"apple banana apple orange banana apple"`  
**Output:**

```
apple: 3  
banana: 2  
orange: 1  
```

---

#### **2. Sort a List**

Given a list of integers, sort it in:

- Ascending order
- Descending order

**Input:** `[5, 2, 8, 1, 3]`  
**Output:**

- Ascending: `[1, 2, 3, 5, 8]`
- Descending: `[8, 5, 3, 2, 1]`

---

#### **3. Remove Duplicates from a List**

Write a program to remove duplicate elements from a `List` using a `Set`.  
**Input:** `[1, 2, 2, 3, 4, 4, 5]`  
**Output:** `[1, 2, 3, 4, 5]`

---

#### **4. Find the Most Frequent Element**

Find the most frequent element in a given list of integers.  
**Input:** `[1, 3, 2, 3, 4, 1, 3]`  
**Output:**  
`Most frequent element: 3`

---

#### **5. Sort a Map by Values**

Given a `Map<String, Integer>`, sort it by values in ascending order.  
**Input:**

```
{"apple": 3, "banana": 1, "orange": 2}
```

**Output:**

```
{"banana": 1, "orange": 2, "apple": 3}
```

---

#### **6. Find Common Elements Between Two Lists**

Write a program to find common elements between two lists.  
**Input:**  
List1: `[1, 2, 3, 4, 5]`  
List2: `[4, 5, 6, 7, 8]`  
**Output:** `[4, 5]`

---

#### **7. Group Elements by Frequency**

Group integers by their frequency in a list using a `Map`.  
**Input:** `[1, 2, 2, 3, 3, 3, 4]`  
**Output:**

```
1: [1]  
2: [2]  
3: [3]  
```

---

#### **8. Sort a List of Custom Objects**

Create a class `Employee` with fields `id`, `name`, and `salary`. Sort a list of `Employee` objects by:

- Salary (ascending)
- Name (alphabetical order)

**Input:**

```
[
    Employee(1, "Alice", 50000),
    Employee(2, "Bob", 40000),
    Employee(3, "Charlie", 60000)
]
```

**Output (sorted by salary):**

```
[
    Employee(2, "Bob", 40000),
    Employee(1, "Alice", 50000),
    Employee(3, "Charlie", 60000)
]
```

---

#### **9. Find the First Non-Repeated Character**

Given a string, find the first non-repeated character using a `Map`.  
**Input:** `"swiss"`  
**Output:** `w`

---

#### **10. Priority Queue for Task Management**

Implement a program to manage tasks with priorities using a `PriorityQueue`. Each task should have a description and a priority (higher priority comes first).

**Input:**  
Add tasks:

- `Task("Do homework", 2)`
- `Task("Pay bills", 1)`
- `Task("Go to gym", 3)`

**Output (tasks in priority order):**

```
Go to gym  
Do homework  
Pay bills  
```

---

#### **11. Check if Two Lists are Anagrams**

Check if two lists of strings are anagrams of each other.  
**Input:**  
List1: `["listen", "silent", "enlist"]`  
List2: `["inlets", "tinsel", "listen"]`  
**Output:** `true`

---

#### **12. Merge Two Sorted Lists**

Given two sorted lists, merge them into a single sorted list.  
**Input:**  
List1: `[1, 3, 5]`  
List2: `[2, 4, 6]`  
**Output:** `[1, 2, 3, 4, 5, 6]`

---

#### **13. Count Unique Elements in a List**

Write a program to count the number of unique elements in a list using a `Set`.  
**Input:** `[1, 2, 2, 3, 4, 4, 5]`  
**Output:** `5`

---

#### **14. Implement LRU Cache**

Design a Least Recently Used (LRU) cache using a `LinkedHashMap`.  
**Operations:**

- `put(key, value)`
- `get(key)`

---

#### **15. Reverse a List**

Write a program to reverse a list of integers.  
**Input:** `[1, 2, 3, 4, 5]`  
**Output:** `[5, 4, 3, 2, 1]`

---

### **How to Approach**

1. Identify the best collection type for the problem (`List`, `Set`, `Map`, etc.).
2. Choose appropriate methods or algorithms:
    - Use `Collections` utility methods (`sort`, `reverse`, etc.).
    - Use Streams for modern, concise solutions.
3. Optimize for time and space complexity.

Let me know if you'd like solutions for any of these problems or additional guidance!

Here are solutions for each of the problems involving Java Collections Framework:

---

### **1. Frequency Count**

```java
import java.util.*;

public class FrequencyCount {
    public static void main(String[] args) {
        String input = "apple banana apple orange banana apple";
        String[] words = input.split(" ");
        Map<String, Integer> frequencyMap = new HashMap<>();

        for (String word : words) {
            frequencyMap.put(word, frequencyMap.getOrDefault(word, 0) + 1);
        }

        frequencyMap.forEach((key, value) -> System.out.println(key + ": " + value));
    }
}
```

---

### **2. Sort a List**

```java
import java.util.*;

public class SortList {
    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(5, 2, 8, 1, 3);

        // Ascending order
        List<Integer> ascending = new ArrayList<>(list);
        Collections.sort(ascending);
        System.out.println("Ascending: " + ascending);

        // Descending order
        List<Integer> descending = new ArrayList<>(list);
        Collections.sort(descending, Collections.reverseOrder());
        System.out.println("Descending: " + descending);
    }
}
```

---

### **3. Remove Duplicates from a List**

```java
import java.util.*;

public class RemoveDuplicates {
    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1, 2, 2, 3, 4, 4, 5);
        Set<Integer> uniqueSet = new HashSet<>(list);

        System.out.println(new ArrayList<>(uniqueSet));
    }
}
```

---

### **4. Find the Most Frequent Element**

```java
import java.util.*;

public class MostFrequentElement {
    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1, 3, 2, 3, 4, 1, 3);
        Map<Integer, Integer> frequencyMap = new HashMap<>();
        int mostFrequent = -1, maxCount = -1;

        for (int num : list) {
            frequencyMap.put(num, frequencyMap.getOrDefault(num, 0) + 1);
            if (frequencyMap.get(num) > maxCount) {
                mostFrequent = num;
                maxCount = frequencyMap.get(num);
            }
        }

        System.out.println("Most frequent element: " + mostFrequent);
    }
}
```

---

### **5. Sort a Map by Values**

```java
import java.util.*;
import java.util.stream.*;

public class SortMapByValues {
    public static void main(String[] args) {
        Map<String, Integer> map = new HashMap<>();
        map.put("apple", 3);
        map.put("banana", 1);
        map.put("orange", 2);

        Map<String, Integer> sortedMap = map.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new));

        System.out.println(sortedMap);
    }
}
```

---

### **6. Find Common Elements Between Two Lists**

```java
import java.util.*;

public class CommonElements {
    public static void main(String[] args) {
        List<Integer> list1 = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> list2 = Arrays.asList(4, 5, 6, 7, 8);

        Set<Integer> common = new HashSet<>(list1);
        common.retainAll(list2);

        System.out.println("Common elements: " + common);
    }
}
```

---

### **7. Group Elements by Frequency**

```java
import java.util.*;

public class GroupByFrequency {
    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1, 2, 2, 3, 3, 3, 4);
        Map<Integer, List<Integer>> frequencyMap = new HashMap<>();

        for (int num : list) {
            int freq = Collections.frequency(list, num);
            frequencyMap.putIfAbsent(freq, new ArrayList<>());
            if (!frequencyMap.get(freq).contains(num)) {
                frequencyMap.get(freq).add(num);
            }
        }

        frequencyMap.forEach((key, value) -> System.out.println(key + ": " + value));
    }
}
```

---

### **8. Sort a List of Custom Objects**

```java
import java.util.*;

class Employee {
    int id;
    String name;
    int salary;

    public Employee(int id, String name, int salary) {
        this.id = id;
        this.name = name;
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Employee{" + "id=" + id + ", name='" + name + '\'' + ", salary=" + salary + '}';
    }
}

public class SortEmployees {
    public static void main(String[] args) {
        List<Employee> employees = Arrays.asList(
                new Employee(1, "Alice", 50000),
                new Employee(2, "Bob", 40000),
                new Employee(3, "Charlie", 60000)
        );

        employees.sort(Comparator.comparingInt(e -> e.salary));
        System.out.println("Sorted by salary: " + employees);

        employees.sort(Comparator.comparing(e -> e.name));
        System.out.println("Sorted by name: " + employees);
    }
}
```

---

### **9. Find the First Non-Repeated Character**

```java
import java.util.*;

public class FirstNonRepeatedCharacter {
    public static void main(String[] args) {
        String str = "swiss";
        Map<Character, Integer> charCount = new LinkedHashMap<>();

        for (char c : str.toCharArray()) {
            charCount.put(c, charCount.getOrDefault(c, 0) + 1);
        }

        for (Map.Entry<Character, Integer> entry : charCount.entrySet()) {
            if (entry.getValue() == 1) {
                System.out.println("First non-repeated character: " + entry.getKey());
                break;
            }
        }
    }
}
```

---

### **10. Priority Queue for Task Management**

```java
import java.util.*;

class Task {
    String description;
    int priority;

    public Task(String description, int priority) {
        this.description = description;
        this.priority = priority;
    }

    @Override
    public String toString() {
        return description + " (Priority: " + priority + ")";
    }
}

public class TaskManager {
    public static void main(String[] args) {
        PriorityQueue<Task> taskQueue = new PriorityQueue<>(Comparator.comparingInt(t -> -t.priority));

        taskQueue.add(new Task("Do homework", 2));
        taskQueue.add(new Task("Pay bills", 1));
        taskQueue.add(new Task("Go to gym", 3));

        while (!taskQueue.isEmpty()) {
            System.out.println(taskQueue.poll());
        }
    }
}
```

---
