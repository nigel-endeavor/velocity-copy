export interface Environment {
  production: boolean;
  appUrl: string;
  baseHref: string;
  azureClientId: string;
  azureAuthority: string;
  azureRedirectUri: string;
}

export const environment: Environment = {
  production: import.meta.env.PROD,
  appUrl: import.meta.env.VITE_API_URL || '/qto/api',
  baseHref: '/qto-ops/',
  azureClientId: import.meta.env.VITE_AZURE_CLIENT_ID || '77dd2c9c-5d15-46ad-98d9-039c62d8ef9a',
  azureAuthority: import.meta.env.VITE_AZURE_AUTHORITY || 'https://login.microsoftonline.com/119de762-6e78-4af0-a159-76b9a12af1a4',
  azureRedirectUri: import.meta.env.VITE_AZURE_REDIRECT_URI || 'http://localhost:4200/qto-ops/',
};
