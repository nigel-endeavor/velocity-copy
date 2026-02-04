/**
 * MSAL (Microsoft Authentication Library) Configuration
 *
 * Azure AD authentication configuration for OAuth2/OIDC flows.
 * Uses environment variables with fallback to development defaults.
 */

import { Configuration, LogLevel } from '@azure/msal-browser';

/**
 * MSAL Configuration
 *
 * @see https://github.com/AzureAD/microsoft-authentication-library-for-js/blob/dev/lib/msal-browser/docs/configuration.md
 */
export const msalConfig: Configuration = {
  auth: {
    clientId: import.meta.env.VITE_AZURE_CLIENT_ID || '77dd2c9c-5d15-46ad-98d9-039c62d8ef9a',
    authority:
      import.meta.env.VITE_AZURE_AUTHORITY ||
      'https://login.microsoftonline.com/119de762-6e78-4af0-a159-76b9a12af1a4',
    redirectUri: import.meta.env.VITE_AZURE_REDIRECT_URI || 'http://localhost:4200/qto-ops/',
    postLogoutRedirectUri:
      import.meta.env.VITE_AZURE_POST_LOGOUT_URI || 'http://localhost:4200/qto-ops/',
  },
  cache: {
    cacheLocation: 'sessionStorage', // "sessionStorage" or "localStorage"
  },
  system: {
    loggerOptions: {
      loggerCallback: (level: LogLevel, message: string, containsPii: boolean) => {
        if (containsPii) {
          return;
        }
        switch (level) {
          case LogLevel.Error:
            console.error(message);
            return;
          case LogLevel.Warning:
            console.warn(message);
            return;
          case LogLevel.Info:
            console.info(message);
            return;
          case LogLevel.Verbose:
            console.debug(message);
            return;
          default:
            return;
        }
      },
      logLevel: LogLevel.Warning,
    },
  },
};

/**
 * Scopes for Login Request
 */
export const loginRequest = {
  scopes: ['User.Read', 'openid', 'profile', 'email'],
};

/**
 * Scopes for Token Acquisition
 */
export const tokenRequest = {
  scopes: ['User.Read'],
};

/**
 * Protected Resources (APIs requiring authentication)
 */
export const protectedResources = {
  qtoApi: {
    endpoint: import.meta.env.VITE_API_URL || 'http://localhost:8080/qto/api',
    scopes: ['User.Read'],
  },
};

/**
 * Helper function to get access token
 */
export const getAccessToken = async (instance: any): Promise<string | null> => {
  const account = instance.getActiveAccount();
  if (!account) {
    return null;
  }

  try {
    const response = await instance.acquireTokenSilent({
      ...tokenRequest,
      account,
    });
    return response.accessToken;
  } catch (error) {
    console.error('Silent token acquisition failed:', error);
    try {
      const response = await instance.acquireTokenPopup(tokenRequest);
      return response.accessToken;
    } catch (popupError) {
      console.error('Token acquisition failed:', popupError);
      return null;
    }
  }
};
