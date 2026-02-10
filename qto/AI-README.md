# QTO (Quantum Task Orchestrator) - AI Development Guide

## Project Overview

**QTO Platform** is an enterprise Spring Boot service orchestration and order management system for telecommunications and cybersecurity services lifecycle management.

- **Version**: 1.18.1-SNAPSHOT
- **Group ID**: com.endeavor-ms
- **Architecture**: Spring Boot with multi-tenancy
- **Scale**: 140+ JPA entities, 90+ REST endpoints, 15+ service types
- **Build System**: Gradle with Kotlin DSL

## Technology Stack

### Core Technologies
- **Java**: 17
- **Spring Boot**: 3.2.2
- **Application Server**: Embedded Tomcat
- **Database**: PostgreSQL
- **ORM**: Spring Data JPA / Hibernate
- **REST API**: Spring MVC REST
- **Security**: Apache Shiro + Azure AD OAuth 2.0
- **Scheduler**: Quartz 2.3.2
- **Database Migrations**: Liquibase 4.23.0

### Key Frameworks & Libraries
- **QueryDSL 5.x**: Type-safe database queries
- **Jackson 2.14.1**: JSON serialization
- **Apache HttpClient 5.2.1**: External API integration
- **JWT 4.4.0**: Token-based authentication
- **SLF4J 2.0.7**: Logging facade
- **Microsoft Graph API**: User management integration
- **Azure Identity SDK**: Cloud authentication

## Project Structure

```
qto/
├── build.gradle.kts                 # Gradle build configuration
├── qto-spring-boot-app/              # Main Spring Boot application
│   ├── build.gradle.kts             # Gradle build with Kotlin DSL
│   ├── src/main/java/
│   │   └── com/endeavorms/qto/
│   │       ├── QtoApplication.java   # Main application class
│   │       └── controller/          # Spring REST controllers
│   └── src/main/resources/
│       ├── application.yml           # Base configuration
│       ├── application-dev.yml      # Dev profile (no database)
│       └── db/changelog/            # Liquibase migrations
└── .github/                         # GitHub workflows
```

## Prerequisites

### Required Software
- **Java JDK**: 17 or higher
- **Gradle**: 8.5+ (wrapper included)
- **PostgreSQL**: 12+ (optional for dev profile)

### Environment Variables
```bash
export JAVA_HOME=/path/to/jdk-17
export PATH=$JAVA_HOME/bin:$PATH
```

## Database Setup

### 1. Create PostgreSQL Database

```sql
CREATE DATABASE qto ENCODING 'UTF8';
CREATE USER qto_user WITH PASSWORD 'your_password';
GRANT ALL PRIVILEGES ON DATABASE qto TO qto_user;
```

### 2. Configure Application

Database configuration is in `application.yml`. Use environment variables:

```bash
export DB_HOST=localhost
export DB_PORT=5432
export DB_NAME=qto
export DB_USER=qto_user
export DB_PASSWORD=your_password
```

### 3. Liquibase Migrations

Migrations are applied automatically on application startup.

## Build Instructions

### Build and Run

```bash
cd qto-spring-boot-app

# Build
./gradlew clean build

# Run with dev profile (no database required)
./gradlew bootRun --args='--spring.profiles.active=dev'

# Run with database
./gradlew bootRun
```

### Build Output

- **Executable JAR**: `build/libs/qto-spring-boot-app-1.18.1-SNAPSHOT.jar`

## Running the Application

### Application URLs

- **Application Root**: http://localhost:8080/qto
- **REST API Base**: http://localhost:8080/qto/api

### Key API Endpoints

```
GET    /api/orders              # List orders
GET    /api/services            # List services
GET    /api/locations           # List locations
GET    /api/activations         # Activation schedules
POST   /api/orders              # Create order
PUT    /api/orders/{id}         # Update order
DELETE /api/services/{id}       # Delete service
```

### Authentication

QTO uses **Apache Shiro + Azure AD OAuth 2.0**:

1. **Azure AD Configuration**: Set environment variables or system properties
   ```bash
   -Dazure.tenant.id=your-tenant-id
   -Dazure.client.id=your-client-id
   -Dazure.client.secret=your-client-secret
   ```

2. **API Authentication**: Include JWT token in Authorization header
   ```bash
   curl -H "Authorization: Bearer <jwt-token>" \
        http://localhost:8080/qto/api/orders
   ```

## Testing

### Run Tests

```bash
# All tests
./gradlew test

# Specific test class
./gradlew test --tests StatusControllerTest

# With coverage report
./gradlew test jacocoTestReport
```

### REST API Testing

```bash
# Using curl
curl -X GET http://localhost:8080/qto/api/status
curl -X GET http://localhost:8080/qto/api/status/ping
```

