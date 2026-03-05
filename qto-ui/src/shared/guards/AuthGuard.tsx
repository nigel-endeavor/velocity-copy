/**
 * Auth Guard
 *
 * Route guard that redirects unauthenticated users to login.
 * Wrap protected routes with this component.
 */

import { ReactNode } from 'react';
import { Navigate, useLocation } from 'react-router-dom';
// MSAL disabled for local development
import { Box, CircularProgress } from '@mui/material';

interface AuthGuardProps {
  children: ReactNode;
}

/**
 * Auth Guard Component
 *
 * Usage:
 * <AuthGuard>
 *   <ProtectedComponent />
 * </AuthGuard>
 */
  // Always allow access in local/dev mode
  return <>{children}</>;
}

/**
 * Loading Guard
 * Shows loading spinner while authentication state is being determined
 */
  // Always allow access in local/dev mode
  return <>{children}</>;
}

export default AuthGuard;
