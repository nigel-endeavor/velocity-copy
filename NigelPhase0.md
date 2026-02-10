# Nigel Phase 0 — QTO Build & Run Guide

How to build and run the QTO (Quantum Task Orchestrator) Spring Boot application.

---

## Architecture

QTO is a **service orchestration and order management platform** for telecommunications and cybersecurity services. The backend is a single Spring Boot application with embedded Tomcat.

### ASCII Architecture Diagram

```
                    ┌─────────────────────────────────────────────────┐
                    │                   CLIENTS                         │
                    │  (Browser, curl, qto-ui, help-desk-ui)           │
                    └─────────────────────┬─────────────────────────────┘
                                          │ HTTP
                                          ▼
                    ┌─────────────────────────────────────────────────┐
                    │              QTO Spring Boot App                 │
                    │              (Embedded Tomcat)                   │
                    │              Port 8080 / Context /qto            │
                    │  ┌─────────────────────────────────────────┐   │
                    │  │  REST Controllers                        │   │
                    │  │  /api/status, /api/status/ping           │   │
                    │  └─────────────────┬───────────────────────┘   │
                    │                    │                             │
                    │  ┌─────────────────▼───────────────────────┐   │
                    │  │  Spring Data JPA / Hibernate              │   │
                    │  │  (com.endeavorms.qto)                    │   │
                    │  └─────────────────┬───────────────────────┘   │
                    │                    │                             │
                    │  ┌─────────────────▼───────────────────────┐   │
                    │  │  HikariCP Connection Pool                 │   │
                    │  └─────────────────┬───────────────────────┘   │
                    └────────────────────┼────────────────────────────┘
                                          │ JDBC
                                          ▼
                    ┌─────────────────────────────────────────────────┐
                    │              PostgreSQL Database                 │
                    │              (qto schema)                        │
                    └─────────────────────────────────────────────────┘
```

### Technology Stack

| Layer           | Technology                          |
|----------------|-------------------------------------|
| Runtime        | Java 17, Spring Boot 3.2.2          |
| Web Server     | Embedded Tomcat                     |
| REST API       | Spring MVC                          |
| Persistence    | Spring Data JPA, Hibernate          |
| Database       | PostgreSQL 12+                      |
| Migrations     | Liquibase                           |
| Build          | Gradle 8.5 (Kotlin DSL)             |
| Monitoring     | Spring Actuator (health, metrics)    |

---

## Refactoring History (Johnny Crupi)

### Java EE → Spring Boot

The QTO platform was originally built on **Java EE** (WildFly/JBoss) and has been refactored to **Spring Boot** by Johnny Crupi:

| Before (Java EE)           | After (Spring Boot)                 |
|---------------------------|-------------------------------------|
| WildFly/JBoss app server   | Embedded Tomcat                     |
| JAX-RS (RESTEasy)         | Spring MVC REST                     |
| EJB stateless beans       | Spring @Service / @Component        |
| JNDI datasources          | Spring DataSource + HikariCP        |
| WAR deployment            | Executable JAR                      |
| Maven multi-module        | Gradle single module               |
| ~1.5GB footprint          | ~485MB (67% smaller)                |

### MySQL → PostgreSQL

The database layer was refactored from **MySQL** to **PostgreSQL**:

| Before (MySQL)            | After (PostgreSQL)                  |
|---------------------------|-------------------------------------|
| MySQL Connector/J         | PostgreSQL JDBC driver              |
| MySQL dialect             | `PostgreSQLDialect`                 |
| JNDI datasource config    | Spring `application.yml` + env vars  |

PostgreSQL is now the primary (and default) database. Configuration is via environment variables (`DB_HOST`, `DB_PORT`, `DB_NAME`, `DB_USER`, `DB_PASSWORD`).

---

## Prerequisites

- **Java 17** or higher
- **Gradle 8.5+** (wrapper included with project)

---

## Build

From the project root:

```bash
cd qto/qto-spring-boot-app
./gradlew clean build
```

Or from the `qto-spring-boot-app` directory:

```bash
cd qto/qto-spring-boot-app
./gradlew clean build
```

### Build without tests

```bash
./gradlew clean build -x test
```

### Run tests only

```bash
./gradlew test
```

---

## Run

### Development mode (no database required)

Recommended for local development:

```bash
cd qto/qto-spring-boot-app
./gradlew bootRun --args='--spring.profiles.active=dev'
```

- Application URL: **http://localhost:8080/qto**
- Status: **http://localhost:8080/qto/api/status**
- Health: **http://localhost:8080/qto/actuator/health**

### With PostgreSQL database

1. Set environment variables:

```bash
export DB_HOST=localhost
export DB_PORT=5432
export DB_NAME=qto
export DB_USER=qto_user
export DB_PASSWORD=your_password
```

2. Run:

```bash
cd qto/qto-spring-boot-app
./gradlew bootRun
```

(Ensure PostgreSQL is running and the database exists.)

---

## Run packaged JAR

```bash
cd qto/qto-spring-boot-app
./gradlew bootJar

# Dev profile (no database)
java -jar build/libs/qto-spring-boot-app-1.18.1-SNAPSHOT.jar --spring.profiles.active=dev

# With database (set DB_* env vars first)
java -jar build/libs/qto-spring-boot-app-1.18.1-SNAPSHOT.jar
```

---

## Verify

```bash
# Application status
curl http://localhost:8080/qto/api/status

# Ping
curl http://localhost:8080/qto/api/status/ping

# Health check
curl http://localhost:8080/qto/actuator/health
```

---

## Quick reference

| Command | Description |
|--------|-------------|
| `./gradlew clean build` | Clean and build |
| `./gradlew bootRun --args='--spring.profiles.active=dev'` | Run without database |
| `./gradlew bootRun` | Run with database |
| `./gradlew bootJar` | Build executable JAR |
| `./gradlew test` | Run tests |

---

**Context path:** `/qto`  
**Default port:** `8080`
