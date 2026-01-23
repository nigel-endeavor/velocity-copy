import { createFeatureSelector, createSelector } from '@ngrx/store';
import * as reducer from './service-history.reducer';

export const selectServiceHistoryState
  = createFeatureSelector<reducer.ServiceHistoryState>(reducer.serviceHistoryFeatureKey);

export const getIsLoading = createSelector(selectServiceHistoryState,
  (state: reducer.ServiceHistoryState) => {
    return state.isLoading
  }
);

export const getColumns = createSelector(selectServiceHistoryState,
  (state: reducer.ServiceHistoryState) => {
    return state.columns
  }
);

export const getFilters = createSelector(selectServiceHistoryState,
  (state: reducer.ServiceHistoryState) => {
    return state.filters
  }
);