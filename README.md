# StackSplit - Orders Service 📦

This is the **Orders microservice** of the shopping cart web application.

It handles all operations related to **products**, **cart**, **checkout**, and **orders** in the microservices-based eCommerce system. Built with **Spring Boot**, **JPA**, and **PostgreSQL**, this service operates independently and communicates with other services like `auth-service` and `delivery-service`.

> 🧩 Part of a 3-tier microservices architecture:
> - `auth-service` – for authentication & user management  
> - `orders-service` – for shopping/cart/checkout logic *(this repo)*  
> - `delivery-service` – for order delivery tracking  
> - `frontend` – built in React.js

---

## 🔧 Prerequisites

Make sure these are installed before running this service:

- [Java JDK 17+](https://adoptium.net/)
- [Maven](https://maven.apache.org/)
- [PostgreSQL](https://www.postgresql.org/)
- [Git](https://git-scm.com/)

---

## ⚙️ Technologies Used

- Java 17  
- Spring Boot  
- Spring Web  
- Spring Data JPA  
- PostgreSQL  
- Maven

---

## 📁 Folder Structure (Example)

```
orders-service/
├── controller/ # REST controllers (Product, Cart, Order APIs)
├── service/ # Business logic
├── repository/ # JPA repositories
├── model/ # Entities: Product, Cart, Order
├── dto/ # Data transfer objects
├── config/ # CORS, Swagger, or App config
├── util/ # Helper utilities (mappers, constants)
├── OrdersApplication.java
└── application.properties
```

---

## 🗃️ Database Configuration

Create a PostgreSQL database (e.g., `orders_db`) and update the following in `application.properties`:

```properties
server.port=8082

spring.datasource.url=jdbc:postgresql://localhost:5432/orders_db
spring.datasource.username=your_db_username
spring.datasource.password=your_db_password
spring.jpa.hibernate.ddl-auto=update
```

---

##🚀 Running the Service

```
git clone https://github.com/himanshujha411/Microservices-Orders-Mgmt.git
cd Microservices-Orders-Mgmt

./mvnw spring-boot:run
```
