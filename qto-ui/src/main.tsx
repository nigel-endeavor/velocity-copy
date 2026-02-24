/**
 * Application Entry Point
 *
 * Sets up the React application with:
 * - Redux store and persist gate
 * - Auth provider (simple login)
 * - React Router
 * - Tailwind CSS
 */

import { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';
import { Provider } from 'react-redux';
import { PersistGate } from 'redux-persist/integration/react';
import { AuthProvider } from './contexts/AuthContext';

import App from './App';
import { store, persistor } from './store';

import './index.css';

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <Provider store={store}>
      <PersistGate loading={null} persistor={persistor}>
        <AuthProvider>
          <App />
        </AuthProvider>
      </PersistGate>
    </Provider>
  </StrictMode>
);