## Common Gradle Commands

```bash
# Clean build
./gradlew clean

# Compile only
./gradlew compileJava

# Build without tests
./gradlew build -x test

# Run application
./gradlew bootRun

# Dependency tree
./gradlew dependencies

# List all tasks
./gradlew tasks
```

## Development Workflow

### 1. Code Changes

```bash
# Make code changes in src/main/java/com/endeavorms/qto/
```

### 2. Build and Run

```bash
./gradlew clean build
./gradlew bootRun --args='--spring.profiles.active=dev'
```

### 3. Hot Reload

Spring DevTools enables automatic restart when code changes.

## Troubleshooting

### Build Issues

**Problem**: Gradle build fails
```bash
# Solution: Clean and rebuild
./gradlew clean build --refresh-dependencies
```

**Problem**: Port already in use (8080)
```bash
lsof -i :8080
kill -9 <PID>

# Or change port in application.yml: server.port: 8081
```

### Database Issues

**Problem**: Liquibase migration fails
- Check PostgreSQL is running and credentials are correct
- Use dev profile to run without database: `--spring.profiles.active=dev`

**Problem**: Connection pool exhausted
- Adjust Hikari pool settings in `application.yml`

### Runtime Issues

**Problem**: OutOfMemoryError
```bash
./gradlew bootRun -Dspring-boot.run.jvmArguments="-Xms2g -Xmx4g"
```

## Key Configuration Files

| File | Purpose |
|------|---------|
| `build.gradle.kts` | Gradle build configuration |
| `application.yml` | Spring Boot configuration |
| `application-dev.yml` | Development profile (no database) |
| `db/changelog/db.changelog-master.xml` | Liquibase migrations |

## Multi-Tenant Architecture

QTO implements **platform-level multi-tenancy**:

- **Platform Database**: Stores tenant metadata and user mappings
- **Tenant Databases**: Separate database per tenant (or shared with tenant_id column)
- **Tenant Resolution**: Based on JWT token claims or request headers
- **Data Isolation**: Enforced at JPA layer with @PrePersist/@PreUpdate interceptors

## Background Jobs (Quartz)

Scheduled jobs configuration in `quartz-jobs.xml`:

- **Service Disconnection Job**: Processes pending disconnections
- **Snapshot Job**: Creates service state snapshots
- **Cleanup Job**: Archives old data
- **Invoice Generation Job**: Automated billing

Start/stop jobs via REST API or JMX console.

## Integration Points

1. **Azure AD / Microsoft Graph API**: User authentication and profile management
2. **FTDI Field Services**: Dispatch and appointment management
3. **CRM / Dataverse**: Customer data synchronization
4. **Message Queues**: Asynchronous bulk import operations

## Performance Optimization

- Enable **Hibernate 2nd-level cache** for frequently accessed entities
- Use **QueryDSL** for type-safe, optimized queries
- Configure **connection pooling** appropriately
- Enable **HTTP compression** in Spring Boot/Tomcat
- Use **async processing** for long-running operations

## Code Quality Tools

```bash
# Static analysis
./gradlew sonarqube

# Code coverage
./gradlew jacocoTestReport

# Security scanning
./gradlew dependencyCheckAnalyze

# Code formatting
./gradlew spotlessApply
```

## CI/CD Pipeline

GitHub Actions workflows in `.github/workflows/`:

- `gradle-publish.yml`: Build and publish releases
- Gradle-based workflows for CI/CD

## Additional Resources

- **Spring Boot Documentation**: https://docs.spring.io/spring-boot/
- **Hibernate ORM**: https://hibernate.org/orm/documentation/
- **Apache Shiro**: https://shiro.apache.org/documentation.html
- **Liquibase**: https://docs.liquibase.com/

## Getting Help

For AI assistants working on this codebase:

1. **Understand the domain model first**: Review entity relationships in `qto-core`
2. **Check existing patterns**: Follow established Manager/Service/DAO patterns
3. **Test thoroughly**: Write unit tests for business logic, integration tests for APIs
4. **Database changes**: Always use Liquibase migrations, never manual SQL
5. **Security**: Validate all user inputs, enforce authorization checks
6. **Multi-tenancy**: Ensure tenant isolation in all database operations

## Quick Start Summary

```bash
# 1. Build
cd qto-spring-boot-app
./gradlew clean build

# 2. Run (dev profile - no database required)
./gradlew bootRun --args='--spring.profiles.active=dev'

# 3. Verify
curl http://localhost:8080/qto/api/status
curl http://localhost:8080/qto/actuator/health
```

---

**Last Updated**: 2025-01-23
**Version**: 1.18.1-SNAPSHOT
**Maintained By**: QTO Development Team
