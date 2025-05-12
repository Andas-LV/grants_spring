# Stage 1: Build the app using Maven
FROM maven:3.9.9-eclipse-temurin-24-alpine AS build

# Copy the whole project
COPY . .

# Build the project and skip tests
RUN mvn clean package -DskipTests

# List the contents of the target directory to debug
RUN ls -al /target

# Stage 2: Run the app using OpenJDK 21
FROM openjdk:21-jdk-slim

# Copy the jar from the build stage
COPY --from=build /target/demo-0.0.1-SNAPSHOT.jar spring.jar

# Expose the application port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "spring.jar"]
