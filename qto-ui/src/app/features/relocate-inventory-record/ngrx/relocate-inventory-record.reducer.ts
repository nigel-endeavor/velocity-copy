import { createReducer, on } from "@ngrx/store";
import { CommonColumn } from "src/app/interfaces/columns.interface";
import * as actions from './relocate-inventory-record.actions';
import { RELOCATE_INVENTORY_RECORD_COLUMNS } from "../data/relocate-inventory-record-columns.const";

export const relocateInventoryRecordFeatureKey = 'relocateInventoryRecord';

export interface RelocateInventoryRecordState {
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

export const initialState: RelocateInventoryRecordState = {
  filters: {
    offset: 0,
    limit: 25,
    sortDir: "",
    sortField: "",
    format: "",
    fields: "",
    headers: ""
  },
  isLoading: false,
  columns: RELOCATE_INVENTORY_RECORD_COLUMNS
}


export const relocateRecordReducer = createReducer(
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