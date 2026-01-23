import { createFeatureSelector, createSelector } from '@ngrx/store';
import * as reducer from './cost-history.reducer';

export const selectCostHistoryState
  = createFeatureSelector<reducer.CostHistoryState>(reducer.costHistoryFeatureKey);

export const getIsLoading = createSelector(selectCostHistoryState,
  (state: reducer.CostHistoryState) => {
    return state.isLoading
  }
);

export const getColumns = createSelector(selectCostHistoryState,
  (state: reducer.CostHistoryState) => {
    return state.columns
  }
);

export const getFilters = createSelector(selectCostHistoryState,
  (state: reducer.CostHistoryState) => {
    return state.filters
  }
);

export const getMeta = createSelector(selectCostHistoryState,
  (state: reducer.CostHistoryState) => {
    return state.meta
  }
);