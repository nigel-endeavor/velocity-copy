# QTO Application - Setup Guide for Nigel

Quick reference guide for building and running the QTO application.

---

## 📋 Prerequisites

### Required Software
- **Java 21+** - for Spring Boot backend
- **Bun 1.3+** - for frontend development (qto-ui-react)
- **Git** - for version control

### Verify Installation
```bash
java -version    # Should show Java 21 or higher
bun --version    # Should show 1.3.0 or higher
```

---

## 🚀 Quick Start (Recommended)

### Start Everything
```bash
cd /path/to/velocity
./START_ALL.sh
```

This starts both:
- **Backend** (Spring Boot) on http://localhost:8080/qto
- **Frontend** (React) on http://localhost:7887/qto-ops/

### Stop Everything
```bash
./STOP_ALL.sh
```

### Access the Application
- **Frontend (React)**: http://localhost:7887/qto-ops/
- **Service Worklist**: http://localhost:7887/qto-ops/#/services
- **Backend API**: http://localhost:8080/qto
- **Health Check**: http://localhost:8080/qto/actuator/health
- **Status Endpoint**: http://localhost:8080/qto/api/status

---

## 🏗️ Build System (Gradle)

The project has **fully migrated from Maven to Gradle**. All backend and UI builds use Gradle.

### Package & Group
- **Group ID**: `com.endeavorms.velocity`
- **Package**: `com.endeavorms.velocity.qto` (backend core, database, rest-api)
- **Spring Boot App**: `com.endeavorms.qto`

---

## 🛠️ Manual Build & Run

### Backend (Spring Boot)

**Location**: `qto/qto-spring-boot-app/`

#### Build the Backend (from qto root)
```bash
cd qto
./gradlew clean build -x test
```

#### Run the Backend
```bash
cd qto/qto-spring-boot-app
./gradlew bootRun --args='--spring.profiles.active=dev'

# Or use the convenience script
./start.sh
```

#### Run Backend Tests
```bash
cd qto/qto-spring-boot-app
./gradlew test
```

#### Stop the Backend
```bash
./stop.sh
# Or manually: kill the process using the PID from .qto-backend.pid
```

**Backend URL**: http://localhost:8080/qto

---

### Frontend (React + Vite)

**Location**: `qto-ui-react/`

#### Install Dependencies
```bash
cd qto-ui-react
bun install
```

#### Run the Frontend
```bash
# Development mode (hot reload enabled)
bun --bun dev
```

#### Run ESLint (Code Quality Check)
```bash
bun run lint
```

#### Run TypeScript Type Check
```bash
bun run tsc --noEmit
```

#### Build for Production
```bash
bun run build
```

**Frontend URL**: http://localhost:7887/qto-ops/

---

## 📂 Project Structure

```
velocity/
├── qto/                           # Backend (multi-module Gradle)
│   ├── build.gradle.kts           # Root Gradle config
│   ├── settings.gradle.kts
│   ├── gradlew
│   ├── qto-core/                  # Core domain, managers, JPA entities
│   ├── qto-database/              # Liquibase migrations, DB scripts
│   ├── qto-rest-api/              # REST resources (JAX-RS + Spring MVC)
│   └── qto-spring-boot-app/       # Main Spring Boot application
│       ├── src/
│       ├── build.gradle.kts
│       ├── start.sh
│       └── stop.sh
│
├── qto-ui-react/                  # React frontend (primary UI)
│   ├── src/
│   ├── package.json
│   ├── vite.config.ts
│   └── QUICK_START.md
│
├── qto-ui/                        # Angular frontend (legacy)
│   └── build.gradle
│
├── help-desk-ui/                  # Angular help desk UI
│   └── build.gradle
│
├── START_ALL.sh                   # Start backend + frontend
├── STOP_ALL.sh                    # Stop both services
├── RUN_INSTRUCTIONS.md            # Detailed run instructions
└── README-Nigel.md                # This file
```

---

## 🖥️ UI Options for Testing the Backend

| UI | Tech | URL | Purpose |
|----|------|-----|---------|
| **qto-ui-react** | React 19 | http://localhost:7887/qto-ops/ | Primary UI – Service Worklist, etc. |
| **qto-ui** | Angular 16 | (build & serve separately) | Legacy Angular UI |
| **help-desk-ui** | Angular 16 | (build & serve separately) | Help desk interface |

**Recommended**: Use **qto-ui-react** for testing the backend.

---

## 🔌 Backend API Endpoints

| Endpoint | Method | Description |
|----------|--------|-------------|
| `/qto/api/status` | GET | Application status and version |
| `/qto/api/status/ping` | GET | Simple connectivity check |
| `/qto/actuator/health` | GET | Health check |
| `/qto/actuator/info` | GET | Application info |
| `/qto/actuator/metrics` | GET | Application metrics |

```bash
# Quick test
curl http://localhost:8080/qto/api/status
curl http://localhost:8080/qto/actuator/health
```

---

## 🧪 Testing

### Backend Tests
```bash
cd qto/qto-spring-boot-app
./gradlew test

# Expected: 40/40 tests passing
# ✓ ActuatorEndpointsTest: 11 tests
# ✓ ConfigurationPropertiesTest: 8 tests
# ✓ StatusControllerTest: 12 tests
# ✓ QtoApplicationTests: 9 tests
```

