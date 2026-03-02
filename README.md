# QTO Application

Quantum Task Orchestrator - React Frontend

## 🚀 Quick Start

### Start Frontend
```bash
./START_ALL.sh
```

### Stop
```bash
./STOP_ALL.sh
```

### Access Application
- **Frontend**: http://localhost:7887/qto-ops/

---

## 📚 Documentation

- **`RUN_INSTRUCTIONS.md`** - Detailed run instructions (start/stop/troubleshoot)
- **[Graphite workflow](agentx/graphite.agentx.md)** - Stacked branches and PR workflow
- **`qto-ui/QUICK_START.md`** - Frontend quick start guide
- **`qto-ui/MIGRATION_COMPLETE.md`** - Migration summary and features
- **`qto-ui/COMPONENT_LIBRARY.md`** - Component usage guide

---

## 🎯 What's Working

### ✅ Complete Features
- **Service Worklist** - Full CRUD with search, filter, sort, pagination, export
  - Navigate to: http://localhost:7887/qto-ops/#/services

### ✅ Component Library
- **Table** - Sorting, pagination, selection, card view
- **Forms** - Input, Select, Checkbox, Textarea
- **Modal** - Dialog system with focus management
- **Base** - Button, Card components

### ✅ Infrastructure
- React 19 + TypeScript + Vite + Bun
- Redux Toolkit state management
- Azure MSAL authentication
- Tailwind CSS styling
- Hash-based routing

---

## 📂 Project Structure

```
velocity/
├── START_ALL.sh              # ← Start backend + frontend
├── STOP_ALL.sh               # ← Stop both services
├── RUN_INSTRUCTIONS.md       # ← Detailed instructions
│
├── qto/                      # Backend libraries (qto-core, qto-rest-api)
└── qto-ui/             # Frontend (React)
    ├── QUICK_START.md
    ├── MIGRATION_COMPLETE.md
    └── COMPONENT_LIBRARY.md
```

---

## 🛠️ Tech Stack

### Frontend
- **React** 19.2 with hooks
- **TypeScript** 5.9
- **Vite** 7.3 (build tool)
- **Bun** 1.3 (package manager)
- **Tailwind CSS** 4.1
- **Redux Toolkit** 2.11
- **React Router** 7.13

### Backend
- **Spring Boot** 3.2.2
- **Java** 17
- **Gradle** 8.5

---

## 🎓 Learn More

### Frontend Development
See **`qto-ui/QUICK_START.md`** for:
- Component usage examples
- Redux patterns
- Styling conventions
- Feature migration guide

### Component Library
See **`qto-ui/COMPONENT_LIBRARY.md`** for:
- Table component (sorting, pagination, selection)
- Form components (Input, Select, Checkbox, Textarea)
- Modal component (dialog system)
- Usage examples and API reference

---

## ✅ Migration Status

**Foundation: COMPLETE ✅**
- React infrastructure
- Component library (15 components)
- Service Worklist feature
- Authentication & routing
- State management

**Ready for Production:**
- Service Worklist is fully functional
- 23 remaining Angular features ready to migrate

---

## 💡 Daily Workflow

```bash
# Morning - Start development
./START_ALL.sh

# ... develop and test ...

# Evening - Stop services
./STOP_ALL.sh
```

---

**Need Help?** See `RUN_INSTRUCTIONS.md` for troubleshooting.
