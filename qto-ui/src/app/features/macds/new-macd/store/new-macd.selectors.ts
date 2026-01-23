import { createFeatureSelector, createSelector } from '@ngrx/store';

import * as reducer from './new-macd.reducer';

export const selectNewMacdState
  = createFeatureSelector<reducer.NewMacdState>(reducer.newMacdFeatureKey);

export const getIsLoading = createSelector(selectNewMacdState,
  (state: reducer.NewMacdState) => {
    return state.isLoading
  }
);

export const getOrderTypes = createSelector(selectNewMacdState,
  (state: reducer.NewMacdState) => {
    return state.orderTypes;
  }
);

export const getFilteredOrderTypes = createSelector(selectNewMacdState,
  (state: reducer.NewMacdState) => {
    //remove Disconnect Order Type if there is already one macdSelection
    //note that it's greater than 1 because the currently editing macdSelection is included in the array
    if (state.macds.length > 1) {
      return state.orderTypes.filter((orderType) => {
        return orderType.value !== 'Disconnect';
      });
    } else {
      return state.orderTypes;
    }
  }
);

export const getSubOrderTypes = createSelector(selectNewMacdState,
  (state: reducer.NewMacdState) => {
    return state.subOrderTypes;
  }
);

export const getServiceTypes = createSelector(selectNewMacdState,
  (state: reducer.NewMacdState) => {
    return state.serviceTypes;
  }
);

export const getDisconnectReasons = createSelector(selectNewMacdState,
  (state: reducer.NewMacdState) => {
    return state.disconnectReasons;
  }
);

export const getProjectNames = createSelector(selectNewMacdState,
  (state: reducer.NewMacdState) => {
    return state.projectNames;
  }
);

export const getFilteredSubOrderTypes = createSelector(selectNewMacdState,
  (state: reducer.NewMacdState) => {
    //filter subOrderTypes by their parentId for the selected orderType from the latest macdSelection
    const latestMacdSelection = state.macds[state.macds.length - 1];
    let orderTypeId = latestMacdSelection.orderType?.id;
    if (orderTypeId) {
      let subSubOrderTypes = state.subOrderTypes.filter((subOrderType) => {
        return subOrderType.parentId === orderTypeId;
      });
      // if this is not the first macdSelection, filter out subOrderTypes that start with 'New Service'
      //note that it's greater than 1 because the currently editing macdSelection is included in the array
      if (state.macds.length > 1) {
        subSubOrderTypes = subSubOrderTypes.filter((subOrderType) => {
          return !subOrderType.value.startsWith('New Service');
        });
      }
      return subSubOrderTypes;
    } else {
      return [];
    }
  }
);

export const getMacdSelections = createSelector(selectNewMacdState,
  (state: reducer.NewMacdState) => {
    return state.macds;
  }
);

export const getSaveMacdResult = createSelector(selectNewMacdState,
  (state: reducer.NewMacdState) => {
    return state.saveMacdResult;
  }
);

export const getSubmitMacdsResult = createSelector(selectNewMacdState,
  (state: reducer.NewMacdState) => {
    return state.submitMacdsResult;
  }
);

export const selectMacdNote = createSelector(selectNewMacdState,
  (state: reducer.NewMacdState) => {
    return state.macdNote;
  }
);

export const selectCreateLinkedBundled = createSelector(selectNewMacdState,
  (state: reducer.NewMacdState) => {
    return state.createLinkedBundled;
  }
);
