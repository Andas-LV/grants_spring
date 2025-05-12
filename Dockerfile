FROM maven:3.8.5-openjdk-21 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:21.0.1-jdk-slim
COPY --from=build /target/demo-0.0.1-SNAPSHOT.jar spring.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","spring.jar"]