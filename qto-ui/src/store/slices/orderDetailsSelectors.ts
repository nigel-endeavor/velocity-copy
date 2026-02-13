/**
 * Order Details Selectors
 *
 * Memoized selectors for accessing order details state
 */

import { createSelector } from '@reduxjs/toolkit';
import type { RootState } from '../index';
import type { Order, Location, Service } from '@/shared/types/models';
import {
  getOrderLocationCount,
  getOrderServiceCount,
  getOrderServiceListString,
} from '@/shared/types/models/order.model';
import { getServiceDisplayText } from '@/shared/types/models/service.model';

// ==========================================
// Base Selectors
// ==========================================

export const selectOrderDetailsState = (state: RootState) => state.orderDetails;

export const selectOrder = (state: RootState) => state.orderDetails.order;

export const selectOriginalOrder = (state: RootState) => state.orderDetails.originalOrder;

export const selectEditFlags = (state: RootState) => state.orderDetails.editFlags;

export const selectLoading = (state: RootState) => state.orderDetails.loading;

export const selectSaving = (state: RootState) => state.orderDetails.saving;

export const selectError = (state: RootState) => state.orderDetails.error;

export const selectIsDirty = (state: RootState) => state.orderDetails.isDirty;

export const selectIsValid = (state: RootState) => state.orderDetails.isValid;

export const selectValidationErrors = (state: RootState) => state.orderDetails.validationErrors;

export const selectSelectedLocationId = (state: RootState) =>
  state.orderDetails.selectedLocationId;

export const selectSelectedServiceId = (state: RootState) => state.orderDetails.selectedServiceId;

export const selectExpandedLocations = (state: RootState) => state.orderDetails.expandedLocations;

export const selectExpandedServices = (state: RootState) => state.orderDetails.expandedServices;

export const selectActiveTab = (state: RootState) => state.orderDetails.activeTab;

export const selectLiveUpdates = (state: RootState) => state.orderDetails.liveUpdates;

export const selectLastUpdateTimestamp = (state: RootState) =>
  state.orderDetails.lastUpdateTimestamp;

// ==========================================
// Computed Selectors
// ==========================================

/**
 * Select order locations
 */
export const selectOrderLocations = createSelector([selectOrder], (order): Location[] => {
  return order?.locations || [];
});

/**
 * Select active (non-cancelled) locations
 */
export const selectActiveLocations = createSelector([selectOrderLocations], (locations) => {
  return locations.filter((loc) => loc.status !== 'Location Cancelled');
});

/**
 * Select location count
 */
export const selectLocationCount = createSelector([selectOrder], (order): number => {
  return order ? getOrderLocationCount(order) : 0;
});

/**
 * Select service count across all locations
 */
export const selectServiceCount = createSelector([selectOrder], (order): number => {
  return order ? getOrderServiceCount(order) : 0;
});

/**
 * Select all services across all locations
 */
export const selectAllServices = createSelector([selectOrderLocations], (locations): Service[] => {
  const services: Service[] = [];
  locations.forEach((loc) => {
    if (loc.services) {
      services.push(...loc.services);
    }
  });
  return services;
});

/**
 * Select active (non-cancelled) services
 */
export const selectActiveServices = createSelector([selectAllServices], (services) => {
  return services.filter((svc) => svc.status !== 'Service Cancelled');
});

/**
 * Select service list string (for display)
 */
export const selectServiceListString = createSelector([selectOrder], (order): string => {
  return order ? getOrderServiceListString(order) : '';
});

/**
 * Select a specific location by ID
 */
export const selectLocationById = (locationId: number | null) =>
  createSelector([selectOrderLocations], (locations): Location | undefined => {
    if (!locationId) return undefined;
    return locations.find((loc) => loc.id === locationId);
  });

/**
 * Select the currently selected location
 */
export const selectSelectedLocation = createSelector(
  [selectOrderLocations, selectSelectedLocationId],
  (locations, selectedId): Location | undefined => {
    if (!selectedId) return undefined;
    return locations.find((loc) => loc.id === selectedId);
  }
);

/**
 * Select a specific service by ID
 */
