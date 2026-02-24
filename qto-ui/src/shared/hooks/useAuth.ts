/**
 * useAuth Hook
 *
 * Simple authentication for Endeavor Velocity.
 * Wraps AuthContext with app-specific types.
 */

import { useAuthContext } from '@/contexts/AuthContext';

export type { UserRole, UserInfo } from '@/contexts/AuthContext';

/**
 * useAuth Hook
 */
export function useAuth() {
  const { user, isAuthenticated, login, logout } = useAuthContext();

  return {
    isAuthenticated,
    user,
    login,
    logout,
    getAccessToken: async () => null,
  };
}

export default useAuth;
