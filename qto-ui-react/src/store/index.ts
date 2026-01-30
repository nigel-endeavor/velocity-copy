import { configureStore } from '@reduxjs/toolkit';
import { TypedUseSelectorHook, useDispatch, useSelector } from 'react-redux';
import serviceWorklistReducer from '../features/service-worklist/serviceWorklistSlice';

export const store = configureStore({
  reducer: {
    serviceWorklist: serviceWorklistReducer,
  },
  middleware: (getDefaultMiddleware) =>
    getDefaultMiddleware({
      serializableCheck: {
        // Ignore these action types
        ignoredActions: ['msal/loginSuccess', 'msal/loginFailure'],
        // Ignore these paths in the state (Set objects, MSAL instances)
        ignoredPaths: ['msal', 'serviceWorklist.selectedServices'],
      },
    }),
});

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;

// Export typed hooks
export const useAppDispatch = () => useDispatch<AppDispatch>();
export const useAppSelector: TypedUseSelectorHook<RootState> = useSelector;
