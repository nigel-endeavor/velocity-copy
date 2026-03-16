# Local Development Setup Guide

## Overview

This document describes the local development setup for the QTO platform:

| Component      | URL                              | Port |
|----------------|----------------------------------|------|
| WildFly 26     | http://127.0.0.1:7081            | 7081 |
| QTO API        | http://127.0.0.1:7081/qto/api    | 7081 |
| qto-ui         | http://localhost:4200             | 4200 |
| help-desk-ui   | http://localhost:4201             | 4201 |
| PostgreSQL     | localhost                        | 5432 |

## Prerequisites

- **Java 21**: Oracle JDK at `C:\Java\oracleJdk-21`
- **Maven 3.9.12**: At `C:\apache-maven-3.9.12\bin\mvn.cmd`
- **Node.js v24+** / **npm 11+**
- **PostgreSQL 18**: localhost:5432, superuser `postgres`

## 1. Database Setup

The local database `qto-demo-104bf38` was created from the `qto` template:

```sql
CREATE DATABASE "qto-demo-104bf38" TEMPLATE qto;
GRANT ALL PRIVILEGES ON DATABASE "qto-demo-104bf38" TO qto_user;
\c "qto-demo-104bf38"
GRANT ALL ON SCHEMA public TO qto_user;
GRANT ALL ON ALL TABLES IN SCHEMA public TO qto_user;
GRANT ALL ON ALL SEQUENCES IN SCHEMA public TO qto_user;
```

### Required Schema Fixes

**Quartz boolean columns** — the `qrtz_job_details` table has varchar(1) columns that Quartz writes "TRUE"/"FALSE" into:

```sql
ALTER TABLE qrtz_job_details ALTER COLUMN is_durable TYPE varchar(5);
ALTER TABLE qrtz_job_details ALTER COLUMN is_nonconcurrent TYPE varchar(5);
ALTER TABLE qrtz_job_details ALTER COLUMN is_update_data TYPE varchar(5);
ALTER TABLE qrtz_job_details ALTER COLUMN requests_recovery TYPE varchar(5);
```

**lookup_type.sort_strategy** — defined as `boolean` in DB but mapped as `int` in the Java entity:

```sql
ALTER TABLE lookup_type ALTER COLUMN sort_strategy TYPE integer USING CASE WHEN sort_strategy THEN 1 ELSE 0 END;
```

## 2. Backend (WildFly 26)

### Why WildFly 26 (not 27)

The codebase uses **javax.\*** namespace (Java EE). WildFly 27+ uses **jakarta.\*** (Jakarta EE 10). Shiro 1.13.0 and all application code are javax-based. Eclipse Transformer cannot recursively transform nested JARs in WEB-INF/lib. WildFly 26.1.3.Final natively supports javax.

### WildFly 26 Installation

Downloaded from: `https://github.com/wildfly/wildfly/releases/download/26.1.3.Final/wildfly-26.1.3.Final.zip`
Extracted to: `qto/wildfly-26.1.3.Final/`

### Configuration: standalone-full.xml

Location: `qto/wildfly-26.1.3.Final/standalone/configuration/standalone-full.xml`

Key modifications:

1. **System Properties** (after `</extensions>`, before `<management>`):
   ```xml
   <system-properties>
       <property name="com.arjuna.ats.arjuna.allowMultipleLastResources" value="true"/>
   </system-properties>
   ```

2. **Datasources** (in `<subsystem xmlns="urn:jboss:domain:datasources:6.0">`):
   - `qto` → `jdbc:postgresql://localhost:5432/qto-demo-104bf38` (user: qto_user)
   - `qto-ddl` → `jdbc:postgresql://localhost:5432/qto-demo-104bf38` (user: qto_user)
   - `platform` → `jdbc:postgresql://localhost:5432/platform` (user: qto_user)
   - PostgreSQL JDBC driver module: `org.postgresql`

3. **HTTP Port**: Changed from 8080 to **7081**

4. **JMS Queues** (in messaging-activemq subsystem, 13 queues):
   CompanyMessageQueue, DisconnectMultiEditQueue, DisputeMultiEditQueue, FileImportQueue, FtdiProcessingQueue, IntervalQueue, InvoiceChargeQueue, LocationMessageQueue, MultiDisputeQueue, MultiMacdQueue, ServiceMultiEditQueue, QuoteProcessingQueue, QuoteProcessingFailureQueue

### PostgreSQL Driver Module

Copied from WildFly 27 into:
`qto/wildfly-26.1.3.Final/modules/org/postgresql/main/`

### Build the WAR

```powershell
$env:JAVA_HOME = "C:\Java\oracleJdk-21"
cd qto
& "C:\apache-maven-3.9.12\bin\mvn.cmd" clean install -DskipTests
```

The WAR is produced at: `qto/qto-war/target/qto-war-1.18.1-SNAPSHOT.war`

### Deploy

```powershell
Copy-Item "qto\qto-war\target\qto-war-1.18.1-SNAPSHOT.war" "qto\wildfly-26.1.3.Final\standalone\deployments\qto.war"
```

### Start WildFly

