# Banking Management System
A full-stack **Spring Boot** web application that simulates core online banking operations and provides role-based access for administrators and customers. 

## Project Overview
The **Banking Management System** allows users to register, create bank accounts, perform transactions, and view account details. Administrators have full control over users and accounts, while customers access only their accounts and transactions.

This project demonstrates real-world backend concepts including:

- Entity relationships
- Secure authentication
- Account creation and management
- Transaction processing
- Dashboard personalization
- Database persistence
- MVC architecture 

## Tech Stack

**Backend:**
- Java 17+
- Spring Boot
- Spring MVC
- Spring Security
- Hibernate
- JPA  

**Frontend:**
- Thymeleaf
- HTML5
-  CSS
- Bootstrap  

**Database:**
- MySQL 

**Build Tool:**
- Maven

---

## ️ How to Run Locally

### 1. Clone the project
```bash
git clone https://github.com/your-repo/banking-management-system.git
cd banking-management-system
```

### 2. Configure the database in `application.properties`
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/banking_db
spring.datasource.username=root
spring.datasource.password=yourpassword

spring.jpa.hibernate.ddl-auto=update
```


### 3. Run the application
```bash
mvn spring-boot:run
```

### 4. Open your browser at:
```
http://localhost:8080
```

---