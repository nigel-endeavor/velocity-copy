# QTO (Quantum Task Orchestrator) - AI Development Guide

> **Migration Notice (2026)**: The project has migrated to **Spring Boot**, **Gradle**, and **PostgreSQL**. The qto-spring-boot-app is the main deployment. Legacy Java EE/WildFly/MySQL sections below are for reference only.

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
- **REST API**: JAX-RS (RESTEasy)
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
├── pom.xml                          # Parent POM (aggregator)
├── wildfly/                         # Bundled WildFly application server
│   ├── bin/                         # Server scripts (standalone.sh, jboss-cli.sh)
│   └── standalone/
│       └── configuration/           # Server configuration files
│           └── standalone.xml       # Main server config (datasources, security)
├── qto-database/                    # Database migrations module
│   └── src/main/resources/database/
│       └── [version]/               # Liquibase changelogs by version
├── qto-core/                        # Core domain entities and services
│   └── src/main/java/
│       └── com/vertek/corporate/qto/
│           ├── common/              # Common utilities and base classes
│           ├── service/             # Service domain (DIA, Broadband, etc.)
│           ├── order/               # Order management
│           ├── location/            # Location management
│           ├── activation/          # Activation workflows
│           ├── invoicing/           # Billing and invoicing
│           └── ...                  # Other business domains
├── qto-rest-api/                    # REST API endpoints
│   └── src/main/
│       ├── java/                    # JAX-RS resources
│       └── resources/
│           └── META-INF/
│               └── persistence.xml  # JPA configuration
├── qto-help-desk/                   # Help desk functionality module
├── qto-war/                         # Web application packaging
│   └── src/main/webapp/
│       └── WEB-INF/
│           ├── web.xml              # Servlet configuration
│           ├── jboss-web.xml        # JBoss-specific config
│           └── jboss-deployment-structure.xml  # Module dependencies
└── qto-spring-boot/                 # Spring Boot module (experimental)
```

## Prerequisites

### Required Software
- **Java JDK**: 21 or higher
- **Gradle**: 8.5+ (wrapper included)
- **PostgreSQL**: 12+

### Environment Variables
```bash
export JAVA_HOME=/path/to/jdk-11
export MAVEN_HOME=/path/to/maven
export PATH=$JAVA_HOME/bin:$MAVEN_HOME/bin:$PATH
```

## Database Setup

### 1. Create MySQL Databases

QTO uses **two databases** with separate datasources:

```sql
-- Main tenant-specific database
CREATE DATABASE qto CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Platform-level multi-tenant database
CREATE DATABASE platform CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Create database user
CREATE USER 'qto_user'@'localhost' IDENTIFIED BY 'your_password';
GRANT ALL PRIVILEGES ON qto.* TO 'qto_user'@'localhost';
GRANT ALL PRIVILEGES ON platform.* TO 'qto_user'@'localhost';
FLUSH PRIVILEGES;
```

### 2. Configure WildFly Datasources

Edit `wildfly/standalone/configuration/standalone.xml` and add/configure datasources:

```xml
<subsystem xmlns="urn:jboss:domain:datasources:6.0">
    <datasources>
        <!-- Main QTO Datasource -->
        <datasource jndi-name="java:jboss/datasources/qto"
                    pool-name="qto"
                    enabled="true">
            <connection-url>jdbc:mysql://localhost:3306/qto?useSSL=false</connection-url>
            <driver>mysql</driver>
            <security>
                <user-name>qto_user</user-name>
                <password>your_password</password>
            </security>
            <validation>
                <valid-connection-checker class-name="org.jboss.jca.adapters.jdbc.extensions.mysql.MySQLValidConnectionChecker"/>
                <exception-sorter class-name="org.jboss.jca.adapters.jdbc.extensions.mysql.MySQLExceptionSorter"/>
            </validation>
        </datasource>

        <!-- Platform Datasource -->
        <datasource jndi-name="java:jboss/datasources/platform"
                    pool-name="platform"
                    enabled="true">
            <connection-url>jdbc:mysql://localhost:3306/platform?useSSL=false</connection-url>
            <driver>mysql</driver>
            <security>
                <user-name>qto_user</user-name>
                <password>your_password</password>
            </security>
        </datasource>

        <drivers>
            <driver name="mysql" module="com.mysql"/>
        </drivers>
    </datasources>
