import { createFeatureSelector, createSelector } from '@ngrx/store';
import * as reducer from './link-location.reducer';

export const selectLinkLocationState
  = createFeatureSelector<reducer.LinkLocationState>(reducer.linkLocationFeatureKey);

export const getIsLoading = createSelector(selectLinkLocationState,
  (state: reducer.LinkLocationState) => {
    return state.isLoading;
  }
);

export const getColumns = createSelector(selectLinkLocationState,
  (state: reducer.LinkLocationState) => {
    return state.columns;
  }
);

export const getFilters = createSelector(selectLinkLocationState,
  (state: reducer.LinkLocationState) => {
    return state.filters;
  }
);