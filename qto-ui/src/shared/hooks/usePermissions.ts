/**
 * usePermissions Hook
 *
 * Role-based access control (RBAC) hook.
 * Checks user permissions based on roles from useAuth.
 */

import { useMemo } from 'react';
import { useAuth, UserRole } from './useAuth';

/**
 * Permission Type
 */
export type Permission = UserRole | 'any-write' | 'any-read';

/**
 * usePermissions Hook
 */
export function usePermissions() {
  const { user } = useAuth();

  const roles = useMemo(() => user?.roles || [], [user]);

  /**
   * Check if user has specific permission
   */
  const hasPermission = (permission: Permission): boolean => {
    if (!user || roles.length === 0) return false;

    // Admin has all permissions
    if (roles.includes('admin')) return true;

    // Special combined permissions
    if (permission === 'any-write') {
      return roles.some((role) => role.endsWith('-write'));
    }
    if (permission === 'any-read') {
      return roles.some((role) => role.endsWith('-read'));
    }

    // Check specific permission
    return roles.includes(permission as UserRole);
  };

  /**
   * Check if user has ALL of the specified permissions
   */
  const hasAllPermissions = (permissions: Permission[]): boolean => {
    return permissions.every((permission) => hasPermission(permission));
  };

  /**
   * Check if user has ANY of the specified permissions
   */
  const hasAnyPermission = (permissions: Permission[]): boolean => {
    return permissions.some((permission) => hasPermission(permission));
  };

  /**
   * Check if user is admin
   */
  const isAdmin = (): boolean => {
    return roles.includes('admin');
  };

  /**
   * Check if user can read specific resource
   */
  const canRead = (resource: 'order' | 'service' | 'location' | 'invoice' | 'dispute' | 'report'): boolean => {
    return hasPermission(`${resource}-read` as UserRole);
  };

  /**
   * Check if user can write specific resource
   */
  const canWrite = (resource: 'order' | 'service' | 'location' | 'invoice' | 'dispute'): boolean => {
    return hasPermission(`${resource}-write` as UserRole);
  };

  return {
    roles,
    hasPermission,
    hasAllPermissions,
    hasAnyPermission,
    isAdmin,
    canRead,
    canWrite,
  };
}

export default usePermissions;
