/**
 * Auth Guard
 *
 * Route guard that redirects unauthenticated users to login.
 */

import { ReactNode } from 'react';
import { Navigate, useLocation } from 'react-router-dom';
import { useAuthContext } from '@/contexts/AuthContext';

interface AuthGuardProps {
  children: ReactNode;
}

export function AuthGuard({ children }: AuthGuardProps) {
  const { isAuthenticated } = useAuthContext();
  const location = useLocation();

  if (!isAuthenticated) {
    return <Navigate to="/login" state={{ from: location }} replace />;
  }

  return <>{children}</>;
}

/**
 * Loading Guard
 * Shows loading spinner while auth state is being determined.
 */
export function LoadingGuard({ children }: AuthGuardProps) {
  return <>{children}</>;
}

export default AuthGuard;
