# QTO (Quantum Task Orchestrator)

**Enterprise service orchestration and order management** for telecommunications and cybersecurity services lifecycle management.

## Project Structure

```
qto/
├── qto-core/          # Domain entities, managers, DAOs, REST controllers (140+ entities)
├── qto-database/      # Liquibase migrations
├── qto-app/           # Spring Boot runnable application
└── build.gradle.kts
```

## Technology Stack

- **Java**: 21
- **Build**: Gradle (Kotlin DSL)
- **ORM**: Spring Data JPA / Hibernate
- **REST**: Spring MVC
- **Database**: PostgreSQL 12+

## Build

```bash
# From qto/ directory
./gradlew build

# Run tests
./gradlew test
```

## Docker (PostgreSQL only)

```bash
# From qto/ directory - start PostgreSQL
docker compose up -d

# Stop
docker compose down
```

PostgreSQL is available at `localhost:5432` (user: qto_user, db: qto).

## Modules

- **qto-core**: Business logic, entities, managers, REST controllers (Spring Data JPA, Spring MVC)
- **qto-database**: Liquibase changelogs by version
- **qto-app**: Spring Boot application (runnable JAR)

## Run

```bash
# Start PostgreSQL
docker compose up -d

# Run the application
./gradlew :qto-app:bootRun
```

API available at `http://localhost:8080/api`

---

**Version**: 1.18.1-SNAPSHOT
**Group**: com.endeavorms.velocity
