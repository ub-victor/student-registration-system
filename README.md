# Student Registration System

## Overview

This project is a Spring Boot-based Student Registration Management System for an academic institution. It is designed to manage the core operations of student enrollment, course offering, academic unit organization, semester lifecycle, teacher assignment, and student academic performance.

The system is being built around a layered architecture with strong persistence modeling, validation, and a clear path toward REST APIs, business services, and test automation.

## Project Goals

The system aims to support the following academic workflows:

- Register and manage students
- Organize academic units such as faculties, programmes, and departments
- Define reusable course catalog entries
- Create semester-based course offerings
- Assign teachers to courses
- Allow students to register for semesters and courses
- Track marks, grades, and academic results

## Current Status

The repository already includes the foundation for the domain model:

- JPA entities for the main academic concepts
- Validation annotations for strong data integrity
- Spring Boot project structure
- Initial application smoke test

The next implementation stages will typically include repositories, services, DTOs, controllers, and full API-level testing.

## Technology Stack

- Java 21
- Spring Boot 3.5.x
- Spring Web
- Spring Data JPA
- Spring Validation
- Spring Security
- PostgreSQL
- Maven
- JUnit 5
- Lombok

## Architecture Summary

The application follows a layered approach:

1. Presentation Layer
   - Controllers and web endpoints
2. Service Layer
   - Business rules and transactions
3. Repository Layer
   - Data access using Spring Data JPA
4. Entity Layer
   - Domain model and persistence mapping
5. Validation Layer
   - Bean validation and custom checks

### Core Domain Model

The main entities in the system are:

- Student
- Teacher
- AcademicUnit
- CourseDefinition
- Semester
- Course
- StudentRegistration
- StudentCourse

### Relationship Overview

```text
Student ──< StudentRegistration >── Semester
Student ──< StudentCourse >── Course
StudentRegistration ──< StudentCourse
Course ──> CourseDefinition
Course ──> AcademicUnit
Course ──> Teacher
```

This model supports the key academic flow:

- A student registers for a semester
- The student enrolls in one or more courses for that semester
- Each enrollment can have marks, grades, credits, and remarks

## Project Structure

```text
student-registration-system/
├── src/main/java/com/auca/student_registration_system/
│   ├── entity/           # Domain entities
│   ├── repository/       # Data access layer
│   ├── service/          # Business logic
│   ├── controller/       # REST endpoints
│   ├── dto/              # Request/response objects
│   ├── mapper/           # Mapping between entities and DTOs
│   ├── exception/        # Exception handling
│   ├── validator/        # Validation logic
│   ├── specification/    # Criteria-based query support
│   └── config/           # Configuration classes
├── src/main/resources/
│   ├── application.properties
│   └── templates/        # View templates if needed
└── src/test/             # Test classes
```

## Prerequisites

Before running the application locally, make sure you have:

- Java 21 installed
- Maven installed
- PostgreSQL installed and running
- A database created for the project

## Setup Instructions

### 1. Clone the Repository

```bash
git clone <repository-url>
cd StudentRegistrationSystem
```

### 2. Create a PostgreSQL Database

Create a database such as:

```sql
CREATE DATABASE studentregistrationdb;
```

### 3. Configure the Application

Update the database settings in the Spring configuration file:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/studentregistrationdb
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### 4. Run the Application

```bash
cd student-registration-system
./mvnw spring-boot:run
```

The application should start on port 8080 unless you change the configuration.

## How the Project Can Be Tested

Testing should go beyond simply checking that the application starts. A strong test strategy for this project should cover the following levels.

### 1. Smoke Test

The current repository already contains a basic Spring Boot smoke test:

```bash
./mvnw test
```

This verifies that the application context loads correctly.

### 2. Unit Testing

Unit tests should focus on small pieces of logic such as:

- validation rules
- field constraints
- custom business logic
- mapping logic between entities and DTOs

Recommended tools:

- JUnit 5
- AssertJ
- Mockito

Example test focus areas:

- Student validation for required fields
- Course capacity rules
- Semester status transitions
- Registration duplicate prevention logic

### 3. Repository Testing

Repository-level tests should verify that JPA queries and relationships behave correctly.

This is especially important for:

- finding students by registration number
- retrieving courses by semester
- preventing duplicate student registration entries
- retrieving course enrollments by student

A good approach is to use:

- `@DataJpaTest`
- an in-memory or test database
- transactional test cases

### 4. Integration Testing

Integration tests should confirm that the full application stack works together:

- Spring context boots successfully
- repositories interact with the database correctly
- services perform business operations properly
- controllers return the expected responses

Suggested approach:

- use `@SpringBootTest`
- test real service flows end-to-end
- validate database persistence and transactional behavior

### 5. API Testing

Once controllers are implemented, HTTP-level tests should verify:

- correct request handling
- proper validation errors
- status codes
- response payload structure
- authorization behavior if security is enabled

A common approach is to use:

- `MockMvc`
- `@WebMvcTest` for controller-level tests
- `@SpringBootTest` for full-stack tests

### 6. End-to-End Testing

The most valuable tests for this project are workflow-based tests that reflect real academic behavior, for example:

1. A student registers for a semester
2. The student enrolls in courses
3. Marks are recorded
4. The system calculates or stores the grade result
5. The registration record is reviewed and updated

These tests ensure that the system behaves correctly in realistic scenarios rather than only at the unit level.

## Recommended Test Plan

A practical testing strategy for this project is:

1. Start with smoke tests to confirm the application starts
2. Add unit tests for domain validation and business rules
3. Add repository tests for JPA behavior and entity relationships
4. Add service tests for business workflows
5. Add controller/API tests for request handling
6. Add end-to-end tests for academic registration scenarios

## Example Test Commands

Run the full test suite:

```bash
./mvnw test
```

Run a single test class:

```bash
./mvnw -Dtest=StudentRegistrationSystemApplicationTests test
```

## Development Notes

- Keep business rules in the service layer where possible
- Use validation annotations on entities for input safety
- Prefer clear naming for entities and relationships
- Add tests alongside new features so behavior remains reliable
- Treat database constraints and entity validation as important safeguards

## Summary

This project is more than a basic CRUD application. It is an academic domain model meant to support real registration workflows, strong data integrity, and future API expansion. The best way to validate it is to combine smoke tests, unit tests, repository tests, integration tests, and end-to-end workflow tests.

