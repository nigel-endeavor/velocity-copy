import { createReducer, on } from '@ngrx/store';
import * as actions from './location-inventory-worklist.actions';
import { SubjectInterface } from '../../../models/subject.model';
import { LookupValue } from '../../../models/lookup-value.model';
import { MultiEditFailureInterface } from '../../abstract-table/interfaces/multiEditFailure.interface';
import { CommonColumn } from '../../../interfaces/columns.interface';
import { Company } from '../../../models/company.model';
import { ComparableDateRange } from '../../../interfaces/date-range.interface';
import { CommonSearchCriteria } from '../../../interfaces/commonSearch.interface';
import { LOCATION_INVENTORY_WORKLIST_COLUMNS } from '../data/location-inventory-worklist-columns.consts';

export const locationInventoryWorklistFeatureKey = 'locationInventoryWorklist';

export interface LocationInventoryMeta {
  mrc: number;
  mrr: number;
  nrr: number;
  openDisputeMrc: number;
  openDisputeNrc: number;
  macdCount: number;
}

export interface LocationInventoryWorklistState {
  filters: {
    search: string,
    companyName: string[];
    provisioner: string[];
    clientOrderId: string;
    clientLocationId: string;
    locationName: string;
    locationStatus: string;
    countServices: number[];
    countServicesComparison: any[];
    services: string | undefined;
    completionDate: ComparableDateRange;
    inventoryAddedDate: ComparableDateRange;
    progress: number[];
    progressComparison: number[];
    openJeops: string;
    macdOpen: boolean;
    parentCompanyName: string[];
    disputeOpen: boolean;
    activeInactive: string;
    offset: number;
    limit: number;
    sortDir: string;
    sortField: string;
    format: string;
    fields: string;
    headers: string;
    subOrderTypes: string[];
  };

  provisioners: SubjectInterface[];
  customers: Company[];
  parentCompanyName: Company[];
  companyName: Company[];
  companyNameParams: CommonSearchCriteria,
  customersParams: CommonSearchCriteria,
  provisionersParams: CommonSearchCriteria,
  statuses: LookupValue[];
  isLoading: boolean;
  cardViewSelected: boolean;
  cantUpdate: MultiEditFailureInterface[];
  columns: CommonColumn[];
  updatedItem: any;
  meta: LocationInventoryMeta | null;
  subOrderTypes: LookupValue[];
  serviceTypes: any;
}

export const initialState: LocationInventoryWorklistState = {
  filters: {
    search: '',
    companyName: [],
    parentCompanyName: [],
    provisioner: [],
    clientOrderId: '',
    clientLocationId: '',
    locationName: '',
    locationStatus: '',
    countServices: [],
    countServicesComparison: [],
    services: undefined,
    // @ts-ignore
    completionDate: {
      isEmpty: false,
      dateRange: null
    },
    // @ts-ignore
    inventoryAddedDate: {
      isEmpty: false,
      dateRange: null
    },
    progress: [],
    progressComparison: [],
    openJeops: '',
    macdOpen: false,
    disputeOpen: false,
    activeInactive: "Active",
    offset: 0,
    limit: 25,
    sortDir: '',
    sortField: '',
    format: '',
    fields: '',
    headers: '',
    subOrderTypes: [],
  },
  serviceTypes: [],
  provisioners: [],
  customers: [],
  parentCompanyName: [],
  companyName: [],
  companyNameParams: {
    filters: {
      type: 'End Customer',
      name: '',
      total: 0,
      limit: 50,
      offset: 0,
    },
    wasChanged: false
  },
  customersParams: {
    filters: {
      type: 'Master Customer',
      name: '',
      total: 0,
      limit: 10000,
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
  statuses: [],
  cantUpdate: [],
  isLoading: false,
  cardViewSelected: false,
  columns: LOCATION_INVENTORY_WORKLIST_COLUMNS,
  updatedItem: {},
  subOrderTypes: []
};

export const locationInventoryWorklistReducer = createReducer(
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
        wasChanged: action.key !== "offset",
      }
    }
  }),

  on(actions.updateCompanyNameParams, (state, action) => {
    return {
      ...state,
      companyNameParams: {
        filters: {
          ...state.companyNameParams.filters,
          [action.key]: action.value,
          ...(action.key !== "offset" && { offset: 0 })
        },
        wasChanged: action.key !== "offset",
      }
    }
  }),

  on(actions.updateProvisionersParams, (state, action) => {
    return {
      ...state,
      provisionersParams: {
        filters: {
          ...state.provisionersParams.filters,
          [action.key]: action.value,
          ...(action.key !== "offset" && { offset: 0 })
        },
        wasChanged: action.key !== "offset",
      }
    }
  }),

  on(actions.loadCompanyNameSuccess, (state, action) => {
    return {
      ...state,
      companyName: state.companyNameParams.wasChanged ? action.companyName.collection : [...state.companyName, ...action.companyName.collection],
      companyNameParams: {
        filters: {
          ...state.companyNameParams.filters,
          limit: 50,
          total: action.companyName.total,
          offset: action.companyName.offset,
        },
        wasChanged: false
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

  on(actions.loadMetaDataSuccess, (state, action) => {
    return {
      ...state,
      meta: action
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

