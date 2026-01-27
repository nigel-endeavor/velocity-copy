# QTO (Quantum Task Orchestrator) - Spring Boot Application

**Modern Spring Boot microservice** for telecommunications and cybersecurity service orchestration and order management.

## Quick Start

```bash
# Clone the repository
cd qto-spring-boot-app

# Run without database (development mode)
./gradlew bootRun --args='--spring.profiles.active=dev'

# Run with MySQL
export SPRING_PROFILES_ACTIVE=mysql
export DB_PASSWORD=your_password
./gradlew bootRun

# Run with PostgreSQL
export SPRING_PROFILES_ACTIVE=postgres
export DB_PASSWORD=your_password
./gradlew bootRun

# Run tests (no database required)
./gradlew test
```

## Technology Stack

- **Java**: 17
- **Spring Boot**: 3.2.2
- **Build**: Gradle 8.5 (Kotlin DSL)
- **Database**: MySQL 8+ or PostgreSQL 12+ (configurable)
- **ORM**: Spring Data JPA / Hibernate
- **Testing**: JUnit 5, MockMvc, TestRestTemplate
- **Monitoring**: Spring Actuator

## Project Structure

```
qto/
├── README.md                           # This file
├── AI-README.md                        # AI development guide (legacy reference)
├── .github/                            # GitHub workflows
└── qto-spring-boot-app/                # Main Spring Boot application
    ├── build.gradle.kts                # Gradle build configuration
    ├── settings.gradle.kts             # Gradle settings
    ├── README.md                       # Application-specific documentation
    ├── DATABASE-CONFIG.md              # Database setup guide
    ├── TEST-SUMMARY.md                 # Test suite documentation
    ├── MIGRATION.md                    # Migration strategy documentation
    ├── src/
    │   ├── main/
    │   │   ├── java/com/endeavorms/qto/
    │   │   │   ├── QtoApplication.java         # Main application class
    │   │   │   └── controller/
    │   │   │       └── StatusController.java   # REST endpoints
    │   │   └── resources/
    │   │       ├── application.yml             # Base configuration
    │   │       ├── application-dev.yml         # Dev profile (no DB)
    │   │       ├── application-mysql.yml       # MySQL configuration
    │   │       ├── application-postgres.yml    # PostgreSQL configuration
    │   │       └── application-test.yml        # Test profile
    │   └── test/
    │       └── java/com/endeavorms/qto/        # Comprehensive test suite
    └── gradle/                          # Gradle wrapper
```

## Available Endpoints

### Application Endpoints
- `GET /qto/api/status` - Application status and health
- `GET /qto/api/status/ping` - Connectivity check

### Actuator Endpoints
- `GET /qto/actuator/health` - Health check
- `GET /qto/actuator/info` - Application info
- `GET /qto/actuator/metrics` - Performance metrics

## Database Configuration

The application supports **both MySQL and PostgreSQL** through Spring profiles.

### Development (No Database)
```bash
./gradlew bootRun --args='--spring.profiles.active=dev'
```

### MySQL
```bash
export SPRING_PROFILES_ACTIVE=mysql
export DB_HOST=localhost
export DB_PORT=3306
export DB_NAME=qto
export DB_USER=qto_user
export DB_PASSWORD=your_password
./gradlew bootRun
```

### PostgreSQL
```bash
export SPRING_PROFILES_ACTIVE=postgres
export DB_HOST=localhost
export DB_PORT=5432
export DB_NAME=qto
export DB_USER=qto_user
export DB_PASSWORD=your_password
./gradlew bootRun
```

See [DATABASE-CONFIG.md](qto-spring-boot-app/DATABASE-CONFIG.md) for complete setup instructions including Docker Compose examples.

## Testing

Comprehensive test suite with **40 tests** covering:
- Unit tests (MockMvc)
- Integration tests (full application context)
- Actuator endpoint tests
- Configuration validation tests

```bash
# Run all tests
./gradlew test

# Run specific test class
./gradlew test --tests StatusControllerTest

# Run with coverage
./gradlew test jacocoTestReport
```

**All tests pass without a database connection!**

See [TEST-SUMMARY.md](qto-spring-boot-app/TEST-SUMMARY.md) for detailed test documentation.

## Development

### Prerequisites
- **Java**: JDK 17 or higher
- **Gradle**: 8.5+ (wrapper included)
- **Database**: MySQL 8+ or PostgreSQL 12+ (optional for dev)

### Build Commands
```bash
# Compile
./gradlew compileJava

# Build JAR
./gradlew bootJar

# Clean build
./gradlew clean build

# Run application
./gradlew bootRun
```

### IDE Setup

**IntelliJ IDEA**:
1. Open project root directory
2. Gradle will auto-import
3. Set Project SDK to Java 17
4. Add environment variable: `SPRING_PROFILES_ACTIVE=dev`

**VS Code**:
1. Install "Extension Pack for Java"
2. Open project root
3. Configure launch.json with environment variables

## Docker

```bash
# Build
docker build -t qto-app:latest qto-spring-boot-app/

# Run with dev profile (no database)
docker run -p 8080:8080 -e SPRING_PROFILES_ACTIVE=dev qto-app:latest

# Run with MySQL
docker run -p 8080:8080 \
  -e SPRING_PROFILES_ACTIVE=mysql \
  -e DB_HOST=host.docker.internal \
  -e DB_PASSWORD=your_password \
  qto-app:latest
```

## Migration Status

✅ **Phase 1 Complete**: Spring Boot Foundation
- Migrated from Maven to Gradle
- Migrated from Java EE/WildFly to Spring Boot
- Refactored packages from `com.vertek.corporate.qto` to `com.endeavorms.qto`
- Created comprehensive test suite (40 tests)
- Added multi-database support (MySQL & PostgreSQL)
- Removed all legacy JBoss/Maven dependencies

See [MIGRATION.md](qto-spring-boot-app/MIGRATION.md) for migration strategy.

## API Documentation

Current Phase 1 endpoints:

**Status API**:
```bash
# Get application status
curl http://localhost:8080/qto/api/status

# Response
{
  "application": "qto-application-dev",
  "version": "1.18.1-SNAPSHOT",
  "status": "OPERATIONAL",
  "phase": "1-foundation",
  "message": "QTO Spring Boot application is running",
  "timestamp": "2026-01-27T..."
}

# Ping endpoint
curl http://localhost:8080/qto/api/status/ping

# Response
{
  "message": "pong",
  "timestamp": "2026-01-27T..."
}
```

**Health Check**:
```bash
curl http://localhost:8080/qto/actuator/health

# Response
{
  "status": "UP"
}
```

## Contributing

### Code Style
- Java 17 features encouraged
- Follow Spring Boot best practices
- Maintain test coverage >80%
- Use Lombok for boilerplate reduction

### Testing Requirements
- All new features must have unit tests
- Integration tests for REST endpoints
- No tests should require external database

### Git Workflow
- Branch: `feature/spring` (current)
- Main branch: `main`
- Commit with descriptive messages

## License

Enterprise software - All rights reserved

## Support

For issues and questions, see project documentation in `qto-spring-boot-app/README.md`

---

**Version**: 1.18.1-SNAPSHOT
**Last Updated**: January 27, 2026
**Status**: Phase 1 Complete ✅
