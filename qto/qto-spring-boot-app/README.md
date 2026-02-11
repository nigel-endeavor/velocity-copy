# QTO Spring Boot Application

Modern Spring Boot implementation of the Quantum Task Orchestrator (QTO) platform for Endeavor Managed Services.

## Status

✅ **OPERATIONAL** - Phase 1 Foundation Complete

- Spring Boot 3.2.2
- Java 17
- Gradle 8.5+ build system
- Package: `com.endeavorms.qto`
- Embedded Tomcat web server
- Spring Actuator monitoring
- Development profile for local testing

## Quick Start

### Prerequisites

- Java 17 or higher
- Gradle 8.5+ (or use included wrapper)
- PostgreSQL 12+ (for production)

### Development Mode (No Database Required)

```bash
# Run with dev profile using Gradle wrapper
./gradlew bootRun --args='--spring.profiles.active=dev'

# Application starts on http://localhost:8080/qto
```

### Production Mode (PostgreSQL Required)

```bash
# Set database password
export DB_PASSWORD=your_password

# Run with default profile
./gradlew bootRun

# Or specify production profile explicitly
./gradlew bootRun --args='--spring.profiles.active=prod'
```

## Endpoints

### Application Endpoints

| Endpoint | Method | Description |
|----------|--------|-------------|
| `/api/status` | GET | Application status and version info |
| `/api/status/ping` | GET | Simple connectivity check |

### Actuator Endpoints

| Endpoint | Method | Description |
|----------|--------|-------------|
| `/actuator/health` | GET | Health check with component details |
| `/actuator/info` | GET | Application information |
| `/actuator/metrics` | GET | Application metrics |

### Example Requests

```bash
# Check application status
curl http://localhost:8080/qto/api/status

# Ping test
curl http://localhost:8080/qto/api/status/ping

# Health check
curl http://localhost:8080/qto/actuator/health
```

## Configuration

### Profiles

- **dev** - Development mode without database (default for local development)
- **default** - Standard mode with PostgreSQL database
- **prod** - Production mode (to be configured in Phase 6)

### Application Properties

Key configuration properties in `application.yml`:

```yaml
spring:
  application:
    name: qto-application
  datasource:
    url: jdbc:postgresql://localhost:5432/qto
    username: qto_user
    password: ${QTO_DB_PASSWORD}

server:
  port: 8080
  servlet:
    context-path: /qto

qto:
  version: 1.18.1-SNAPSHOT
  migration:
    phase: 1-foundation
```

## Build Commands

```bash
# Clean and compile
./gradlew clean compileJava

# Run tests
./gradlew test

# Build executable JAR
./gradlew bootJar

# Run the application
./gradlew bootRun

# Run with specific profile
./gradlew bootRun --args='--spring.profiles.active=dev'

# Build without tests
./gradlew build -x test
```

## Project Structure

```
qto-spring-boot-app/
├── build.gradle.kts                          # Gradle build configuration
├── settings.gradle.kts                       # Gradle settings
├── gradle.properties                         # Gradle properties
├── gradlew                                   # Gradle wrapper (Unix)
├── gradlew.bat                               # Gradle wrapper (Windows)
├── src/
│   ├── main/
│   │   ├── java/com/endeavorms/qto/
│   │   │   ├── QtoApplication.java          # Main application class
│   │   │   └── controller/
│   │   │       └── StatusController.java    # Status REST endpoints
│   │   └── resources/
│   │       ├── application.yml              # Default configuration
│   │       └── application-dev.yml          # Dev profile configuration
│   └── test/
│       └── java/                            # Unit tests (to be added)
├── .gitignore                               # Git ignore patterns
└── README.md                                # This file
```

## Development

### Adding a REST Controller

1. Create controller class in `src/main/java/com/endeavorms/qto/controller/`
2. Annotate with `@RestController` and `@RequestMapping`
3. Rebuild: `./gradlew clean compileJava`
4. Restart application

Example:

```java
package com.endeavorms.qto.controller;

@RestController
@RequestMapping("/api/example")
public class ExampleController {

    @GetMapping
    public ResponseEntity<String> example() {
        return ResponseEntity.ok("Hello from QTO!");
    }
}
```

### Hot Reload

Spring DevTools enables automatic restart when code changes:

1. Make code changes
2. Save file
3. Application automatically restarts (takes ~2 seconds)

## Migration Roadmap

- ✅ **Phase 1: Foundation** - Basic Spring Boot application
  - ✅ Spring Boot 3.2.2 setup
  - ✅ Gradle build system
  - ✅ Package structure: `com.endeavorms.qto`
  - ✅ REST endpoints
  - ✅ Development profile
- ⏳ **Phase 2: Database** - Dual datasource configuration
- ⏳ **Phase 3: JPA Repositories** - Data access layer
- ⏳ **Phase 4: Service Layer** - Business logic
- ⏳ **Phase 5: REST API** - Migrate JAX-RS to Spring REST
- ⏳ **Phase 6: Security** - Spring Security integration

## Troubleshooting

### Application won't start

**Problem**: `Cannot load driver class: org.postgresql.Driver`

**Solution**: Use dev profile or ensure PostgreSQL is running:
```bash
./gradlew bootRun --args='--spring.profiles.active=dev'
```

### Port 8080 already in use

**Solution**: Kill existing process or change port:
```bash
# Kill existing process
lsof -ti:8080 | xargs kill -9

# Or change port in application.yml
server.port: 8081
```

### Gradle build fails

**Solution**: Clean Gradle cache and rebuild:
```bash
./gradlew clean build --refresh-dependencies
```

### Gradle wrapper not executable

**Solution**: Make wrapper executable:
```bash
chmod +x gradlew
```

## Testing

```bash
# Run all tests
./gradlew test

# Run specific test class
./gradlew test --tests QtoApplicationTests

# Run with coverage report
./gradlew test jacocoTestReport
```

## Monitoring

### Application Metrics

Access Micrometer metrics at `/actuator/metrics`:

```bash
# View available metrics
curl http://localhost:8080/qto/actuator/metrics

# View specific metric
curl http://localhost:8080/qto/actuator/metrics/jvm.memory.used
```

### Health Checks

```bash
# Detailed health check
curl http://localhost:8080/qto/actuator/health

# Response includes disk space, database (if configured), and custom checks
```

## Gradle Tasks

```bash
# List all available tasks
./gradlew tasks

# Build info
./gradlew buildEnvironment

# Dependency tree
./gradlew dependencies

# Show project properties
./gradlew properties
```

## Contributing

1. Create feature branch from `main`
2. Make changes following existing code style
3. Add/update tests
4. Run `./gradlew test` to verify
5. Commit with descriptive message
6. Create pull request

## Migration from Maven

This project has been migrated from Maven to Gradle:
- All `pom.xml` files replaced with `build.gradle.kts`
- Package structure updated from `com.endeavorms.velocity` to `com.endeavorms`
- Build commands changed from `mvn` to `./gradlew`

## Support

For issues or questions:
- Check this README
- Review application logs in console
- Check `/actuator/health` for component status
- Review Gradle build logs: `./gradlew build --stacktrace`
- Contact development team

## License

Copyright © 2024 Endeavor Managed Services. All rights reserved.
