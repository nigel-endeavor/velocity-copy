/**
 * Permission Guard
 *
 * Route guard that checks user permissions.
 * Redirects to 403 (Forbidden) page if user lacks required permissions.
 */

import { ReactNode } from 'react';
import { Navigate } from 'react-router-dom';
import { Box, Typography, Button } from '@mui/material';
import { usePermissions, Permission } from '../hooks/usePermissions';

interface PermissionGuardProps {
  children: ReactNode;
  requiredPermissions: Permission[];
  requireAll?: boolean; // If true, user must have ALL permissions; if false, user needs ANY permission
}

/**
 * Permission Guard Component
 *
 * Usage:
 * <PermissionGuard requiredPermissions={['order-write']}>
 *   <OrderEditComponent />
 * </PermissionGuard>
 *
 * <PermissionGuard requiredPermissions={['admin', 'order-write']} requireAll={false}>
 *   <OrderManagementComponent />
 * </PermissionGuard>
 */
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
    <Box
      display="flex"
      flexDirection="column"
      justifyContent="center"
      alignItems="center"
      minHeight="100vh"
      textAlign="center"
      p={3}
    >
      <Typography variant="h1" color="error" gutterBottom>
        403
      </Typography>
      <Typography variant="h5" gutterBottom>
        Access Forbidden
      </Typography>
      <Typography variant="body1" color="text.secondary" paragraph>
        You do not have permission to access this page.
      </Typography>
      <Typography variant="body2" color="text.secondary" paragraph>
        Required permissions: {requiredPermissions.join(', ')}
      </Typography>
      <Box mt={2}>
        <Button variant="contained" color="primary" href="/" sx={{ mr: 2 }}>
          Go to Home
        </Button>
        <Button variant="outlined" onClick={() => window.history.back()}>
          Go Back
        </Button>
      </Box>
    </Box>
  );
}

export default PermissionGuard;
