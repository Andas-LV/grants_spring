FROM maven:3.9.9-eclipse-temurin-24-alpine AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:21-jdk-slim
COPY --from=build /target/demo-0.0.1-SNAPSHOT.jar spring.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","spring.jar"]