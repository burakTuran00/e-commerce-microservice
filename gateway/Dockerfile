# Multi-stage Dockerfile for the gateway service
# Build stage
FROM maven:3.8.8-openjdk-17 AS builder
WORKDIR /workspace
# copy only what is needed to leverage build cache
COPY pom.xml ./
COPY src ./src/

RUN mvn -B -DskipTests package

# Run stage
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=builder /workspace/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