</subsystem>
```

### 3. Deploy MySQL JDBC Driver to WildFly

```bash
# Download MySQL Connector/J
wget https://dev.mysql.com/get/Downloads/Connector-J/mysql-connector-j-8.0.33.jar

# Deploy to WildFly
cp mysql-connector-j-8.0.33.jar wildfly/standalone/deployments/
touch wildfly/standalone/deployments/mysql-connector-j-8.0.33.jar.dodeploy
```

### 4. Run Liquibase Migrations

Migrations are applied automatically on application startup via the `qto-database` module. Alternatively, run manually:

```bash
cd qto-database
mvn liquibase:update
```

## Build Instructions

### Complete Build (All Modules)

```bash
# From qto/ directory
mvn clean install

# Skip tests for faster build
mvn clean install -DskipTests

# Build with specific profile
mvn clean install -P production
```

### Build Individual Modules

```bash
# Core business logic only
mvn clean install -pl qto-core

# REST API only
mvn clean install -pl qto-rest-api -am

# WAR package only (requires core and api built first)
mvn clean install -pl qto-war -am
```

### Build Output

- **WAR file**: `qto-war/target/qto-war-1.18.1-SNAPSHOT.war`
- **JAR modules**: Each module's `target/` directory

## WildFly Configuration

### Start WildFly Server

```bash
# Navigate to WildFly bin directory
cd wildfly/bin

# Start in standalone mode (foreground)
./standalone.sh

# Start in background
./standalone.sh &

# Start with custom configuration
./standalone.sh -c standalone-full.xml

# Start with debug port
./standalone.sh --debug
```

### WildFly Admin Console

1. Create admin user (first time):
```bash
cd wildfly/bin
./add-user.sh
# Follow prompts to create management user
```

2. Access console: http://localhost:9990/console

### Configure WildFly for QTO

Required configurations in `standalone.xml`:

1. **Datasources** (see Database Setup above)
2. **Security Realm** (Azure AD integration)
3. **Logging** configuration
4. **JMS Queues** (for async processing)
5. **Resource Adapters** (if needed)

## Deployment Instructions

### Deploy to WildFly

#### Option 1: Maven Plugin (Recommended)

```bash
# From qto-war/ directory
mvn clean install wildfly:deploy

# Redeploy existing application
mvn wildfly:redeploy

# Undeploy
mvn wildfly:undeploy
```

#### Option 2: Manual Deployment

```bash
# Copy WAR to deployments directory
cp qto-war/target/qto-war-1.18.1-SNAPSHOT.war wildfly/standalone/deployments/

# Create .dodeploy marker file to trigger deployment
touch wildfly/standalone/deployments/qto-war-1.18.1-SNAPSHOT.war.dodeploy

# Monitor deployment logs
tail -f wildfly/standalone/log/server.log
```

#### Option 3: JBoss CLI

```bash
cd wildfly/bin
./jboss-cli.sh --connect

# Deploy
[standalone@localhost:9990 /] deploy /path/to/qto-war-1.18.1-SNAPSHOT.war

# Redeploy
[standalone@localhost:9990 /] deploy --force /path/to/qto-war-1.18.1-SNAPSHOT.war

# Undeploy
[standalone@localhost:9990 /] undeploy qto-war-1.18.1-SNAPSHOT.war
```

### Deployment Verification

1. Check deployment status:
```bash
ls -la wildfly/standalone/deployments/qto-war-*.deployed
```

2. Check server logs:
```bash
tail -f wildfly/standalone/log/server.log
```

3. Verify REST API accessibility:
```bash
curl http://localhost:8080/qto/api/health
```

## Running the Application

### Application URLs

- **Application Root**: http://localhost:8080/qto
- **REST API Base**: http://localhost:8080/qto/api
- **WildFly Console**: http://localhost:9990/console

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

### Run Unit Tests

```bash
# All modules
mvn test

# Specific module
mvn test -pl qto-core

# Single test class
mvn test -Dtest=OrderManagerTest

# With coverage report
mvn test jacoco:report
```

### Integration Tests

```bash
# Run integration tests (Arquillian)
mvn verify -P integration-tests