export const selectServiceById = (serviceId: number | null) =>
  createSelector([selectAllServices], (services): Service | undefined => {
    if (!serviceId) return undefined;
    return services.find((svc) => svc.id === serviceId);
  });

/**
 * Select the currently selected service
 */
export const selectSelectedService = createSelector(
  [selectAllServices, selectSelectedServiceId],
  (services, selectedId): Service | undefined => {
    if (!selectedId) return undefined;
    return services.find((svc) => svc.id === selectedId);
  }
);

/**
 * Select services for a specific location
 */
export const selectServicesByLocationId = (locationId: number | null) =>
  createSelector([selectOrderLocations], (locations): Service[] => {
    if (!locationId) return [];
    const location = locations.find((loc) => loc.id === locationId);
    return location?.services || [];
  });

// ==========================================
// Edit Flag Selectors
// ==========================================

/**
 * Check if any section is being edited
 */
export const selectIsAnyEditing = createSelector([selectEditFlags], (editFlags): boolean => {
  return (
    editFlags.editingOrderInfo ||
    editFlags.editingTechnical ||
    editFlags.editingBilling ||
    editFlags.editingFinancial ||
    editFlags.editingContacts ||
    Object.keys(editFlags.editingLocation).length > 0 ||
    Object.keys(editFlags.editingService).length > 0
  );
});

/**
 * Check if a specific location is being edited
 */
export const selectIsLocationEditing = (locationId: number) =>
  createSelector([selectEditFlags], (editFlags): boolean => {
    return !!editFlags.editingLocation[locationId];
  });

/**
 * Check if a specific service is being edited
 */
export const selectIsServiceEditing = (serviceId: number) =>
  createSelector([selectEditFlags], (editFlags): boolean => {
    return !!editFlags.editingService[serviceId];
  });

/**
 * Select count of locations being edited
 */
export const selectEditingLocationCount = createSelector([selectEditFlags], (editFlags): number => {
  return Object.keys(editFlags.editingLocation).length;
});

/**
 * Select count of services being edited
 */
export const selectEditingServiceCount = createSelector([selectEditFlags], (editFlags): number => {
  return Object.keys(editFlags.editingService).length;
});

// ==========================================
// Validation Selectors
// ==========================================

/**
 * Check if a field has validation errors
 */
export const selectHasFieldError = (fieldPath: string) =>
  createSelector([selectValidationErrors], (errors): boolean => {
    return !!errors[fieldPath] && errors[fieldPath].length > 0;
  });

/**
 * Get validation errors for a field
 */
export const selectFieldErrors = (fieldPath: string) =>
  createSelector([selectValidationErrors], (errors): string[] => {
    return errors[fieldPath] || [];
  });

/**
 * Get count of validation errors
 */
export const selectValidationErrorCount = createSelector(
  [selectValidationErrors],
  (errors): number => {
    return Object.keys(errors).length;
  }
);

/**
 * Check if form can be saved
 */
export const selectCanSave = createSelector(
  [selectIsDirty, selectIsValid, selectSaving],
  (isDirty, isValid, saving): boolean => {
    return isDirty && isValid && !saving;
  }
);

/**
 * Check if form can be reset
 */
export const selectCanReset = createSelector(
  [selectIsDirty, selectSaving],
  (isDirty, saving): boolean => {
    return isDirty && !saving;
  }
);

// ==========================================
// Financial Selectors
// ==========================================

/**
 * Calculate total MRC (Monthly Recurring Cost)
 */
export const selectTotalMrc = createSelector([selectOrder], (order): number => {
  if (!order) return 0;
  return order.mrc || 0;
});

/**
 * Calculate total NRC (Non-Recurring Cost)
 */
export const selectTotalNrc = createSelector([selectOrder], (order): number => {
  if (!order) return 0;
  return order.nrc || 0;
});

/**
 * Calculate total services MRC
 */
export const selectServicesMrc = createSelector([selectActiveServices], (services): number => {
  return services.reduce((total, svc) => total + (svc.mrc || 0), 0);
});

/**
 * Calculate total services NRC
 */
