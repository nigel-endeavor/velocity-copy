import { createFeatureSelector, createSelector } from '@ngrx/store';
import * as reducer from './disputes.reducer';

export const selectDisputesState
  = createFeatureSelector<reducer.DisputesState>(reducer.disputesFeatureKey);

export const getIsLoading = createSelector(selectDisputesState,
  (state: reducer.DisputesState) => {
    return state.isLoading
  }
);

export const getColumns = createSelector(selectDisputesState,
  (state: reducer.DisputesState) => {
    return state.columns
  }
);

export const getFilters = createSelector(selectDisputesState,
  (state: reducer.DisputesState) => {
    return state.filters
  }
);

export const getDisputeTypes = createSelector(selectDisputesState,
  (state: reducer.DisputesState) => {
    return state.disputeTypes;
  }
);

export const getDisputeAssignments = createSelector(selectDisputesState,
  (state: reducer.DisputesState) => {
    return state.disputeAssignments;
  }
);
export const getMetaData = createSelector(selectDisputesState,
  (state: reducer.DisputesState) => {
    return {
      ...state.meta
    }
  }
);

export const getSelectedDispute = createSelector(selectDisputesState,
  (state: reducer.DisputesState) => {
    return state.selectedDispute;
  }
);
