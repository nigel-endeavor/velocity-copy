import { createReducer, on } from '@ngrx/store';
import * as actions from './table-deprecated.actions';
import { SubjectInterface } from '../../../models/subject.model';
import { LookupValue } from '../../../models/lookup-value.model';
import { MultiEditFailureInterface } from '../../../interfaces/multiEditFailure.interface';

export const tableDeprecatedStoreFeatureKey = 'tableDeprecatedState';

export interface SelectedItems extends Record<string, string | number | {} | null> {
  id: number;
}

export interface TableDeprecatedState{
  selectedItems: SelectedItems[];
  provisioners: SubjectInterface[];
  clientManagers: LookupValue[];
  serviceJeopardy: LookupValue[];
  jeopardyResponsibility: LookupValue[];
  providers: LookupValue[];
  speed: LookupValue[];
  protocols: LookupValue[];
  mediaTypes: LookupValue[];
  isLoading: boolean;
  cantUpdate: MultiEditFailureInterface[];
}

export const initialState: TableDeprecatedState = {
  selectedItems: [],
  provisioners: [],
  clientManagers: [],
  serviceJeopardy: [],
  jeopardyResponsibility: [],
  providers: [],
  protocols: [],
  mediaTypes: [],
  speed: [],
  cantUpdate: [],
  isLoading: false
};

export const tableDeprecatedReducer = createReducer(
  initialState,
  on(actions.addSelectedItem, (state, action) => {
    return {
      ...state,
      selectedItems: [
        ...state.selectedItems,
        {
          ...action.selectedItem
        }
      ]
    }
  }),

  on(actions.addSelectedItems, (state, action) => {
    const selectedIds = state.selectedItems.map(item => item.id)
    const newlySelected = action.selectedItems.filter(item => !selectedIds.includes(item.id));
    return {
      ...state,
      selectedItems: [
        ...state.selectedItems,
        ...newlySelected
      ]
    }
  }),

  on(actions.removeSelectedItems, (state, action) => {
    const removedIds = action.unSelectedItems?.map(item => item.id) || [];
    const filteredItems = state.selectedItems.filter(item => !removedIds.includes(item.id));
    return {
      ...state,
      selectedItems: [
        ...filteredItems
      ]
    }
  }),

  on(actions.loadProvisionersSuccess, (state, action) => {
    return {
      ...state,
      provisioners: action.subjects
    }
  }),

  on(actions.loadLookupValuesByKeySuccess, (state, action) => {
    return {
      ...state,
      [action.key]: action.values
    }
  }),

  on(actions.sendMultieEdit, (state) => {
    return {
      ...state,
      isLoading: true
    }
  }),

  on(
    actions.sendMultieEditFailure,
    actions.sendMultieEditSuccess,
    (state) => {
    return {
      ...state,
      isLoading: false
    }
  }),

  on(
    actions.sendMultieEditFailure,
    (state, action) => {
      const remainingIds = action.cantUpdate?.map(item => item.service.id) || [];
      const filteredItems = state.selectedItems.filter(item => remainingIds.includes(item.id));
      return {
        ...state,
        selectedItems: [
          ...filteredItems
        ],
      cantUpdate: action.cantUpdate
    }
  }),

  on(actions.clearStore, (state) => {
    return {
      ...state,
      selectedItems: []
    }
  }),
);

