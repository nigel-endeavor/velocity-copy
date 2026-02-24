/**
 * Auth Context
 *
 * Simple authentication for Endeavor Velocity.
 * Replaces MSAL with local username/password login.
 */

import {
  createContext,
  useContext,
  useState,
  useCallback,
  useEffect,
  ReactNode,
} from 'react';

const AUTH_STORAGE_KEY = 'endeavor-velocity-auth';

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

interface AuthState {
  user: UserInfo | null;
  isAuthenticated: boolean;
}

interface AuthContextValue extends AuthState {
  login: (username: string, password: string) => Promise<boolean>;
  logout: () => void;
}

const AuthContext = createContext<AuthContextValue | null>(null);

function loadStoredAuth(): UserInfo | null {
  try {
    const stored = localStorage.getItem(AUTH_STORAGE_KEY);
    if (stored) {
      const parsed = JSON.parse(stored) as UserInfo;
      if (parsed?.username) return parsed;
    }
  } catch {
    // ignore
  }
  return null;
}

function saveAuth(user: UserInfo | null) {
  if (user) {
    localStorage.setItem(AUTH_STORAGE_KEY, JSON.stringify(user));
  } else {
    localStorage.removeItem(AUTH_STORAGE_KEY);
  }
}

export function AuthProvider({ children }: { children: ReactNode }) {
  const [user, setUser] = useState<UserInfo | null>(loadStoredAuth);

  const login = useCallback(async (username: string, password: string): Promise<boolean> => {
    const trimmedUser = username?.trim();
    const trimmedPass = password?.trim();

    if (!trimmedUser || !trimmedPass) {
      return false;
    }

    // Simple auth: accept any non-empty username/password
    const userInfo: UserInfo = {
      username: trimmedUser,
      name: trimmedUser,
      email: `${trimmedUser}@endeavor.local`,
      roles: [
        'order-read',
        'service-read',
        'location-read',
        'order-write',
        'service-write',
        'location-write',
      ] as UserRole[],
    };

    setUser(userInfo);
    saveAuth(userInfo);
    return true;
  }, []);

  const logout = useCallback(() => {
    setUser(null);
    saveAuth(null);
  }, []);

  useEffect(() => {
    const stored = loadStoredAuth();
    if (stored && !user) {
      setUser(stored);
    }
  }, []);

  const value: AuthContextValue = {
    user,
    isAuthenticated: !!user,
    login,
    logout,
  };

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}

export function useAuthContext() {
  const ctx = useContext(AuthContext);
  if (!ctx) {
    throw new Error('useAuthContext must be used within AuthProvider');
  }
  return ctx;
}
