import { CommonColumn } from 'src/app/interfaces/columns.interface';
import * as actions from './cost-history.actions';
import { COST_HISTORY_COLUMNS } from '../data/cost-history-columns.const';
import { createReducer, on } from '@ngrx/store';
import { CostHistoryMeta } from 'src/app/models/cost-history.model';

export const costHistoryFeatureKey = 'costHistory';

export interface CostHistoryState {
  filters: {
    serviceId: number | undefined;
    locationId: number | undefined;
    offset: number;
    limit: number;
    sortDir: string;
    sortField: string;
    format: string;
    fields: string;
    headers: string;
  }

  isLoading: boolean;
  columns: CommonColumn[];
  meta: CostHistoryMeta | null;
}

export const initialState: CostHistoryState = {
  filters: {
    serviceId: undefined,
    locationId: undefined,
    offset: 0,
    limit: 100,
    sortDir: '',
    sortField: '',
    format: '',
    fields: '',
    headers: ''
  },
  isLoading: false,
  columns: COST_HISTORY_COLUMNS,
  meta: null
}

export const costHistoryReducer = createReducer(
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

  on(actions.loadMetaDataSuccess, (state, action) => {
    return {
      ...state,
      meta: action
    }
  })
  
);