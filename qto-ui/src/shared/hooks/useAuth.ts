/**
 * useAuth Hook
 *
 * Custom hook for MSAL authentication with role/permission extraction.
 * Wraps @azure/msal-react hooks with app-specific logic.
 */

// MSAL disabled for local development

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

// Mock useAuth for development
export function useAuth() {
  return {
    user: {
      username: 'devuser',
      name: 'Dev User',
      email: 'devuser@example.com',
      roles: ['admin', 'order-read', 'order-write'],
    },
    isAuthenticated: true,
  };
}

export default useAuth;

/**
 * User Roles (from Angular app)
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

/**
 * User Info Interface
 */
export interface UserInfo {
  username: string;
  name: string;
  email: string;
  roles: UserRole[];
}

/**
 * useAuth Hook
 */
export function useAuth() {
  const { instance, accounts } = useMsal();
  const isAuthenticated = useIsAuthenticated();

  /**
   * Get current account
   */
  const account: AccountInfo | null = accounts[0] || null;

  /**
   * Extract user info from account
   */
  const user: UserInfo | null = account
    ? {
        username: account.username,
        name: account.name || account.username,
        email: account.username,
        roles: extractRoles(account),
      }
    : null;

  /**
   * Get access token
   */
  const getAccessToken = async (): Promise<string | null> => {
    if (!account) return null;

    try {
      const response = await instance.acquireTokenSilent({
        scopes: ['User.Read'],
        account,
      });
      return response.accessToken;
    } catch (error) {
      console.error('Silent token acquisition failed:', error);
      try {
        const response = await instance.acquireTokenPopup({
          scopes: ['User.Read'],
        });
        return response.accessToken;
      } catch (popupError) {
        console.error('Token acquisition failed:', popupError);
        return null;
      }
    }
  };

  /**
   * Login
   */
  const login = async () => {
    try {
      await instance.loginRedirect({
        scopes: ['User.Read', 'openid', 'profile', 'email'],
      });
    } catch (error) {
      console.error('Login failed:', error);
    }
  };

  /**
   * Logout
   */
  const logout = async () => {
    try {
      await instance.logoutRedirect({
        account,
      });
    } catch (error) {
      console.error('Logout failed:', error);
    }
  };

  return {
    isAuthenticated,
    user,
    account,
    login,
    logout,
    getAccessToken,
    instance,
  };
}

/**
 * Extract roles from MSAL account
 * Roles come from Azure AD claims (roles, groups, or app roles)
 */
function extractRoles(account: AccountInfo): UserRole[] {
  const roles: UserRole[] = [];

  // Check idTokenClaims for roles
  const claims = account.idTokenClaims as any;
  if (claims?.roles && Array.isArray(claims.roles)) {
    claims.roles.forEach((role: string) => {
      if (isValidRole(role)) {
        roles.push(role as UserRole);
      }
    });
  }

  // Check groups (if using group-based permissions)
  if (claims?.groups && Array.isArray(claims.groups)) {
    // Map group IDs to roles (configure based on your Azure AD setup)
    // Example: if (claims.groups.includes('admin-group-id')) roles.push('admin');
  }

  // Default role if no roles found
  if (roles.length === 0) {
    roles.push('order-read', 'service-read', 'location-read');
  }

  return roles;
}

/**
 * Check if role string is valid
 */
function isValidRole(role: string): boolean {
  const validRoles: UserRole[] = [
    'admin',
    'order-read',
    'order-write',
    'service-read',
    'service-write',
    'location-read',
    'location-write',
    'invoice-read',
    'invoice-write',
    'dispute-read',
    'dispute-write',
    'report-read',
  ];
  return validRoles.includes(role as UserRole);
}

export default useAuth;
