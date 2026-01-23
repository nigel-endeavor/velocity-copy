import { createAction, props } from "@ngrx/store";
import { TaskGroup } from "../../../models/task-group.model";
import { LookupValue } from "../../../models/lookup-value.model";
import { ComparableDateRange } from "../../../interfaces/date-range.interface";

const actionKey = '[TaskManager]';

export const saveTaskGroup = createAction(
  `${actionKey} Save Task Group`,
  props<{ taskGroup: TaskGroup }>()
);

export const saveTaskGroupSuccess = createAction(
  `${actionKey} Save Task Group Success`
);

export const saveTaskGroupFailure = createAction(
  `${actionKey} Save Task Group Failure`,
  props<{ error: string }>()
);

export const deleteTaskGroup = createAction(
  `${actionKey} Delete Task Group`,
  props<{ taskGroup: TaskGroup }>()
);

export const deleteTaskGroupSuccess = createAction(
  `${actionKey} Delete Task Group Success`
);

export const deleteTaskGroupFailure = createAction(
  `${actionKey} Delete Task Group Failure`,
  props<{ error: string }>()
);

export const loadLookupValuesByKey = createAction(
  `${actionKey} Load Lookup Values by key`,
  props<{ key: string, lookupKey: string }>()
);

export const loadLookupValuesByKeyFailure = createAction(
  `${actionKey} Load Lookup Values Failure`,
  props<any>()
);

export const loadLookupValuesByKeySuccess = createAction(
  `${actionKey} Load Lookup Values Success`,
  props<{ values: LookupValue[], key: string }>()
);

export const setSelectedTaskGroup = createAction(
  `${actionKey} Set Selected Task Group`,
  props<{ taskGroup: TaskGroup | null }>()
);

//task group table
export const pageDestroyed = createAction(
  `${actionKey} page destroyed`,
);

export const updateFilters = createAction(
  `${actionKey} update filters`,
  props<{ key: string, value: string | number | string[] | boolean | ComparableDateRange }>()
);

export const updateSort = createAction(
  `${actionKey} update sort`,
  props<{ sort: {dir: string, col: string } }>()
);
