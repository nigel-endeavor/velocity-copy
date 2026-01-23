import { createReducer, on } from '@ngrx/store';
import * as actions from './service-equipment.actions';
import { LookupValue } from '../../../../models/lookup-value.model';
import { SERVICE_EQUIPMENT_COLUMNS } from '../data/service-equipment-columns.consts';
import { CommonColumn } from '../../../../interfaces/columns.interface';
import { ServiceEquipment } from '../../../../models/service-equipment-model';
import { CommonSearchCriteria } from '../../../../interfaces/commonSearch.interface';
import { Subject } from 'rxjs';
import { SubjectInterface } from '../../../../models/subject.model';

export const serviceEquipmentFeatureKey = 'serviceEquipment';

export interface CustomersMeta {
  exampleTotal: number;
}

export interface ServiceEquipmentState {
  filters: {
    search: string;
    offset: number;
    limit: number;
    sortDir: string;
    sortField: string;
    format: string;
    fields: string;
    headers: string;
    active: boolean;
  };
  isLoading: boolean;
  cardViewSelected: boolean;
  columns: CommonColumn[];
  selectedServiceEquipment: ServiceEquipment | null;
  equipmentTypes: LookupValue[];
  equipmentSubTypes: LookupValue[];
  ownerships: LookupValue[];
  shippingMethods: LookupValue[];
}

export const initialState: ServiceEquipmentState = {
  filters: {
    search: '',

    offset: 0,
    limit: 25,
    sortDir: '',
    sortField: '',
    format: '',
    fields: '',
    headers: '',
    active: true
  },
  isLoading: false,
  cardViewSelected: true,
  columns: SERVICE_EQUIPMENT_COLUMNS,
  selectedServiceEquipment: null,
  equipmentTypes: [],
  equipmentSubTypes: [],
  ownerships: [],
  shippingMethods: []
};

export const serviceEquipmentReducer = createReducer(
  initialState,

  on(actions.updateSort, (state, action) => {
    let resSort;
    if (state.filters.sortDir === action.sort.dir && state.filters.sortField === action.sort.col) {
      resSort = {
        sortDir: '',
        sortField: ''
      }
    } else {
      resSort = {
        sortDir: action.sort.dir,
        sortField: action.sort.col
      }
    }
    return {
      ...state,
      filters: {
        ...state.filters,
        ...resSort
      }
    }
  }),

  on(actions.updateFilters, (state, action) => {
    return {
      ...state,
      filters: {
        ...state.filters,
        [action.key]: action.value,
        ...(action.key !== 'offset' && { offset: 0 }),
      }
    }
  }),

  on(actions.clearFilters, (state) => {
    return {
      ...state,
      filters: {
        ...initialState.filters
      }
    }
  }),

  on(actions.loadLookupValuesByKey, state => ({ ...state, isLoading: true })),

  on(actions.loadLookupValuesByKeySuccess, (state, action) => {
    return {
      ...state,
      [action.key]: action.values
    }
  }),

  on(actions.toggleView, (state) => {
    return {
      ...state,
      cardViewSelected: !state.cardViewSelected
    }
  }),

  on(actions.toggleColumn, (state, action) => {
    const newColumns = [...state.columns].map(col => {
      return {
        ...col,
        hidden: col.propertyName === action.columnName ? !col.hidden : col.hidden
      }
    })
    return {
      ...state,
      columns: newColumns
    }
  }),

  on(actions.toggleDaterangeColumn, (state, action) => {
    const newColumns = [...state.columns].map(col => {
      return {
        ...col,
        active: col.propertyName === action.columnName ? !col.active : false
      }
    })
    return {
      ...state,
      columns: newColumns
    }
  }),

  // on(actions.updateParams, (state, action) => {
  //   return {
  //     ...state,
  //     [action.filterKey]: {
  //       filters: {
  //         ...state[action.filterKey].filters,
  //         [action.key]: action.value,
  //         ...(action.key !== "offset" && { offset: 0 })
  //       },
  //       wasChanged: action.key !== "offset"
  //     }
  //   }
  // }),

  // on(actions.loadDropdownContentSuccess, (state, action) => {
  //   return {
  //     ...state,
  //     [action.optionKey]: state[action.filterKey].wasChanged || state[action.filterKey].filters.offset == 0 ? action.options.collection : [...state[action.optionKey], ...action.options.collection],
  //     [action.filterKey]: {
  //       filters: {
  //         ...state[action.filterKey].filters,
  //         limit: 50,
  //         total: action.options.total,
  //         offset: action.options.offset
  //       },
  //       wasChanged: false
  //     }
  //   }
  // }),

  on(actions.reorderColumns, (state, action) => {
    return {
      ...state,
      columns: action.columns
    }
  }),

  on(actions.loadMetaDataSuccess, (state, action) => {
    return {
      ...state,
      meta: action
    }
  }),

  on(actions.pageDestroyed, (state) => {
    return {
      ...state,
      customers: [],
      masterCustomers: []
    }
  }),

  on(actions.setSelectedServiceEquipment, (state, { serviceEquipment }) => ({ ...state, selectedServiceEquipment: serviceEquipment })),

  on(actions.saveServiceEquipmentSuccess, (state) => {
    return {
      ...state,
      selectedServiceEquipment: null
    }
  })
);
