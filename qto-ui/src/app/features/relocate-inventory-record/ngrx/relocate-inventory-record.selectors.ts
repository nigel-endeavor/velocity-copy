import { createFeatureSelector, createSelector } from '@ngrx/store';
import * as reducer from './relocate-inventory-record.reducer';

export const selectRelocateInventoryRecordState
  = createFeatureSelector<reducer.RelocateInventoryRecordState>(reducer.relocateInventoryRecordFeatureKey);

  export const getColumns = createSelector(selectRelocateInventoryRecordState,
    (state: reducer.RelocateInventoryRecordState) => {
      return state.columns;
    }
  );

  export const getFilters = createSelector(selectRelocateInventoryRecordState,
    (state: reducer.RelocateInventoryRecordState) => {
      return state.filters;
    }
  );