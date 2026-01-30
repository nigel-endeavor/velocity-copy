# QTO Application - Setup Guide for Nigel

Quick reference guide for building and running the QTO application.

---

## 📋 Prerequisites

### Required Software
- **Java 17+** - for Spring Boot backend
- **Bun 1.3+** - for frontend development
- **Git** - for version control

### Verify Installation
```bash
java -version    # Should show Java 17 or higher
bun --version    # Should show 1.3.0 or higher
```

---

## 🚀 Quick Start (Recommended)

### Start Everything
```bash
cd /Users/johnnycrupi/Documents/devel/KickAnalytics/EndeavorVelocity/velocity-jan-2026/velocity
./START_ALL.sh
```

This starts both:
- **Backend** (Spring Boot) on http://localhost:8080
- **Frontend** (React) on http://localhost:7887/qto-ops/

### Stop Everything
```bash
./STOP_ALL.sh
```

---

## 🛠️ Manual Build & Run

### Backend (Spring Boot)

**Location**: `qto/qto-spring-boot-app/`

#### Build the Backend
```bash
cd qto/qto-spring-boot-app
./gradlew clean build
```

#### Run the Backend
```bash
# Development mode
./gradlew bootRun --args='--spring.profiles.active=dev'

# Or use the convenience script
./start.sh
```

#### Run Backend Tests
```bash
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
├── qto/
│   └── qto-spring-boot-app/     # Spring Boot backend
│       ├── src/
│       ├── build.gradle          # Gradle configuration
│       ├── start.sh              # Backend start script
│       └── stop.sh               # Backend stop script
│
├── qto-ui-react/                 # React frontend
│   ├── src/
│   ├── package.json              # Dependencies
│   ├── vite.config.ts            # Vite configuration
│   └── tsconfig.json             # TypeScript config
│
├── START_ALL.sh                  # Start both services
├── STOP_ALL.sh                   # Stop both services
└── README-Nigel.md               # This file
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
- **Java 17**
- **Spring Boot 3.2.2**
- **Gradle 8.5**
- **Port**: 8080

### Frontend
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
# Find and kill the process
lsof -ti:8080 | xargs kill -9
```

**Problem**: Tests failing
```bash
# Clean and rebuild
./gradlew clean test
```

### Frontend Issues

**Problem**: `bun: command not found`
```bash
# Install Bun
curl -fsSL https://bun.sh/install | bash
```

**Problem**: Port 7887 already in use
```bash
# Find and kill the process
lsof -ti:7887 | xargs kill -9

# Or check PID file
cat /Users/johnnycrupi/Documents/devel/KickAnalytics/EndeavorVelocity/velocity-jan-2026/velocity/.qto-frontend.pid
kill <PID>
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
# Check what's wrong
bun run lint

# Auto-fix where possible
bun run lint --fix
```

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

# Frontend only
cd qto-ui-react
bun --bun dev                 # Start
bun run lint                  # Lint
bun run build                 # Build for production

# Check what's running
lsof -ti:8080                 # Backend port
lsof -ti:7887                 # Frontend port
```

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

---

**Last Updated**: 2026-01-29
**Tested On**: macOS with Java 17, Bun 1.3
