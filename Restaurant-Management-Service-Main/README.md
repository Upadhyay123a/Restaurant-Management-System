# 🍽 Restaurant Management System

![Java](https://img.shields.io/badge/Java-21-darkblue.svg)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.1.4-brightgreen.svg)
![Maven](https://img.shields.io/badge/Maven-4.0-brightgreen.svg)
![License](https://img.shields.io/badge/License-BSD_3--Clause-blue.svg)

## � Overview

A comprehensive **Restaurant Management System** built with Spring Boot that provides complete business operations for restaurant management. This system handles user authentication, food menu management, visitor tracking, and order processing with professional security and modern architecture.

## 🎯 Key Features

### 🔐 **Authentication & Security**
- **JWT-based Authentication** with secure token generation
- **Email Verification** for new user registration
- **Password Reset** with OTP functionality
- **Account Lockout** after failed login attempts
- **Role-based Access Control** (Admin/User/Visitor)
- **BCrypt Password Encryption** for secure storage

### 👥 **User Management**
- **User Registration** with email verification
- **Profile Management** with secure updates
- **Account Security** with failed attempt tracking
- **Password Policies** with strength validation

### 🍕 **Food Management**
- **Menu Item Creation** with categorization
- **Food Inventory** tracking with availability status
- **Price Management** with decimal support
- **Food Categories** (Appetizer, Main Course, Dessert, Beverage)
- **Real-time Updates** for menu changes

### 📊 **Order Management**
- **Order Placement** with food item selection
- **Order Status Tracking** (Created, Preparing, Ready, Delivered)
- **Order History** with filtering capabilities
- **Revenue Calculation** with order totals
- **Customer Order Management** with user association

### 🏪 **Visitor Management**
- **Guest Registration** with visitor categorization
- **Visitor Tracking** with timestamp logging
- **Visit History** with analytics support
- **Guest Type Management** (VIP, Regular, Delivery, Staff)
- **Check-in System** with real-time monitoring

---

## Owner

**Atul Kumar Upadhyay**  
📧 Email: atulupa12345@gmail.com

---

## Technologies Used

- **Framework:** Spring Boot 3.1.4
- **Language:** Java 21
- **Database:** MySQL 8
- **Build Tool:** Maven 4.0
- **API Documentation:** SpringDoc OpenAPI (Swagger UI)
- **Security:** Spring Security + JWT
- **Email Handling:** JavaMail API
- **Validation:** Spring Boot Starter Validation

---

## Dependencies

- `spring-boot-starter-data-jpa` – database access using Spring Data JPA
- `spring-boot-starter-web` – RESTful APIs and web support
- `mysql-connector-j` – MySQL JDBC driver
- `lombok` – reduces boilerplate code (optional)
- `spring-boot-starter-test` – for unit and integration testing
- `springdoc-openapi-starter-webmvc-ui` – Swagger UI for API documentation
- `spring-boot-starter-security` – JWT-based security and authentication
- `javax.mail` – JavaMail API for sending emails

---

## Project Structure & Flow

### Controllers

| Controller           | Functions                                                      |
|---------------------|----------------------------------------------------------------|
| `AuthController`     | - `login`: Authenticate users and return JWT token.           |
| `AdminController`    | - `addAdmin`: Add new admin.<br/> - `addFoodItem`: Add food.  |
| `UserController`     | - `signUpUser`: Register new user.<br/> - `signInUser`: Login and get JWT.<br/> - `addOrder`: Place an order.<br/> - `getAllFoodItems`: List food. |
| `VisitorController`  | - `getAllFoodItems`: List food for visitors.                  |

### Services

| Service                | Functions                                                  |
|------------------------|------------------------------------------------------------|
| `AdminService`         | Add admin and check admin existence.                       |
| `UserService`          | SignUp, SignIn, find user by email, place orders.         |
| `FoodService`          | Add food, check existence, retrieve all food items.       |
| `OrderService`         | Place and manage orders.                                   |
| `AuthenticationService`| Save and validate authentication tokens.                  |

### Repositories

| Repository          | Purpose                                         |
|--------------------|-------------------------------------------------|
| `IUserRepo`         | Manage user data.                               |
| `IAdminRepo`        | Manage admin data.                              |
| `IFoodRepo`         | Manage food items.                              |
| `IOrderRepo`        | Manage orders.                                  |
| `IAuthenticationRepo` | Manage JWT tokens and authentication details.|

---

## Security (JWT Implementation)

- JWT is used for authentication and stateless security.
- Users login via `/auth/login` and receive a JWT.
- JWT is sent in `Authorization` header for accessing protected endpoints.
- `/auth/**`, `/swagger-ui/**`, `/v3/api-docs/**` are publicly accessible.

### JWT Flow

1. **Login** → Returns JWT token.
2. **Request with JWT** → `JwtFilter` validates token.
3. **Valid Token** → User is authenticated and granted access.
4. **Invalid Token** → Access denied (HTTP 401).

---

## Database Design

### Tables

#### User Table

| Column Name  | Type           | Description                         |
|-------------|----------------|-------------------------------------|
| id          | BIGINT PK      | Unique identifier                  |
| username    | VARCHAR(255)   | User's name                        |
| password    | VARCHAR(255)   | Hashed password                    |
| email       | VARCHAR(255)   | User email                         |
| role        | VARCHAR(50)    | Admin / User / Visitor             |
| is_signed_up| BOOLEAN        | True if signed up                  |
| is_signed_in| BOOLEAN        | True if signed in                  |
| created_at  | TIMESTAMP      | Account creation timestamp         |

#### Food Item Table

| Column Name  | Type           | Description                         |
|-------------|----------------|-------------------------------------|
| id          | BIGINT PK      | Unique ID                          |
| name        | VARCHAR(255)   | Food title                          |
| description | TEXT           | Food description                    |
| price       | DECIMAL(10,2)  | Price                               |
| type        | VARCHAR(50)    | Food type (Appetizer/Main Course)  |

#### Order Table

| Column Name  | Type           | Description                          |
|-------------|----------------|--------------------------------------|
| id          | BIGINT PK      | Unique ID                             |
| user_id     | BIGINT FK      | References user                       |
| food_item_id| BIGINT FK      | References food item                  |
| status      | VARCHAR(50)    | Order status (CREATED, DELIVERED)    |
| order_date  | TIMESTAMP      | Timestamp of order                    |

---

## API Endpoints

| Method | Endpoint                     | Description                      |
|--------|-------------------------------|----------------------------------|
| POST   | `/auth/login`                 | Login user → returns JWT token   |
| POST   | `/api/users/signup`           | Register new user                |
| GET    | `/api/users/all`              | Get all users                    |
| POST   | `/api/fooditems/create`       | Add food item (Admin only)       |
| GET    | `/api/fooditems/all`          | Get all food items               |
| POST   | `/api/orders/create`          | Place new order                  |
| GET    | `/api/orders/all`             | Get all orders                   |
| POST   | `/api/visitors/signup`        | Register visitor                 |
| GET    | `/api/visitors/all`           | Get all visitors                 |

---

## Configuration (`application.properties`) 
Note: Basic Spring Security username/password has been removed; JWT is now used for authentication.


```properties
spring.datasource.url=jdbc:mysql://localhost:3306/RestaurantService
spring.datasource.username=root
spring.datasource.password=YourPassword
spring.datasource.driverClassName=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update
spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect
spring.jpa.properties.hibernate.show_sql=true
```

---

## Testing HTML Page

A test front-end page (`test-api.html`) is included for quickly checking endpoints:

- Add Users, Food Items, Orders, Visitors
- Display responses in JSON format
- Include Authorization header for endpoints protected by JWT

---

## Running the Application

### Prerequisites

- Java 21
- Maven 4.0
- MySQL 8

### Steps

1. Clone the repository:
```bash
git clone https://github.com/yourusername/Restaurant-Management-System.git
cd Restaurant-Management-System
```

2. Create MySQL database:
```sql
CREATE DATABASE RestaurantService;
```

3. Update `application.properties` with your MySQL credentials

4. Build and run:
```bash
mvn clean install
mvn spring-boot:run
```

5. Access Swagger UI:
```
http://localhost:8080/swagger-ui.html
```

6. Access Test HTML Page:
```
Open test-api.html in your browser
```

---

## Project Summary

The Restaurant Management System is a Spring Boot application that provides:

- User management (Admin/User/Visitor)
- Food menu management
- Order placement and tracking
- JWT-based authentication for secure API access
- Swagger UI for API testing and documentation

### Future Improvements

- Email verification for user signup
- Enhanced role-based access control
- Advanced order management features
- Payment gateway integration
- Real-time order tracking

---

## License

This project is licensed under the [BSD 3-Clause License](LICENSE).

---

## Contact

For questions or feedback, contact:

**Atul Kumar Upadhyay**  
📧 Email: atulupa12345@gmail.com

---

## Contributing

Contributions are welcome! Please follow these guidelines:

1. Fork repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

---

**⭐ If you find this project useful, please give it a star!**