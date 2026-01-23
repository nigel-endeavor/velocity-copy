import { createFeatureSelector, createSelector } from '@ngrx/store';

import * as reducer from './surcharge-dialog.reducer';

export const selectSurchargeDialogState
  = createFeatureSelector<reducer.SurchargeDialogState>(reducer.surchargeDialogFeatureKey);

export const getIsLoading = createSelector(selectSurchargeDialogState,
  (state: reducer.SurchargeDialogState) => {
    return state.isLoading
  }
);

export const getColumns = createSelector(selectSurchargeDialogState,
  (state: reducer.SurchargeDialogState) => {
    return state.columns
  }
);

export const getFilters = createSelector(selectSurchargeDialogState,
  (state: reducer.SurchargeDialogState) => {
    return state.filters
  }
);

export const getSelectedSurcharge = createSelector(selectSurchargeDialogState,
  (state: reducer.SurchargeDialogState) => {
    return state.selectedSurcharge;
  }
);

export const getSurchargeTypes = createSelector(selectSurchargeDialogState,
  (state: reducer.SurchargeDialogState) => {
    return state.surchargeTypes;
  }
);