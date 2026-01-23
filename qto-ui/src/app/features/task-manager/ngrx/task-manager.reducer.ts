import { createReducer, on } from "@ngrx/store";
import { LookupValue } from "../../../models/lookup-value.model";
import { TaskGroup } from "../../../models/task-group.model";
import * as actions from './task-manager.actions';
import { CommonColumn } from "../../../interfaces/columns.interface";
import { TASK_GROUP_COLUMNS } from "../data/task-group-table-columns.consts";

export const taskManagerFeatureKey = 'taskManager';

export interface TaskManagerState {
  //task group table
  filters: {
    offset: number;
    limit: number;
    sortDir: string;
    sortField: string;
    format: string;
    fields: string;
    headers: string;
  };
  columns: CommonColumn[];
  //everything else
  isLoading: boolean;
  taskGroups: TaskGroup[];
  accountOnboardingTasks: LookupValue[];
  selectedTaskGroup: TaskGroup | null;
}

export const initialState: TaskManagerState = {
  filters: {
    offset: 0,
    limit: 25,
    sortDir: '',
    sortField: '',
    format: '',
    fields: '',
    headers: ''
  },
  columns: TASK_GROUP_COLUMNS,

  isLoading: false,
  taskGroups: [],
  accountOnboardingTasks: [],
  selectedTaskGroup: null
};

export const taskManagerReducer = createReducer(
  initialState,
  on(actions.loadLookupValuesByKey, state => ({ ...state, isLoading: true })),
  on(actions.loadLookupValuesByKeySuccess, (state, action) => {
    return {
      ...state,
      [action.key]: action.values
    }
  }),
  on(actions.loadLookupValuesByKeySuccess, state => ({ ...state, isLoading: false })),
  on(actions.setSelectedTaskGroup, (state, { taskGroup }) => ({ ...state, selectedTaskGroup: taskGroup })),
  on(actions.saveTaskGroupSuccess, (state) => ({ ...state, selectedTaskGroup: null}))
);