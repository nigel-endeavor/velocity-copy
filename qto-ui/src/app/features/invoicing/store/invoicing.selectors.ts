import { createFeatureSelector, createSelector } from '@ngrx/store';

import * as reducer from './invoicing.reducer';
import { Company } from '../../../models/company.model';

export const selectInvoicingState
  = createFeatureSelector<reducer.InvoicingState>(reducer.invoicingFeatureKey);

export const getIsLoading = createSelector(selectInvoicingState,
  (state: reducer.InvoicingState) => {
    return state.isLoading
  }
);

export const getColumns = createSelector(selectInvoicingState,
  (state: reducer.InvoicingState) => {
    return state.columns
  }
);

export const getFilters = createSelector(selectInvoicingState,
  (state: reducer.InvoicingState) => {
    return state.filters
  }
);

export const getSelectedInvoice = createSelector(selectInvoicingState,
  (state: reducer.InvoicingState) => {
    return state.selectedInvoice;
  }
);

export const getVertekClients = createSelector(selectInvoicingState,
  (state: reducer.InvoicingState) => {
    return state.vertekClients
  }
);

export const getVertekClientsAsOptions = createSelector(getVertekClients,
  (customers: Company[]) => {
    return customers.map(item => item.name);
  }
);