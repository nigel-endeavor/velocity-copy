import { createReducer, on } from '@ngrx/store';
import * as actions from './location-worklist.actions';
import { SubjectInterface } from '../../../models/subject.model';
import { LookupValue } from '../../../models/lookup-value.model';
import { MultiEditFailureInterface } from '../../abstract-table/interfaces/multiEditFailure.interface';
import { LOCATION_WORKLIST_COMPONENTS } from '../data/location-worklist-columns.consts';
import { CommonColumn } from '../../../interfaces/columns.interface';
import { Company } from '../../../models/company.model';
import { ComparableDateRange } from '../../../interfaces/date-range.interface';
import { CommonDropdownSearchCriteria, CommonSearchCriteria } from '../../../interfaces/commonSearch.interface';


export const locationWorklistFeatureKey = 'locationWorklist';

export interface LocationWorklistState {
  filters: {
    search: string,
    companyName: string;
    provisioner: string[] | undefined;
    clientOrderId: string;
    clientLocationId: string;
    locationName: string;
    locationStatus: string | undefined;
    countServices: number[];
    countServicesComparison: any[];
    services: string | undefined;
    completionDate: ComparableDateRange;
    progress: number[];
    progressComparison: number[];
    openJeops: string;
    hideTerminalStatuses: boolean;
    macOnly: boolean;

    offset: number;
    limit: number;
    sortDir: string;
    sortField: string;
    format: string;
    fields: string;
    headers: string;
  };

  provisioners: SubjectInterface[];
  customers: Company[];
  customersParams: CommonSearchCriteria;
  endCustomers: Company[];
  endCustomerParams: CommonSearchCriteria;
  provisionersParams: CommonSearchCriteria;
  statuses: LookupValue[];
  isLoading: boolean;
  cardViewSelected: boolean;
  cantUpdate: MultiEditFailureInterface[];
  columns: CommonColumn[];
  updatedItem: any;
  jeopardyResponsibility: LookupValue[];
  serviceTypes: string[];
}

export const initialState: LocationWorklistState = {
  filters: {
    search: '',
    companyName: '',
    provisioner: undefined,
    clientOrderId: '',
    clientLocationId: '',
    locationName: '',
    locationStatus: undefined,
    countServices: [],
    countServicesComparison: [],
    services: undefined,
    // @ts-ignore
    completionDate: {
      isEmpty: false,
      dateRange: null
    },
    progress: [],
    progressComparison: [],
    openJeops: '',
    hideTerminalStatuses: true,
    macOnly: false,

    offset: 0,
    limit: 25,
    sortDir: '',
    sortField: '',
    format: '',
    fields: '',
    headers: ''
  },
  provisioners: [],
  customers: [],
  customersParams: {
    filters: {
      type: 'Master Customer',
      name: '',
      total: 0,
      limit: 50,
      offset: 0,
    },
    wasChanged: false
  },
  endCustomers: [],
  endCustomerParams: {
    filters: {
      type: 'End Customer',
      name: '',
      total: 0,
      limit: 50,
      offset: 0,
    },
    wasChanged: false
  },
  provisionersParams: {
    filters: {
      name: '',
      orderId: null,
      total: 0,
      limit: 50,
      offset: 0,
    },
    wasChanged: false
  },
  serviceTypes: [],
  statuses: [],
  cantUpdate: [],
  isLoading: false,
  cardViewSelected: false,
  columns: LOCATION_WORKLIST_COMPONENTS,
  updatedItem: {},
  jeopardyResponsibility: []
};

export const locationWorklistReducer = createReducer(
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

  on(actions.toggleView, (state) => {
    return {
      ...state,
      cardViewSelected: !state.cardViewSelected
    }
  }),

  on(actions.loadProvisionersSuccess, (state, action) => {
    return {
      ...state,
      provisioners: action.subjects
    }
  }),

  on(actions.updateParams, (state, action) => {
    return {
      ...state,
      customersParams: {
        filters: {
          ...state.customersParams.filters,
          [action.key]: action.value,
          ...(action.key !== "offset" && { offset: 0 })
        },
        wasChanged: action.key !== "offset" ,
      }
    }
  }),

  on(actions.updateEndCustomerParams, (state, action) => {
    return {
      ...state,
      endCustomerParams: {
        filters: {
          ...state.endCustomerParams.filters,
          [action.key]: action.value,
          ...(action.key !== "offset" && { offset: 0 })
        },
        wasChanged: action.key !== "offset" ,
      }
    }
  }),

  on(actions.updateProvisionersParams, (state, action) => {
    return {
      ...state,
      provisionersParams: {
        filters:{
        ...state.provisionersParams.filters,
        [action.key]: action.value,
        ...(action.key !== "offset" && { offset: 0 })},
        wasChanged: action.key !== "offset" ,
      }
    }
  }),

  on(actions.loadCustomersSuccess, (state, action) => {
    return {
      ...state,
      customers: state.customersParams.wasChanged || state.customersParams.filters.offset == 0 ? action.customers.collection : [...state.customers, ...action.customers.collection],
      customersParams: {
        filters: {
          ...state.customersParams.filters,
          limit: 50,
          total: action.customers.total,
          offset: action.customers.offset,
        },
        wasChanged: false
      }
    }
  }),

  on(actions.loadEndCustomersSuccess, (state, action) => {
    return {
      ...state,
      endCustomers: state.endCustomerParams.wasChanged || state.endCustomerParams.filters.offset == 0 ? action.endCustomers.collection : [...state.endCustomers, ...action.endCustomers.collection],
      endCustomerParams: {
        filters: {
          ...state.endCustomerParams.filters,
          limit: 50,
          total: action.endCustomers.total,
          offset: action.endCustomers.offset,
        },
        wasChanged: false
      }
    }
  }),

  on(actions.loadLookupValuesByKeySuccess, (state, action) => {
    return {
      ...state,
      [action.key]: action.values
    }
  }),

  on(actions.setUpdatedItem, (state, action) => {
    return {
      ...state,
      updatedItem: action.updatedItem
    }
  }),

  on(actions.toggleColumn, (state, action) => {
    const newColumns = [...state.columns].map(col => {
      return {
        ...col,
        hidden: col.propertyName === action.columnName ? !col.hidden : col.hidden
      }
    });
    return {
      ...state,
      columns: newColumns
    }
  }),

  on(actions.reorderColumns, (state, action) => {
    return {
      ...state,
      columns: action.columns
    }
  }),
    on(actions.loadServiceTypesSuccess, (state, action) => {
      return {
        ...state,
        serviceTypes: action.serviceTypes
      }
    }),
);

