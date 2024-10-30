Moonpadan Shop Backend

This project is the backend for Moonpadan, an e-commerce platform developed using Spring Boot. It provides RESTful APIs for managing users, products and orders.

Features

User Authentication & Authorization: JWT-based security for users and admins.
Product Management: CRUD operations for products.
Order Management: Handle order creation, updating, and status management.
Payment Integration: Integration with payment services.
Role-based Access Control: Access restrictions based on user roles (admin, user).
Technologies

Java 11
Spring Boot 2.x
Spring Security (JWT Authentication)
Hibernate & JPA (ORM)
MySQL (Database)
Maven (Build tool)
Getting Started

Prerequisites
Before running the application, make sure you have the following:

Java 11 or newer
MySQL installed and running
Maven installed
Setup Database
Create a MySQL database for the project. You can name it moonpadan_db.

sql
Copy code
CREATE DATABASE moonpadan_db;
Make sure to configure your MySQL username and password in the application.properties file:

properties
Copy code
spring.datasource.url=jdbc:mysql://localhost:3306/moonpadan_db
spring.datasource.username=your_mysql_username
spring.datasource.password=your_mysql_password
Running the Application
Clone the repository:
bash
Copy code
git clone https://github.com/AnaMoroz1/springboot-moonpadan-shop-backend.git
cd springboot-moonpadan-shop-backend
Build the project:
bash
Copy code
mvn clean install
Run the Spring Boot application:
bash
Copy code
mvn spring-boot:run
The application will be accessible at http://localhost:8080.

Testing the API
You can test the APIs using tools like Postman or cURL.

User Registration & Login:
POST /api/auth/signup - Register a new user
POST /api/auth/signin - Authenticate a user and get a JWT token
Product Management:
GET /api/products - Retrieve all products
POST /api/products - Add a new product (Admin access required)
Order Management:
POST /api/orders - Create a new order
