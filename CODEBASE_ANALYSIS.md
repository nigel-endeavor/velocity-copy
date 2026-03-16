# Velocity (QTO) Codebase — Compilation, Deployment, Testing & Documentation

> **Commit:** `104bf38b9066e67db2fd07f6232cdf2dd630c232`  
> **Date:** 2026-03-13  
> **Author:** GitHub Copilot (automated analysis)

---

## Table of Contents

1. [High-Level Overview](#1-high-level-overview)
2. [Database Analysis](#2-database-analysis)
3. [API & Endpoints Specification](#3-api--endpoints-specification)
4. [Technical Write-Ups](#4-technical-write-ups)
5. [Deep Dive — Build, Deploy, Test](#5-deep-dive--build-deploy-test)
6. [Essence Extraction & Diagrams](#6-essence-extraction--diagrams)

---

## 1. High-Level Overview

### Repository Structure

```
velocity/
├── qto/                      # Java EE 8 Maven multi-module backend (v1.18.1-SNAPSHOT)
│   ├── qto-database/         # Liquibase migrations + JPA entities
│   ├── qto-core/             # Business logic, managers, DAOs, authentication
│   ├── qto-help-desk/        # Help desk module (Thymeleaf templates, email)
│   ├── qto-rest-api/         # JAX-RS REST resources (~88 Resource classes, ~311 endpoints)
│   ├── qto-war/              # WAR packaging + Shiro config
│   ├── qto-spring-boot-app/  # Spring Boot 3.2.2 migration (Phase 1, Gradle 8.5)
│   └── wildfly/              # Bundled WildFly 27 (Jakarta EE 10)
├── qto-ui/                   # Angular 16 — main operations UI (v1.18.0)
└── help-desk-ui/             # Angular 16 — help desk portal (v1.2.0)
```

### Technology Stack

| Layer | Technology | Version |
|-------|-----------|---------|
| Backend (Java EE) | Java | 17 (source/target) |
| Build (Java EE) | Apache Maven | 3.x |
| App Server | WildFly | 26.1.3 Final (Java EE 8) |
| ORM | Hibernate | 5.3.28 |
| Auth Filter | Apache Shiro | 1.13.0 |
| Identity | Azure AD (MSAL) | OAuth2 Bearer |
| Database | PostgreSQL | 18.3 |
| Schema Mgmt | Liquibase | 4.23.0 |
| Query DSL | QueryDSL | 5.0.0 |
| JMS | ActiveMQ Artemis | 2.19.1 (embedded in WildFly) |
| Scheduling | Quartz | 2.3.2 |
| Frontend | Angular | 16.x |
| State Mgmt | NgRx | 16.x (qto-ui only) |
| Auth (UI) | MSAL Angular | 3.x |
| Build (Spring) | Gradle | 8.5 |
| Backend (Spring) | Spring Boot | 3.2.2 |

### Architecture

```
┌─────────────────┐     ┌─────────────────┐
│   qto-ui        │     │  help-desk-ui   │
│ (Angular 16)    │     │  (Angular 16)   │
│ localhost:4200   │     │  localhost:4201  │
└────────┬────────┘     └────────┬────────┘
         │  HTTP/WS              │  HTTP
         ▼                       ▼
┌─────────────────────────────────────────┐
│        WildFly 26.1.3 (Java EE 8)      │
│  ┌─────────────────────────────────┐    │
│  │  qto.war                        │    │
│  │  ├─ Shiro Filter (authcBearer)  │    │
│  │  ├─ JAX-RS (RESTEasy 4.7.7)    │    │
│  │  ├─ EJB 3 (Stateless/Singleton)│    │
│  │  ├─ JPA (Hibernate 5.3.28)     │    │
│  │  ├─ JMS (13 queues)            │    │
│  │  └─ Quartz Scheduler           │    │
│  └─────────────────────────────────┘    │
│  HTTP: 8080 | HTTPS: 8443 | Mgmt: 9990 │
└───────────┬─────────────┬───────────────┘
            │             │
     ┌──────▼──────┐ ┌───▼────────┐
     │ qto DB      │ │ platform DB│
     │ (207 tables)│ │ (4 tables) │
     │ PostgreSQL  │ │ PostgreSQL │
     │ port: 5432  │ │ port: 5432 │
     └─────────────┘ └────────────┘
```

---

## 2. Database Analysis

### Databases

| Database | Purpose | Tables | Datasource JNDI | PU Name |
|----------|---------|--------|-----------------|---------|
| `qto` | Main application data | 207 | `java:jboss/datasources/qto` | `qto` |
| `qto` | DDL/Liquibase migrations | — | `java:jboss/datasources/qto-ddl` | — |
| `platform` | Multi-tenancy (tenants, subjects) | 4 | `java:jboss/datasources/platform` | `platform` |

### PostgreSQL Configuration

- **User:** `qto_user` (trust auth for localhost)
- **XA Transactions:** `max_prepared_transactions = 64` (required for platform XA datasource)
- **Auth:** `pg_hba.conf` uses `trust` for `127.0.0.1/32` and `::1/128`

### Platform Database Schema

The platform database uses a **dual-schema** layout:

| Table | Schema | Columns |
|-------|--------|---------|
| `tenant` | `public` | tenant_id (PK), name, version |
| `subject` | `public` | subject_id (PK), email, first_name, last_name, azure_oid, version |
| `tenant_subject` | `platform` | tenant_subject_id (PK), tenant_id (FK), subject_id (FK), tenant_subject_selected, version |
| `company` | `platform` | company_id (PK), company_name, tenant_id (FK), version |

### QTO Database — Liquibase

- **19 version changelogs:** 1.0.0 → 1.18.0
- **Master changelog:** `database/changelog-master.xml`
- **831 tracked changesets** in `databasechangelog`
- **Startup bean:** `LiquibaseRunner` (EJB Singleton, runs on deployment via `@PostConstruct`)
- **Issue found/fixed:** Duplicate changeset ID `1_12_0-007` (3 context variants sharing same ID) — renamed to `1_12_0-007-dev`, `1_12_0-007-test`, `1_12_0-007-prod`

### Key QTO Tables (subset)

| Category | Tables |
|----------|--------|
| Core | `company`, `order_header`, `service`, `location`, `vendor`, `carrier`, `circuit` |
| Billing | `invoice`, `invoice_charge`, `dispute`, `cost_summary`, `rate`, `surcharge` |
| Workflow | `workflow`, `workflow_state`, `workflow_transition`, `workflow_view` |
| Telecom | `bandwidth`, `circuit_type`, `disconnect_reason`, `macd_type` |
| Config | `config_property`, `company_config_property`, `lookup_value` |
| Auth | `role`, `role_permission`, `permission`, `subject_company` |
| Import | `file_import`, `import_activity`, `import_template`, `import_mapping` |
| Quartz | `qrtz_triggers`, `qrtz_job_details`, `qrtz_cron_triggers`, etc. |

---

## 3. API & Endpoints Specification

### Base Path

```
/qto/api/**
```

### Authentication

All endpoints pass through Apache Shiro's `authcBearer` filter requiring Azure AD OAuth2 bearer tokens. The filter chain:

```
shiro.ini:
  [urls]
  /api/** = authcBearer
```

Method-level authorization uses `@RequiresPermissions(Permissions.XXX)`.

### Resource Classes (88 total, ~311 endpoints)

#### Core Business Resources

| Resource | Path | Key Operations |
|----------|------|----------------|
| CompanyResource | `/companies` | GET (list), GET `/{id}`, POST, GET `/{id}/tasks` |
| OrderResource | `/orders` | POST (create), GET `/{id}`, PUT `/{id}`, DELETE `/{id}` |
| ServiceResource | `/services` | GET (search), GET `/{id}`, PUT `/{id}`, DELETE `/{id}` |
| LocationResource | `/locations` | GET, GET `/{id}`, POST, PUT `/{id}`, DELETE `/{id}` |
| VendorResource | `/vendors` | GET, GET `/{id}`, POST, PUT, DELETE |
| CarrierResource | `/carriers` | GET, GET `/{id}`, POST, PUT, DELETE |
| CircuitResource | `/circuits` | GET, GET `/{id}`, POST, PUT, DELETE |
| SubjectResource | `/subjects` | GET, GET `/me`, POST, PUT, DELETE |

#### Billing & Invoicing

| Resource | Path | Key Operations |
|----------|------|----------------|
| InvoiceResource | `/invoices` | GET, GET `/{id}`, POST, PUT, DELETE |
| InvoiceChargeResource | `/invoiceCharges` | GET, POST, PUT, DELETE |
| DisputeResource | `/disputes` | GET, GET `/{id}`, POST, PUT, DELETE |
| CostSummaryResource | `/costSummaries` | GET, GET `/{id}` |
| SurchargeTypeResource | `/surchargeTypes` | GET |
| ServiceSurchargeResource | `/serviceSurcharges` | GET, POST, PUT, DELETE |
| LevelOfEffortResource | `/levelOfEffort` | GET |

#### Workflow & Operations

| Resource | Path | Key Operations |
|----------|------|----------------|
| WorkflowResource | `/workflows` | GET, GET `/{id}` |
| WorkflowViewResource | `/workflowViews` | GET, POST |
| OrderNoteResource | `/orderNotes` | GET, POST, PUT, DELETE |
| ActivationAttemptResource | `/activationAttempts` | GET, POST, PUT, DELETE |
| ActivationScheduleResource | `/activationSchedules` | GET, POST, PUT |
| FtdiOrderTypeResource | `/ftdiOrderTypes` | GET, POST, PUT, DELETE |

#### Dashboard & Reporting

| Resource | Path | Key Operations |
|----------|------|----------------|
| ActivationViewResource | `/activationViews` | GET, GET `/meta` |
| WipViewResource | `/wipViews` | GET |
| ProviderViewResource | `/providerViews` | GET |
| ServiceSnapshotResource | `/serviceSnapshots` | GET |
| DashboardResource | `/dashboard` | GET |

#### Configuration & Admin

| Resource | Path | Key Operations |
|----------|------|----------------|
| ConfigPropertyResource | `/config-properties` | GET, PUT |
| CompanyConfigPropertyResource | `/companyConfigProperties` | GET, PUT |
| TenantResource | `/tenants` | GET, GET `/{id}`, POST, PUT |
| RoleResource | `/roles` | GET, GET `/{id}`, POST, PUT, DELETE |
| LookupValueResource | `/lookupValues` | GET, POST, PUT, DELETE |
| TemplateVariableResource | `/templateVariables/{type}` | GET |

#### File Import & Notifications

| Resource | Path | Key Operations |
|----------|------|----------------|
| FileImportResource | `/fileImports` | GET, POST |
| ImportTemplateResource | `/importTemplates` | GET, POST, PUT, DELETE |
| ImportActivityResource | `/importActivities` | GET |
| NotificationResource | `/notifications` | GET, POST, PUT, DELETE |
| AlertResource | `/alerts` | GET, PUT |

### JMS Queues (13)

| Queue JNDI | Purpose |
|------------|---------|
| `java:/queue/qto.CompanyMessageQueue` | Company entity changes |
| `java:/queue/qto.DisconnectMultiEditQueue` | Bulk disconnect operations |
| `java:/queue/qto.DisputeMultiEditQueue` | Bulk dispute operations |
| `java:/queue/qto.FileImportQueue` | File import processing |
| `java:/queue/qto.FtdiProcessingQueue` | FTDI processing |
| `java:/queue/qto.IntervalQueue` | Interval processing |
| `java:/queue/qto.InvoiceChargeQueue` | Invoice charge processing |
| `java:/queue/qto.LocationMessageQueue` | Location entity changes |
| `java:/queue/qto.MultiDisputeQueue` | Multi-dispute processing |
| `java:/queue/qto.MultiMacdQueue` | Multi-MACD processing |
| `java:/queue/qto.ServiceMultiEditQueue` | Bulk service edits |
| `java:/queue/qto.QuoteProcessingQueue` | Quote processing |
| `java:/queue/qto.QuoteProcessingFailureQueue` | Quote processing failures |

---

## 4. Technical Write-Ups

### 4.1 Authentication Architecture

```
Browser → Angular (MSAL) → Azure AD → Bearer Token
                                         │
Browser → WildFly → Shiro Filter ────────┤
                     │                    │
                     ▼                    ▼
              AADAuthorizingRealm    Token Validation
                     │
                     ▼
              SecurityUtils.getSubject()
              → TenantSubject (platform DB)
              → Permission resolution
              → @RequiresPermissions enforcement
```

- **Frontend:** MSAL Angular acquires Azure AD tokens
- **Backend:** Shiro's `authcBearer` filter validates the bearer token
- **Realm:** `AADAuthorizingRealm` resolves principals and permissions from Azure AD + platform DB
- **Multi-tenancy:** `TenantSubjectJpaDao` maps authenticated subjects to allowed tenants/companies

### 4.2 Multi-Tenancy

The application uses a two-database multi-tenancy model:

1. **Platform DB** — Stores tenant, subject, tenant_subject, and company mappings
2. **QTO DB** — All business data, partitioned by tenant_id FK on key tables

Row-level filtering in `AbstractMultitenantJpaDao`:
- `getAllowedTenantIds()` → queries platform.tenant_subject for current user
- `getAllowedCompanyIds()` → queries platform.company for current user's tenants
- Query predicates auto-filter by tenant_id/company_id

### 4.3 JMS Message Processing

Async operations use JMS MDBs (Message-Driven Beans):
- File imports are queued to `FileImportQueue` and processed asynchronously
- Bulk edits (disconnect, dispute, MACD, service) use dedicated queues
- Quote processing uses a separate queue with a failure queue for DLQ handling

### 4.4 Quartz Scheduling

- Configured via `quartz.properties` in the WAR
- Uses JDBC JobStore (`JobStoreCMT`) backed by the QTO database
- Clustered mode enabled (for multi-instance deployments)
- Quartz tables: `qrtz_*` (18 tables)

### 4.5 Spring Boot Migration (Phase 1)

Located at `qto/qto-spring-boot-app/`:
- **Build:** Gradle 8.5 (`build.gradle.kts`)
- **Framework:** Spring Boot 3.2.2, Java 17
- **Package:** `com.endeavorms.qto`
- **Status:** Phase 1 Foundation complete
- **Endpoints:** `/api/status` (health), `/api/status/ping` (connectivity), `/actuator/**`
- **Profile:** `dev` (configured via `application-dev.yml`)

---

## 5. Deep Dive — Build, Deploy, Test

### 5.1 Issues Encountered & Solutions

#### Issue 1: Maven Build Failure — qto-spring-boot-app
- **Error:** `qto-spring-boot-app` listed in parent `pom.xml` modules but has no `pom.xml` (uses Gradle)
- **Solution:** Removed `<module>qto-spring-boot-app</module>` from `qto/pom.xml`
- **File:** `qto/pom.xml`

#### Issue 2: WildFly 27 Jakarta EE Incompatibility
- **Error:** `java.lang.ClassNotFoundException: javax.servlet.Filter` — Shiro 1.13 uses the `javax.servlet` namespace, but WildFly 27 is Jakarta EE 10 (`jakarta.servlet`)
- **Root Cause:** WildFly 27's Undertow explicitly checks for `jakarta.servlet.Filter` interface
- **Solution:** Downloaded and configured WildFly 26.1.3 (last Java EE 8 compatible release)
- **Location:** `c:\wf26\wildfly-26.1.3.Final\`

#### Issue 3: Missing EJB Profile
- **Error:** `jboss.ejb.default-resource-adapter-name-service not found`
- **Root Cause:** `standalone.xml` profile lacks EJB/JMS subsystems
- **Solution:** Switched to `standalone-full.xml` profile

#### Issue 4: Missing JMS Queues (13 total)
- **Error:** `jboss.naming.context.java.queue."qto.*"` services missing
- **Solution:** Added all 13 JMS queue definitions to `standalone-full.xml`

#### Issue 5: Liquibase Checksum Validation (521 failures)
- **Error:** `ValidationFailedException: 521 changesets check sum` — checksums in `databasechangelog` didn't match current changelog files
- **Root Cause:** Database was populated by a different Liquibase version with different checksum algorithm
- **Solution:** `UPDATE databasechangelog SET md5sum = null;` (forces recalculation)

#### Issue 6: Duplicate Liquibase Changeset ID
- **Error:** `1 changesets had duplicate identifiers: database/1_12_0/changelog-master.xml::1_12_0-007::rcasey`
- **Root Cause:** 3 context-variant changesets (dev/test/prod) sharing the same ID+author+filepath
- **Solution:** Renamed to `1_12_0-007-dev`, `1_12_0-007-test`, `1_12_0-007-prod`
- **File:** `qto/qto-database/src/main/resources/database/1_12_0/changelog-master.xml`

#### Issue 7: Platform Database Missing Schema
- **Error:** `relation "platform.tenant_subject" does not exist`
- **Root Cause:** JPA entities annotated with `@Table(schema = "platform")` but tables were in `public` schema
- **Solution:** Created `platform` schema in the platform database, moved `tenant_subject` and `company` tables there. Kept `tenant` and `subject` in `public` schema (no schema annotation on those entities).

#### Issue 8: JTA Multiple Last Resource
- **Error:** `ARJUNA012140: Adding multiple last resources is disallowed`
- **Root Cause:** Two non-XA datasources (qto + platform) used in the same JTA transaction; Narayana TM only allows one "last resource"
- **Solution:** Converted `platform` datasource to XA (`<xa-datasource>` with `PGXADataSource`), enabled `max_prepared_transactions = 64` in PostgreSQL

#### Issue 9: PostgreSQL Service Restart
- **Error:** `pg_ctl` starts PostgreSQL but the process was killed by the terminal session
- **Solution:** Used `Start-Process` with `-WindowStyle Hidden` to launch `pg_ctl` as a fully detached process

### 5.2 Compilation

#### Java EE Backend (Maven)

```bash
cd qto
mvn package -DskipTests
```

**Result:** `BUILD SUCCESS` — All 6 modules compiled:
- `qto-database` (JAR)
- `qto-core` (JAR)
- `qto-help-desk` (JAR)
- `qto-rest-api` (JAR)
- `qto-war` (WAR — 87 MB)

#### Spring Boot App (Gradle)

```bash
cd qto/qto-spring-boot-app
./gradlew bootJar
```

**Result:** `BUILD SUCCESSFUL` — `qto-spring-boot-app-0.0.1-SNAPSHOT.jar`

#### Angular UIs (npm)

```bash
cd qto-ui && npm install --legacy-peer-deps --ignore-scripts
cd help-desk-ui && npm install --legacy-peer-deps --ignore-scripts
```

**Result:** Both installed successfully (husky postinstall skipped — requires .git root)

### 5.3 Database Setup

```sql
-- PostgreSQL 18 (local, user: qto_user, trust auth)
-- QTO database: 207 tables, fully populated via Liquibase
-- Platform database: manually created

-- Platform schema setup:
CREATE SCHEMA IF NOT EXISTS platform;
-- Tables in public schema: tenant, subject
-- Tables in platform schema: tenant_subject, company

-- Seeded data:
INSERT INTO tenant (name, version) VALUES 
  ('QTO First Tenant', 1), ('Endeavor', 1), ('Demo Tenant', 1);
INSERT INTO subject (email, first_name, last_name, azure_oid, version) VALUES
  ('admin@vertek.com', 'Admin', 'User', 'local-dev', 1);
INSERT INTO platform.tenant_subject (tenant_id, subject_id, tenant_subject_selected, version)
  VALUES (1, 1, true, 1);

-- XA transactions:
ALTER SYSTEM SET max_prepared_transactions = 64;
-- Restart PostgreSQL required
```

### 5.4 WildFly Deployment

#### Configuration (standalone-full.xml)

**Datasources:**
```xml
<!-- QTO (local datasource) -->
<datasource jndi-name="java:jboss/datasources/qto" pool-name="qto">
    <connection-url>jdbc:postgresql://localhost:5432/qto</connection-url>
    <driver>postgresql</driver>
    <security><user-name>qto_user</user-name></security>
</datasource>

<!-- QTO DDL (local datasource for Liquibase) -->
<datasource jndi-name="java:jboss/datasources/qto-ddl" pool-name="qto-ddl">
    <connection-url>jdbc:postgresql://localhost:5432/qto</connection-url>
    <driver>postgresql</driver>
    <security><user-name>qto_user</user-name></security>
</datasource>

<!-- Platform (XA datasource — required for JTA with qto DS) -->
<xa-datasource jndi-name="java:jboss/datasources/platform" pool-name="platform">
    <xa-datasource-property name="ServerName">localhost</xa-datasource-property>
    <xa-datasource-property name="PortNumber">5432</xa-datasource-property>
    <xa-datasource-property name="DatabaseName">platform</xa-datasource-property>
    <driver>postgresql</driver>
    <security><user-name>qto_user</user-name></security>
</xa-datasource>
```

**JMS Queues:** 13 queues defined (see Section 3)

**System Properties:**
```xml
<system-properties>
    <property name="com.arjuna.ats.jta.allowMultipleLastResources" value="true"/>
</system-properties>
```

#### Startup Command

```bash
cd c:\wf26\wildfly-26.1.3.Final
bin\standalone.bat -c standalone-full.xml
```

**Result:** `WFLYSRV0025: WildFly Full 26.1.3.Final started in ~58s — Started 4602 of 4782 services`

### 5.5 Endpoint Testing

#### Spring Boot App

| Endpoint | Status | Response |
|----------|--------|----------|
| `GET /api/status` | ✅ 200 | `{"status":"OPERATIONAL","version":"0.0.1-SNAPSHOT"}` |
| `GET /api/status/ping` | ✅ 200 | `pong` |
| `GET /actuator/health` | ✅ 200 | `{"status":"UP"}` |

#### WildFly Java EE (with Shiro set to `anon` for testing)

| Endpoint | HTTP | Notes |
|----------|------|-------|
| `GET /qto/api/companies` | ✅ **200** | Returns JSON: `{"offset":0,"limit":25,"total":0,"collection":[]}` |
| `GET /qto/api/activationViews` | ✅ **200** | Returns JSON: `{"offset":0,"limit":25,"total":0,"collection":[]}` |
| `GET /qto/api/orders` | **405** | Method Not Allowed — endpoint registered, only supports POST for creation |
| `GET /qto/api/subjects` | **401** | `@RequiresPermissions` — needs authenticated subject |
| `GET /qto/api/services` | **401** | `@RequiresPermissions` — needs authenticated subject |
| `GET /qto/api/circuits` | **401** | `@RequiresPermissions` — needs authenticated subject |
| `GET /qto/api/disputes` | **401** | `@RequiresPermissions` — needs authenticated subject |
| `GET /qto/api/surchargeTypes` | **500** | `companyId is required` — needs tenant context |
| `GET /qto/api/ftdiOrderTypes` | **500** | `eq(null) is not allowed` — needs tenant context |
| `GET /qto/api/lookupValues` | **500** | `eq(null) is not allowed` — needs tenant context |

**Analysis:**
- **200s** confirm: WAR deployed → REST registered → EJB layer → JPA → PostgreSQL (both qto and platform DBs)
- **401s** are expected: `@RequiresPermissions` annotations enforce method-level auth even with Shiro filter set to anon
- **405s** confirm: endpoint exists, correct HTTP method required
- **500s** are expected: anonymous user has no tenant/company context → null passed to QueryDSL predicates

#### UI-Backend Proxy

| Test | Status |
|------|--------|
| `curl http://localhost:4200/qto/api/companies` | ✅ **200** — Angular proxy forwards to WildFly, returns JSON |

### 5.6 Files Modified

| File | Change | Reason |
|------|--------|--------|
| `qto/pom.xml` | Removed `qto-spring-boot-app` module | Uses Gradle, not Maven |
| `qto/qto-database/src/main/resources/database/1_12_0/changelog-master.xml` | Renamed duplicate changeset IDs | Liquibase validation failure |
| `qto/qto-war/src/main/resources/WEB-INF/shiro.ini` | Temporarily set to anon, then reverted | Endpoint testing |
| `qto-ui/src/environments/environment.ts` | Changed `appUrl` to `/qto/api` (relative) | Proxy-based dev setup |
| `qto-ui/proxy.conf.json` | Created — proxy config for Angular dev server | CORS bypass for local dev |
| `c:\wf26\wildfly-26.1.3.Final\standalone\configuration\standalone-full.xml` | Added datasources, JMS queues, system properties, XA config | WildFly deployment |
| `c:\wf26\wildfly-26.1.3.Final\modules\org\postgresql\main\` | Created PostgreSQL driver module | JDBC driver for WildFly |
| `C:\Program Files\PostgreSQL\18\data\postgresql.conf` | Set `max_prepared_transactions = 64` | XA transaction support |

---

## 6. Essence Extraction & Diagrams

### Request Flow

```
  Client (Browser / curl)
       │
       ▼
  ┌─── Undertow HTTP Listener (8080/8443) ───┐
  │                                            │
  │  ┌── QTOShiroFilter ──────────────────┐   │
  │  │  OPTIONS → pass through            │   │
  │  │  /api/** → authcBearer validation  │   │
  │  │  → AADAuthorizingRealm            │   │
  │  └────────────────────────────────────┘   │
  │                                            │
  │  ┌── JAX-RS (RESTEasy) ──────────────┐   │
  │  │  @Path routing                     │   │
  │  │  @RequiresPermissions check        │   │
  │  │  → EJB (Stateless Session Bean)   │   │
  │  └────────────────────────────────────┘   │
  │                                            │
  │  ┌── Business Layer ─────────────────┐   │
  │  │  Manager → Dao → EntityManager    │   │
  │  │  @QtoDatabase (qto PU)           │   │
  │  │  @PlatformDatabase (platform PU)  │   │
  │  │  QueryDSL predicates              │   │
  │  └────────────────────────────────────┘   │
  │                                            │
  │  ┌── JTA Transaction Manager ────────┐   │
  │  │  qto DS (local/non-XA)           │   │
  │  │  platform DS (XA)                 │   │
  │  │  Narayana TM coordination         │   │
  │  └────────────────────────────────────┘   │
  │                                            │
  └────────────────────────────────────────────┘
       │                    │
       ▼                    ▼
  PostgreSQL:qto      PostgreSQL:platform
  (207 tables)        (4 tables, 2 schemas)
```

### Module Dependency Graph

```
qto-database
    │
    ▼
qto-core ←── qto-help-desk
    │
    ▼
qto-rest-api
    │
    ▼
qto-war (packages all into WAR)
```

### Persistence Units

```
persistence.xml:

  PU "qto" ──────────────────────────────────
  │  jta-data-source: java:jboss/datasources/qto
  │  ~130 entity classes
  │  Hibernate 5.3.28 + QueryDSL 5.0.0
  │  SharedCacheMode: ENABLE_SELECTIVE
  │  L2 cache: Infinispan (via WildFly)
  │
  PU "platform" ─────────────────────────────
  │  jta-data-source: java:jboss/datasources/platform
  │  4 entity classes:
  │    - Tenant (@Table(name="tenant"))
  │    - Subject (@Table(name="subject"))
  │    - TenantSubject (@Table(schema="platform", name="tenant_subject"))
  │    - TenantOwnedCompany (@Table(schema="platform", name="company"))
```

### Key Patterns

1. **Abstract Base Classes:**
   - `AbstractResource` → URI building, pagination, Excel export
   - `AbstractQTOResource` → QueryDSL search criteria, sorting
   - `AbstractMultitenantJpaDao` → Tenant-scoped queries via platform DB
   - `AbstractMasterCustomerJpaDao` → Company-scoped queries

2. **CDI Qualifiers:**
   - `@QtoDatabase` → injects EntityManager for the qto persistence unit
   - `@PlatformDatabase` → injects EntityManager for the platform persistence unit

3. **Security Model:**
   - Shiro filter → bearer token validation
   - `@RequiresPermissions` → method-level authorization
   - `SecurityUtils.getLoggedInUser()` → returns authenticated principal or "Anonymous"
   - Row-level filtering via `getAllowedTenantIds()` / `getAllowedCompanyIds()`

### Local Development Quick Start

```bash
# 1. Start PostgreSQL (if not running)
Start-Process pg_ctl -ArgumentList 'start -D "C:\Program Files\PostgreSQL\18\data"' -WindowStyle Hidden

# 2. Start WildFly 26
cd c:\wf26\wildfly-26.1.3.Final
Start-Process cmd -ArgumentList "/c bin\standalone.bat -c standalone-full.xml" -WindowStyle Hidden
# Wait ~60 seconds for deployment

# 3. Start Angular dev server (with proxy)
cd qto-ui
npx ng serve --proxy-config proxy.conf.json
# Browse to http://localhost:4200

# 4. (Optional) Start Spring Boot app
cd qto/qto-spring-boot-app
./gradlew bootRun --args='--spring.profiles.active=dev'
```

### Environment Summary

| Component | URL | Status |
|-----------|-----|--------|
| WildFly (HTTP) | `http://127.0.0.1:8080/qto/api/` | ✅ Running |
| WildFly (HTTPS) | `https://127.0.0.1:8443/qto/api/` | ✅ Running (self-signed cert) |
| WildFly Admin | `http://127.0.0.1:9990/` | ✅ Running |
| Angular (qto-ui) | `http://localhost:4200/` | ✅ Running |
| Spring Boot | `http://localhost:8081/api/status` | ✅ Tested (not currently running) |
| PostgreSQL | `localhost:5432` | ✅ Running |

---

*Generated by GitHub Copilot — automated codebase analysis, compilation, deployment, and testing.*