export const selectServicesNrc = createSelector([selectActiveServices], (services): number => {
  return services.reduce((total, svc) => total + (svc.nrc || 0), 0);
});

// ==========================================
// Status Selectors
// ==========================================

/**
 * Check if order is loading
 */
export const selectIsLoading = createSelector(
  [selectLoading, selectSaving],
  (loading, saving): boolean => {
    return loading || saving;
  }
);

/**
 * Check if order has unsaved changes
 */
export const selectHasUnsavedChanges = createSelector([selectIsDirty], (isDirty): boolean => {
  return isDirty;
});

/**
 * Check if order is new (not saved to database)
 */
export const selectIsNewOrder = createSelector([selectOrder], (order): boolean => {
  return !order || !order.id;
});

/**
 * Get order status
 */
export const selectOrderStatus = createSelector([selectOrder], (order): string | null => {
  return order?.status || null;
});

/**
 * Get order company
 */
export const selectOrderCompany = createSelector([selectOrder], (order) => {
  return order?.company || null;
});

/**
 * Get order client ID
 */
export const selectOrderClientId = createSelector([selectOrder], (order): string | null => {
  return order?.clientOrderId || null;
});

// ==========================================
// Expansion Selectors
// ==========================================

/**
 * Check if a location is expanded
 */
export const selectIsLocationExpanded = (locationId: number) =>
  createSelector([selectExpandedLocations], (expandedLocations): boolean => {
    return expandedLocations.includes(locationId);
  });

/**
 * Check if a service is expanded
 */
export const selectIsServiceExpanded = (serviceId: number) =>
  createSelector([selectExpandedServices], (expandedServices): boolean => {
    return expandedServices.includes(serviceId);
  });

/**
 * Check if all locations are expanded
 */
export const selectAreAllLocationsExpanded = createSelector(
  [selectOrderLocations, selectExpandedLocations],
  (locations, expandedLocations): boolean => {
    if (locations.length === 0) return false;
    return locations.every((loc) => loc.id && expandedLocations.includes(loc.id));
  }
);

// ==========================================
// Contact Selectors
// ==========================================

/**
 * Select order contacts
 */
export const selectOrderContacts = createSelector([selectOrder], (order) => {
  return order?.contacts || [];
});

/**
 * Select contacts by type
 */
export const selectContactsByType = (contactType: string) =>
  createSelector([selectOrderContacts], (contacts) => {
    return contacts.filter((c) => c.type === contactType);
  });

// ==========================================
// Export all selectors
// ==========================================

export default {
  // Base
  selectOrderDetailsState,
  selectOrder,
  selectOriginalOrder,
  selectEditFlags,
  selectLoading,
  selectSaving,
  selectError,
  selectIsDirty,
  selectIsValid,
  selectValidationErrors,
  selectSelectedLocationId,
  selectSelectedServiceId,
  selectExpandedLocations,
  selectExpandedServices,
  selectActiveTab,
  selectLiveUpdates,
  selectLastUpdateTimestamp,

  // Computed
  selectOrderLocations,
  selectActiveLocations,
  selectLocationCount,
  selectServiceCount,
  selectAllServices,
  selectActiveServices,
  selectServiceListString,
  selectLocationById,
  selectSelectedLocation,
  selectServiceById,
  selectSelectedService,
  selectServicesByLocationId,

  // Edit Flags
  selectIsAnyEditing,
  selectIsLocationEditing,
  selectIsServiceEditing,
  selectEditingLocationCount,
  selectEditingServiceCount,

  // Validation
  selectHasFieldError,
  selectFieldErrors,
  selectValidationErrorCount,
  selectCanSave,
  selectCanReset,

  // Financial
  selectTotalMrc,
  selectTotalNrc,
  selectServicesMrc,
  selectServicesNrc,

  // Status
  selectIsLoading,
  selectHasUnsavedChanges,
  selectIsNewOrder,
  selectOrderStatus,
  selectOrderCompany,
  selectOrderClientId,

  // Expansion
  selectIsLocationExpanded,
  selectIsServiceExpanded,
  selectAreAllLocationsExpanded,

  // Contacts
  selectOrderContacts,
  selectContactsByType,
};
