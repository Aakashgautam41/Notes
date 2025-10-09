**Synchronous service-to-service communication** is the backbone of microservice interaction (e.g., via REST using `RestTemplate` or `WebClient`).

---

## 🧩 Goal

You’ll create **two Spring Boot microservices**:

1. **`order-service`** → the **client** that calls another API.
    
2. **`payment-service`** → the **server** that responds to the request.
    

You’ll implement synchronous communication using:

- ✅ `RestTemplate` (classic)
    
- ✅ `WebClient` (modern, reactive & non-blocking)
    

---

## 🏗 Step 1: Project Structure

```
synchronous-demo/
 ├── order-service/
 └── payment-service/
```

---

## 💳 payment-service (Service B)

**Purpose:** Expose an endpoint `/payment/process` that takes order details and returns payment status.

### `pom.xml`

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
</dependencies>
```

### `PaymentServiceApplication.java`

```java
@SpringBootApplication
public class PaymentServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(PaymentServiceApplication.class, args);
    }
}
```

### `PaymentController.java`

```java
@RestController
@RequestMapping("/payment")
public class PaymentController {

    @PostMapping("/process")
    public ResponseEntity<Map<String, String>> processPayment(@RequestBody Map<String, Object> order) {
        String orderId = order.get("orderId").toString();
        Map<String, String> response = new HashMap<>();
        response.put("orderId", orderId);
        response.put("status", "PAYMENT_SUCCESS");
        response.put("transactionId", UUID.randomUUID().toString());
        return ResponseEntity.ok(response);
    }
}
```

### Run it on port `8081`

```properties
server.port=8081
```

---

## 📦 order-service (Service A)

**Purpose:** Calls `payment-service` synchronously and returns a combined response.

### `pom.xml`

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <!-- for WebClient -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-webflux</artifactId>
    </dependency>
</dependencies>
```

---

### `OrderServiceApplication.java`

```java
@SpringBootApplication
public class OrderServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
```

---

### `OrderController.java` (RestTemplate version)

```java
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private RestTemplate restTemplate;

    private final String PAYMENT_URL = "http://localhost:8081/payment/process";

    @PostMapping("/place")
    public ResponseEntity<Map<String, Object>> placeOrder(@RequestBody Map<String, Object> order) {
        ResponseEntity<Map> paymentResponse = restTemplate.postForEntity(PAYMENT_URL, order, Map.class);
        Map<String, Object> result = new HashMap<>();
        result.put("order", order);
        result.put("payment", paymentResponse.getBody());
        return ResponseEntity.ok(result);
    }
}
```

### `application.properties`

```properties
server.port=8080
```

---

## 🧪 Test in Postman

### Step 1: Start both services

- `payment-service` → port `8081`
    
- `order-service` → port `8080`
    

### Step 2: Make a request

`POST http://localhost:8080/order/place`

**Body:**

```json
{
  "orderId": "ORD123",
  "amount": 499.99
}
```

✅ You should get:

```json
{
  "order": {
    "orderId": "ORD123",
    "amount": 499.99
  },
  "payment": {
    "orderId": "ORD123",
    "status": "PAYMENT_SUCCESS",
    "transactionId": "a6d123f9-b2f7-4bde-9081-cb4e0a19bdf4"
  }
}
```

---

## ⚙️ Bonus: Using WebClient instead of RestTemplate

### Add this in `OrderController.java`

```java
@Autowired
private WebClient.Builder webClientBuilder;

@PostMapping("/place/webclient")
public ResponseEntity<Map<String, Object>> placeOrderWebClient(@RequestBody Map<String, Object> order) {
    Map paymentResponse = webClientBuilder.build()
            .post()
            .uri("http://localhost:8081/payment/process")
            .bodyValue(order)
            .retrieve()
            .bodyToMono(Map.class)
            .block(); // since synchronous

    Map<String, Object> result = new HashMap<>();
    result.put("order", order);
    result.put("payment", paymentResponse);
    return ResponseEntity.ok(result);
}
```

### Add WebClient Bean

```java
@Bean
public WebClient.Builder webClientBuilder() {
    return WebClient.builder();
}
```

---

## 🎯 Practice Tasks for You

1. ✅ Implement both APIs and test with Postman.
    
2. 🧠 Add a delay (e.g., `Thread.sleep(3000)`) in payment-service and see how the synchronous call behaves.
    
3. ⚡ Add timeout handling (using `RestTemplate` or `WebClient` timeout).
    
4. 💥 Add exception handling when the payment service is down.
    
5. 🚀 Replace hardcoded URLs with service discovery (Eureka or Consul).
    

---

Let’s walk through the **complete end-to-end sequence** step by step, from client → controller → RestTemplate → remote API → response → back to client.

## 🧩 Setup Recap

We have **two microservices**:

### 1️⃣ `order-service` → the **caller** (client)

Runs on **port 8080**

### 2️⃣ `payment-service` → the **receiver** (server)

Runs on **port 8081**

---

## ⚙️ The Code Flow (simplified)

### `OrderController.java`

```java
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private RestTemplate restTemplate;

    private final String PAYMENT_URL = "http://localhost:8081/payment/process";

    @PostMapping("/place")
    public ResponseEntity<Map<String, Object>> placeOrder(@RequestBody Map<String, Object> order) {
        ResponseEntity<Map> paymentResponse = restTemplate.postForEntity(PAYMENT_URL, order, Map.class);
        Map<String, Object> result = new HashMap<>();
        result.put("order", order);
        result.put("payment", paymentResponse.getBody());
        return ResponseEntity.ok(result);
    }
}
```

