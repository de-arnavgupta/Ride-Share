# RideShare Backend

Spring Boot + MongoDB backend for a mini ride sharing application. It demonstrates:

- JWT-based authentication (login + protected endpoints)
- Role-based access control (`ROLE_USER`, `ROLE_DRIVER`)
- DTOs and validation
- Global exception handling
- Clean architecture (Controller → Service → Repository)

## Tech Stack

- Java 17+ (check your IDE JDK setup)
- Spring Boot (web, security, data-mongodb)
- MongoDB
- Maven

## Project Structure

Source root (simplified):

- `src/main/java/com/saniya/ride_share`
  - `controller` – REST controllers (`AuthController`, `RideController`)
  - `service` – service interfaces and implementations
  - `model` – MongoDB entities (`User`, `Ride`)
  - `repository` – Spring Data repositories (`UserRepository`, `RideRepository`)
  - `dto` – request/response DTOs (`AuthDtos`, `RideDtos`)
  - `config` – JWT + Spring Security config
  - `exception` – custom exceptions + global exception handler
- `src/main/resources`
  - `application.properties` – server and MongoDB config

## Prerequisites

1. **Java**: Make sure `java -version` shows Java 17 or compatible.
2. **MongoDB**: Running locally, e.g. on `mongodb://localhost:27017`.
3. **Maven wrapper**: Project already contains `mvnw` / `mvnw.cmd`.

## Configuration

Check `src/main/resources/application.properties` for:

- `server.port` – HTTP port (default is usually `8080` or as set by your instructor).
- `spring.data.mongodb.uri` – MongoDB connection URI.

Adjust these values if needed for your environment.

## Build and Run

From the project root (`ride_share`), in a terminal (PowerShell on Windows):

```powershell
# Compile and run tests
.\mvnw.cmd clean test

# Run the Spring Boot app
.\mvnw.cmd spring-boot:run
```

Wait until the console logs something like:

```text
Started RideShareApplication in ... seconds
Tomcat initialized with port(s): 8080
```

Use that port (e.g. `8080` or `8081`) in Postman.

## Basic API Usage (Postman)

### 1. Register Users

**Endpoint:** `POST /api/auth/register`

Body (JSON):

```json
{
  "username": "john",
  "password": "1234",
  "role": "ROLE_USER"
}
```

For a driver:

```json
{
  "username": "driver1",
  "password": "abcd",
  "role": "ROLE_DRIVER"
}
```

### 2. Login and Get JWT Token

**Endpoint:** `POST /api/auth/login`

```json
{
  "username": "john",
  "password": "1234"
}
```

Response (example):

```json
{
  "token": "<jwt-token-here>"
}
```

Copy the `token` value. For all protected endpoints, set this header:

- `Authorization: Bearer <jwt-token-here>`

### 3. User: Create a Ride

**Endpoint:** `POST /api/v1/rides`

Headers:

- `Authorization: Bearer <user-token>`
- `Content-Type: application/json`

Body:

```json
{
  "pickupLocation": "Koramangala",
  "dropLocation": "Indiranagar"
}
```

### 4. Driver: View Pending Ride Requests

**Endpoint:** `GET /api/v1/driver/rides/requests`

Headers:

- `Authorization: Bearer <driver-token>`

### 5. Driver: Accept a Ride

**Endpoint:** `POST /api/v1/driver/rides/{rideId}/accept`

Headers:

- `Authorization: Bearer <driver-token>`

Replace `{rideId}` with the `id` from the ride created earlier.

### 6. Complete a Ride

**Endpoint:** `POST /api/v1/rides/{rideId}/complete`

Headers:

- `Authorization: Bearer <user-token>` **or** `Bearer <driver-token>`

### 7. User: Get Their Rides

**Endpoint:** `GET /api/v1/user/rides`

Headers:

- `Authorization: Bearer <user-token>`

## Error Handling

Validation and other errors are handled by `GlobalExceptionHandler` and returned as JSON, e.g.:

```json
{
  "error": "VALIDATION_ERROR",
  "message": "Pickup is required",
  "timestamp": "2025-12-08T12:00:00Z"
}
```

## Notes

- Roles are strings: `ROLE_USER` or `ROLE_DRIVER`.
- Passwords are stored encoded using `BCryptPasswordEncoder`.
- JWTs must be sent on every protected request via the `Authorization` header.

This project is meant as a learning exercise for Spring Boot, JWT security, MongoDB, DTOs, and global exception handling.

