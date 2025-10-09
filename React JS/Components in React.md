---
tags:
  - React
---
---
## 📌 What are Components in React?

- A **component** in React is a **reusable, independent piece of UI**.
    
- You can think of it as a **JavaScript function** (or class) that:
    
    - Accepts **input** (called **props**).
        
    - Returns a **UI element** (JSX).
        

👉 Components help split the UI into **small, manageable pieces** instead of writing one giant HTML page.

---

## 🛠 Types of Components in React

### 1. **Functional Components** (Modern & Preferred)

- Simple JavaScript functions.
    
- Take `props` as arguments.
    
- Return JSX.
    

```jsx
function Welcome(props) {
  return <h1>Hello, {props.name} 👋</h1>;
}

// Usage
<Welcome name="Carl" />
```

---

### 2. **Class Components** (Older, but still used in legacy projects)

- Defined as ES6 classes.
    
- Have lifecycle methods (`componentDidMount`, etc.).
    
- Use `this.props` and `this.state`.
    

```jsx
class Welcome extends React.Component {
  render() {
    return <h1>Hello, {this.props.name} 👋</h1>;
  }
}
```

---

## 🔑 Key Points About Components

1. **Reusable** → You can use the same component in multiple places.
    
2. **Composable** → Components can be combined to build complex UIs.
    
3. **Isolated** → Each component manages its own logic and styling.
    
4. **Data Flow** → Props are passed **downward** (parent → child).
    

---

## 📊 Example: Breaking UI into Components

Imagine you’re building a **Profile Card**.

Instead of one big block of HTML, you split it:

- `<ProfileCard />`
    
    - `<Avatar />`
        
    - `<UserInfo />`
        
    - `<FollowButton />`
        

This makes code cleaner, easier to maintain, and **reusable**.

---

✅ **In short:**  
Components are the **building blocks of React apps**. Each component is like a Lego brick — small, independent, and reusable. Together, they build your full UI.

---

# 🟦 **Stateful vs Stateless Components in React**

---

## 1. **Stateless Components**

- These are **presentational components**.
    
- They **do not manage state** internally.
    
- They just take **props as input** and return UI.
    

👉 Think of them like **pure functions** — input in, output out.

### Example:

```jsx
function Greeting(props) {
  return <h1>Hello, {props.name} 👋</h1>;
}

// Usage
<Greeting name="Carl" />
```

- Doesn’t store any data itself.
    
- Always depends on parent’s input (`props`).
    

---

## 2. **Stateful Components**

- These components **hold and manage state** (data that changes over time).
    
- They **control behavior + UI changes**.
    
- Can be implemented using **useState (Hooks)** in functional components or `this.state` in class components.
    

### Example (Functional with Hooks):

```jsx
import React, { useState } from "react";

function Counter() {
  const [count, setCount] = useState(0);

  return (
    <>
      <h2>Count: {count}</h2>
      <button onClick={() => setCount(count + 1)}>Increment</button>
    </>
  );
}
```

- Manages its own state (`count`).
    
- UI updates automatically when state changes.
    

---

## 🗂️ Key Differences (Interview Style)

|Feature|Stateless Component|Stateful Component|
|---|---|---|
|**State Management**|No state, only props|Holds & manages its own state|
|**Purpose**|UI (presentational)|Logic + behavior + UI|
|**Reusability**|More reusable|Less reusable, specific|
|**Performance**|Faster (no state updates)|Slightly slower due to state handling|
|**Examples**|Button, Header, Avatar|Counter, Form, Modal with toggle|

---

## ✅ How to Answer in Interview

> “In React, stateless components are those that don’t manage internal state — they only rely on props and are mostly used for UI rendering. Stateful components, on the other hand, maintain and update their own state, making them suitable for dynamic and interactive features. For example, a `Greeting` component that just displays a name is stateless, while a `Counter` component that increments a number is stateful.”

---

⚡ Pro Tip:  
Since React **Hooks** (from v16.8), we mostly write **functional components**. A functional component can be either **stateless** or **stateful** depending on whether we use `useState` (or other hooks).

---

# 🟦 **Controlled vs Uncontrolled Components in React**

---

## 1. **Controlled Components**

- A **controlled component** is one where **form data is controlled by React state**.
    
- The value of the input is bound to a state variable, and any change updates the state.
    
- React has **full control** over the form data.
    

👉 You always know what’s inside your input because it’s stored in **state**.

### Example: Controlled Input

```jsx
import React, { useState } from "react";

function ControlledForm() {
  const [name, setName] = useState("");

  const handleSubmit = (e) => {
    e.preventDefault();
    alert(`Submitted name: ${name}`);
  };

  return (
    <form onSubmit={handleSubmit}>
      <input 
        type="text" 
        value={name} 
        onChange={(e) => setName(e.target.value)} 
      />
      <button type="submit">Submit</button>
    </form>
  );
}
```

- `value` is linked to `state` (`name`).
    
- `onChange` keeps state and input in sync.
    

---

## 2. **Uncontrolled Components**

- An **uncontrolled component** is one where form data is handled by the **DOM itself**, not React.
    
- You access the value using **refs** instead of state.
    
- React does **not track the input value** on every keystroke.
    

👉 Think of it as **“React says: you (DOM) manage it, I’ll just read it when I need.”**

### Example: Uncontrolled Input

```jsx
import React, { useRef } from "react";

function UncontrolledForm() {
  const inputRef = useRef(null);

  const handleSubmit = (e) => {
    e.preventDefault();
    alert(`Submitted name: ${inputRef.current.value}`);
  };

  return (
    <form onSubmit={handleSubmit}>
      <input type="text" ref={inputRef} />
      <button type="submit">Submit</button>
    </form>
  );
}
```

- Input’s value is not in React state.
    
- We use `ref` to get the value directly from the DOM.
    

---

## 🗂️ Key Differences (Interview Style)

|Feature|Controlled Component|Uncontrolled Component|
|---|---|---|
|**Form Data Source**|Stored in React state|Stored in DOM|
|**Accessing Value**|`state` + `onChange`|`ref.current.value`|
|**React Involvement**|React controls input|React just reads input when needed|
|**Validation**|Easy (via state)|Harder (manual DOM checks)|
|**Performance**|More renders (state updates)|Faster (DOM handles input)|
|**Use Case**|Login forms, validations|Simple forms, quick prototyping|

---

## ✅ How to Answer in Interview

> **“A controlled component is one where the form inputs are tied to React state, so React has complete control over the data. For example, a login form where inputs are stored in state. An uncontrolled component, on the other hand, relies on the DOM to manage its state, and we usually access values via refs. Controlled components are preferred when we need validation, dynamic rendering, or instant feedback, while uncontrolled ones are simpler and require less boilerplate.”**

---

⚡ Pro Tip:

- In **real-world apps**, controlled components are the **standard**.
    
- But **uncontrolled components** are useful in cases like **file uploads**, **third-party integrations**, or when performance is critical.
    

