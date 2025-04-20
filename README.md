# Ticket Management Service

A simple train ticket booking system built using Spring Boot 3, JPA, and H2 Database. The app allows users to book tickets, assign seats, manage bookings, and view booking details.

## Features

- **User Registration:** Users can register with first name, last name, and email.
- **Ticket Booking:** Book a train ticket with details like from/to station, price paid, and seat allocation.
- **Booking Management:** View, modify, or delete bookings.
- **Section Allocation:** The train has two sections (A and B) for seat allocation.
- **Seat Availability:** Assumption is that the sections have unlimited seats as race condition would occur if multiple users book ticket simultaneously. That solution would require using atomic operations to avoid that.
- **Swagger Documentation:** API documentation for easy testing and integration.
- **Exception Handling:** Global exception handling for user-friendly error messages.

## Requirements

- Java 17 or higher
- Maven or Gradle
- Spring Boot 3
- H2 Database (in-memory)

