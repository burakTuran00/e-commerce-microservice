# Multi-stage Dockerfile for the eureka-server service
# Build stage
FROM maven:3.8.8-openjdk-17 AS builder
WORKDIR /workspace
COPY pom.xml ./
COPY src ./src/
RUN mvn -B -DskipTests package

# Run stage
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=builder /workspace/target/*.jar app.jar
EXPOSE 8761
ENTRYPOINT ["java","-jar","/app/app.jar"]
