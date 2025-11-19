# Reading List Spring Boot Project

## Overview
This is a simple Spring Boot application for managing a reading list.  
Users can add books, view all books, and perform basic CRUD operations.  

Currently, the project supports:
- Viewing all books (`GET /`)
- Adding a book (`POST /`)
- Hello endpoints for testing (`GET /hello` and `GET /test`)
- PostgreSQL integration for persistence
- Thymeleaf templates for UI

---

## Technologies
- Java 23
- Spring Boot 3.5.x
- Spring Data JPA
- PostgreSQL
- Thymeleaf
- Maven

---

## Database Configuration
This project uses PostgreSQL. Create a database called `davis` and update your `.env` or `application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/davis
spring.datasource.username=postgres
spring.datasource.password=salom1197@
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
