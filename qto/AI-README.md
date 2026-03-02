# QTO (Quantum Task Orchestrator) - AI Development Guide

> **Migration Notice (2026)**: The project uses **Gradle**, **PostgreSQL**, and **Spring Boot** only. Core modules: qto-core, qto-database, qto-app. No J2EE, JBoss, WildFly, or Java EE.

## Project Overview

**QTO Platform** is an enterprise service orchestration and order management system for telecommunications and cybersecurity services lifecycle management.

- **Version**: 1.18.1-SNAPSHOT
- **Group ID**: com.endeavorms.velocity
- **Architecture**: Spring Boot with multi-tenancy
- **Scale**: 140+ JPA entities, 90+ REST endpoints, 15+ service types
- **Build System**: Gradle
- **Database**: PostgreSQL 12+

## Technology Stack

### Core Technologies (Current)
- **Java**: 21
- **Framework**: Spring Boot 3.2.2
- **Database**: PostgreSQL 12+
- **ORM**: JPA 2.2 / Hibernate 6.2
- **REST API**: Spring MVC
- **Security**: Spring Security
- **Scheduler**: Quartz 2.3.2
- **Database Migrations**: Liquibase 4.23.0

### Key Frameworks & Libraries
- **QueryDSL 5.x**: Type-safe database queries
- **Jackson 2.14.1**: JSON serialization
- **Apache HttpClient 5.2.1**: External API integration
- **JWT 4.4.0**: Token-based authentication
- **SLF4J 2.0.7**: Logging facade

## Project Structure

```
qto/
├── qto-core/                        # Domain entities, managers, REST controllers
│   └── src/main/java/com/endeavorms/velocity/qto/
│       ├── common/                  # Common utilities and base classes
│       ├── service/                 # Service domain (DIA, Broadband, etc.)
│       ├── order/                   # Order management
│       ├── location/                # Location management
│       ├── activation/              # Activation workflows
│       ├── invoicing/               # Billing and invoicing
│       └── ...                      # Other business domains
├── qto-database/                    # Liquibase migrations
│   └── src/main/resources/database/
│       └── [version]/               # Changelogs by version
└── qto-app/                         # Spring Boot runnable application
    └── src/main/
        ├── java/                    # QtoApplication main class
        └── resources/                # application.yml
```

## Prerequisites

### Required Software
- **Java JDK**: 21 or higher
- **Gradle**: 8.5+ (wrapper included)
- **PostgreSQL**: 12+

### Environment Variables
```bash
export JAVA_HOME=/path/to/jdk-21
export PATH=$JAVA_HOME/bin:$PATH
```

## Database Setup

### 1. PostgreSQL (Docker)

```bash
cd qto && docker compose up -d
```

PostgreSQL at `localhost:5432` (user: qto_user, db: qto, password: changeme).

### 2. Spring Boot Configuration

Datasource is configured in `qto-app/src/main/resources/application.yml`. Liquibase runs automatically on startup.

## Build & Run

```bash
# From qto/ directory
./gradlew build -x test

# Run the application
./gradlew :qto-app:bootRun
```

## Running the Application

### Application URLs

- **Application Root**: http://localhost:8080
- **REST API Base**: http://localhost:8080/api

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

QTO uses **Spring Security** for authentication and authorization.

2. **API Authentication**: Include JWT token in Authorization header
   ```bash
   curl -H "Authorization: Bearer <jwt-token>" http://localhost:8080/api/orders
   ```

## Testing

```bash
./gradlew test
```

## Troubleshooting

**Problem**: Build fails
```bash
./gradlew clean build -x test
- Verify all required JARs are in `WEB-INF/lib`

**Problem**: Datasource not found
- Verify datasource configuration in `application.yml`
- Verify PostgreSQL driver and connection settings

**Problem**: Port already in use (8080)
```bash
# Find process using port
lsof -i :8080
kill -9 <PID>

# Or change server.port in application.yml
```

### Database Issues

**Problem**: Liquibase migration fails
```bash
# Manually run migrations with verbose output
cd qto-database
mvn liquibase:update -X

# Rollback last changeset
mvn liquibase:rollback -Dliquibase.rollbackCount=1
```

**Problem**: Connection pool exhausted
- Increase pool size in datasource configuration
- Check for connection leaks in code (unclosed EntityManagers)

### Runtime Issues

**Problem**: OutOfMemoryError
```bash
# Increase JVM heap size
export JAVA_OPTS="-Xms2g -Xmx4g -XX:MetaspaceSize=512m"
./gradlew :qto-app:bootRun
```

**Problem**: Authentication fails
- Verify Spring Security configuration
- Check credentials and session configuration

## Key Configuration Files

| File | Purpose |
|------|---------|
| `build.gradle.kts` | Gradle build and dependencies |
| `application.yml` | Spring Boot datasource and config |
| `persistence.xml` | JPA configuration and entity mappings |

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

1. **User management**: Subject and tenant management
2. **FTDI Field Services**: Dispatch and appointment management
3. **CRM / Dataverse**: Customer data synchronization
4. **JMS Queues**: Asynchronous bulk import operations

## Performance Optimization

- Enable **Hibernate 2nd-level cache** for frequently accessed entities
- Use **QueryDSL** for type-safe, optimized queries
- Configure **connection pooling** appropriately
- Enable **HTTP compression** in Spring Boot
- Use **async processing** for long-running operations

## Code Quality Tools

```bash
# Static analysis
mvn sonar:sonar

# Code coverage
mvn jacoco:report

# Security scanning
mvn dependency-check:check

# Code formatting
mvn spotless:apply
```

## CI/CD Pipeline

GitHub Actions workflows in `.github/workflows/`:

- `maven-publish.yml`: Build and publish releases
- `maven-publish-test-snapshot.yml`: Publish snapshots
- `maven-deploy-release.yml`: Deploy releases

## Additional Resources

- **Spring Boot Documentation**: https://docs.spring.io/spring-boot/
- **Hibernate ORM**: https://hibernate.org/orm/documentation/
- **Spring Security**: https://docs.spring.io/spring-security/reference/
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
# 1. Start PostgreSQL
cd qto && docker compose up -d

# 2. Run application
./gradlew :qto-app:bootRun

# 3. Build
cd qto
./gradlew clean build

# 4. Verify deployment
curl http://localhost:8080/qto/api/health

# 5. Monitor logs
# Logs appear in console when using bootRun
```

---

**Last Updated**: 2025-01-23
**Version**: 1.18.1-SNAPSHOT
**Maintained By**: QTO Development Team
