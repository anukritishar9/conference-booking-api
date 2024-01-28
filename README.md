# Conference Room Booking API

## Overview

This is a simple Conference Room Booking API that allows users to book conference rooms, check booked rooms, view available rooms, cancel bookings, and check room maintenance timings. The API is built using Spring Boot.

## Getting Started

Follow the steps below to run the Conference Booking API on your local machine.

### Clone the Repository


git clone https://github.com/anukritishar9/conference-booking-api.git

Build the Project

cd conference-booking-api
mvn clean install

Run the Application

java -jar target/conference-booking-api-<version>.jar

# API Usage

1. Book a Conference Room

curl --location 'http://localhost:8092/conference/api/rooms' \
	--header 'Content-Type: application/json' \
	--data-raw '{
		"startDateTime": "2024-01-29T15:00:00",
		"endDateTime": "2024-01-29T15:30:00",
		"participants": 5
	}'
2. Check Booked Rooms

curl --location 'http://localhost:8092/conference/api/rooms/booked'

3. Check Available Rooms

curl --location 'http://localhost:8092/conference/api/rooms/available?startTime=2024-01-29T14:00:00&endTime=2024-01-29T14:30:00'

4. Cancel Booking

curl --location --request DELETE 'http://localhost:8092/conference/api/rooms/1'

5. Check Room Maintenance Timings

curl --location 'http://localhost:8092/conference/api/maintenance-timings'

## Swagger UI

The Swagger UI is available at http://localhost:8092/swagger-ui/index.html to test the API.

### H2 Console

The H2 database console is available at http://localhost:8092/h2-console.

Username: sa

Password: 123456

JDBC URL: jdbc:h2:mem:conference


Note: Replace `<version>` with the actual version number of the JAR file generated during the build.


