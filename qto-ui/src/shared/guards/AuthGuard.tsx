/**
 * Auth Guard
 *
 * Route guard that redirects unauthenticated users to login.
 * Wrap protected routes with this component.
 */

import { ReactNode } from 'react';
import { Navigate, useLocation } from 'react-router-dom';
import { useIsAuthenticated } from '@azure/msal-react';
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
export function AuthGuard({ children }: AuthGuardProps) {
  const isAuthenticated = useIsAuthenticated();
  const location = useLocation();

  if (!isAuthenticated) {
    // Redirect to login, saving current location
    return <Navigate to="/login" state={{ from: location }} replace />;
  }

  return <>{children}</>;
}

/**
 * Loading Guard
 * Shows loading spinner while authentication state is being determined
 */
export function LoadingGuard({ children }: AuthGuardProps) {
  const isAuthenticated = useIsAuthenticated();

  // You can add additional loading logic here if needed
  // For example, checking if MSAL is still initializing

  if (isAuthenticated === null) {
    return (
      <Box
        display="flex"
        justifyContent="center"
        alignItems="center"
        minHeight="100vh"
      >
        <CircularProgress />
      </Box>
    );
  }

  return <>{children}</>;
}

export default AuthGuard;
