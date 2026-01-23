import { Dispute } from "src/app/models/dispute.model";
import { LookupValue } from "src/app/models/lookup-value.model";
import * as actions from "./multi-dispute.actions";
import { createReducer, on } from "@ngrx/store";

export const multiDisputeFeatureKey = 'multiDispute';

export interface MultiDisputeState {
  disputeTypes: LookupValue[];
  disputeAssignments: string[];
  dispute: Dispute;
}

export const initialState: MultiDisputeState = {
  disputeTypes: [],
  disputeAssignments: [],
  dispute: createDispute()
};

export const multiDisputeReducer = createReducer(
  initialState,

  on(actions.loadDisputeTypesSuccess, (state, action) => {
    return {
      ...state,
      disputeTypes: action.disputeTypes
    }
  }),

  on(actions.loadDisputeAssignmentsSuccess, (state, action) => {
    return {
      ...state,
      disputeAssignments: action.disputeAssignments,
      dispute: {
        ...state.dispute,
        disputeAssignment: action.currentUser
      } as Dispute
    }
  }),

  on(actions.loadInternalOnlyDefaultSuccess, (state, action) => {
    return {
      ...state,
      dispute: {
        ...state.dispute,
        initialNoteInternalOnly: action.internalOnlyDefault
      } as Dispute
    }
  }),

  on(actions.updateDisputeProperty, (state, action) => {
    return {
      ...state,
      dispute: {
        ...state.dispute,
        [action.key]: action.value
      } as Dispute
    }
  }),

  on(actions.pageDestroyed, (state) => {
    return {
      ...state,
      dispute: createDispute()
    }
  }),

);

export function createDispute() {
  let d = new Dispute();
  d.invoiceNum = '';
  d.vendorTrackingNum = '';
  d.initialNote = '';
  return d;
}