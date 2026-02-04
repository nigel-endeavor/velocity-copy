/**
 * Order Details Slice
 *
 * Manages complex order state with nested locations and services
 * Migrated from Angular NgRx reducer (540 lines)
 *
 * Features:
 * - 30+ edit flags for granular UI control
 * - Nested state management (Order → Location[] → Service[])
 * - Change detection with dirty state
 * - Validation state management
 * - RTK Query integration
 */

import { createSlice, PayloadAction } from '@reduxjs/toolkit';
import { Order, Location, Service, Contact } from '@/shared/types/models';
import { ordersApi } from '@/services/api/ordersApi';

/**
 * Edit Flags Interface
 * Controls which sections of the order form are in edit mode
 */
export interface OrderEditFlags {
  // Order-level edit flags
  editingOrderInfo: boolean;
  editingTechnical: boolean;
  editingBilling: boolean;
  editingFinancial: boolean;
  editingContacts: boolean;
  editingNotes: boolean;
  editingCustomFields: boolean;
  editingScheduling: boolean;
  editingProvisioning: boolean;
  editingAccounting: boolean;

  // Nested entity edit flags (keyed by entity ID)
  editingLocation: Record<number, boolean>;
  editingService: Record<number, boolean>;
  editingContact: Record<number, boolean>;

  // Section-specific flags
  editingSalesContact: boolean;
  editingTechContact: boolean;
  editingBillingContact: boolean;
  editingAuthContact: boolean;
  editingLconContact: boolean;

  // Advanced edit flags
  editingCosts: boolean;
  editingRevenue: boolean;
  editingDates: boolean;
  editingStatus: boolean;
  editingAssignments: boolean;
  editingIntegration: boolean;
  editingCompliance: boolean;
}

/**
 * Validation Errors Interface
 * Tracks validation errors by field path
 */
export interface ValidationErrors {
  [fieldPath: string]: string[];
}

/**
 * Order Details State Interface
 */
export interface OrderDetailsState {
  // Core Data
  order: Order | null;
  originalOrder: Order | null; // For change detection and reset

  // Edit Flags (30+)
  editFlags: OrderEditFlags;

  // UI State
  loading: boolean;
  saving: boolean;
  error: string | null;

  // Selection State
  selectedLocationId: number | null;
  selectedServiceId: number | null;
  expandedLocations: number[]; // IDs of expanded location accordions
  expandedServices: number[]; // IDs of expanded service accordions

  // Validation State
  validationErrors: ValidationErrors;
  isDirty: boolean; // Has the order been modified?
  isValid: boolean; // Are all validations passing?

  // History State
  changeHistory: OrderChange[];
  canUndo: boolean;
  canRedo: boolean;

  // WebSocket State
  liveUpdates: boolean;
  lastUpdateTimestamp: number | null;

  // Tab State
  activeTab: 'general' | 'technical' | 'billing' | 'financial' | 'history' | 'notes';
}

/**
 * Order Change for History
 */
export interface OrderChange {
  timestamp: number;
  field: string;
  oldValue: any;
  newValue: any;
  userId: string;
}

/**
 * Initial State
 */
const initialEditFlags: OrderEditFlags = {
  editingOrderInfo: false,
  editingTechnical: false,
  editingBilling: false,
  editingFinancial: false,
  editingContacts: false,
  editingNotes: false,
  editingCustomFields: false,
  editingScheduling: false,
  editingProvisioning: false,
  editingAccounting: false,
  editingLocation: {},
  editingService: {},
  editingContact: {},
  editingSalesContact: false,
  editingTechContact: false,
  editingBillingContact: false,
  editingAuthContact: false,
  editingLconContact: false,
  editingCosts: false,
  editingRevenue: false,
  editingDates: false,
  editingStatus: false,
  editingAssignments: false,
  editingIntegration: false,
  editingCompliance: false,
};

const initialState: OrderDetailsState = {
  order: null,
  originalOrder: null,
  editFlags: initialEditFlags,
  loading: false,
  saving: false,
  error: null,
  selectedLocationId: null,
  selectedServiceId: null,
  expandedLocations: [],
  expandedServices: [],
  validationErrors: {},
  isDirty: false,
  isValid: true,
  changeHistory: [],
  canUndo: false,
  canRedo: false,
  liveUpdates: true,
  lastUpdateTimestamp: null,
  activeTab: 'general',
};

