# Stage 1: Build the application using Maven and Java 17 (consistent with pom.xml)
FROM maven:3.9.6-amazoncorretto-21 AS build

WORKDIR /app

# First, copy only the POM to cache dependencies
COPY pom.xml ./

# Update compiler plugin version before downloading dependencies
RUN sed -i 's/<maven.compiler.plugin.version>.*<\/maven.compiler.plugin.version>/<maven.compiler.plugin.version>3.11.0<\/maven.compiler.plugin.version>/' pom.xml || \
    { echo 'Setting default compiler plugin version'; \
    xmlstarlet ed -L -N x=http://maven.apache.org/POM/4.0.0 \
    -s '/x:project/x:build/x:pluginManagement/x:plugins' -t elem -n plugin -v "" \
    -s '/x:project/x:build/x:pluginManagement/x:plugins/x:plugin[last()]' -t elem -n groupId -v "org.apache.maven.plugins" \
    -s '/x:project/x:build/x:pluginManagement/x:plugins/x:plugin[last()]' -t elem -n artifactId -v "maven-compiler-plugin" \
    -s '/x:project/x:build/x:pluginManagement/x:plugins/x:plugin[last()]' -t elem -n version -v "3.11.0" pom.xml; }

# Download dependencies (offline mode)
RUN mvn dependency:go-offline

# Copy source code
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Stage 2: Runtime with Java 23
FROM amazoncorretto:23

WORKDIR /app

# Copy the built JAR (using wildcard to avoid version conflicts)
COPY --from=build /app/target/*.jar ./app.jar

# Expose application port
EXPOSE 6500

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]