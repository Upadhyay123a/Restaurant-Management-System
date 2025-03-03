# 🍽️ Restaurant Management System  
**Java | Spring Boot | MySQL | Maven | REST API | Spring Security**  

[![License: BSD 3-Clause](https://img.shields.io/badge/License-BSD--3--Clause-blue.svg)](https://opensource.org/licenses/BSD-3-Clause)  

------

## 📌 Overview  

The **Restaurant Management System** is a **Spring Boot-based** application that provides a robust backend for **managing users, food items, and orders** with **role-based access control (RBAC)**.  

The system supports the following user roles:  
✅ **Admin** – Manages users and food items.  
✅ **Normal User** – Registers, logs in, and places orders.  
✅ **Visitor** – Views the available food items.  

This application ensures **secure authentication & authorization** with **Spring Security** and supports **CRUD operations** for restaurant management.  

---

## 🛠 Tech Stack  

### **Frontend (Optional)**  
- React.js (Future Scope)  

### **Backend**  
- **Java 21** – Programming Language  
- **Spring Boot** – Backend Framework  
- **Spring Security** – Authentication & Authorization  
- **Spring Data JPA** – ORM for Database Interaction  
- **Maven** – Dependency Management  

### **Database**  
- **MySQL** – Relational Database  
- **Hibernate** – ORM Framework  

### **Other Tools & Libraries**  
- **SpringDoc OpenAPI (Swagger UI)** – API Documentation  
- **JavaMail API** – Email Notifications  
- **Lombok** – Reduces Boilerplate Code  
- **JWT (JSON Web Token)** – Secure Authentication  

---

## 📌 Features  

### ✅ **User Features:**  
✔️ **User Registration & Login** (JWT-based authentication)  
✔️ **Secure Role-Based Access Control (RBAC)**  
✔️ **View Available Food Items**  
✔️ **Place & Track Orders**  

### ✅ **Admin Features:**  
✔️ **Manage Users** (Create, Update, Delete)  
✔️ **Add & Manage Food Items**  
✔️ **Track & Manage Orders**  

### ✅ **Security & Performance:**  
✔️ **Password Encryption** using BCrypt  
✔️ **Input Validation & Email Verification**  
✔️ **Database Optimized with Indexing**  

---

## 📌 Installation & Setup  

### 🔹 **Prerequisites**  
Ensure you have:  
- **Java 21+** installed – [Download Java](https://adoptopenjdk.net/)  
- **Maven** installed – [Install Maven](https://maven.apache.org/install.html)  
- **MySQL** installed & running – [Download MySQL](https://dev.mysql.com/downloads/)  

### 🔹 **Clone the Repository**  
```sh
git clone https://github.com/yourusername/restaurant-management-system.git
cd restaurant-management-system

🔹 Configure Database
Update src/main/resources/application.properties:

properties

spring.datasource.url=jdbc:mysql://localhost:3306/restaurant_db
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.security.jwt.secret=your_secret_key

🔹 Install Dependencies & Run Server
sh
Copy
Edit

mvn clean install
mvn spring-boot:run
The backend will start on http://localhost:8080/ 🚀

📌 Project Structure
📂 restaurant-management-system
 ├── 📁 src/main/java/com/restaurant
 │   ├── 📁 controller      # REST API Controllers
 │   ├── 📁 service         # Business Logic & Services
 │   ├── 📁 repository      # JPA Repositories
 │   ├── 📁 model           # Entities (User, FoodItem, Order)
 │   ├── 📁 security        # Spring Security Configurations
 │   ├── 📁 dto             # Data Transfer Objects (DTOs)
 │   ├── 📄 RestaurantManagementApplication.java  # Main Application
 ├── 📁 src/main/resources
 │   ├── 📄 application.properties  # Database & App Configuration
 ├── 📄 pom.xml  # Project Dependencies
 ├── 📄 README.md  # Documentation
 ├── 📄 .gitignore  # Ignore Node Modules & IDE Files

📌 RESTful API Endpoints

🔹 User Management


Method	      Endpoint	             Description
POST	     /api/users/register 	   Register a new user
POST	     /api/users/login	       Authenticate user & return JWT token
GET	      /api/users/{userId}	    Retrieve user profile

🔹 Food Management

Method	          Endpoint        	Description
GET	          /api/food	       Get all food items
POST	        /api/food/add  	  Add a new food item (Admin only)
PUT         	/api/food/{id}	   Update food details (Admin only)
DELETE	      /api/food/{id}	   Remove a food item (Admin only)

🔹 Order Management

Method	     Endpoint	               Description
POST	      /api/orders	           Place a new order
GET	       /api/orders/{userId}	  Get user order history
PUT	       /api/orders/{orderId}	 Update order status (Admin only)

📌 Database Schema

Table Name	    Description
users       	Stores user accounts & profiles
food_items	  Stores food items available in the menu
orders	      Stores orders placed by users

Database Table Details
🛠 Users Table

Column Name	        Data Type	      Description
id	               BIGINT (PK)	    Unique identifier
username	         VARCHAR(255)	   User's name
email	            VARCHAR(255)	   User's email
password	         VARCHAR(255)	   Hashed password
role	             VARCHAR(50)	    Admin / Normal User / Visitor

🍔 Food Items Table

Column Name          	Data Type	          Description
id	                 BIGINT (PK)	        Unique identifier
name	               VARCHAR(255)	       Food item name
description	        TEXT	               Food item details
price	              DECIMAL(10,2)	      Food item price

📦 Orders Table

Column Name	         Data Type	 Description
id	               BIGINT (PK)	Unique identifier
user_id	          BIGINT (FK)	Reference to users table
food_item_id	     BIGINT (FK)	Reference to food_items table
status	           VARCHAR(50)	Placed / Delivered

📌 Deployment
🚀 Deploy on Heroku (Backend)

git push heroku main
Or deploy on Render/AWS/DigitalOcean.

📌 Future Enhancements
🔹 Payment Integration (Razorpay/Stripe)
🔹 Table Reservation System
🔹 Mobile App Integration (React Native / Flutter)
🔹 AI-Based Order Recommendation System

📌 License
This project is licensed under the BSD 3-Clause License.

📬 Contact
📧 Email: atulupa@12345@gmail.com
🐙 GitHub: https://github.com/Upadhyay123a/
