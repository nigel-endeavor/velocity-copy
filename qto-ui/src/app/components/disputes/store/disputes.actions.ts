import { createAction, props } from "@ngrx/store";
import { ComparableDateRange } from "src/app/interfaces/date-range.interface";
import { LookupValue } from "src/app/models/lookup-value.model";
import { DisputeMeta } from "./disputes.reducer";

export const updateFilters = createAction(
  '[Disputes] update filters',
  props<{ key: string, value: string | number | string[] | boolean | ComparableDateRange }>()
);

export const updateSort = createAction(
  '[Disputes] update sort',
  props<{ sort: {dir: string, col: string } }>()
);

export const clearFilters = createAction(
  '[Disputes] clear filters',
);

export const pageDestroyed = createAction(
  '[Disputes] page destroyed',
);

export const saveDispute = createAction(
  '[Disputes] Save a dispute',
  props<{ dispute: any }>()
);

export const saveDisputeSuccess = createAction(
  '[Disputes] Save a dispute Success',
  props<{ dispute: any }>()
);

export const saveDisputeMilestone = createAction(
  '[Disputes] Save a dispute milestone',
  props<{ dispute: any }>()
);

export const saveDisputeMilestoneSuccess = createAction(
  '[Disputes] Save a dispute milesone Success',
  props<{ dispute: any }>()
);

export const loadDisputeTypes = createAction(
  '[Disputes] Load Dispute Types',
  props<{ companyId: number }>()
);

export const loadDisputeTypesSuccess = createAction(
  '[Disputes] Load Dispute Types Success',
  props<{ disputeTypes: string[] }>()
);

export const loadDisputeAssignments = createAction(
  '[Disputes] Load Dispute Assignments',
);

export const loadDisputeAssignmentsSuccess = createAction(
  '[Disputes] Load Dispute Assignments Success',
  props<{ disputeAssignments: string[] }>()
);

export const loadMetaData = createAction(
  '[Disputes] Load Meta Data',
);

export const loadMetaDataFailure = createAction(
  '[Disputes] Load Meta Data Failure',
  props<any>()
);

export const loadMetaDataSuccess = createAction(
  '[Disputes] Load Meta Data Success',
  props<DisputeMeta>()
);

