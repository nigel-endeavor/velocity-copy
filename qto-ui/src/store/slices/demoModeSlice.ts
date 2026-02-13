/**
 * Demo Mode Slice
 *
 * Manages demo mode state including:
 * - Demo mode toggle
 * - Network slowdown simulation
 * - Mock data preferences
 */

import { createSlice, PayloadAction } from '@reduxjs/toolkit';
import type { RootState } from '../index';

/**
 * Demo Mode State Interface
 */
export interface DemoModeState {
  enabled: boolean;
  networkDelay: number; // milliseconds
  useMockData: boolean;
  mockDataDelay: number; // milliseconds
}

/**
 * Initial State
 */
const initialState: DemoModeState = {
  enabled: false,
  networkDelay: 0,
  useMockData: false,
  mockDataDelay: 500,
};

/**
 * Demo Mode Slice
 */
export const demoModeSlice = createSlice({
  name: 'demoMode',
  initialState,
  reducers: {
    toggleDemoMode: (state) => {
      state.enabled = !state.enabled;
      if (!state.enabled) {
        state.networkDelay = 0;
        state.useMockData = false;
      }
    },
    setDemoMode: (state, action: PayloadAction<boolean>) => {
      state.enabled = action.payload;
      if (!action.payload) {
        state.networkDelay = 0;
        state.useMockData = false;
      }
    },
    setNetworkDelay: (state, action: PayloadAction<number>) => {
      state.networkDelay = action.payload;
    },
    setUseMockData: (state, action: PayloadAction<boolean>) => {
      state.useMockData = action.payload;
    },
    setMockDataDelay: (state, action: PayloadAction<number>) => {
      state.mockDataDelay = action.payload;
    },
    resetDemoMode: () => initialState,
  },
});

/**
 * Actions
 */
export const {
  toggleDemoMode,
  setDemoMode,
  setNetworkDelay,
  setUseMockData,
  setMockDataDelay,
  resetDemoMode,
} = demoModeSlice.actions;

/**
 * Selectors
 */
export const selectDemoMode = (state: RootState) => state.demoMode;
export const selectIsDemoEnabled = (state: RootState) => state.demoMode.enabled;
export const selectNetworkDelay = (state: RootState) => state.demoMode.networkDelay;
export const selectUseMockData = (state: RootState) => state.demoMode.useMockData;
export const selectMockDataDelay = (state: RootState) => state.demoMode.mockDataDelay;

/**
 * Reducer
 */
export default demoModeSlice.reducer;
