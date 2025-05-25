# 🚗 Smart Parking System

Smart Parking is a RESTful web service built with Spring Boot that allows users to manage parking locations, payments and reservations.

---

## 📚 Table of Contents

- [Features](#features)
- [Tech Stack](#-tech-stack)
- [Getting Started](#-getting-started)
- [API Endpoints](#-api-endpoints)
- [Database Schema](#database-schema-%EF%B8%8F)

---

## ✨Features 

- Create, update, and delete parking locations
- Make reservations with time-based pricing
- Calculate current reservation cost dynamically
- Track parking availability
- Structured REST API with clear request/response formats

---

## 🧰 Tech Stack 

- Java 17
- Spring Boot
- Spring Data JPA
- Lombok
- PostgreSQL
- Hibernate
- Maven
- Swagger (optional for API docs)

---

## 🚀 Getting Started 

### Prerequisites

- Java 17+
- Maven 3.6+
- PostgreSQL (or change to another supported DB)

### Setup

1. **Clone the repository:**
   ```bash
   git clone https://github.com/eraysolenkol/smartparking.git
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

### 🅿️ Parking Locations

| Method | Endpoint                     | Description                    |
|--------|------------------------------|--------------------------------|
| GET    | `/api/parking-locations`      | Get all parking locations      |
| GET    | `/api/parking-locations/{id}` | Get parking location by ID     |
| POST   | `/api/parking-locations`      | Create a new parking location  |
| PUT    | `/api/parking-locations/{id}` | Update parking location by ID  |
| DELETE | `/api/parking-locations/{id}` | Delete parking location by ID  |

---

### 📅 Reservations

| Method | Endpoint                             | Description                                 |
|--------|------------------------------------|---------------------------------------------|
| GET    | `/api/reservations`                 | Get all reservations                       |
| GET    | `/api/reservations/{id}`            | Get reservation by ID                      |
| GET    | `/api/reservations/user/{id}`       | Get reservations by user ID                |
| GET    | `/api/reservations/parking/{id}`    | Get reservations by parking location ID   |
| POST   | `/api/reservations`                 | Create a new reservation                   |
| PUT    | `/api/reservations/{id}`            | Update reservation by ID                   |
| DELETE | `/api/reservations/{id}`            | Delete reservation by ID                   |
| GET    | `/api/reservations/price/{id}`      | Get current cost of an ongoing reservation|
| PUT    | `/api/reservations/{id}/cancel`     | Cancel reservation by ID                   |
| PUT    | `/api/reservations/{id}/complete`   | Mark reservation as completed              |
| GET    | `/api/reservations/daily-reservations` | Get all reservations for the current day  |

---

### 💳 Payments

| Method | Endpoint                          | Description                                   |
|--------|----------------------------------|-----------------------------------------------|
| GET    | `/api/payments`                  | Get all payments                             |
| GET    | `/api/payments/{id}`             | Get payment by ID                            |
| GET    | `/api/payments/reservation/{id}` | Get payments by reservation ID               |
| POST   | `/api/payments`                  | Make a payment                              |
| PUT    | `/api/payments/{id}/reservation/{reservationId}` | Update payment by payment and reservation IDs |
| DELETE | `/api/payments/{id}`             | Delete payment by ID                         |
| PUT    | `/api/payments/{id}/pay/{isCard}` | Perform payment by payment ID |
| GET    | `/api/payments/daily-payments`  | Get payments made today                      |

---

### 👤 Users

| Method | Endpoint                 | Description                       |
|--------|--------------------------|-----------------------------------|
| GET    | `/api/users`             | Get all users                    |
| GET    | `/api/users/{id}`        | Get user by ID                  |
| GET    | `/api/users/citizen/{citizenId}` | Get user by citizen ID  |
| GET    | `/api/users/name/{name}` | Get users by name               |
| POST   | `/api/users`             | Create a new user               |
| PUT    | `/api/users/{id}`        | Update user by ID               |
| DELETE | `/api/users/{id}`        | Delete user by ID               |

---

### 📜 Logs

| Method | Endpoint     | Description          |
|--------|--------------|----------------------|
| GET    | `/api/logs`  | Get all the logs |

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
| price_per_hour   | Double   | Hourly cost                 |
| total_spots      | Integer  | Total number of spots        |
| available_spots  | Integer  | Currently free spots         |
| created_at       | String   | Creation timestamp |
| updated_at       | String   | Last update timestamp |

---

### `reservation`

| Field               | Type     | Description                                 |
|---------------------|----------|---------------------------------------------|
| id                  | Long     | Primary key                                 |
| user_id             | Long     | ID of user    |
| parking_location_id | Long     | ID of parking location         |
| start_time          | String   | Start time of the reservation   |
| end_time            | String   | End time of the reservation      |
| total_price         | Double   | Calculated price based on time and rate     |
| status              | String   | Reservation status  |
| created_at          | String   | Creation timestamp            |
| updated_at          | String   | Last update timestamp             |

---

### `payment`

| Field               | Type     | Description                              |
|---------------------|----------|------------------------------------------|
| id                  | Long     | Primary key                            |
| reservation_id      | Long     | Foreign key to reservation             |
| amount              | Double   | Payment amount                         |
| is_card             | Boolean  | Payment method          |
| status              | String   | Payment status                        |
| created_at          | String   | Creation timestamp           |
| updated_at          | String   | Last update timestamp         |

---

### `parking_log`

| Field          | Type   | Description                          |
|----------------|--------|------------------------------------|
| id             | Long   | Primary key                        |
| action         | String | Action performed     |
| ip_address     | String | IP address of the requester        |
| entity         | String | Entity affected |
| api_endpoint   | String | API endpoint called                |
| created_at     | String | Timestamp of the log entry          |

---

### `parkinguser`

| Field          | Type   | Description                          |
|----------------|--------|------------------------------------|
| id             | Long   | Primary key                        |
| name           | String | User's full name                   |
| citizen_id     | Long   | Unique citizen number |
| created_at     | String | Creation timestamp       |
| updated_at     | String | Last update timestamp    |
