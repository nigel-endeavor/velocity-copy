# QTO UI - React + TypeScript + Vite + Bun

Modern React frontend for the Quantum Task Orchestrator (QTO) application, migrated from Angular 16.

## 🚀 Tech Stack

- **React**: 19.2+ with functional components and hooks
- **TypeScript**: 5.9+ for type safety
- **Vite**: 7.3+ for blazing-fast development and optimized builds
- **Bun**: 1.3+ as package manager and runtime
- **Tailwind CSS**: 4.1+ for utility-first styling
- **Redux Toolkit**: 2.11+ for state management
- **React Router**: 7.13+ for routing (hash-based)
- **Azure MSAL**: 5+ for authentication
- **Axios**: 1.13+ for API calls

## 📦 Quick Start

### Prerequisites

- **Bun**: 1.3+ ([Install Bun](https://bun.sh))
- **Backend**: Spring Boot backend running on port 8080

### Installation & Running

```bash
# Install dependencies
bun install

# Start development server (port 4200)
bun --bun dev
# or
bun start

# Build for production
bun run build

# Preview production build
bun run preview

# Run tests
bun test

# Lint code
bun run lint
```

The application will be available at:
- **Dev**: http://localhost:4200/qto-ops/
- **API Proxy**: Configured for https://127.0.0.1:8443/qto/api

## 📁 Project Structure

```
qto-ui-react/
├── src/
│   ├── config/           # Configuration files
│   │   ├── environment.ts    # Environment variables
│   │   └── authConfig.ts     # Azure MSAL configuration
│   ├── store/            # Redux store and slices
│   │   └── index.ts          # Store configuration
│   ├── services/         # API services
│   │   ├── apiClient.ts      # Axios instance with auth interceptor
│   │   └── statusService.ts  # Backend status service
│   ├── features/         # Feature modules (to be added)
│   ├── components/       # Reusable components (to be added)
│   ├── layouts/          # Layout components
│   │   └── MainLayout.tsx    # Main app layout with nav
│   ├── pages/            # Page components
│   │   └── LandingPage.tsx   # Landing page with backend status
│   ├── router/           # Routing configuration
│   │   └── index.tsx         # React Router setup
│   ├── hooks/            # Custom React hooks
│   ├── utils/            # Utility functions
│   └── types/            # TypeScript type definitions
├── public/               # Static assets
├── .env.local            # Local environment variables
├── vite.config.ts        # Vite configuration
└── package.json          # Project dependencies
```

## 🔒 Authentication

The app uses **Azure MSAL** (Microsoft Authentication Library) for OAuth2 authentication:

- **Client ID**: Configured in `.env.local`
- **Authority**: Azure AD tenant
- **Flow**: Redirect-based authentication
- **Token**: Automatically added to API requests via Axios interceptor

## 🛠️ Configuration

### Environment Variables

Create `.env.local` in the project root:

```env
VITE_API_URL=/qto/api
VITE_AZURE_CLIENT_ID=your-client-id
VITE_AZURE_AUTHORITY=https://login.microsoftonline.com/your-tenant-id
VITE_AZURE_REDIRECT_URI=http://localhost:4200/qto-ops/
```

### Vite Config Highlights

- **Port**: 4200 (matches Angular dev server)
- **Base Path**: /qto-ops/
- **API Proxy**: Proxies `/qto/api` to backend
- **Path Alias**: `@/` for src directory imports

## 🔄 Migration from Angular

### Completed

✅ Project setup with React + TypeScript + Vite + Bun
✅ Azure MSAL authentication integration
✅ Redux Toolkit store setup
✅ React Router (hash-based routing)
✅ Tailwind CSS v4 styling system
✅ API client with authentication interceptor
✅ Main layout component with Tailwind
✅ Landing page with backend status check
✅ Environment configuration
✅ **Complete shared component library:**
  - Table (with sorting, pagination, selection, card view)
  - Form components (Input, Select, Checkbox, Textarea)
  - Modal with focus management
  - Base components (Button, Card)

### Pending (Tasks #7, #8)

🔲 Convert Angular components to React components
🔲 Migrate NgRx stores to Redux Toolkit slices
🔲 Convert feature modules to React pages
🔲 Implement guards as route protection
🔲 Convert Angular services to React services
🔲 Migrate forms and validation

## 🎯 Key Differences from Angular

| Angular | React |
|---------|-------|
| NgModules | Function components |
| Services | Custom hooks + services |
| NgRx | Redux Toolkit |
| RxJS | React Query / native promises |
| Dependency Injection | Props + Context |
| Directives | Components + hooks |
| Pipes | Functions |
| Guards | Route loaders/protection |

## 📡 API Integration

The `apiClient` service automatically:
- Adds authentication token to requests
- Handles token refresh via MSAL
- Manages error responses (401, etc.)
- Proxies requests through Vite dev server

Example service:

```typescript
import apiClient from '@/services/apiClient';

export const myService = {
  getData: async () => {
    const response = await apiClient.get('/endpoint');
    return response.data;
  }
};
```

## 🧪 Testing

Testing setup with:
- **Vitest**: Fast unit testing
- **React Testing Library**: Component testing
- **@testing-library/jest-dom**: DOM matchers

```bash
# Run tests
bun test

# Watch mode
bun test --watch
```

## 🏗️ Development

### Adding a New Feature

1. Create feature directory in `src/features/myFeature/`
2. Create Redux slice in `src/store/` (if needed)
3. Create service in `src/services/`
4. Add route in `src/router/index.tsx`
5. Create page component in `src/pages/`

### Code Style

- Use functional components with hooks
- Use TypeScript for all files
- Follow Tailwind CSS utility-first approach
- Use Redux Toolkit for global state
- Use React Query for server state (optional)
- See `TAILWIND_GUIDE.md` for styling conventions

## 📦 Building

```bash
# Build for production
bun run build

# Output directory: dist/
# Base path: /qto-ops/
```

The build output can be deployed to any static hosting or served from the Spring Boot backend.

## 🔗 Backend Integration

The frontend expects the Spring Boot backend to be running:

- **Dev**: http://localhost:8080/qto
- **Endpoints**:
  - `/qto/api/status` - Application status
  - `/qto/api/status/ping` - Connectivity check
  - `/qto/actuator/health` - Health check

## 📝 License

Enterprise software - All rights reserved

---

**Version**: 1.0.0
**Last Updated**: January 29, 2026
**Migration Status**: Foundation Complete ✅
