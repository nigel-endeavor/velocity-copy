/**
 * useAuth Hook
 *
 * Custom hook for authentication with role/permission extraction.
 * Uses mock user for local development.
 */

export type UserRole =
  | 'admin'
  | 'order-read'
  | 'order-write'
  | 'service-read'
  | 'service-write'
  | 'location-read'
  | 'location-write'
  | 'invoice-read'
  | 'invoice-write'
  | 'dispute-read'
  | 'dispute-write'
  | 'report-read';

export interface UserInfo {
  username: string;
  name: string;
  email: string;
  roles: UserRole[];
}

// Mock useAuth for local development (MSAL disabled)
export function useAuth() {
  return {
    user: {
      username: 'devuser',
      name: 'Dev User',
      email: 'devuser@example.com',
      roles: ['admin', 'order-read', 'order-write'] as UserRole[],
    },
    isAuthenticated: true,
    account: null,
    login: async () => { console.log('MSAL login disabled in local dev'); },
    logout: async () => { console.log('MSAL logout disabled in local dev'); },
    getAccessToken: async () => null,
    instance: null,
  };
}

export default useAuth;
