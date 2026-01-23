import { CommonColumn } from "src/app/interfaces/columns.interface";
import { SERVICE_HISTORY_COLUMNS } from "../data/service-history-columns.const";
import { createReducer, on } from "@ngrx/store";
import * as actions from './service-history.actions';

export const serviceHistoryFeatureKey = 'serviceHistory';

export interface ServiceHistoryState {
  filters: {
    serviceId: number | undefined;
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

export const initialState: ServiceHistoryState = {
  filters: {
    serviceId: undefined,
    offset: 0,
    limit: 100,
    sortDir: '',
    sortField: '',
    format: '',
    fields: '',
    headers: ''
  },
  isLoading: false,
  columns: SERVICE_HISTORY_COLUMNS
};

export const serviceHistoryReducer = createReducer(
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

);