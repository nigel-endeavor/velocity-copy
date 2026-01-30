import type { Configuration, RedirectRequest } from '@azure/msal-browser';
import { environment } from './environment';

export const msalConfig: Configuration = {
  auth: {
    clientId: environment.azureClientId,
    authority: environment.azureAuthority,
    redirectUri: environment.azureRedirectUri,
    postLogoutRedirectUri: window.location.origin + environment.baseHref,
  },
  cache: {
    cacheLocation: 'localStorage',
    storeAuthStateInCookie: false,
  },
};

export const loginRequest: RedirectRequest = {
  scopes: [`api://${environment.azureClientId}/qto`],
};

export const tokenRequest = {
  scopes: [`api://${environment.azureClientId}/qto`],
  forceRefresh: false,
};
