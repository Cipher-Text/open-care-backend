# OpenCare Backend

OpenCare Backend is a Spring Boot-based application designed to manage healthcare-related data and operations. It includes features such as managing doctors, workplaces, and institutions.

## Features

- Manage doctor profiles and workplaces.
- Integration with Liquibase for database versioning.
- Dockerized deployment for easy setup and scalability.

## Technologies Used

- **Java**: Programming language.
- **Spring Boot**: Backend framework.
- **Maven**: Build and dependency management.
- **Liquibase**: Database migration and versioning.
- **Docker**: Containerization.

## Prerequisites

- Java 17 or higher
- Maven 3.10.0 or higher
- Docker (optional for containerized deployment)

## Getting Started

### Clone the Repository

```bash
git clone https://github.com/your-repo/opencare-backend.git
cd opencare-backend
```

### Build and Run the Application

1. **Build the project**:
   ```bash
   mvn clean package
   ```

2. **Run the application**:
   ```bash
   java -jar target/open-care-0.0.1-SNAPSHOT.jar
   ```

### Run with Docker

1. **Build the Docker image**:
   ```bash
   docker build -t opencare-backend .
   ```

2. **Run the Docker container**:
   ```bash
   docker run -p 6700:6700 opencare-backend
   ```

### Database Migration

Liquibase is used for database versioning. Ensure the changelog file is correctly configured in `application.properties` or `application.yml`:

```properties
spring.liquibase.change-log=classpath:/db.changelog/db.changelog-master.yaml
```

### Directory Structure

```
src/
├── main/
│   ├── java/
│   │   └── com.ciphertext.opencarebackend/
│   │       ├── entity/
│   │       └── ...
│   ├── resources/
│       ├── db.changelog/
│       └── application.yml
```

## Contributing

Contributions are welcome! Please fork the repository and submit a pull request.

## License

This project is licensed under the MIT License. See the `LICENSE` file for details.
```