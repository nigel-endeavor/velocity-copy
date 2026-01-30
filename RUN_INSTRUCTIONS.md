# QTO Application - Run Instructions

## 🚀 Easiest Way (Recommended)

### Start Everything
```bash
cd /path/to/velocity
./START_ALL.sh
```

This starts both backend and frontend in the background.

### Stop Everything
```bash
./STOP_ALL.sh
```

### Access the Application
- **Frontend**: http://localhost:7887/qto-ops/
- **Backend**: http://localhost:8080/qto
- **Health Check**: http://localhost:8080/qto/actuator/health

---

## 🛠️ Alternative Methods

### Method 1: Separate Terminals (More Control)

**Terminal 1 - Backend:**
```bash
cd qto/qto-spring-boot-app
./start.sh
```

**Terminal 2 - Frontend:**
```bash
cd qto-ui-react
bun --bun dev
```

**To Stop:**
- Press `Ctrl+C` in each terminal
- Or run `./stop.sh` in backend directory

### Method 2: Manual Commands

**Start Backend:**
```bash
cd qto/qto-spring-boot-app
./gradlew bootRun --args='--spring.profiles.active=dev'
```

**Start Frontend:**
```bash
cd qto-ui-react
bun install  # First time only
bun --bun dev
```

---

## 📋 Prerequisites

### Required
- **Bun**: 1.3+ ([Install](https://bun.sh))
- **Java**: 17+ (for backend)
- **Gradle**: 8.5+ (included via gradlew)

### Optional
- **Node.js**: Not required if using Bun runtime
- **npm**: Not required if using Bun

---

## 🔍 Troubleshooting

### Port Already in Use

**Backend (Port 8080):**
```bash
# Find process using port 8080
lsof -ti:8080 | xargs kill -9
```

**Frontend (Port 7887):**
```bash
# Find process using port 7887
lsof -ti:7887 | xargs kill -9
```

### Backend Won't Start
```bash
# Clean and rebuild
cd qto/qto-spring-boot-app
./gradlew clean
./gradlew build
./gradlew bootRun --args='--spring.profiles.active=dev'
```

### Frontend Won't Start
```bash
cd qto-ui-react

# Reinstall dependencies
rm -rf node_modules
bun install

# Start with Bun runtime (IMPORTANT)
bun --bun dev
```

### Vite Node Version Error
```
Error: Vite requires Node.js version 20.19+
```

**Solution**: Always use `bun --bun dev` instead of `bun dev`

---

## 📊 Check Status

### Backend Status
```bash
# Health check
curl http://localhost:8080/qto/actuator/health

# Application status
curl http://localhost:8080/qto/api/status
```

### Frontend Status
Visit: http://localhost:7887/qto-ops/

Should show landing page with backend status.

---

## 🗂️ Project Structure

```
velocity/
├── START_ALL.sh           # ← Start backend + frontend
├── STOP_ALL.sh            # ← Stop both services
├── qto/
│   └── qto-spring-boot-app/
│       ├── start.sh       # Start backend only
│       └── stop.sh        # Stop backend only
└── qto-ui-react/
    ├── package.json
    └── vite.config.ts
```

---

## 🎯 Quick Start Guide

### First Time Setup
```bash
# 1. Navigate to project
cd /path/to/velocity

# 2. Install frontend dependencies
cd qto-ui-react
bun install

# 3. Go back to velocity directory
cd ..

# 4. Start everything
./START_ALL.sh
```

### Daily Development
```bash
# Start
./START_ALL.sh

# Open browser to http://localhost:7887/qto-ops/

# Stop when done
./STOP_ALL.sh
```

---

## 🌟 Features to Try

### Service Worklist (Complete Feature)
1. Open http://localhost:7887/qto-ops/
2. Click **"Services"** in navigation
3. Try:
   - Search and filter
   - Sort columns (click headers)
   - Select multiple rows
   - Change page size
   - Export selected services

---

## 📚 More Information

- **Frontend Docs**: `qto-ui-react/QUICK_START.md`
- **Component Library**: `qto-ui-react/COMPONENT_LIBRARY.md`
- **Migration Guide**: `qto-ui-react/MIGRATION_COMPLETE.md`

---

## 💡 Pro Tips

### Run in Background
The `START_ALL.sh` script runs both services in the background, so you can close the terminal.

### View Logs
```bash
# Backend logs (if configured)
tail -f qto/qto-spring-boot-app/logs/application.log

# Frontend logs (console output)
# Check browser console (F12)
```

### IDE Integration
- **IntelliJ IDEA**: Open `qto/qto-spring-boot-app`, run `QtoApplication.main()`
- **VS Code**:
  - Backend: Run from terminal
  - Frontend: Use built-in terminal with `bun --bun dev`

---

## ✅ Quick Reference

| Task | Command |
|------|---------|
| **Start Both** | `./START_ALL.sh` |
| **Stop Both** | `./STOP_ALL.sh` |
| **Frontend Only** | `cd qto-ui-react && bun --bun dev` |
| **Backend Only** | `cd qto/qto-spring-boot-app && ./start.sh` |
| **Check Backend** | `curl http://localhost:8080/qto/actuator/health` |
| **Open Frontend** | http://localhost:7887/qto-ops/ |

---

**Questions?** See `qto-ui-react/QUICK_START.md` for detailed frontend guide.
