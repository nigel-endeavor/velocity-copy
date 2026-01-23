import { CommonColumn } from "src/app/interfaces/columns.interface";
import { LINK_LOCATION_COLUMNS } from "../data/link-location-columns.const";
import * as actions from './link-location.actions';
import { createReducer, on } from "@ngrx/store";

export const linkLocationFeatureKey = 'linkLocation';

export interface LinkLocationState {
  filters: {
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
}

export const initialState: LinkLocationState = {
  filters: {
    offset: 0,
    limit: 100,
    sortDir: '',
    sortField: '',
    format: '',
    fields: '',
    headers: ''
  },
  isLoading: false,
  columns: LINK_LOCATION_COLUMNS
}

export const linkLocationReducer = createReducer(
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