/**
 * Order Details Slice
 */
const orderDetailsSlice = createSlice({
  name: 'orderDetails',
  initialState,
  reducers: {
    // ==========================================
    // Order Actions
    // ==========================================

    /**
     * Set the current order
     */
    setOrder: (state, action: PayloadAction<Order>) => {
      state.order = action.payload;
      state.originalOrder = structuredClone(action.payload);
      state.isDirty = false;
      state.validationErrors = {};
      state.error = null;
    },

    /**
     * Update order fields
     */
    updateOrder: (state, action: PayloadAction<Partial<Order>>) => {
      if (state.order) {
        state.order = { ...state.order, ...action.payload };
        state.isDirty = true;
      }
    },

    /**
     * Reset order to original state
     */
    resetOrder: (state) => {
      if (state.originalOrder) {
        state.order = structuredClone(state.originalOrder);
        state.isDirty = false;
        state.validationErrors = {};
        state.editFlags = initialEditFlags;
      }
    },

    /**
     * Clear current order
     */
    clearOrder: (state) => {
      state.order = null;
      state.originalOrder = null;
      state.isDirty = false;
      state.validationErrors = {};
      state.editFlags = initialEditFlags;
      state.selectedLocationId = null;
      state.selectedServiceId = null;
      state.expandedLocations = [];
      state.expandedServices = [];
      state.error = null;
    },

    // ==========================================
    // Edit Flag Actions
    // ==========================================

    /**
     * Toggle edit mode for a specific section
     */
    toggleEdit: (state, action: PayloadAction<{ key: keyof OrderEditFlags }>) => {
      const { key } = action.payload;
      if (typeof state.editFlags[key] === 'boolean') {
        (state.editFlags[key] as boolean) = !(state.editFlags[key] as boolean);
      }
    },

    /**
     * Set edit flag value
     */
    setEditFlag: (
      state,
      action: PayloadAction<{ key: keyof OrderEditFlags; value: boolean }>
    ) => {
      const { key, value } = action.payload;
      if (typeof state.editFlags[key] === 'boolean') {
        (state.editFlags[key] as boolean) = value;
      }
    },

    /**
     * Toggle edit mode for a location
     */
    toggleLocationEdit: (state, action: PayloadAction<{ locationId: number }>) => {
      const { locationId } = action.payload;
      state.editFlags.editingLocation[locationId] = !state.editFlags.editingLocation[locationId];
    },

    /**
     * Toggle edit mode for a service
     */
    toggleServiceEdit: (state, action: PayloadAction<{ serviceId: number }>) => {
      const { serviceId } = action.payload;
      state.editFlags.editingService[serviceId] = !state.editFlags.editingService[serviceId];
    },

    /**
     * Reset all edit flags
     */
    resetAllEditFlags: (state) => {
      state.editFlags = initialEditFlags;
    },

    /**
     * Enable edit mode for all sections
     */
    enableAllEdits: (state) => {
      state.editFlags = {
        ...state.editFlags,
        editingOrderInfo: true,
        editingTechnical: true,
        editingBilling: true,
        editingFinancial: true,
        editingContacts: true,
        editingNotes: true,
        editingCustomFields: true,
        editingScheduling: true,
        editingProvisioning: true,
        editingAccounting: true,
      };
    },

    // ==========================================
    // Location Actions
    // ==========================================

    /**
     * Add a new location
     */
    addLocation: (state, action: PayloadAction<Location>) => {
      if (state.order) {
        if (!state.order.locations) {
          state.order.locations = [];
        }
        state.order.locations.push(action.payload);
        state.isDirty = true;
      }
    },

    /**
     * Update a location
     */
    updateLocation: (
      state,
      action: PayloadAction<{ locationId: number; updates: Partial<Location> }>
    ) => {
      if (state.order?.locations) {
        const index = state.order.locations.findIndex((loc) => loc.id === action.payload.locationId);
        if (index !== -1) {
          state.order.locations[index] = {
            ...state.order.locations[index],
            ...action.payload.updates,
          };
          state.isDirty = true;
        }
      }
    },

    /**
     * Remove a location
     */
    removeLocation: (state, action: PayloadAction<{ locationId: number }>) => {
      if (state.order?.locations) {
        state.order.locations = state.order.locations.filter(
          (loc) => loc.id !== action.payload.locationId
        );
        // Clean up edit flags
        delete state.editFlags.editingLocation[action.payload.locationId];
        // Remove from expanded list
        state.expandedLocations = state.expandedLocations.filter(
          (id) => id !== action.payload.locationId
        );
        // Clear selection if removed
        if (state.selectedLocationId === action.payload.locationId) {
          state.selectedLocationId = null;
        }
        state.isDirty = true;
      }
    },

    /**
     * Reorder locations
     */
    reorderLocations: (state, action: PayloadAction<{ fromIndex: number; toIndex: number }>) => {
      if (state.order?.locations) {
        const { fromIndex, toIndex } = action.payload;
        const [movedLocation] = state.order.locations.splice(fromIndex, 1);
        state.order.locations.splice(toIndex, 0, movedLocation);
        state.isDirty = true;
      }
    },

    // ==========================================
    // Service Actions
    // ==========================================

    /**
     * Add a new service to a location
     */
    addService: (
      state,
      action: PayloadAction<{ locationId: number; service: Service }>
    ) => {
      if (state.order?.locations) {
        const location = state.order.locations.find((loc) => loc.id === action.payload.locationId);
        if (location) {
          if (!location.services) {
            location.services = [];
          }
          location.services.push(action.payload.service);
          state.isDirty = true;
        }
      }
    },

    /**
     * Update a service
     */
    updateService: (
      state,
      action: PayloadAction<{
        locationId: number;
        serviceId: number;
        updates: Partial<Service>;
      }>
    ) => {
      if (state.order?.locations) {
        const location = state.order.locations.find(
          (loc) => loc.id === action.payload.locationId
        );
        if (location?.services) {
          const index = location.services.findIndex(
            (svc) => svc.id === action.payload.serviceId
          );
          if (index !== -1) {
            location.services[index] = {
              ...location.services[index],
              ...action.payload.updates,
            };
            state.isDirty = true;
          }
        }
      }
    },

    /**
     * Remove a service
     */
    removeService: (
      state,
      action: PayloadAction<{ locationId: number; serviceId: number }>
    ) => {
      if (state.order?.locations) {
        const location = state.order.locations.find(
          (loc) => loc.id === action.payload.locationId
        );
        if (location?.services) {
          location.services = location.services.filter(
            (svc) => svc.id !== action.payload.serviceId
          );
          // Clean up edit flags
          delete state.editFlags.editingService[action.payload.serviceId];
          // Remove from expanded list
          state.expandedServices = state.expandedServices.filter(
            (id) => id !== action.payload.serviceId
          );
          // Clear selection if removed
          if (state.selectedServiceId === action.payload.serviceId) {
            state.selectedServiceId = null;
          }
          state.isDirty = true;
        }
      }
    },

    /**
     * Bulk update services
     */
    bulkUpdateServices: (
      state,
      action: PayloadAction<{
        locationId: number;
        serviceIds: number[];
        updates: Partial<Service>;
      }>
    ) => {
      if (state.order?.locations) {
        const location = state.order.locations.find(
          (loc) => loc.id === action.payload.locationId
        );
        if (location?.services) {
          location.services = location.services.map((svc) => {
            if (action.payload.serviceIds.includes(svc.id!)) {
              return { ...svc, ...action.payload.updates };
            }
            return svc;
          });
          state.isDirty = true;
        }
      }
    },

    // ==========================================
    // Contact Actions
    // ==========================================

    /**
     * Update a contact
     */
    updateContact: (
      state,
      action: PayloadAction<{ contactId: number; updates: Partial<Contact> }>
    ) => {
      if (state.order?.contacts) {
        const index = state.order.contacts.findIndex(
          (contact) => contact.id === action.payload.contactId
        );
        if (index !== -1) {
          state.order.contacts[index] = {
            ...state.order.contacts[index],
            ...action.payload.updates,
          };
          state.isDirty = true;
        }
      }
    },

    /**
     * Add a contact
     */
    addContact: (state, action: PayloadAction<Contact>) => {
      if (state.order) {
        if (!state.order.contacts) {
          state.order.contacts = [];
        }
        // OrderContact extends Contact, so we can spread it and add orderId
        state.order.contacts.push({
          ...action.payload,
          orderId: state.order.id,
        });
        state.isDirty = true;
      }
    },

    /**
     * Remove a contact
     */
    removeContact: (state, action: PayloadAction<{ contactId: number }>) => {
      if (state.order?.contacts) {
        state.order.contacts = state.order.contacts.filter(
          (contact) => contact.id !== action.payload.contactId
        );
        state.isDirty = true;
      }
    },

    // ==========================================
    // Validation Actions
    // ==========================================

    /**
     * Set validation errors
     */
    setValidationErrors: (state, action: PayloadAction<ValidationErrors>) => {
      state.validationErrors = action.payload;
      state.isValid = Object.keys(action.payload).length === 0;
    },

    /**
     * Add validation error
     */
    addValidationError: (
      state,
      action: PayloadAction<{ field: string; errors: string[] }>
    ) => {
      state.validationErrors[action.payload.field] = action.payload.errors;
      state.isValid = false;
    },

    /**
     * Clear validation errors
     */
    clearValidationErrors: (state) => {
      state.validationErrors = {};
      state.isValid = true;
    },

    /**
     * Clear validation error for a field
     */
    clearFieldValidationError: (state, action: PayloadAction<{ field: string }>) => {
      delete state.validationErrors[action.payload.field];
      state.isValid = Object.keys(state.validationErrors).length === 0;
    },

    // ==========================================
    // Selection Actions
    // ==========================================

    /**
     * Select a location
     */
    selectLocation: (state, action: PayloadAction<{ locationId: number | null }>) => {
      state.selectedLocationId = action.payload.locationId;
      // Expand selected location
      if (
        action.payload.locationId &&
        !state.expandedLocations.includes(action.payload.locationId)
      ) {
        state.expandedLocations.push(action.payload.locationId);
      }
    },

    /**
     * Select a service
     */
    selectService: (state, action: PayloadAction<{ serviceId: number | null }>) => {
      state.selectedServiceId = action.payload.serviceId;
      // Expand selected service
      if (
        action.payload.serviceId &&
        !state.expandedServices.includes(action.payload.serviceId)
      ) {
        state.expandedServices.push(action.payload.serviceId);
      }
    },

    /**
     * Toggle location expansion
     */
    toggleLocationExpansion: (state, action: PayloadAction<{ locationId: number }>) => {
      const { locationId } = action.payload;
      const index = state.expandedLocations.indexOf(locationId);
      if (index !== -1) {
        state.expandedLocations.splice(index, 1);
      } else {
        state.expandedLocations.push(locationId);
      }
    },

    /**
     * Toggle service expansion
     */
    toggleServiceExpansion: (state, action: PayloadAction<{ serviceId: number }>) => {
      const { serviceId } = action.payload;
      const index = state.expandedServices.indexOf(serviceId);
      if (index !== -1) {
        state.expandedServices.splice(index, 1);
      } else {
        state.expandedServices.push(serviceId);
      }
    },

    /**
     * Expand all locations
     */
    expandAllLocations: (state) => {
      if (state.order?.locations) {
        state.expandedLocations = state.order.locations
          .filter((loc) => loc.id !== undefined)
          .map((loc) => loc.id!);
      }
    },

    /**
     * Collapse all locations
     */
    collapseAllLocations: (state) => {
      state.expandedLocations = [];
    },

    // ==========================================
    // Tab Actions
    // ==========================================

    /**
     * Set active tab
     */
    setActiveTab: (
      state,
      action: PayloadAction<'general' | 'technical' | 'billing' | 'financial' | 'history' | 'notes'>
    ) => {
      state.activeTab = action.payload;
    },

    // ==========================================
    // UI State Actions
    // ==========================================

    /**
     * Set loading state
     */
    setLoading: (state, action: PayloadAction<boolean>) => {
      state.loading = action.payload;
    },

    /**
     * Set saving state
     */
    setSaving: (state, action: PayloadAction<boolean>) => {
      state.saving = action.payload;
    },

    /**
     * Set error
     */
    setError: (state, action: PayloadAction<string | null>) => {
      state.error = action.payload;
    },

    /**
     * Clear error
     */
    clearError: (state) => {
      state.error = null;
    },

    /**
     * Toggle live updates
     */
    toggleLiveUpdates: (state) => {
      state.liveUpdates = !state.liveUpdates;
    },

    /**
     * Set last update timestamp
     */
    setLastUpdateTimestamp: (state, action: PayloadAction<number>) => {
      state.lastUpdateTimestamp = action.payload;
    },

    // ==========================================
    // Change History Actions
    // ==========================================

    /**
     * Add to change history
     */
    addToHistory: (state, action: PayloadAction<OrderChange>) => {
      state.changeHistory.push(action.payload);
      state.canUndo = state.changeHistory.length > 0;
    },

    /**
     * Clear change history
     */
    clearHistory: (state) => {
      state.changeHistory = [];
      state.canUndo = false;
      state.canRedo = false;
    },
  },

  extraReducers: (builder) => {
    // ==========================================
    // RTK Query Integration
    // ==========================================

    // Handle successful order fetch
    builder.addMatcher(
      ordersApi.endpoints.getOrder.matchFulfilled,
      (state, action) => {
        state.order = action.payload;
        state.originalOrder = structuredClone(action.payload);
        state.loading = false;
        state.error = null;
        state.isDirty = false;
        state.validationErrors = {};
        state.editFlags = initialEditFlags;
      }
    );

    // Handle order fetch pending
    builder.addMatcher(
      ordersApi.endpoints.getOrder.matchPending,
      (state) => {
        state.loading = true;
        state.error = null;
      }
    );

    // Handle order fetch error
    builder.addMatcher(
      ordersApi.endpoints.getOrder.matchRejected,
      (state, action) => {
        state.loading = false;
        state.error = action.error.message || 'Failed to load order';
      }
    );

    // Handle successful order save
    builder.addMatcher(
      ordersApi.endpoints.saveOrder.matchFulfilled,
      (state, action) => {
        state.order = action.payload;
        state.originalOrder = structuredClone(action.payload);
        state.saving = false;
        state.isDirty = false;
        state.error = null;
        state.editFlags = initialEditFlags;
      }
    );

    // Handle order save pending
    builder.addMatcher(
      ordersApi.endpoints.saveOrder.matchPending,
      (state) => {
        state.saving = true;
        state.error = null;
      }
    );

    // Handle order save error
    builder.addMatcher(
      ordersApi.endpoints.saveOrder.matchRejected,
      (state, action) => {
        state.saving = false;
        state.error = action.error.message || 'Failed to save order';
      }
    );

    // Handle successful order update
    builder.addMatcher(
      ordersApi.endpoints.updateOrder.matchFulfilled,
      (state, action) => {
        state.order = action.payload;
        state.originalOrder = structuredClone(action.payload);
        state.saving = false;
        state.isDirty = false;
        state.error = null;
      }
    );
  },
});

// ==========================================
// Exports
// ==========================================

export const {
  // Order actions
  setOrder,
  updateOrder,
  resetOrder,
  clearOrder,

  // Edit flag actions
  toggleEdit,
  setEditFlag,
  toggleLocationEdit,
  toggleServiceEdit,
  resetAllEditFlags,
  enableAllEdits,

  // Location actions
  addLocation,
  updateLocation,
  removeLocation,
  reorderLocations,

  // Service actions
  addService,
  updateService,
  removeService,
  bulkUpdateServices,

  // Contact actions
  updateContact,
  addContact,
  removeContact,

  // Validation actions
  setValidationErrors,
  addValidationError,
  clearValidationErrors,
  clearFieldValidationError,

  // Selection actions
  selectLocation,
  selectService,
  toggleLocationExpansion,
  toggleServiceExpansion,
  expandAllLocations,
  collapseAllLocations,

  // Tab actions
  setActiveTab,

  // UI actions
  setLoading,
  setSaving,
  setError,
  clearError,
  toggleLiveUpdates,
  setLastUpdateTimestamp,

  // History actions
  addToHistory,
  clearHistory,
} = orderDetailsSlice.actions;

export default orderDetailsSlice.reducer;
