import { createAction, props } from "@ngrx/store";
import { Dispute } from "src/app/models/dispute.model";
import { LookupValue } from "src/app/models/lookup-value.model";

export const loadDisputeTypes = createAction(
  '[Multi Dispute] Load Dispute Types'
);

export const loadDisputeTypesSuccess = createAction(
  '[Multi Dispute] Load Dispute Types Success',
  props<{ disputeTypes: LookupValue[] }>()
);

export const loadDisputeAssignments = createAction(
  '[Multi Dispute] Load Dispute Assignments'
);

export const loadDisputeAssignmentsSuccess = createAction(
  '[Multi Dispute] Load Dispute Assignments Success',
  props<{ disputeAssignments: string[], currentUser: string }>()
);

export const loadInternalOnlyDefault = createAction(
  '[Multi Dispute] Load Internal Only Default'
);

export const loadInternalOnlyDefaultSuccess = createAction(
  '[Multi Dispute] Load Internal Only Default Success',
  props<{ internalOnlyDefault: boolean }>()
);

export const updateDisputeProperty = createAction(
  '[Multi Dispute] Update Dispute Property',
  props<{ key: string, value: string | boolean | Date | null }>()
);

export const pageDestroyed = createAction(
  '[Multi Dispute] Page Destroyed'
);

export const submitDispute = createAction(
  '[Multi Dispute] Submit Dispute',
  props<{ serviceIds: number[], dispute: Dispute }>()
);
