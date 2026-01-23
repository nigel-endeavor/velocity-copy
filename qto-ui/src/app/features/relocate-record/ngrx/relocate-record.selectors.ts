import { createFeatureSelector, createSelector } from '@ngrx/store';
import * as reducer from './relocate-record.reducer';

export const selectRelocateRecordState
  = createFeatureSelector<reducer.RelocateRecordState>(reducer.relocateRecordFeatureKey);

  export const getColumns = createSelector(selectRelocateRecordState,
    (state: reducer.RelocateRecordState) => {
      return state.columns;
    }
  );

  export const getFilters = createSelector(selectRelocateRecordState,
    (state: reducer.RelocateRecordState) => {
      return state.filters;
    }
  );