### Frontend Code Quality
```bash
cd qto-ui-react
bun run lint

# Expected: ESLint passing with 0 errors, 0 warnings
```

---

## 🔧 Technology Stack

### Backend
- **Java 21**
- **Spring Boot 3.2.2**
- **Gradle 8.5** (no Maven)
- **Database**: PostgreSQL 12+
- **Package**: `com.endeavorms.velocity.qto`
- **Port**: 8080

### Frontend (qto-ui-react)
- **React 19.2** with TypeScript
- **Vite 7.3** (build tool)
- **Bun 1.3** (package manager & runtime)
- **Tailwind CSS 4.1**
- **Redux Toolkit 2.11** (state management)
- **React Router 7.13**
- **Azure MSAL** (authentication)
- **Port**: 7887

---

## 🐛 Troubleshooting

### Backend Issues

**Problem**: `./gradlew: Permission denied`
```bash
chmod +x gradlew
chmod +x start.sh
chmod +x stop.sh
```

**Problem**: Port 8080 already in use
```bash
lsof -ti:8080 | xargs kill -9
```

**Problem**: Tests failing
```bash
cd qto/qto-spring-boot-app
./gradlew clean test
```

**Problem**: Build fails – clean and rebuild
```bash
cd qto
./gradlew clean build -x test
```

### Frontend Issues

**Problem**: `bun: command not found`
```bash
# Install Bun
curl -fsSL https://bun.sh/install | bash
```

**Problem**: Port 7887 already in use
```bash
lsof -ti:7887 | xargs kill -9
```

**Problem**: Module import errors
```bash
# Clear Vite cache and reinstall
rm -rf node_modules/.vite
bun install
bun --bun dev
```

**Problem**: ESLint errors
```bash
bun run lint
bun run lint --fix
```

**Problem**: Vite Node version error
```
Error: Vite requires Node.js version 20.19+
```
**Solution**: Use `bun --bun dev` instead of `bun dev`

---

## 📝 Development Workflow

### Typical Development Session

1. **Start both services**:
   ```bash
   ./START_ALL.sh
   ```

2. **Open in browser**:
   - Frontend: http://localhost:7887/qto-ops/
   - Backend API: http://localhost:8080/qto

3. **Make code changes**:
   - Frontend changes auto-reload (Vite HMR)
   - Backend requires restart for most changes

4. **Run tests before committing**:
   ```bash
   # Backend tests
   cd qto/qto-spring-boot-app
   ./gradlew test

   # Frontend lint
   cd qto-ui-react
   bun run lint
   ```

5. **Stop services when done**:
   ```bash
   ./STOP_ALL.sh
   ```

---

## 🔐 Authentication

The application uses **Azure MSAL** for authentication:

- Login redirects to Azure AD
- Tokens are stored in localStorage
- API requests automatically include Bearer token
- Configuration in `qto-ui-react/src/config/authConfig.ts`

---

## 📊 Current Features

### Implemented
- ✅ Service Worklist (full CRUD with search/filter/sort/export)
- ✅ Table component library
- ✅ Form components (Input, Select, Checkbox, Textarea)
- ✅ Modal component
- ✅ Authentication (Azure MSAL)
- ✅ Redux state management
- ✅ Mock data for offline development

### Pending Migration (from Angular)
- ⏳ Order Detail
- ⏳ Locations Worklist
- ⏳ 21 additional features

---

## 🆘 Need Help?

### Common Commands Reference
```bash
# Start everything
./START_ALL.sh

# Stop everything
./STOP_ALL.sh

# Backend only
cd qto/qto-spring-boot-app
./start.sh                    # Start
./stop.sh                     # Stop
./gradlew test                # Test

# Build entire backend from qto root
cd qto
./gradlew clean build -x test

# Frontend only
cd qto-ui-react
bun --bun dev                 # Start
bun run lint                  # Lint
bun run build                 # Build for production

# Check what's running
lsof -ti:8080                 # Backend port
lsof -ti:7887                 # Frontend port
```

### Documentation
- **RUN_INSTRUCTIONS.md** – detailed run instructions
- **qto-ui-react/QUICK_START.md** – frontend quick start
- **qto-ui-react/COMPONENT_LIBRARY.md** – component usage
- **qto/qto-spring-boot-app/README.md** – backend details

### Logs
- **Backend**: Check terminal output or `qto/qto-spring-boot-app/logs/`
- **Frontend**: Check terminal output or `/tmp/qto-ui-dev.log`

---

## 📌 Quick Tips

1. **Always use `bun --bun dev`** (not just `bun dev`) to use Bun's runtime instead of Node.js
2. **Backend changes** require restart, **frontend changes** auto-reload
3. **Mock data** is used when backend API fails (see `qto-ui-react/src/features/service-worklist/mockData.ts`)
4. **Type imports** must use `import type { ... }` syntax for Vite compatibility
5. **Clear Vite cache** if you see weird module errors: `rm -rf node_modules/.vite`
6. **Gradle only** – no Maven; all `pom.xml` files have been removed
7. **PostgreSQL only** – database migrated from MySQL

---

**Last Updated**: 2026-02-10
**Tested On**: macOS with Java 21, Bun 1.3
**Build System**: Gradle 8.5 (Maven removed)
