import { createFeatureSelector, createSelector } from "@ngrx/store";
import { LookupValue } from '../../../models/lookup-value.model';
import * as reducer from './task-manager.reducer';

export const selectTaskManagerState = createFeatureSelector<reducer.TaskManagerState>('taskManager');

export const isLoading = createSelector(selectTaskManagerState, (state: reducer.TaskManagerState) => state.isLoading);

export const getAccountOnboardingTasks = createSelector(selectTaskManagerState, (state: reducer.TaskManagerState) => state.accountOnboardingTasks);

export const getAccountOnboardingTasksValues = createSelector(getAccountOnboardingTasks, (tasks: LookupValue[]) => tasks.map(task => task.value));

export const getSelectedTaskGroup = createSelector(selectTaskManagerState, (state: reducer.TaskManagerState) => state.selectedTaskGroup);

//task group table
export const getColumns = createSelector(selectTaskManagerState,
  (state: reducer.TaskManagerState) => {
    return state.columns
  }
);

export const getFilters = createSelector(selectTaskManagerState,
  (state: reducer.TaskManagerState) => {
    return state.filters
  }
);