---

### `PaymentController.java`

```java
@RestController
@RequestMapping("/payment")
public class PaymentController {

    @PostMapping("/process")
    public ResponseEntity<Map<String, String>> processPayment(@RequestBody Map<String, Object> order) {
        String orderId = order.get("orderId").toString();
        Map<String, String> response = new HashMap<>();
        response.put("orderId", orderId);
        response.put("status", "PAYMENT_SUCCESS");
        response.put("transactionId", UUID.randomUUID().toString());
        return ResponseEntity.ok(response);
    }
}
```

---

## 🧠 Step-by-Step Flow of Execution

---

### 🧭 Step 1: The client sends a request

- You call this API from Postman (or frontend):
    

```
POST http://localhost:8080/order/place
```

**Request Body:**

```json
{
  "orderId": "ORD123",
  "amount": 500
}
```

---

### ⚙️ Step 2: Spring MVC handles the request (in order-service)

1. The request hits the **DispatcherServlet** (Spring’s front controller).
    
2. It looks up a matching controller based on the URL `/order/place`.
    
3. Finds `OrderController.placeOrder()` method.
    
4. **Jackson** converts the JSON body into a Java object:
    
    ```java
    Map<String, Object> order = {"orderId"="ORD123", "amount"=500.0};
    ```
    
5. The method executes.
    

---

### ⚡ Step 3: Inside `placeOrder()` — calling another API

```java
ResponseEntity<Map> paymentResponse =
    restTemplate.postForEntity(PAYMENT_URL, order, Map.class);
```

What happens here:

1. `RestTemplate` prepares an **HTTP POST** request.
    
2. The URL is `http://localhost:8081/payment/process`.
    
3. It serializes your `order` Map into JSON (using Jackson).
    
4. It sends the HTTP request to `payment-service` synchronously (blocking call).
    

🕓 At this point, `order-service` thread is **waiting** for the response.

---

### 💳 Step 4: `payment-service` receives the request

1. The request reaches **DispatcherServlet** in `payment-service`.
    
2. It maps `/payment/process` to `PaymentController.processPayment()`.
    
3. The JSON body is deserialized into a `Map<String, Object>`.
    
4. Inside the method:
    
    ```java
    String orderId = order.get("orderId").toString();
    response.put("status", "PAYMENT_SUCCESS");
    response.put("transactionId", UUID.randomUUID().toString());
    ```
    
5. A `ResponseEntity` is created and returned with the response body (as JSON).
    

---

### 📬 Step 5: Response travels back

1. The `payment-service` sends back an HTTP **200 OK** response.
    
2. Response Body (JSON):
    
    ```json
    {
      "orderId": "ORD123",
      "status": "PAYMENT_SUCCESS",
      "transactionId": "a6f9e3d2-9b12-44b5-a7f2-1f95b8e6b72a"
    }
    ```
    
3. `RestTemplate` in the `order-service` receives it.
    

---

### 🧩 Step 6: Back to `order-service`

- `RestTemplate` deserializes the JSON body into a `Map` (because we passed `Map.class`).
    
- `paymentResponse.getBody()` gives you the map:
    
    ```java
    {
      "orderId"="ORD123",
      "status"="PAYMENT_SUCCESS",
      "transactionId"="a6f9e3d2-9b12-44b5-a7f2-1f95b8e6b72a"
    }
    ```
    
- The controller combines order + payment info and sends the final response:
    

```java
{
  "order": {
    "orderId": "ORD123",
    "amount": 500
  },
  "payment": {
    "orderId": "ORD123",
    "status": "PAYMENT_SUCCESS",
    "transactionId": "a6f9e3d2-9b12-44b5-a7f2-1f95b8e6b72a"
  }
}
```

---

### 🧵 Step 7: Response back to client (Postman)

- `DispatcherServlet` in `order-service` serializes the Map into JSON.
    
- Sends HTTP **200 OK** to Postman.
    

✅ **Your synchronous round-trip is complete!**

---

## ⚙️ Behind-the-scenes timeline

|Stage|Component|Thread Behavior|
|---|---|---|
|1️⃣ Client → order-service|DispatcherServlet|Request received|
|2️⃣ order-service → payment-service|`RestTemplate`|Thread **blocks** until payment-service replies|
|3️⃣ payment-service executes|Controller|Processes and responds|
|4️⃣ payment-service → order-service|HTTP Response|Sent synchronously|
|5️⃣ order-service → Client|Final combined response|Returned after waiting|

---

## ⚡ Key Points

|Concept|Explanation|
|---|---|
|**Synchronous**|order-service waits (blocks) for payment-service response|
|**Serialization/Deserialization**|Jackson converts between JSON ↔ Java objects|
|**RestTemplate**|Handles HTTP communication, blocking|
|**DispatcherServlet**|Spring MVC front controller that routes requests|
|**ResponseEntity**|Wraps both response body and HTTP status|

---

## 🧠 Summary Diagram

```
[Postman]
   ↓
HTTP POST /order/place
   ↓
[OrderController]
   ↓
RestTemplate.postForEntity()
   ↓ (HTTP POST)
[PaymentController]
   ↓
Return payment response
   ↑
RestTemplate receives response
   ↑
Combine + return to client
   ↑
[Postman receives final JSON]
```

---
