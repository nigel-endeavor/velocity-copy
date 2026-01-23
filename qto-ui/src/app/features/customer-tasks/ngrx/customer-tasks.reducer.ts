import { createReducer, on } from '@ngrx/store';
import * as actions from './customer-tasks.actions';
import { Company } from '../../../models/company.model';
import { SubjectInterface } from 'src/app/models/subject.model';
import { CompanyTask } from '../../../models/company-task.model';
import { TaskGroup } from '../../../models/task-group.model';

export const customerTasksFeatureKey = 'customerTasks';

export interface CustomerTasksState {
  provisioners: SubjectInterface[];
  customerTasks: CompanyTask[];
  taskGroup: TaskGroup | null;
}

export const initialState: CustomerTasksState = {
  provisioners: [],
  customerTasks: [],
  taskGroup: null
};

export const customerTasksReducer = createReducer(
  initialState,

  on(actions.loadCustomerTasksSuccess, (state, action) => {
    return {
      ...state,
      customerTasks: action.customerTasks
    }
  }),

  on(actions.loadProvisionersSuccess, (state, action) => {
    return {
      ...state,
      provisioners: action.subjects
    }
  }),

  on (actions.loadTaskGroupSuccess, (state, action) => {
    return {
      ...state,
      taskGroup: action.taskGroup
    }
  }),

  on(actions.setTaskGroup, (state, action) => {
    return {
      ...state,
      taskGroup: action.taskGroup
    }
  })
);
