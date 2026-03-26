/**
 * Service Worklist Redux Slice
 * UI state management for service worklist feature.
 * Data fetching is handled by RTK Query (serviceViewsApi).
 */

import { createSlice } from '@reduxjs/toolkit';
import type { PayloadAction } from '@reduxjs/toolkit';
import type { RootState } from '@/store';
import { ServiceWorklistState } from './types';

// Initial state
const initialState: ServiceWorklistState = {
  loading: false,
  error: null,
  searchCriteria: {
    page: 1,
    pageSize: 25,
    sortBy: 'status',
    sortOrder: 'desc',
  },
  selectedServices: new Set(),
  currentPage: 1,
  pageSize: 25,
};

// Slice
const serviceWorklistSlice = createSlice({
  name: 'serviceWorklist',
  initialState,
  reducers: {
    setSelectedServices: (state, action: PayloadAction<Set<number>>) => {
      state.selectedServices = action.payload;
    },
    clearSelectedServices: (state) => {
      state.selectedServices = new Set();
    },
    setPage: (state, action: PayloadAction<number>) => {
      state.currentPage = action.payload;
    },
    setPageSize: (state, action: PayloadAction<number>) => {
      state.pageSize = action.payload;
      state.currentPage = 1;
    },
    clearError: (state) => {
      state.error = null;
    },
  },
});

// Actions
export const {
  setSelectedServices,
  clearSelectedServices,
  setPage,
  setPageSize,
  clearError,
} = serviceWorklistSlice.actions;

// Selectors
export const selectSelectedServices = (state: RootState) => state.serviceWorklist.selectedServices;
export const selectCurrentPage = (state: RootState) => state.serviceWorklist.currentPage;
export const selectPageSize = (state: RootState) => state.serviceWorklist.pageSize;

export default serviceWorklistSlice.reducer;
