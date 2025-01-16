# Tenpo Backend

Tenpo Backend is a Spring Boot application designed to manage transactions with features like rate limiting and OpenAPI documentation.

## Prerequisites

- Java 17 or higher
- Gradle 7.0 or higher
- Docker (optional for running with Docker)

## Installation

1. Clone the repository:

   ```bash
   git clone https://github.com/codelious/tenpo-backend.git
   cd tenpo-backend
   ```

2. Build the project using Gradle:

   ```bash
   ./gradlew build
   ```

## Running the Application

### Using Gradle

1. Run the application:

   ```bash
   ./gradlew bootRun
   ```

2. The application will start and be accessible at `http://localhost:8080`.

### Using Docker (Optional)

1. Build the Docker image:

   ```bash
   docker build -t tenpo-backend .
   ```

2. Run the Docker container:

   ```bash
   docker run -p 8080:8080 tenpo-backend
   ```

## Accessing the API Documentation

The API documentation is automatically generated using Swagger and is accessible at:

- Swagger UI: `http://localhost:8080/swagger-ui/index.html`

## Features

- **Rate Limiting**: Limits the number of requests per client to prevent abuse.
- **Transaction Management**: Create, read, update, and delete transactions.
- **OpenAPI Documentation**: Automatically generated API documentation.

## Endpoints

Refer to the Swagger UI for a detailed list of endpoints and their usage.

## Testing

Run tests using Gradle:

```bash
./gradlew test
```

## Contributing

Feel free to fork the repository and submit pull requests. For major changes, please open an issue first to discuss what you would like to change.

## License

This project is licensed under the MIT License. See the LICENSE file for details.

---

Enjoy using Tenpo Backend! If you have any questions or need further assistance, feel free to reach out.

