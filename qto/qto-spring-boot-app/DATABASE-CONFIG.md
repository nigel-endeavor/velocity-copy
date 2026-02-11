# Database Configuration Guide

## Supported Database

The QTO Spring Boot application uses **PostgreSQL** as its database. The project has fully migrated from MySQL to PostgreSQL.

## Quick Start

### Using PostgreSQL (Default)
```bash
# Set environment variables
export DB_HOST=localhost
export DB_PORT=5432
export DB_NAME=qto
export DB_USER=qto_user
export DB_PASSWORD=your_password

# Run the application
./gradlew bootRun
```

### Development Mode (No Database)
```bash
# Use dev profile - database is disabled
export SPRING_PROFILES_ACTIVE=dev

# Run the application
./gradlew bootRun
```

## Configuration

### application.yml (Default)
PostgreSQL configuration:
- **URL**: `jdbc:postgresql://localhost:5432/qto`
- **Driver**: `org.postgresql.Driver`
- **Dialect**: `PostgreSQLDialect`
- **Quartz**: Uses `PostgreSQLDelegate` for job store

### application-dev.yml
Development profile with database disabled:
- No datasource required
- Excludes DataSource and JPA autoconfiguration
- Perfect for testing REST endpoints

### application-test.yml
Test profile used by unit/integration tests:
- No datasource required
- Random port allocation for parallel execution
- Used automatically by test suite

## Environment Variables

| Variable | Description | Default |
|----------|-------------|---------|
| `DB_HOST` | Database host | localhost |
| `DB_PORT` | Database port | 5432 |
| `DB_NAME` | Database name | qto |
| `DB_USER` | Database username | qto_user |
| `DB_PASSWORD` | Database password | changeme |

## Database Setup

### PostgreSQL Setup
```sql
-- Create database
CREATE DATABASE qto ENCODING 'UTF8' LC_COLLATE='en_US.UTF-8' LC_CTYPE='en_US.UTF-8';

-- Create user
CREATE USER qto_user WITH PASSWORD 'your_password';

-- Grant privileges
GRANT ALL PRIVILEGES ON DATABASE qto TO qto_user;

-- Connect to qto database and grant schema privileges
\c qto
GRANT ALL ON SCHEMA public TO qto_user;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO qto_user;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO qto_user;
```

## Running the Application

### Command Line
```bash
# With PostgreSQL
./gradlew bootRun

# Development (no database)
./gradlew bootRun --args='--spring.profiles.active=dev'
```

### IDE Configuration

**IntelliJ IDEA / Eclipse**:
- Add environment variables: `DB_HOST`, `DB_PASSWORD`, etc.
- Run `QtoApplication.main()`

**VS Code**:
Add to `launch.json`:
```json
{
  "type": "java",
  "name": "QtoApplication (PostgreSQL)",
  "request": "launch",
  "mainClass": "com.endeavorms.qto.QtoApplication",
  "env": {
    "DB_HOST": "localhost",
    "DB_PASSWORD": "your_password"
  }
}
```

## Docker Compose

A full Docker setup (app + PostgreSQL) is in the `qto/` directory:

```bash
# From the qto/ directory
cd qto
docker compose up -d

# App: http://localhost:8080/qto
# API status: http://localhost:8080/qto/api/status
```

See `../README.md` or `../docker-compose.yml` for details.

## Testing

The test suite runs with the `test` profile and does NOT require a database:

```bash
./gradlew test
```

All 40 tests will pass without any database connection.

## Troubleshooting

### Connection Refused
- Verify database is running: `psql -h localhost -U qto_user -d qto`
- Check `DB_HOST` and `DB_PORT` environment variables
- Verify firewall rules allow connection

### Authentication Failed
- Check `DB_USER` and `DB_PASSWORD` environment variables
- Verify user has proper privileges on the database

### Database Does Not Exist
- Create the database using the SQL commands above
- Verify `DB_NAME` environment variable matches the database name

---

**Last Updated**: February 10, 2026
**Version**: 1.0.0
**Database**: PostgreSQL only (migrated from MySQL)
