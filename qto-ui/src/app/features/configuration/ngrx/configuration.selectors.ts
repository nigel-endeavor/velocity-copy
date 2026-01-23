import { createFeatureSelector, createSelector } from '@ngrx/store';
import * as reducer from './configuration.reducer';

export const selectCustomerManagementState
  = createFeatureSelector<reducer.ConfigurationState>(reducer.configurationFeatureKey);


export const getIsLoading = createSelector(selectCustomerManagementState,
  (state: reducer.ConfigurationState) => {
    return state.isLoading
  }
);

export const getTenants = createSelector(selectCustomerManagementState,
  (state: reducer.ConfigurationState) => {
    return state.tenants
  }
);

export const getSelectedTenant = createSelector(selectCustomerManagementState,
  (state: reducer.ConfigurationState) => {
    return state.selectedTenant
  }
);

export const getSelectedCustomer = createSelector(selectCustomerManagementState,
  (state: reducer.ConfigurationState) => {
    return state.selectedCustomer
  }
);

export const getUserHasLookupAdmin = createSelector(selectCustomerManagementState,
  (state: reducer.ConfigurationState) => {
    return state.userHasLookupAdmin
  }
);

export const getTabs = createSelector(
  getSelectedTenant,
  getSelectedCustomer,
  getUserHasLookupAdmin,
  (tenant, customer, userHasLookupAdmin) => {
    if (tenant) {
      if (userHasLookupAdmin) {
        let tabs = ['Lookups', 'Configuration'];
        return tabs;
      }
    }
    return [];
  }
);

export const getSelectedTab = createSelector(selectCustomerManagementState,
  (state: reducer.ConfigurationState) => {
    return state.selectedTab
  }
);

export const getBillingContact = createSelector(selectCustomerManagementState,
  (state: reducer.ConfigurationState) => {
    return state.billingContact
  }
);

export const getTechContact = createSelector(selectCustomerManagementState,
  (state: reducer.ConfigurationState) => {
    return state.techContact
  }
);

export const getSalesContact = createSelector(selectCustomerManagementState,
  (state: reducer.ConfigurationState) => {
    return state.salesContact
  }
);

export const getAuthContact = createSelector(selectCustomerManagementState,
  (state: reducer.ConfigurationState) => {
    return state.authContact
  }
);

export const getProvisioners = createSelector(selectCustomerManagementState,
  (state: reducer.ConfigurationState) => {
    return state.provisioners
  }
);