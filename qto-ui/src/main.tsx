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

import App from './App';
import { store, persistor } from './store';

import './index.css';

/**
 * Render Application
 */
createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <Provider store={store}>
      <PersistGate loading={null} persistor={persistor}>
        <App />
      </PersistGate>
    </Provider>
  </StrictMode>
);
