# Build stage
FROM eclipse-temurin:21-jdk-jammy AS builder
WORKDIR /app

COPY gradlew .
COPY gradle gradle
COPY build.gradle.kts .
COPY settings.gradle.kts .
COPY qto-core qto-core
COPY qto-database qto-database
COPY qto-app qto-app

# Fix wrapper permissions + CRLF endings (Windows checkout safe)
RUN sed -i 's/\r$//' gradlew && chmod +x gradlew

RUN ./gradlew clean :qto-app:bootJar --no-daemon -x test

# Run stage
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# Copy the built jar
COPY --from=builder /app/qto-app/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]