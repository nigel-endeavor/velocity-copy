import { createReducer, on } from '@ngrx/store';
import * as actions from './invoice-charges.actions';
import { INVOICE_CHARGE_COLUMNS } from '../data/invoice-charge-table-columns.consts';
import { CommonColumn } from '../../../../interfaces/columns.interface';

export const invoiceChargeTableFeatureKey = 'invoiceCharges';

export interface InvoiceChargesState {
  filters: {
    search: string;
    invoiceId: number | undefined;
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
}

export const initialState: InvoiceChargesState = {
  filters: {
    search: '',
    invoiceId: undefined,
    offset: 0,
    limit: 25,
    sortDir: '',
    sortField: '',
    format: '',
    fields: '',
    headers: ''
  },
  isLoading: false,
  columns: INVOICE_CHARGE_COLUMNS
};

export const invoiceChargeTableReducer = createReducer(
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
  })
);