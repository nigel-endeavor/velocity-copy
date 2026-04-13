/**
 * Permission Guard
 *
 * Route guard that checks user permissions.
 * Redirects to 403 (Forbidden) page if user lacks required permissions.
 */

import { ReactNode } from 'react';
import { usePermissions, Permission } from '../hooks/usePermissions';

interface PermissionGuardProps {
  children: ReactNode;
  requiredPermissions: Permission[];
  requireAll?: boolean;
}

export function PermissionGuard({
  children,
  requiredPermissions,
  requireAll = false,
}: PermissionGuardProps) {
  const { hasAllPermissions, hasAnyPermission } = usePermissions();

  const hasAccess = requireAll
    ? hasAllPermissions(requiredPermissions)
    : hasAnyPermission(requiredPermissions);

  if (!hasAccess) {
    return <ForbiddenPage requiredPermissions={requiredPermissions} />;
  }

  return <>{children}</>;
}

/**
 * Admin Guard
 * Shorthand for requiring admin permission
 */
export function AdminGuard({ children }: { children: ReactNode }) {
  return (
    <PermissionGuard requiredPermissions={['admin']}>
      {children}
    </PermissionGuard>
  );
}

/**
 * Forbidden Page
 * Displayed when user lacks required permissions
 */
function ForbiddenPage({ requiredPermissions }: { requiredPermissions: Permission[] }) {
  return (
    <div className="flex min-h-screen flex-col items-center justify-center p-6 text-center">
      <h1 className="text-6xl font-bold text-red-500">403</h1>
      <h2 className="mt-4 text-xl font-semibold text-slate-900">Access Forbidden</h2>
      <p className="mt-2 text-slate-600">You do not have permission to access this page.</p>
      <p className="mt-1 text-sm text-slate-500">
        Required permissions: {requiredPermissions.join(', ')}
      </p>
      <div className="mt-6 flex gap-3">
        <a
          href="/"
          className="inline-flex items-center rounded-lg bg-primary px-4 py-2 text-sm font-medium text-white hover:bg-primary/90"
        >
          Go to Home
        </a>
        <button
          type="button"
          onClick={() => window.history.back()}
          className="inline-flex items-center rounded-lg border border-slate-300 bg-white px-4 py-2 text-sm font-medium text-slate-700 hover:bg-slate-50"
        >
          Go Back
        </button>
      </div>
    </div>
  );
}

export default PermissionGuard;