```powershell
$env:JAVA_HOME = "C:\Java\oracleJdk-21"
& "qto\wildfly-26.1.3.Final\bin\standalone.bat" -c standalone-full.xml -b 0.0.0.0
```

`standalone-full.xml` is required (not `standalone.xml`) because the application uses JMS (MDB message-driven beans).

### Verify

- Server log: `qto/wildfly-26.1.3.Final/standalone/log/server.log`
- Look for: `WFLYSRV0025: WildFly Full 26.1.3.Final started`
- Test endpoint: `curl http://127.0.0.1:7081/qto/api/lookupTypes` → should return 200 with data

### Known Warnings (Non-Fatal)

- **ARJUNA012141**: Multiple last resources warning — expected with non-XA datasources
- **MSGraph errors**: "Must provide non-null values for clientId, tenantId, clientSecret" — Azure AD not configured locally, non-fatal

## 3. Authentication (Demo Mode)

The app uses Apache Shiro 1.13.0. Demo mode is already configured in `qto/qto-core/src/main/resources/shiro.ini`:

- `DemoAllAccessRealm` — authenticates any user
- `DemoAutoAuthFilter` — auto-logs in without credentials

No changes were needed for authentication.

## 4. Frontend: qto-ui (Port 4200)

```powershell
cd qto-ui
npm install --legacy-peer-deps --ignore-scripts
npx ng serve --port 4200 --proxy-config proxy.conf.json
```

`--ignore-scripts` skips the husky postinstall (fails because `.git` is at workspace root, not in `qto-ui/`).

### Files Modified

- **src/environments/environment.ts**: `wsUrl` → `ws://127.0.0.1:7081/qto`, `publicUrl` → `http://127.0.0.1:7081/public`
- **proxy.conf.json**: proxy targets changed from `127.0.0.1:6081` to `127.0.0.1:7081`

### API Routing

qto-ui uses a **proxy** (`proxy.conf.json`) to forward `/qto/api/*` and `/qto/ws/*` requests from localhost:4200 to the backend at 127.0.0.1:7081.

## 5. Frontend: help-desk-ui (Port 4201)

```powershell
cd help-desk-ui
npm install --legacy-peer-deps
npx ng serve --port 4201
```

### Files Modified

- **src/environments/environment.ts**: `qtoUrl` → `http://127.0.0.1:7081/qto/api`, `wsUrl` → `ws://127.0.0.1:7081/qto`

### API Routing

help-desk-ui makes **direct cross-origin requests** to `http://127.0.0.1:7081/qto/api`. The backend returns CORS headers (`Access-Control-Allow-Origin: *`).

## 6. Quick Start (All Components)

Open 3 terminals:

**Terminal 1 — WildFly:**
```powershell
$env:JAVA_HOME = "C:\Java\oracleJdk-21"
& "c:\Users\nigel.arugay\Downloads\repository\velocity\qto\wildfly-26.1.3.Final\bin\standalone.bat" -c standalone-full.xml -b 0.0.0.0
```

**Terminal 2 — qto-ui:**
```powershell
cd c:\Users\nigel.arugay\Downloads\repository\velocity\qto-ui
npx ng serve --port 4200 --proxy-config proxy.conf.json
```

**Terminal 3 — help-desk-ui:**
```powershell
cd c:\Users\nigel.arugay\Downloads\repository\velocity\help-desk-ui
npx ng serve --port 4201
```

## 7. Verified API Endpoints

| Endpoint               | Status | Notes                        |
|------------------------|--------|------------------------------|
| GET /qto/api/companies    | 200    | Returns empty collection     |
| GET /qto/api/lookupTypes  | 200    | 59 records                   |
| GET /qto/api/lookupValues | 200    | Returns collection           |
| GET /qto/api/subjects     | 200    | Returns collection           |
| GET /qto/api/services     | 200    | Returns empty collection     |
| GET /qto/api/locations    | 200    | Returns empty collection     |
| GET /qto/api/disputes     | 200    | Returns empty collection     |
| GET /qto/api/invoices     | 200    | Returns empty collection     |

## 8. Troubleshooting

| Problem | Cause | Fix |
|---------|-------|-----|
| `Bad value for type int : t` on lookupTypes | `lookup_type.sort_strategy` is boolean in DB, int in Java entity | `ALTER TABLE lookup_type ALTER COLUMN sort_strategy TYPE integer USING CASE WHEN sort_strategy THEN 1 ELSE 0 END;` |
| Quartz insert fails on `qrtz_job_details` | varchar(1) columns can't hold "TRUE"/"FALSE" | Widen 4 columns to varchar(5) |
| `ARJUNA012140: Adding multiple last resources is disallowed` | Two non-XA datasources in same JTA transaction | Add system property `com.arjuna.ats.arjuna.allowMultipleLastResources=true` |
| Missing JMS queues at startup | standalone.xml doesn't have messaging subsystem | Use `standalone-full.xml` and add 13 queue definitions |
| WildFly 27 ClassNotFoundException javax.ws.rs | Codebase uses javax.*, WildFly 27 expects jakarta.* | Use WildFly 26.1.3.Final instead |
| npm postinstall fails (husky) | `.git` not in project root | Use `--ignore-scripts` flag |
