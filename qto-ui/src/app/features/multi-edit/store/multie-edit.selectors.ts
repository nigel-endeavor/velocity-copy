import { createFeatureSelector, createSelector } from '@ngrx/store';

import * as reducer from './multie-edit.reducer';

export const selectMultiEditState
  = createFeatureSelector<reducer.MultiEditState>(reducer.multieEditFeatureKey);

export const getFormState = createSelector(selectMultiEditState,
  (state: reducer.MultiEditState) => {
  return state.formState
  }
);
