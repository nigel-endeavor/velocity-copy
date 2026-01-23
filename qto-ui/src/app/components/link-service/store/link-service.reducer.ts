import { CommonColumn } from "src/app/interfaces/columns.interface";
import { LINK_SERVICE_COLUMNS } from "../data/link-service-columns.const";
import * as actions from './link-service.actions';
import { createReducer, on } from "@ngrx/store";
import { ServiceView } from "../../../models/service-view.model";

export const linkServiceFeatureKey = 'linkService';

export interface LinkServiceState {
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
  selectedItems: ServiceView[];
}

export const initialState: LinkServiceState = {
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
  columns: LINK_SERVICE_COLUMNS,
  selectedItems: []
}

export const linkServiceReducer = createReducer(
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

  on(actions.saveLinkSuccess, (state) => {
    return {
      ...state,
      filters: {
        ...initialState.filters
      },
      isLoading: false,
      selectedItems: []
      }
  }),

  on(actions.saveLinkFailure, (state) => {
    return {
      ...state,
      filters: {
        ...initialState.filters
      },
      isLoading: false
      }
  }),

  on(actions.addSelectedItems, (state, action) => {
    const selectedIds = state.selectedItems.map(item => item.id)
    const newlySelected = action.value.filter(item => !selectedIds.includes(item.id));
    return {
      ...state,
      selectedItems: [
        ...state.selectedItems,
        ...newlySelected
      ]
    }
  }),

  on(actions.removeUnselectedItems, (state, action) => {
    const removedIds = action.value?.map(item => item.id) || [];
    const filteredItems = state.selectedItems.filter(item => !removedIds.includes(item.id));
    return {
      ...state,
      selectedItems: [
        ...filteredItems
      ]
    }
  }),
  
  on(actions.pageDestroyed, (state) => {
    return {
      ...state,
      selectedItems: []
    }
  })

);
