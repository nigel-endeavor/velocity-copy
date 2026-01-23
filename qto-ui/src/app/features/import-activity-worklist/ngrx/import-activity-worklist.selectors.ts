import { createFeatureSelector, createSelector } from '@ngrx/store';
import * as reducer from './import-activity-worklist.reducer';

export const selectImportActivityWorklistState
  = createFeatureSelector<reducer.ImportActivityWorklistState>(reducer.importActivityWorklistFeatureKey);

export const getIsLoading = createSelector(selectImportActivityWorklistState,
  (state: reducer.ImportActivityWorklistState) => {
    return state.isLoading
  }
);

export const getColumns = createSelector(selectImportActivityWorklistState,
  (state: reducer.ImportActivityWorklistState) => {
    return state.columns
  }
);

export const getFilters = createSelector(selectImportActivityWorklistState,
  (state: reducer.ImportActivityWorklistState) => {
    return state.filters
  }
);

export const getUploadedByOptions = createSelector(selectImportActivityWorklistState,
  (state: reducer.ImportActivityWorklistState) => {
    return state.subjects.map(item => item.displayName);
  }
);
