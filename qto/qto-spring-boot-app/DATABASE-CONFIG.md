# Database Configuration Guide

## Supported Databases

The QTO Spring Boot application supports both **MySQL** and **PostgreSQL** through Spring profiles.

## Quick Start

### Using MySQL
```bash
# Set environment variables
export SPRING_PROFILES_ACTIVE=mysql
export DB_HOST=localhost
export DB_PORT=3306
export DB_NAME=qto
export DB_USER=qto_user
export DB_PASSWORD=your_password

# Run the application
./gradlew bootRun
```

### Using PostgreSQL
```bash
# Set environment variables
export SPRING_PROFILES_ACTIVE=postgres
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

## Configuration Profiles

### application-mysql.yml
MySQL 8+ configuration with default settings:
- **URL**: `jdbc:mysql://localhost:3306/qto`
- **Driver**: `com.mysql.cj.jdbc.Driver`
- **Dialect**: `MySQL8Dialect`

### application-postgres.yml
PostgreSQL 12+ configuration with default settings:
- **URL**: `jdbc:postgresql://localhost:5432/qto`
- **Driver**: `org.postgresql.Driver`
- **Dialect**: `PostgreSQLDialect`

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

All database profiles support these environment variables:

| Variable | Description | Default |
|----------|-------------|---------|
| `SPRING_PROFILES_ACTIVE` | Active profile (mysql/postgres/dev) | none |
| `DB_HOST` | Database host | localhost |
| `DB_PORT` | Database port | 3306 (MySQL) / 5432 (Postgres) |
| `DB_NAME` | Database name | qto |
| `DB_USER` | Database username | qto_user |
| `DB_PASSWORD` | Database password | changeme |

## Database Setup

### MySQL Setup
```sql
-- Create database
CREATE DATABASE qto CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Create user and grant privileges
CREATE USER 'qto_user'@'localhost' IDENTIFIED BY 'your_password';
GRANT ALL PRIVILEGES ON qto.* TO 'qto_user'@'localhost';
FLUSH PRIVILEGES;
```

### PostgreSQL Setup
```sql
-- Create database
CREATE DATABASE qto ENCODING 'UTF8' LC_COLLATE='en_US.UTF-8' LC_CTYPE='en_US.UTF-8';

-- Create user
CREATE USER qto_user WITH PASSWORD 'your_password';

-- Grant privileges
GRANT ALL PRIVILEGES ON DATABASE qto TO qto_user;
```

## Running with Different Databases

### Command Line
```bash
# MySQL
./gradlew bootRun --args='--spring.profiles.active=mysql'

# PostgreSQL
./gradlew bootRun --args='--spring.profiles.active=postgres'

# Development (no database)
./gradlew bootRun --args='--spring.profiles.active=dev'
```

### IDE Configuration

**IntelliJ IDEA / Eclipse**:
- Add environment variable: `SPRING_PROFILES_ACTIVE=mysql` (or `postgres`)
- Add database connection variables as needed

**VS Code**:
Add to `launch.json`:
```json
{
  "type": "java",
  "name": "QtoApplication (MySQL)",
  "request": "launch",
  "mainClass": "com.endeavorms.qto.QtoApplication",
  "env": {
    "SPRING_PROFILES_ACTIVE": "mysql",
    "DB_HOST": "localhost",
    "DB_PASSWORD": "your_password"
  }
}
```

## Docker Compose

Example `docker-compose.yml` for local development:

### MySQL
```yaml
version: '3.8'
services:
  mysql:
    image: mysql:8
    environment:
      MYSQL_ROOT_PASSWORD: rootpassword
      MYSQL_DATABASE: qto
      MYSQL_USER: qto_user
      MYSQL_PASSWORD: changeme
    ports:
      - "3306:3306"
    volumes:
      - mysql-data:/var/lib/mysql

  qto-app:
    image: openjdk:17-jdk-slim
    working_dir: /app
    environment:
      SPRING_PROFILES_ACTIVE: mysql
      DB_HOST: mysql
      DB_PASSWORD: changeme
    depends_on:
      - mysql
    ports:
      - "8080:8080"

volumes:
  mysql-data:
```

### PostgreSQL
```yaml
version: '3.8'
services:
  postgres:
    image: postgres:15
    environment:
      POSTGRES_DB: qto
      POSTGRES_USER: qto_user
      POSTGRES_PASSWORD: changeme
    ports:
      - "5432:5432"
    volumes:
      - postgres-data:/var/lib/postgresql/data

  qto-app:
    image: openjdk:17-jdk-slim
    working_dir: /app
    environment:
      SPRING_PROFILES_ACTIVE: postgres
      DB_HOST: postgres
      DB_PASSWORD: changeme
    depends_on:
      - postgres
    ports:
      - "8080:8080"

volumes:
  postgres-data:
```

## Testing

The test suite runs with the `test` profile and does NOT require a database:

```bash
./gradlew test
```

All 40 tests will pass without any database connection.

## Switching Databases

To switch from MySQL to PostgreSQL (or vice versa):

1. **Change the profile**:
   ```bash
   export SPRING_PROFILES_ACTIVE=postgres  # or mysql
   ```

2. **Update environment variables** (if different):
   ```bash
   export DB_HOST=your-postgres-host
   export DB_PORT=5432
   export DB_PASSWORD=your-password
   ```

3. **Restart the application**:
   ```bash
   ./gradlew bootRun
   ```

No code changes required! The application automatically uses the correct driver and dialect based on the active profile.

## Troubleshooting

### Connection Refused
- Verify database is running: `mysql -h localhost -u qto_user -p` or `psql -h localhost -U qto_user -d qto`
- Check `DB_HOST` and `DB_PORT` environment variables
- Verify firewall rules allow connection

### Authentication Failed
- Check `DB_USER` and `DB_PASSWORD` environment variables
- Verify user has proper privileges on the database

### Wrong Dialect
- Ensure correct profile is active (mysql or postgres)
- Check `SPRING_PROFILES_ACTIVE` environment variable

### Database Does Not Exist
- Create the database using the SQL commands above
- Verify `DB_NAME` environment variable matches the database name

---

**Last Updated**: January 27, 2026
**Version**: 1.0.0
