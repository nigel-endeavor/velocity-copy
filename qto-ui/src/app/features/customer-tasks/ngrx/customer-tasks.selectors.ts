import { createFeatureSelector, createSelector } from '@ngrx/store';

import * as reducer from './customer-tasks.reducer';

export const selectCustomerDetailsState
  = createFeatureSelector<reducer.CustomerTasksState>(reducer.customerTasksFeatureKey);

export const getProvisioners = createSelector(selectCustomerDetailsState,
  (state: reducer.CustomerTasksState) => {
    return state.provisioners
  }
);

export const getCustomerTasks = createSelector(selectCustomerDetailsState,
  (state: reducer.CustomerTasksState) => {
    return state.customerTasks
  }
)

export const getTaskGroup = createSelector(selectCustomerDetailsState,
  (state: reducer.CustomerTasksState) => {
    return state.taskGroup
  }
)