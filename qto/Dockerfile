# QTO Spring Boot Application - Multi-stage Dockerfile
# Build stage
FROM eclipse-temurin:21-jdk-alpine AS builder

WORKDIR /app

# Copy Gradle wrapper and build files
COPY gradle gradle
COPY gradlew .
COPY gradlew.bat .
COPY build.gradle.kts .
COPY settings.gradle.kts .

# Copy source for all modules
COPY qto-core qto-core
COPY qto-database qto-database
COPY qto-rest-api qto-rest-api
COPY qto-spring-boot-app qto-spring-boot-app

# Build the boot JAR (skip tests for faster image build)
RUN sed -i 's/\r$//' gradlew && chmod +x gradlew
RUN ./gradlew :qto-spring-boot-app:bootJar --no-daemon -x test

# Runtime stage
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Create non-root user for security
RUN addgroup -g 1000 qto && adduser -u 1000 -G qto -D qto

# Copy the built JAR from builder
COPY --from=builder /app/qto-spring-boot-app/build/libs/qto-spring-boot-app-*.jar app.jar

# File storage mount point
RUN mkdir -p /var/qto/files && chown -R qto:qto /var/qto

USER qto

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
