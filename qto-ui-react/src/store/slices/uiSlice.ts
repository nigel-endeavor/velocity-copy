/**
 * UI Slice
 *
 * Manages global UI state including:
 * - Sidebar open/close
 * - Loading indicators
 * - Notifications/Snackbars
 * - Theme preferences
 */

import { createSlice, PayloadAction } from '@reduxjs/toolkit';
import type { RootState } from '../index';

/**
 * Notification Interface
 */
export interface Notification {
  id: string;
  message: string;
  type: 'success' | 'error' | 'warning' | 'info';
  duration?: number;
}

/**
 * UI State Interface
 */
export interface UIState {
  sidebarOpen: boolean;
  loading: boolean;
  notifications: Notification[];
  theme: 'light' | 'dark';
}

/**
 * Initial State
 */
const initialState: UIState = {
  sidebarOpen: true,
  loading: false,
  notifications: [],
  theme: 'light',
};

/**
 * UI Slice
 */
export const uiSlice = createSlice({
  name: 'ui',
  initialState,
  reducers: {
    toggleSidebar: (state) => {
      state.sidebarOpen = !state.sidebarOpen;
    },
    setSidebarOpen: (state, action: PayloadAction<boolean>) => {
      state.sidebarOpen = action.payload;
    },
    setLoading: (state, action: PayloadAction<boolean>) => {
      state.loading = action.payload;
    },
    addNotification: (state, action: PayloadAction<Omit<Notification, 'id'>>) => {
      const notification: Notification = {
        id: Date.now().toString(),
        ...action.payload,
      };
      state.notifications.push(notification);
    },
    removeNotification: (state, action: PayloadAction<string>) => {
      state.notifications = state.notifications.filter((n) => n.id !== action.payload);
    },
    clearNotifications: (state) => {
      state.notifications = [];
    },
    setTheme: (state, action: PayloadAction<'light' | 'dark'>) => {
      state.theme = action.payload;
    },
    toggleTheme: (state) => {
      state.theme = state.theme === 'light' ? 'dark' : 'light';
    },
  },
});

/**
 * Actions
 */
export const {
  toggleSidebar,
  setSidebarOpen,
  setLoading,
  addNotification,
  removeNotification,
  clearNotifications,
  setTheme,
  toggleTheme,
} = uiSlice.actions;

/**
 * Selectors
 */
export const selectSidebarOpen = (state: RootState) => state.ui.sidebarOpen;
export const selectLoading = (state: RootState) => state.ui.loading;
export const selectNotifications = (state: RootState) => state.ui.notifications;
export const selectTheme = (state: RootState) => state.ui.theme;

/**
 * Reducer
 */
export default uiSlice.reducer;
