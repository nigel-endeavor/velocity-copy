import { createReducer, on } from '@ngrx/store';
import * as actions from './new-macd.actions';
import { LookupValue } from '../../../../models/lookup-value.model';
import { Service } from '../../../../models/service.model';

export const newMacdFeatureKey = 'newMacd';

export interface NewMacdState {
  isLoading: boolean;
  orderTypes: LookupValue[];
  subOrderTypes: LookupValue[];
  serviceTypes: LookupValue[];
  disconnectReasons: LookupValue[];
  projectNames: LookupValue[];
  macds: {
    orderType: LookupValue | undefined,
    subOrderType: LookupValue | undefined,
    createDisconnectUponCompletion: boolean,
    disconnectReason: LookupValue | undefined,
    serviceType: LookupValue | undefined,
    projectName: string | undefined
  }[];
  saveMacdResult: Service | null;
  submitMacdsResult: any;
  macdNote: string | null;
  createLinkedBundled: boolean;
}

export const initialState: NewMacdState = {
  isLoading: false,
  orderTypes: [],
  subOrderTypes: [],
  serviceTypes: [],
  disconnectReasons: [],
  projectNames: [],
  macds: [{
    orderType: undefined,
    subOrderType: undefined,
    createDisconnectUponCompletion: false,
    disconnectReason: undefined,
    serviceType: undefined,
    projectName: undefined
  }],
  saveMacdResult: null,
  submitMacdsResult: null,
  macdNote: null,
  createLinkedBundled: false
};

export const newMacdReducer = createReducer(
  initialState,

  on(actions.loadOrderTypesSuccess, (state, action) => {
    return {
      ...state,
      orderTypes: action.orderTypes
    }
  }),

  on(actions.loadSubOrderTypesSuccess, (state, action) => {
    return {
      ...state,
      subOrderTypes: action.subOrderTypes
    }
  }),

  on(actions.loadDisconnectReasonsSuccess, (state, action) => {
    return {
      ...state,
      disconnectReasons: action.disconnectReasons
    }
  }),

  on(actions.loadProjectNamesSuccess, (state, action) => {
    return {
      ...state,
      projectNames: action.projectNames
    }
  }),

  on (actions.loadServiceTypesSuccess, (state , action) => {
    return {
      ...state,
      serviceTypes: action.serviceTypes
    }
  }),

  on(actions.updateMacdSelection, (state, { selection, key, value }) => {
    //@ts-ignore
    const updatedMacdSelections = state.macds.map((macd, index) => {
      if (index === selection) {
        // Create a copy of the current macdSelection and update the specified property
        const currentSelection = { ...macd};
        if (key === 'orderType') {
          currentSelection.orderType = value as LookupValue;
          //reset subsequent fields if orderType is changed
          currentSelection.subOrderType = undefined;
          currentSelection.createDisconnectUponCompletion = false;
          currentSelection.disconnectReason = undefined;
        } else if (key === 'subOrderType') {
          currentSelection.subOrderType = value as LookupValue;
          //reset subsequent fields if subOrderType is changed
          currentSelection.createDisconnectUponCompletion = false;
          currentSelection.disconnectReason = undefined;
        } else if (key === 'createDisconnectUponCompletion') {
          currentSelection.createDisconnectUponCompletion = value as boolean;
          currentSelection.disconnectReason = undefined;
        } else if (key === 'disconnectReason') {
          currentSelection.disconnectReason = value as LookupValue;
        } else if (key === 'serviceType') {
          currentSelection.serviceType = value as LookupValue;
        } else if (key === 'projectName') {
          currentSelection.projectName = value as string;
        }
        return currentSelection;
      }
      return macd;
    });

    return {
      ...state,
      macds: updatedMacdSelections,
    };
  }),

  on(actions.addMacd, (state) => {
    return {
      ...state,
      macds: [...state.macds, {
        orderType: undefined,
        subOrderType: undefined,
        createDisconnectUponCompletion: false,
        disconnectReason: undefined,
        serviceType: undefined,
        projectName: undefined
      }]
    };
  }),

  on(actions.removeMacd, (state, { index }) => {
    return {
      ...state,
      macds: state.macds.filter((_, i) => i !== index)
    };
  }),

  on(actions.pageDestroyed, (state) => {
    return {
      ...state,
      macds: [{
        orderType: undefined,
        subOrderType: undefined,
        createDisconnectUponCompletion: false,
        disconnectReason: undefined,
        serviceType: undefined,
        projectName: undefined
      }],
      saveMacdResult: null,
      submitMacdsResult: null,
      macdNote: null,
      createLinkedBundled: false,
      isLoading: false,
      orderTypes: [],
      subOrderTypes: [],
      disconnectReasons: [],
      projectNames: [],
      serviceTypes: []
    }
  }),

  on(actions.saveMacd, (state) => {
    return {
      ...state,
      isLoading: true
    }
  }),

  on(actions.saveMacdSuccess, (state, { result }) => {
    return {
      ...state,
      isLoading: false,
      saveMacdResult: result
    }
  }),

  on(actions.submitMacdsSuccess, (state, { result }) => {
    return {
      ...state,
      submitMacdsResult: result
    }
  }),

  on(actions.updateMacdNote, (state, { macdNote }) => ({
    ...state,
    macdNote,
  })),

  on(actions.updateCreateLinkedBundled, (state, { createLinkedBundled }) => ({
    ...state,
    createLinkedBundled,
  }))

);
