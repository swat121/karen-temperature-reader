# Karen Temperature Reader

A Spring Boot application that integrates with MQTT to read and process temperature data. This service is designed to work with Karen r1.5 devices, collecting temperature readings and storing them in a PostgreSQL database.

## Features

- MQTT integration for real-time temperature data collection
- PostgreSQL database for data persistence
- RESTful API endpoints for data access
- Docker support for easy deployment

## Prerequisites

- Java 17 or higher
- Maven
- Docker and Docker Compose
- MQTT broker (e.g., Mosquitto)

## Technology Stack

- Spring Boot 3.4.1
- Spring Data JPA
- PostgreSQL 15
- Eclipse Paho MQTT Client
- Lombok
- Docker

## MQTT Message Format

The service subscribes to the MQTT topic `test/esp32/demo` by default (configurable via `mqtt.topic` property) and expects messages in the following JSON format:

```json
{
    "macAddress": "00:11:22:33:44:55",
    "requestId": "req-123",
    "sensor": "temperature-sensor-1",
    "data": [
        {
            "address": "0x28",
            "value": 25.5
        },
        {
            "address": "0x29",
            "value": 26.2
        }
    ]
}
```

### Message Fields

- `macAddress`: The MAC address of the device sending the data
- `requestId`: Unique identifier for the request
- `sensor`: Name of the sensor sending the data
- `data`: Array of sensor readings, where each reading contains:
  - `address`: The address of the sensor
  - `value`: The temperature value read by the sensor

### Example Messages

1. Single sensor reading:
```json
{
    "macAddress": "00:11:22:33:44:55",
    "requestId": "req-001",
    "sensor": "main-temperature",
    "data": [
        {
            "address": "0x28",
            "value": 23.7
        }
    ]
}
```

2. Multiple sensor readings:
```json
{
    "macAddress": "00:11:22:33:44:55",
    "requestId": "req-002",
    "sensor": "multi-sensor",
    "data": [
        {
            "address": "0x28",
            "value": 24.1
        },
        {
            "address": "0x29",
            "value": 25.3
        },
        {
            "address": "0x30",
            "value": 23.9
        }
    ]
}
```

## Getting Started

### 1. Clone the Repository

```bash
git clone [repository-url]
cd karen-temperature-reader
```

### 2. Start the Database

The project includes a Docker Compose configuration for PostgreSQL. To start the database:

```bash
docker-compose up -d
```

This will start a PostgreSQL instance with the following configuration:
- Port: 5432
- Database: mqtt_db
- Username: postgres
- Password: p@ssword

### 3. Build and Run the Application

```bash
./mvnw clean install
./mvnw spring-boot:run
```

## Configuration

The application can be configured through `application.properties` or `application.yml`. Key configuration parameters include:

- MQTT broker connection details
- Database connection settings
- Application port and other Spring Boot properties

## API Endpoints

[Document your API endpoints here]

## Development

### Building the Project

```bash
./mvnw clean install
```

### Running Tests

```bash
./mvnw test
```

## Docker Support

The project includes Docker Compose configuration for the database. To start all services:

```bash
docker-compose up -d
```