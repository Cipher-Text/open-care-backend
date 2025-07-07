# OpenCare Backend

OpenCare Backend is an open-source, Spring Boot-based application for managing healthcare-related data and operations. It provides a robust REST API for handling doctors, hospitals, institutions, social organizations, user authentication, file storage, and more. The project is designed for extensibility, security, and ease of deployment in modern healthcare and social service environments.

---

## Features

- **Doctor, Hospital, Institution, and Social Organization Management**
- **User Authentication & Authorization** (Keycloak integration, OAuth2, JWT)
- **Profile Management**
- **Medical Specialties, Degrees, and Tests**
- **File Upload/Download** (MinIO S3-compatible storage)
- **Database Versioning & Seeding** (Liquibase)
- **Caching** (Caffeine)
- **API Documentation** (Swagger/OpenAPI)
- **Dockerized Deployment** (with Docker Compose for Keycloak & MinIO)

---

## Tech Stack

- **Java 21**
- **Spring Boot 3**
- **Maven**
- **PostgreSQL**
- **Liquibase**
- **Keycloak** (for authentication)
- **MinIO** (object storage)
- **Caffeine** (cache)
- **MapStruct, Lombok**
- **SpringDoc OpenAPI**
- **Docker & Docker Compose**

---

## Architecture & Directory Structure

```
src/
├── main/
│   ├── java/com/ciphertext/opencarebackend/
│   │   ├── controller/      # REST API controllers
│   │   ├── service/         # Business logic
│   │   ├── entity/          # JPA entities
│   │   ├── dto/             # Data transfer objects
│   │   ├── mapper/          # MapStruct mappers
│   │   ├── config/          # Configuration classes
│   │   └── ...
│   └── resources/
│       ├── application.yml  # Main configuration
│       └── db/changelog/    # Liquibase migrations
└── test/                    # Tests
```

---

## API Overview

The backend exposes a comprehensive REST API. Some key endpoints:

- **Authentication**
  - `POST /api/auth/login` – User login
  - `POST /api/auth/register` – User registration
  - `GET /api/auth/social/redirect/{provider}` – Social login redirect (Keycloak)

- **Doctors**
  - `GET /api/doctors` – List/search doctors
  - `POST /api/doctors` – Create doctor (admin)
  - `PUT /api/doctors/{id}` – Update doctor (admin)
  - `DELETE /api/doctors/{id}` – Delete doctor (admin)
  - `GET /api/doctors/{doctorId}/degrees` – Doctor's degrees
  - `GET /api/doctors/{doctorId}/workplaces` – Doctor's workplaces

- **Hospitals**
  - `GET /api/hospitals` – List/search hospitals
  - `POST /api/hospitals` – Create hospital (admin)
  - `PUT /api/hospitals/{id}` – Update hospital (admin)
  - `DELETE /api/hospitals/{id}` – Delete hospital (admin)

- **Institutions**
  - `GET /api/institutions` – List/search institutions
  - `POST /api/institutions` – Create institution (admin)

- **Social Organizations**
  - `GET /api/social-organization` – List/search social organizations
  - `POST /api/social-organization` – Create social organization (admin)

- **User & Profile**
  - `GET /api/user/profile` – Get current user profile
  - `GET /api/user/roles` – Get current user roles
  - `POST /api/profiles/{id}/upload` – Upload profile image

- **Medical Specialties, Degrees, Tests, Medicines**
  - `GET /api/medical-specialities`, `GET /api/degrees`, `GET /api/medical-tests`, `GET /api/medicines`

- **File Storage (MinIO)**
  - `POST /api/files/upload` – Upload file
  - `GET /api/files/download/{objectName}` – Download file
  - `GET /api/files/list` – List files

- **Enums & Metadata**
  - `GET /api/blood-groups`, `/api/countries`, `/api/hospital-types`, etc.

- **API Documentation**
  - Swagger UI: [http://localhost:6700/swagger-ui.html](http://localhost:6700/swagger-ui.html)

---

## Database & Migrations

- **Liquibase** is used for schema migrations and seeding initial data.
- Main tables: `doctor`, `hospital`, `institution`, `profile`, `degree`, `medical_speciality`, `medical_test`, `social_organization`, `medicine`, `advertisement`, and more.
- Initial data is seeded for degrees, medical specialties, locations, hospitals, doctors, etc.

---

## Setup & Configuration

### Prerequisites
- Java 21+
- Maven 3.10.0+
- Docker & Docker Compose (for full stack)

### Environment Variables
- Copy `.env.example` to `.env` and adjust as needed.
- Main configs are in `src/main/resources/application.yml` (can be overridden by env vars).

### Running with Docker Compose (Recommended)

This will start the backend, Keycloak (auth), and MinIO (file storage):

```bash
docker-compose up --build
```

- Keycloak: [http://localhost:8180](http://localhost:8180)
- MinIO: [http://localhost:9101](http://localhost:9101) (UI, user: `minioadmin`, pass: `minioadmin123`)
- Backend: [http://localhost:6700](http://localhost:6700)

### Running Locally (Dev)

1. **Clone the repository:**
   ```bash
   git clone https://github.com/Cipher-Text/open-care-backend.git
   cd open-care-backend
   ```
2. **Build the project:**
   ```bash
   mvn clean package
   ```
3. **Run the application:**
   ```bash
   java -jar target/open-care-backend-0.0.1-SNAPSHOT.jar
   ```

### Database Migration

Liquibase migrations run automatically on startup. To manually run:
```bash
mvn liquibase:update
```

---

## Usage

- Access the API at [http://localhost:6700](http://localhost:6700)
- API docs: [http://localhost:6700/swagger-ui.html](http://localhost:6700/swagger-ui.html)
- Use the provided endpoints for CRUD operations on doctors, hospitals, institutions, social organizations, etc.

---

## Contributing

Contributions are welcome! Please fork the repository, create a feature branch, and submit a pull request. For major changes, open an issue first to discuss what you would like to change.

---

## License

This project is licensed under the MIT License. See the `LICENSE` file for details.