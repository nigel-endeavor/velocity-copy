# QTO Database Setup Guide (PostgreSQL)

## 1. Run Liquibase

```bash
cd qto/qto-database
liquibase update
```

## 2. Start PostgreSQL (Docker)

```bash
cd qto
docker compose up -d
```

PostgreSQL is available at `localhost:5432` (user: qto_user, db: qto, password: changeme).

## 3. Create a `liquibase.properties` file

Create `qto/qto-database/liquibase.properties` (or use the one in the repo):

```properties
# Database connection (PostgreSQL)
url=jdbc:postgresql://localhost:5432/qto
username=qto_user
password=changeme
driver=org.postgresql.Driver

# Liquibase changelog
changeLogFile=database/changelog-master.xml
searchPath=src/main/resources

# Contexts (IMPORTANT)
contexts=common,dev
```

## 4. Full Database Reset (Development Only)

Run the following SQL statements in PostgreSQL:

```sql
DROP DATABASE IF EXISTS qto;

CREATE DATABASE qto;

CREATE USER qto_user WITH PASSWORD 'changeme';
GRANT ALL PRIVILEGES ON DATABASE qto TO qto_user;
```

The platform schema (tenant, subject) is created by Liquibase within the qto database.

## 5. Platform Schema

The Liquibase changelog in `1_0_0/changelog-master.xml` includes the platform schema bootstrap (tenant, subject tables). No manual table creation is needed if running Liquibase update.

## 6. Run the Application

```bash
cd qto
./gradlew :qto-app:bootRun
```

The app uses PostgreSQL for both `qto` and `platform` datasources. Liquibase runs automatically on startup via `QtoLiquibaseConfig`.