# With WildFly Managed Container
mvn verify -P wildfly-managed
```

### REST API Testing

```bash
# Using curl
curl -X GET http://localhost:8080/qto/api/orders

# Using REST Assured (in tests)
given()
    .auth().oauth2(token)
    .when()
    .get("/api/orders")
    .then()
    .statusCode(200)
```

## Common Maven Commands

```bash
# Clean build
mvn clean

# Compile only
mvn compile

# Package without tests
mvn package -DskipTests

# Install to local Maven repo
mvn install

# Dependency tree
mvn dependency:tree

# Check for dependency updates
mvn versions:display-dependency-updates

# Generate QueryDSL Q-classes
mvn clean compile -pl qto-core

# Run specific goal
mvn liquibase:update
mvn wildfly:deploy
```

## Development Workflow

### 1. Code Changes

```bash
# Make code changes in qto-core, qto-rest-api, etc.
vim qto-core/src/main/java/.../YourClass.java
```

### 2. Build Changed Module

```bash
# Build specific module with dependencies
mvn clean install -pl qto-core,qto-rest-api -am
```

### 3. Hot Deploy to WildFly

```bash
# Redeploy WAR
cd qto-war
mvn wildfly:redeploy
```

### 4. Monitor Logs

```bash
tail -f wildfly/standalone/log/server.log
```

## Troubleshooting

### Build Issues

**Problem**: Maven build fails with dependency resolution errors
```bash
# Solution: Clean local Maven cache and rebuild
rm -rf ~/.m2/repository/com/vertek/corporate/qto
mvn clean install -U
```

**Problem**: QueryDSL Q-classes not generated
```bash
# Solution: Run annotation processing
mvn clean compile -pl qto-core
```

### Deployment Issues

**Problem**: Deployment fails with ClassNotFoundException
- Check `jboss-deployment-structure.xml` for missing module dependencies
- Verify all required JARs are in `WEB-INF/lib`

**Problem**: Datasource not found (JNDI lookup fails)
- Verify datasource configuration in `standalone.xml`
- Check JNDI name matches `persistence.xml` configuration
- Ensure MySQL driver is deployed

**Problem**: Port already in use (8080)
```bash
# Find process using port
lsof -i :8080
kill -9 <PID>

# Or change WildFly port in standalone.xml
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
# Increase WildFly heap size
export JAVA_OPTS="-Xms2g -Xmx4g -XX:MetaspaceSize=512m"
./standalone.sh
```

**Problem**: Azure AD authentication fails
- Verify Azure AD configuration in system properties
- Check tenant ID, client ID, and client secret
- Ensure redirect URIs are configured in Azure portal

## Key Configuration Files

| File | Purpose |
|------|---------|
| `pom.xml` | Maven project configuration and dependencies |
| `persistence.xml` | JPA configuration and entity mappings |
| `web.xml` | Servlet and web application configuration |
| `jboss-deployment-structure.xml` | WildFly module dependencies |
| `jboss-web.xml` | JBoss-specific web configuration |
| `standalone.xml` | WildFly server configuration |
| `shiro.ini` | Apache Shiro security configuration |
| `quartz.properties` | Quartz scheduler configuration |

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
4. **JMS Queues**: Asynchronous bulk import operations

## Performance Optimization

- Enable **Hibernate 2nd-level cache** for frequently accessed entities
- Use **QueryDSL** for type-safe, optimized queries
- Configure **connection pooling** appropriately
- Enable **HTTP compression** in WildFly
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

- **WildFly Documentation**: https://docs.wildfly.org/
- **Java EE 8 Tutorial**: https://javaee.github.io/tutorial/
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
# 1. Set up databases
mysql -u root -p < setup-databases.sql

# 2. Configure WildFly datasources
vim wildfly/standalone/configuration/standalone.xml

# 3. Start WildFly
cd wildfly/bin && ./standalone.sh &

# 4. Build and deploy
cd qto
mvn clean install
cd qto-war
mvn wildfly:deploy

# 5. Verify deployment
curl http://localhost:8080/qto/api/health

# 6. Monitor logs
tail -f wildfly/standalone/log/server.log
```

---

**Last Updated**: 2025-01-23
**Version**: 1.18.1-SNAPSHOT
**Maintained By**: QTO Development Team
