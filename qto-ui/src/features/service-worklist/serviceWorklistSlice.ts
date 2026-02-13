/**
 * Service Worklist Redux Slice
 * State management for service worklist feature
 */

import { createSlice, createAsyncThunk } from '@reduxjs/toolkit';
import type { PayloadAction } from '@reduxjs/toolkit';
import type { RootState } from '@/store';
import { ServiceSearchCriteria, ServiceWorklistState } from './types';
import apiClient from '@/services/apiClient';
import { mockServices } from './mockData';

// Initial state
const initialState: ServiceWorklistState = {
  services: [],
  loading: false,
  error: null,
  searchCriteria: {
    page: 1,
    pageSize: 25,
    sortBy: 'orderDate',
    sortOrder: 'desc',
  },
  selectedServices: new Set(),
  totalItems: 0,
  currentPage: 1,
  pageSize: 25,
};

// Async thunks
export const fetchServices = createAsyncThunk(
  'serviceWorklist/fetchServices',
  async (criteria: ServiceSearchCriteria) => {
    try {
      const params = new URLSearchParams();
      Object.entries(criteria).forEach(([key, value]) => {
        if (value !== undefined && value !== null && value !== '') {
          params.append(key, value.toString());
        }
      });

      const response = await apiClient.get(`/services?${params.toString()}`);
      return response.data;
    } catch {
      // Use mock data in development when API fails
      console.log('API failed, using mock data');

      // Filter mock data based on criteria
      let filteredServices = [...mockServices];

      if (criteria.customerName) {
        filteredServices = filteredServices.filter(s =>
          s.customerName.toLowerCase().includes(criteria.customerName!.toLowerCase())
        );
      }
      if (criteria.serviceType) {
        filteredServices = filteredServices.filter(s => s.serviceType === criteria.serviceType);
      }
      if (criteria.status) {
        filteredServices = filteredServices.filter(s => s.status === criteria.status);
      }
      if (criteria.priority) {
        filteredServices = filteredServices.filter(s => s.priority === criteria.priority);
      }

      // Pagination
      const page = criteria.page || 1;
      const pageSize = criteria.pageSize || 25;
      const startIndex = (page - 1) * pageSize;
      const endIndex = startIndex + pageSize;
      const paginatedServices = filteredServices.slice(startIndex, endIndex);

      return {
        items: paginatedServices,
        totalItems: filteredServices.length,
      };
    }
  }
);

export const exportServices = createAsyncThunk(
  'serviceWorklist/exportServices',
  async (serviceIds: number[], { rejectWithValue }) => {
    try {
      const response = await apiClient.post('/services/export', { serviceIds }, {
        responseType: 'blob',
      });

      // Create download link
      const url = window.URL.createObjectURL(new Blob([response.data]));
      const link = document.createElement('a');
      link.href = url;
      link.setAttribute('download', `services_export_${new Date().toISOString()}.xlsx`);
      document.body.appendChild(link);
      link.click();
      link.remove();

      return true;
    } catch (error: unknown) {
      const errorMessage = error instanceof Error && 'response' in error
        ? ((error as { response?: { data?: { message?: string } } }).response?.data?.message || 'Failed to export services')
        : 'Failed to export services';
      return rejectWithValue(errorMessage);
    }
  }
);

// Slice
const serviceWorklistSlice = createSlice({
  name: 'serviceWorklist',
  initialState,
  reducers: {
    setSearchCriteria: (state, action: PayloadAction<Partial<ServiceSearchCriteria>>) => {
      state.searchCriteria = { ...state.searchCriteria, ...action.payload };
    },
    setSelectedServices: (state, action: PayloadAction<Set<number>>) => {
      state.selectedServices = action.payload;
    },
    clearSelectedServices: (state) => {
      state.selectedServices = new Set();
    },
    setPage: (state, action: PayloadAction<number>) => {
      state.currentPage = action.payload;
      state.searchCriteria.page = action.payload;
    },
    setPageSize: (state, action: PayloadAction<number>) => {
      state.pageSize = action.payload;
      state.searchCriteria.pageSize = action.payload;
      state.currentPage = 1;
      state.searchCriteria.page = 1;
    },
    clearError: (state) => {
      state.error = null;
    },
  },
  extraReducers: (builder) => {
    builder
      // Fetch services
      .addCase(fetchServices.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(fetchServices.fulfilled, (state, action) => {
        state.loading = false;
        state.services = action.payload.items || [];
        state.totalItems = action.payload.totalItems || 0;
      })
      .addCase(fetchServices.rejected, (state, action) => {
        state.loading = false;
        state.error = action.payload as string;
      })
      // Export services
      .addCase(exportServices.pending, (state) => {
        state.loading = true;
      })
      .addCase(exportServices.fulfilled, (state) => {
        state.loading = false;
      })
      .addCase(exportServices.rejected, (state, action) => {
        state.loading = false;
        state.error = action.payload as string;
      });
  },
});

// Actions
export const {
  setSearchCriteria,
  setSelectedServices,
  clearSelectedServices,
  setPage,
  setPageSize,
  clearError,
} = serviceWorklistSlice.actions;

// Selectors
export const selectServices = (state: RootState) => state.serviceWorklist.services;
export const selectLoading = (state: RootState) => state.serviceWorklist.loading;
export const selectError = (state: RootState) => state.serviceWorklist.error;
export const selectSearchCriteria = (state: RootState) => state.serviceWorklist.searchCriteria;
export const selectSelectedServices = (state: RootState) => state.serviceWorklist.selectedServices;
export const selectTotalItems = (state: RootState) => state.serviceWorklist.totalItems;
export const selectCurrentPage = (state: RootState) => state.serviceWorklist.currentPage;
export const selectPageSize = (state: RootState) => state.serviceWorklist.pageSize;

export default serviceWorklistSlice.reducer;
