/**
 * Redux Store Configuration
 *
 * Configures Redux Toolkit store with:
 * - Redux Persist for state persistence
 * - RTK Query API middleware
 * - TypeScript typed hooks
 * - DevTools integration
 */

import { configureStore, combineReducers } from '@reduxjs/toolkit';
import {
  persistStore,
  persistReducer,
  FLUSH,
  REHYDRATE,
  PAUSE,
  PERSIST,
  PURGE,
  REGISTER,
} from 'redux-persist';
import storage from 'redux-persist/lib/storage'; // defaults to localStorage

// Import slices
import demoModeReducer from './slices/demoModeSlice';
import uiReducer from './slices/uiSlice';
import serviceWorklistReducer from '../features/service-worklist/serviceWorklistSlice';
import orderDetailsReducer from './slices/orderDetailsSlice';

// Import API slices
import { baseApi } from '@/services/api/baseApi';

/**
 * Root Reducer
 * Combines all feature slices
 */
const rootReducer = combineReducers({
  // Core slices
  demoMode: demoModeReducer,
  ui: uiReducer,

  // Feature slices
  serviceWorklist: serviceWorklistReducer,
  orderDetails: orderDetailsReducer,

  // API
  [baseApi.reducerPath]: baseApi.reducer,
});

/**
 * Persist Configuration
 */
const persistConfig = {
  key: 'qto-ui-react',
  version: 1,
  storage,
  whitelist: ['demoMode', 'ui'], // Only persist these slices
  blacklist: ['serviceWorklist', 'orderDetails'], // Don't persist API cache or dynamic data
};

/**
 * Persisted Reducer
 */
const persistedReducer = persistReducer(persistConfig, rootReducer);

/**
 * Store Configuration
 */
export const store = configureStore({
  reducer: persistedReducer,
  middleware: (getDefaultMiddleware) =>
    getDefaultMiddleware({
      serializableCheck: {
        // Ignore redux-persist actions
        ignoredActions: [
          FLUSH,
          REHYDRATE,
          PAUSE,
          PERSIST,
          PURGE,
          REGISTER,
          'msal/loginSuccess',
          'msal/loginFailure',
        ],
        // Ignore these paths in the state (Set objects, MSAL instances)
        ignoredPaths: ['msal', 'serviceWorklist.selectedServices'],
      },
    }).concat(baseApi.middleware), // RTK Query middleware

  devTools: process.env.NODE_ENV !== 'production',
});

/**
 * Persistor for redux-persist
 */
export const persistor = persistStore(store);

/**
 * TypeScript Types
 */
export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;

/**
 * Export typed hooks (re-export from hooks.ts for convenience)
 */
export { useAppDispatch, useAppSelector } from './hooks';

/**
 * Export for testing
 */
export default store;
