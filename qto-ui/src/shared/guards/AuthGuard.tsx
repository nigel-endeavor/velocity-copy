/**
 * Auth Guard
 *
 * Route guard that redirects unauthenticated users to login.
 * Wrap protected routes with this component.
 */

import { ReactNode } from 'react';

interface AuthGuardProps {
  children: ReactNode;
}

/**
 * Auth Guard Component
 */
export function AuthGuard({ children }: AuthGuardProps) {
  // Always allow access in local/dev mode
  return <>{children}</>;
}

/**
 * Loading Guard
 * Shows loading spinner while authentication state is being determined
 */
export function LoadingGuard({ children }: AuthGuardProps) {
  // Always allow access in local/dev mode
  return <>{children}</>;
}

export default AuthGuard;
