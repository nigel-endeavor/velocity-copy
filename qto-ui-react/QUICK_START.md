# QTO React - Quick Start Guide

## 🚀 Start Development

### 1. Start Backend (Terminal 1)
```bash
cd ../qto/qto-spring-boot-app
./start.sh
# Backend will run on http://localhost:8080
```

### 2. Start Frontend (Terminal 2)
```bash
cd qto-ui-react
bun --bun dev
# Frontend will run on http://localhost:4200/qto-ops/
```

### 3. Open Browser
Navigate to: **http://localhost:4200/qto-ops/**

## 📱 Navigate the App

### Landing Page
- **URL**: http://localhost:4200/qto-ops/
- Shows backend status and tech stack info

### Service Worklist (Complete Feature)
- **URL**: http://localhost:4200/qto-ops/#/services
- **OR**: Click "Services" in the navigation menu

**Features to Try:**
1. ✅ **Search Filters** - Filter by customer, type, status, priority
2. ✅ **Sorting** - Click any column header to sort
3. ✅ **Pagination** - Change page size (10, 25, 50, 100 per page)
4. ✅ **Selection** - Select multiple services with checkboxes
5. ✅ **Export** - Click "Export Selected" to download (modal confirmation)
6. ✅ **Row Click** - Click any row to view details (navigation)

## 🔧 Useful Commands

```bash
# Install dependencies
bun install

# Start dev server (IMPORTANT: use --bun flag)
bun --bun dev

# Build for production
bun run build

# Preview production build
bun run preview

# Run linter
bun run lint

# Type check
bun run type-check
```

## 📂 Project Structure

```
qto-ui-react/
├── src/
│   ├── components/          # Shared component library
│   │   ├── Table/           # Full-featured data table
│   │   ├── Form/            # Input, Select, Checkbox, Textarea
│   │   └── Modal/           # Dialog system
│   ├── features/
│   │   └── service-worklist/ # Example feature (complete)
│   ├── layouts/             # MainLayout with navigation
│   ├── pages/               # LandingPage
│   └── store/               # Redux store
└── Documentation files
```

## 🎯 Key Files

### Component Library
- `src/components/Table/Table.tsx` - Main table component
- `src/components/Form/Input.tsx` - Input component
- `src/components/Modal/Modal.tsx` - Modal component
- `src/components/index.ts` - Central exports

### Service Worklist Feature
- `src/features/service-worklist/ServiceWorklist.tsx` - Main page
- `src/features/service-worklist/serviceWorklistSlice.ts` - Redux state
- `src/features/service-worklist/types.ts` - TypeScript interfaces

### Configuration
- `vite.config.ts` - Vite configuration (port, proxy)
- `tailwind.config.js` - Tailwind CSS (custom colors)
- `src/config/environment.ts` - Environment variables
- `src/store/index.ts` - Redux store setup

## 📚 Documentation

- **`README.md`** - Project overview
- **`MIGRATION_ANALYSIS.md`** - Feature migration plan
- **`COMPONENT_LIBRARY.md`** - Component usage guide
- **`TAILWIND_GUIDE.md`** - Styling conventions
- **`MIGRATION_COMPLETE.md`** - Migration summary
- **`src/components/Table/README.md`** - Table component docs

## 🐛 Troubleshooting

### Problem: Node.js version error
```
You are using Node.js 18.x. Vite requires Node.js version 20.19+
```
**Solution**: Use `bun --bun dev` instead of `bun dev`

### Problem: Port 4200 already in use
```bash
# Find and kill the process
lsof -ti:4200 | xargs kill -9

# Or use a different port in vite.config.ts
```

### Problem: Backend API not responding
```bash
# Check backend is running
curl http://localhost:8080/qto/actuator/health

# Restart backend if needed
cd ../qto/qto-spring-boot-app
./stop.sh
./start.sh
```

### Problem: CORS errors
- Vite proxy is configured in `vite.config.ts`
- Backend should allow requests from localhost:4200
- Check console for specific error messages

## 🔐 Authentication

The app uses **Azure MSAL** for authentication:

1. Configure in `.env.local`:
```env
VITE_AZURE_CLIENT_ID=your-client-id
VITE_AZURE_AUTHORITY=https://login.microsoftonline.com/your-tenant
VITE_AZURE_REDIRECT_URI=http://localhost:4200/qto-ops/
```

2. On first visit, you'll be redirected to Azure login
3. After login, you'll return to the app with authentication

## 💡 Development Tips

### Hot Reload
- Save any file to see changes instantly
- No page refresh needed for most changes

### TypeScript
- Use TypeScript for all new files
- Import types: `import type { Service } from './types'`
- Leverage autocomplete in VS Code

### Styling
- Use Tailwind CSS utility classes
- Check `TAILWIND_GUIDE.md` for common patterns
- Custom colors: `bg-primary-600`, `text-primary-500`

### Components
- Import from `@/components`: `import { Table, Button } from '@/components'`
- All components are TypeScript with full prop types
- See `COMPONENT_LIBRARY.md` for usage examples

## 📊 Service Worklist Features

The Service Worklist demonstrates all migration patterns:

### Search & Filter
- Customer name search
- Service type dropdown
- Status filter
- Priority filter
- Clear filters button

### Table Features
- Click column headers to sort (ascending/descending)
- Select individual rows or use "select all"
- Change page size (10, 25, 50, 100)
- Navigate pages with pagination controls

### Actions
- Click row to navigate to details
- Select multiple rows for bulk operations
- Export selected services to Excel
- Modal confirmation for exports

### UI Features
- Loading skeleton while fetching data
- Empty state when no results
- Error alerts for failures
- Status badges with color coding
- Priority indicators

## 🚀 Next Steps

1. **Explore the Service Worklist** - Try all features
2. **Review the Code** - See migration patterns
3. **Migrate Another Feature** - Use Service Worklist as template
4. **Customize Components** - Modify to match your needs

## 📧 Need Help?

- Check documentation files in the project root
- Review component examples in `src/components/`
- See `MIGRATION_COMPLETE.md` for full migration summary

---

**Happy Coding! 🎉**
