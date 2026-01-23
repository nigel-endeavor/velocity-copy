import { createReducer, on } from '@ngrx/store';
import * as actions from './multie-edit.actions';

export const multieEditFeatureKey = 'multieEditState';


export interface MultiEditState{
  formState: Record<string, string | number>
}

export const initialState: MultiEditState = {
  formState: {}
};

export const multieEditReducer = createReducer(
  initialState,
  on(actions.updateFormState, (state, action) => {
    return {
      ...state,
      formState: action.formState
    }
  }),

  on(actions.clearStore, (state) => {
    return {
      ...state,
      formState: {}
    }
  }),
);

