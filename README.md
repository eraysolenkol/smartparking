# 🚗 Smart Parking System

Smart Parking is a RESTful web service built with Spring Boot that allows users to manage parking locations, payments and reservations.

---

## 📚 Table of Contents

- [Features](#features-)
- [Tech Stack](#tech-stack-)
- [Getting Started](#getting-started-)
- [API Endpoints](#api-endpoints-)
- [Database Schema](#database-schema-%EF%B8%8F)
- [Future Improvements](#-future-improvements)

---

## Features ✨

- Create, update, and delete parking locations
- Make reservations with time-based pricing
- Calculate current reservation cost dynamically
- Track parking availability
- Structured REST API with clear request/response formats

---

## Tech Stack 🧰

- Java 17
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Hibernate
- Maven
- Swagger (optional for API docs)

---

## Getting Started 🚀

### Prerequisites

- Java 17+
- Maven 3.6+
- PostgreSQL (or change to another supported DB)

### Setup

1. **Clone the repository:**
   ```bash
   git clone https://github.com/yourusername/smart-parking.git
   cd smart-parking
2. **Configure your database:**
   Edit `src/main/resources/application.properties`:
   ```properties
   spring.application.name=smartparking
   api.key=a5147226-ad75-4c36-9763-2653cf7eb759

   spring.datasource.url=YOUR_DATABASE_URL
   spring.datasource.username=YOUR_USERNAME
   spring.datasource.password=YOUR_PASSWORD
   spring.datasource.driver-class-name=org.postgresql.Driver

   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true
   spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
   ```

3. **Run the application:**
   ```bash
   mvn spring-boot:run
   ```
   OR 
   ```bash
   ./mvnw spring-boot:run
   ```
   
4. **Access the API:**
   Visit `http://localhost:8080` or view Swagger (if configured) at `http://localhost:8080/swagger-ui.html`.

---
## API Endpoints 📡

## 🧪 API Endpoints

### 🅿️ Parking Location

| Method | Endpoint                             | Description                        |
|--------|--------------------------------------|------------------------------------|
| GET    | `/api/parking-locations`             | Get all parking locations          |
| GET    | `/api/parking-locations/{id}`        | Get parking location by ID         |
| POST   | `/api/parking-locations`             | Create a new parking location      |
| PUT    | `/api/parking-locations/{id}`        | Update parking location            |
| DELETE | `/api/parking-locations/{id}`        | Delete parking location            |

---

### 📅 Reservation

| Method | Endpoint                                  | Description                                      |
|--------|-------------------------------------------|--------------------------------------------------|
| GET    | `/api/reservations`                       | Get all reservations                            |
| GET    | `/api/reservations/{id}`                  | Get reservation by ID                           |
| GET    | `/api/reservations/user/{id}`             | Get reservations by User ID                     |
| GET    | `/api/reservations/parking/{id}`          | Get reservations by Parking Location ID         |
| POST   | `/api/reservations`                       | Create a new reservation                        |
| PUT    | `/api/reservations/{id}`                  | Update reservation                              |
| DELETE | `/api/reservations/{id}`                  | Delete reservation                              |
| GET    | `/api/reservations/price/{id}`            | Get current cost of an ongoing reservation      |
| PUT    | `/api/reservations/{id}/cancel`           | Cancel reservation                              |
| PUT    | `/api/reservations/{id}/complete`         | Mark reservation as completed                   |

---

## Database Schema 🗃️

### `parking_location`

| Field            | Type     | Description                  |
|------------------|----------|------------------------------|
| id               | Long     | Primary key                  |
| name             | String   | Parking name                 |
| address          | String   | Location address             |
| image_url        | String   | Photo or logo URL            |
| description      | String   | Parking description          |
| is_available     | Boolean  | Availability status          |
| price_per_hour   | Double   | Hourly rate                  |
| total_spots      | Integer  | Total number of spots        |
| available_spots  | Integer  | Currently free spots         |

### `reservation`

| Field               | Type     | Description                                 |
|---------------------|----------|---------------------------------------------|
| id                  | Long     | Primary key                                 |
| user_id             | Long     | ID of the user who made the reservation     |
| parking_location_id | Long     | ID of the reserved parking location         |
| start_time          | String   | Start time of the reservation (ISO format)  |
| end_time            | String   | End time of the reservation (nullable)      |
| total_price         | Double   | Calculated price based on time and rate     |
| status              | String   | Reservation status (`confirmed`, `cancelled`, `completed`) |

## 🛠 Future Improvements

- Change available slots in parking locations when a reservation made

- Implement payment and user
