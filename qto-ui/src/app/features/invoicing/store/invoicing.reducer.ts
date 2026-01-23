import { createReducer, on } from '@ngrx/store';
import * as actions from './invoicing.actions';
import { INVOICE_COLUMNS } from '../data/invoice-table-columns.consts';
import { CommonColumn } from '../../../interfaces/columns.interface';
import { Invoice } from '../../../models/invoice.model';
import { Company } from '../../../models/company.model';

export const invoicingFeatureKey = 'invoicing';

export interface InvoicingState {
  filters: {
    search: string;
    tenantName: string;
    offset: number;
    limit: number;
    sortDir: string;
    sortField: string;
    format: string;
    fields: string;
    headers: string;
  };

  isLoading: boolean;
  columns: CommonColumn[];
  selectedInvoice: Invoice | undefined;
  vertekClients: Company[];
}

export const initialState: InvoicingState = {
  filters: {
    search: '',
    tenantName: '',
    offset: 0,
    limit: 25,
    sortDir: '',
    sortField: '',
    format: '',
    fields: '',
    headers: ''
  },
  isLoading: false,
  columns: INVOICE_COLUMNS,
  selectedInvoice: undefined,
  vertekClients: []
};

export const invoiceTableReducer = createReducer(
  initialState,

  on(actions.updateSort, (state, action) => {
    let resSort;
    if (state.filters.sortDir === action.sort.dir && state.filters.sortField === action.sort.col) {
      resSort = {
        sortDir: '',
        sortField: ''
      }
    } else {
      resSort = {
        sortDir: action.sort.dir,
        sortField: action.sort.col
      }
    }
    return {
      ...state,
      filters: {
        ...state.filters,
        ...resSort
      }
    }
  }),

  on(actions.updateFilters, (state, action) => {
    return {
      ...state,
      filters: {
        ...state.filters,
        [action.key]: action.value
      }
    }
  }),

  on(actions.clearFilters, (state) => {
    return {
      ...state,
     filters: {
       ...initialState.filters
     }
    }
  }),

  on(actions.createInvoice, (state, action) => {
    const invoice = new Invoice();
    return {
      ...state,
      selectedInvoice: invoice
    }
  }),

  on(actions.setSelectedInvoice, (state, action) => {
    return {
      ...state,
      selectedInvoice: action.invoice
    }
  }),

  on(actions.saveInvoiceSuccess, actions.saveInvoiceFailure, (state, action) => {
    return {
      ...state,
      selectedInvoice: undefined
    }
  }),

  on(actions.finalizeInvoiceSuccess, actions.finalizeInvoiceFailure, (state, action) => {
    return {
      ...state,
      selectedInvoice: undefined
    }
  }),

  on(actions.updateInvoice, (state, action) => {
    return state.selectedInvoice ? {
      ...state,
      selectedInvoice: {
        ...state.selectedInvoice,
        [action.key]: action.value
      }
    } : state;
  }),
  
  on(actions.pageDestroyed, (state) => {
    return {
      ...state,
      selectedInvoice: undefined
    }
  }),

  on(actions.loadVertekClientsSuccess, (state, action) => {
    return {
      ...state,
      vertekClients: action.vertekClients.collection
    }
  }),
);