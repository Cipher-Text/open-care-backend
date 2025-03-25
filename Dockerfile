# Stage 1: Build the application using Maven and Java 17
FROM maven:3.8.5-openjdk-21-slim AS build

WORKDIR /app

# Copy the Maven project descriptor and download dependencies
COPY pom.xml ./
RUN mvn dependency:go-offline

# Copy the entire project source code
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Stage 2: Create the final image using Java 17
FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

# Copy the built JAR file from the previous stage
COPY --from=build /app/target/open-care-backend-0.0.1-SNAPSHOT.jar ./app.jar

# Expose application port
EXPOSE 6500

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]
