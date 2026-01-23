import { createSelector } from '@ngrx/store';
import * as reducer from './multi-dispute.reducer';

export const selectMultiDisputeState = (state: any) => state[reducer.multiDisputeFeatureKey];

export const getDisputeTypes = createSelector(selectMultiDisputeState,
  (state: reducer.MultiDisputeState) => {
    return state.disputeTypes.map((dt) => dt.display);
  }
);

export const getDisputeAssignments = createSelector(selectMultiDisputeState,
  (state: reducer.MultiDisputeState) => {
    return state.disputeAssignments;
  }
);

export const getDispute = createSelector(selectMultiDisputeState,
  (state: reducer.MultiDisputeState) => {
    return state.dispute;
  }
);
