# QTO Application - Run Instructions

## 🚀 Easiest Way (Recommended)

### Start Frontend
```bash
cd /path/to/velocity
./START_ALL.sh
```

### Stop
```bash
./STOP_ALL.sh
```

### Access the Application
- **Frontend**: http://localhost:7887/qto-ops/

---

## 🛠️ Alternative: Manual Start

**Start Frontend:**
```bash
cd qto-ui
bun install  # First time only
bun --bun dev
```

---

## 📋 Prerequisites

### Required
- **Bun**: 1.3+ ([Install](https://bun.sh))

### Optional
- **Node.js**: Not required if using Bun runtime
- **npm**: Not required if using Bun

---

## 🔍 Troubleshooting

### Port Already in Use

**Frontend (Port 7887):**
```bash
# Find process using port 7887
lsof -ti:7887 | xargs kill -9
```

### Frontend Won't Start
```bash
cd qto-ui

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

### Frontend Status
Visit: http://localhost:7887/qto-ops/

---

## 🗂️ Project Structure

```
velocity/
├── START_ALL.sh           # ← Start frontend
├── STOP_ALL.sh            # ← Stop frontend
├── qto/                   # Backend libraries (qto-core, qto-rest-api)
└── qto-ui/
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
cd qto-ui
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

- **Frontend Docs**: `qto-ui/QUICK_START.md`
- **Component Library**: `qto-ui/COMPONENT_LIBRARY.md`
- **Migration Guide**: `qto-ui/MIGRATION_COMPLETE.md`

---

## 💡 Pro Tips

### Run in Background
The `START_ALL.sh` script runs the frontend in the background, so you can close the terminal.

### View Logs
- Frontend: Check browser console (F12)

---

## ✅ Quick Reference

| Task | Command |
|------|---------|
| **Start** | `./START_ALL.sh` |
| **Stop** | `./STOP_ALL.sh` |
| **Frontend** | `cd qto-ui && bun --bun dev` |
| **Open** | http://localhost:7887/qto-ops/ |

---

**Questions?** See `qto-ui/QUICK_START.md` for detailed frontend guide.
