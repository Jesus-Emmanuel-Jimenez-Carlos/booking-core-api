# 📅 BookingCore API — Domain-Driven Reservation & Scheduling Engine

[![Java 17](https://img.shields.io/badge/Java-17-orange.svg?style=flat-square&logo=openjdk)](https://www.oracle.com/java/)
[![Spring Boot 3](https://img.shields.io/badge/Spring_Boot-3.2.3-green.svg?style=flat-square&logo=springboot)](https://spring.io/projects/spring-boot)
[![Architecture](https://img.shields.io/badge/Architecture-DDD_%2F_Clean_Architecture-blue.svg?style=flat-square)](#architecture--design-patterns)
[![License](https://img.shields.io/badge/License-MIT-brightgreen.svg?style=flat-square)](LICENSE)

A generic, high-performance, domain-driven RESTful scheduling engine designed to power reservation systems across industries—including healthcare clinics, legal practices, beauty salons, and SaaS platforms.

Built with **Java 17**, **Spring Boot 3**, and **Spring Data JPA**, **BookingCore API** delivers enterprise-grade concurrency control, sub-millisecond slot conflict detection, dynamic availability calculation, and unified error handling compliant with RFC-7807 standards.



💡 What Problem Does BookingCore Solve?
Building a reliable reservation system is notoriously complex. Unhandled race conditions lead to double-bookings, timezone mismatches disrupt schedules, and rigid database schemas make adapting to varied service types difficult.
BookingCore API addresses these core challenges with:
Zero Double-Bookings: Mathematical time-range boundary validation (Start 
A
​	
 <End 
B
​	
 ∧End 
A
​	
 >Start 
B
​	
 ) guarantees conflict-free scheduling under high concurrency.
Dynamic Slot Generation: Calculates real-time opening slots by analyzing provider operational schedules, break intervals, buffer periods, and existing reservations.
Multi-Tenant Scalability: Standardized domain abstractions accommodate diverse provider structures (e.g., medical specialists, consultation rooms, or service staff).
Clear Developer UX: Fully documented OpenAPI 3 / Swagger interface with localized, structured error responses.
🏗️ Architecture & Design Patterns
The system adheres strictly to Clean Architecture and Domain-Driven Design (DDD) principles to promote maintainability and testability:

src/main/java/com/bookingcore/
├── domain/                      # Core Business Logic & Models (Framework Agnostic)
│   ├── model/                   # Rich Domain Entities (Appointment, TimeSlot)
│   └── exception/               # Domain-Specific Business Exceptions
├── service/                     # Service Layer & Use Case Implementations
│   └── impl/                    # Business Workflow Implementation
└── infrastructure/              # External Integrations & Adapters
    ├── config/                  # Framework & OpenAPI Configuration
    ├── persistence/             # Spring Data JPA Repositories
    └── web/                     # REST Controllers, DTOs & Exception Handlers


⚙️ Tech Stack & Dependencies
Category	Technology	Purpose
Language	Java 17 (LTS)	Modern syntax, pattern matching, and performance improvements
Framework	Spring Boot 3.2.3	Core Application Framework & Dependency Injection
Data Layer	Spring Data JPA / Hibernate	ORM Mapping & Transactional Security
Database	H2 (Dev) / PostgreSQL (Prod ready)	Relational Storage with Indexed Queries
Documentation	OpenAPI 3 (SpringDoc UI)	Interactive REST API Documentation
Utilities	Project Lombok	Boilerplate code reduction
🔌 API Reference & Endpoints
1. Calculate Provider Availability
Retrieves all open, non-overlapping time slots for a given provider, service, and date.
URL: GET /api/v1/availability
Query Parameters:
providerId (long, required): Target service provider ID.
serviceId (long, required): Service type identifier (defines duration).
date (ISO-8601 Date, required): e.g., 2026-10-15.
Sample Response (200 OK):


[
  {
    "startTime": "2026-10-15T09:00:00",
    "endTime": "2026-10-15T09:30:00",
    "available": true
  },
  {
    "startTime": "2026-10-15T09:30:00",
    "endTime": "2026-10-15T10:00:00",
    "available": true
  }
]


2. Schedule an Appointment
Reserves a time slot for a customer after validating availability and preventing collisions.
URL: POST /api/v1/appointments
Headers: Content-Type: application/json
Sample Request Payload:


{
  "customerId": 101,
  "providerId": 12,
  "serviceId": 5,
  "startTime": "2026-10-15T09:00:00",
  "notes": "Initial consultation regarding cloud migration strategy."
}


Sample Response (201 Created):


{
  "id": 1,
  "customerId": 101,
  "providerId": 12,
  "serviceId": 5,
  "startTime": "2026-10-15T09:00:00",
  "endTime": "2026-10-15T09:30:00",
  "status": "SCHEDULED",
  "createdAt": "2026-09-22T20:45:00"
}


🛠️ How to Clone & Run Locally
Prerequisites
JDK 17 or higher
Apache Maven 3.8+
Git
Quickstart Guide
Clone the Repository:

git clone [https://github.com/tu-usuario/booking-core-api.git](https://github.com/tu-usuario/booking-core-api.git)
cd booking-core-api


Build and Run the Application:


mvn clean package
mvn spring-boot:run


Bash
mvn clean package
mvn spring-boot:run
Access Interactive Swagger Documentation:
Open your browser and navigate to:
http://localhost:8080/swagger-ui.html
Access the H2 Database Console (Optional):
URL: http://localhost:8080/h2-console
JDBC URL: jdbc:h2:mem:bookingdb
Username: sa
Password: (leave blank)
👨‍💻 Author
Jesús Emmanuel Jiménez Carlos
Software Engineer & IT Infrastructure Consultant
Specializations: Cloud Infrastructure, Custom Software Development, and Systems Architecture.











