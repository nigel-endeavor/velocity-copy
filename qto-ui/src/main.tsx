/**
 * Application Entry Point
 *
 * Sets up the React application with:
 * - Redux store and persist gate
 * - MSAL authentication provider
 * - React Router
 * - Tailwind CSS
 */

import { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';
import { Provider } from 'react-redux';
import { PersistGate } from 'redux-persist/integration/react';
import { PublicClientApplication, EventType } from '@azure/msal-browser';
import { MsalProvider } from '@azure/msal-react';

import App from './App';
import { store, persistor } from './store';
import { msalConfig } from './config/msal.config';

import './index.css';

/**
 * Initialize MSAL instance
 */
const msalInstance = new PublicClientApplication(msalConfig);

// Account selection logic is app dependent. Adjust as needed for different use cases.
const accounts = msalInstance.getAllAccounts();
if (accounts.length > 0) {
  msalInstance.setActiveAccount(accounts[0]);
}

msalInstance.addEventCallback((event) => {
  if (event.eventType === EventType.LOGIN_SUCCESS && event.payload) {
    const payload = event.payload as any;
    const account = payload.account;
    msalInstance.setActiveAccount(account);
  }
});

/**
 * Render Application
 */
createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <Provider store={store}>
      <PersistGate loading={null} persistor={persistor}>
        <MsalProvider instance={msalInstance}>
          <App />
        </MsalProvider>
      </PersistGate>
    </Provider>
  </StrictMode>
);
