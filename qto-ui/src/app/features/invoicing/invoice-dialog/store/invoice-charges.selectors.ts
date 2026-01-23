import { createFeatureSelector, createSelector } from '@ngrx/store';

import * as reducer from './invoice-charges.reducer';

export const selectInvoiceChargesState
  = createFeatureSelector<reducer.InvoiceChargesState>(reducer.invoiceChargeTableFeatureKey);

export const getIsLoading = createSelector(selectInvoiceChargesState,
  (state: reducer.InvoiceChargesState) => {
    return state.isLoading
  }
);

export const getColumns = createSelector(selectInvoiceChargesState,
  (state: reducer.InvoiceChargesState) => {
    return state.columns
  }
);

export const getFilters = createSelector(selectInvoiceChargesState,
  (state: reducer.InvoiceChargesState) => {
    return state.filters.invoiceId ? state.filters : null;
  }
);