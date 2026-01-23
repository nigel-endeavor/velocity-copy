import { CommonColumn } from "src/app/interfaces/columns.interface";
import { Dispute } from "src/app/models/dispute.model";
import { DISPUTE_COLUMNS } from "../data/disputes-table-columns.consts";
import { createReducer, on } from "@ngrx/store";
import * as actions from './disputes.actions';

export const disputesFeatureKey = 'disputes';

export interface DisputeMeta {
  openDisputeMrc: number;
  openDisputeNrc: number;
}
export interface DisputesState {
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
  disputeTypes: string[];
  disputeAssignments: string[];
  meta: DisputeMeta | null;
  selectedDispute: Dispute | null;
}

export const initialState: DisputesState = {
  filters: {
    serviceId: undefined,
    offset: 0,
    limit: 25,
    sortDir: '',
    sortField: '',
    format: '',
    fields: '',
    headers: ''
  },
  isLoading: false,
  columns: DISPUTE_COLUMNS,
  disputeTypes: [],
  disputeAssignments: [],
  meta: null,
  selectedDispute: null
}

export const disputesReducer = createReducer(
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
        ...initialState.filters,
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

  on(actions.loadDisputeTypesSuccess, (state, action) => {
    return {
      ...state,
      disputeTypes: action.disputeTypes
    }
  }),

  on(actions.loadDisputeAssignmentsSuccess, (state, action) => {
    return {
      ...state,
      disputeAssignments: action.disputeAssignments
    }
  }),

  on(actions.saveDisputeSuccess, (state, action) => {
    return {
      ...state,
      isLoading: false,
      selectedDispute: null
    }
  }),

  on(actions.saveDisputeMilestoneSuccess, (state, action) => {
    return {
      ...state,
      isLoading: false,
      selectedDispute: action.dispute
    }
  }),

  on(actions.loadMetaDataSuccess, (state, action) => {
    return {
      ...state,
      meta: action
    }
  })
);