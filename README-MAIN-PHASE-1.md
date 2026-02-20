
# QTO Database Setup Guide

## 1. Run Liquibase

```bash
liquibase update
```

## 2. Install MySQL Locally
Install MySQL on your local machine before running Liquibase.

Common Pitfalls
Port conflict (3306)
If Docker MySQL is still running, stop it first:
`docker stop qto-mysql`

This ensures local MySQL can bind to port 3306.

## 3. Create a `liquibase.properties` file in the project root.
```
# Database connection
url=jdbc:mysql://localhost:3306/qto?useSSL=false&serverTimezone=UTC
username=qto_user
password=qto_pass
driver=com.mysql.cj.jdbc.Driver

# Liquibase changelog (filesystem path is easiest for Maven)
changeLogFile=database/changelog-master.xml
searchPath=src/main/resources

classpath=C:/ProgramData/chocolatey/lib/mysql-connector-java/tools/mysql-connector-java-8.0.15/mysql-connector-java-8.0.15.jar
driver=com.mysql.cj.jdbc.Driver

# Contexts (IMPORTANT)
contexts=common,dev

# Optional (depends on how scripts are written)
# defaultSchemaName=qto
```

## 4. Full Database Reset (Development Only)
Run the following SQL statements in MySQL:
```
DROP DATABASE IF EXISTS qto;
DROP DATABASE IF EXISTS platform;

CREATE DATABASE qto;
CREATE DATABASE platform;

CREATE USER 'qto_user'@'localhost' IDENTIFIED BY 'changeme';
GRANT ALL PRIVILEGES ON qto.* TO 'qto_user'@'localhost';

CREATE USER 'platform_user'@'localhost' IDENTIFIED BY 'changeme';
GRANT ALL PRIVILEGES ON platform.* TO 'platform_user'@'localhost';

FLUSH PRIVILEGES;
```

## 5. Fix Duplicate Changeset IDs
Locate and fix duplicate changeset IDs in:
`1_12_0/changelog-master.xml`

```
id="1_12_0-007-dev" author="rcasey" context="dev"
id="1_12_0-007-test" author="rcasey" context="test"
id="1_12_0-007-prod" author="rcasey" context="prod"
```

## 6. Manually Create Required Tables
Execute the following SQL statements:
```
USE platform;

CREATE TABLE IF NOT EXISTS platform.tenant (
  tenant_id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  name      VARCHAR(255) NOT NULL,
  active    BIT NOT NULL DEFAULT b'1',
  version   INT NOT NULL DEFAULT 1,
  PRIMARY KEY (tenant_id),
  UNIQUE KEY uq_tenant_name (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS platform.subject (
  subject_id    INT NOT NULL,
  username      VARCHAR(255) NULL,
  display_name  VARCHAR(255) NULL,
  email_address VARCHAR(255) NULL,
  active        BIT NOT NULL DEFAULT b'1',
  version       INT NOT NULL DEFAULT 1,
  PRIMARY KEY (subject_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE company (
  company_id             INT AUTO_INCREMENT PRIMARY KEY,
  company_name           VARCHAR(100) NOT NULL,
  company_type           VARCHAR(100) NULL,
  company_active         BIT DEFAULT 1 NOT NULL,
  company_uuid           VARCHAR(100) NOT NULL,
  billing_account_number VARCHAR(100) NULL,
  address_id             INT NULL,
  tenant_id              INT NOT NULL,
  version                INT DEFAULT 1 NOT NULL,
  CONSTRAINT company_uuid_unique UNIQUE (company_uuid)
);

CREATE INDEX fk_company_tenant ON company (tenant_id);
```

## 7. Update DBMS Configuration
Replace:
`<dbms type="mysql"/>`
With:
`<dbms type="mysql,mariadb"/>`

✅ Final Step
After completing all steps:
```liquibase update```