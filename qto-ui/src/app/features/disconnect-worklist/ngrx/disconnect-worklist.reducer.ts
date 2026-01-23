import { createReducer, on } from '@ngrx/store';
import * as actions from './disconnect-worklist.actions';
import { LookupValue } from '../../../models/lookup-value.model';
import { MultiEditFailureInterface } from '../../abstract-table/interfaces/multiEditFailure.interface';
import { DISCONNECT_WORKLIST_COLUMNS } from '../data/disconnect-worklist-columns.consts';
import { CommonColumn } from '../../../interfaces/columns.interface';
import { ComparableDateRange } from '../../../interfaces/date-range.interface';
import { Company } from '../../../models/company.model';
import { LevelOfEffort } from '../../../models/level-of-effort.model';
import { CommonSearchCriteria } from '../../../interfaces/commonSearch.interface';
import { DisconnectView } from '../../../models/disconnect-view.model';
import { SubjectInterface } from '../../../models/subject.model';

export const disconnectWorklistFeatureKey = 'disconnectWorklist';

export interface DisconnectMeta {
  totalMrc: number;
  totalEarlyTerminationFee: number;
  totalMrr: number;
}

export interface DisconnectWorklistState {
  filters: {
    search: string;
    locationId: number[];
    locationIdComparison: any[];
    clientLocationId: string;
    companyName: string;
    serviceType: string[] | undefined;
    address: string;
    provisioner: string[] | undefined;
    disconnectReason: string[];
    status: string;
    provider: string | undefined;
    providerOrderSubmitted: ComparableDateRange;
    providerOrderNumber: string;
    customerRequestedDisconnect: ComparableDateRange;
    networkProviderFoc: ComparableDateRange;
    complete: ComparableDateRange;
    created: ComparableDateRange;
    statusAge: number[];
    mrc: number[];
    mrcComparison: any[];
    earlyTerminationFee: number[];
    earlyTerminationFeeComparison: any[];
    latestNote: string;
    serviceBilledTo: any;
    billingReviewComplete: ComparableDateRange;

    offset: number;
    limit: number;
    sortDir: string;
    sortField: string;
    format: string;
    fields: string;
    headers: string;
  };

  providers: LookupValue[];
  serviceTypes: string[];
  projectNames: LevelOfEffort[];
  disconnectReasons: LookupValue[];
  serviceBilledTo: LookupValue[];
  provisioners: SubjectInterface[];
  customers: Company[];
  masterCustomers: Company[];
  isLoading: boolean;
  cardViewSelected: boolean;
  cantUpdate: MultiEditFailureInterface[];
  columns: CommonColumn[];
  updatedItem: any;
  selectedItems: DisconnectView[];

  masterCustomerSearchCriteria: CommonSearchCriteria;
  endCustomerSearchCriteria: CommonSearchCriteria;
  providersSearchCriteria: CommonSearchCriteria;
  disconnectReasonsSearchCriteria: CommonSearchCriteria;
  provisionersSearchCriteria: CommonSearchCriteria;
  meta: DisconnectMeta | null;
}

export const initialState: DisconnectWorklistState = {
  filters: {
    search: '',
    locationId: [],
    locationIdComparison: [],
    companyName: '',
    serviceType: undefined,
    address: '',
    provisioner: undefined,
    disconnectReason: [],
    status: '',
    serviceBilledTo: '',
    provider: undefined,
    // @ts-ignore
    providerOrderSubmitted: {
      isEmpty: false,
      dateRange: null
    },
    providerOrderNumber: '',
    // @ts-ignore
    customerRequestedDisconnect: {
      isEmpty: false,
      dateRange: null
    },
    // @ts-ignore
    networkProviderFoc: {
      isEmpty: false,
      dateRange: null
    },
    // @ts-ignore
    complete: {
      isEmpty: false,
      dateRange: null
    },
    // @ts-ignore
    created: {
      isEmpty: false,
      dateRange: null
    },
    statusAge: [],
    mrc: [],
    mrcComparison: [],
    earlyTerminationFee: [],
    earlyTerminationFeeComparison: [],
    latestNote: '',
    // @ts-ignore
    billingReviewComplete: {
      isEmpty: false,
      dateRange: null
    },
    pendingDisconnect: true,

    offset: 0,
    limit: 25,
    sortDir: '',
    sortField: '',
    format: '',
    fields: '',
    headers: ''
  },
  serviceTypes: [],
  providers: [],
  projectNames: [],
  disconnectReasons: [],
  provisioners: [],
  customers: [],
  masterCustomers: [],
  serviceBilledTo: [],
  cantUpdate: [],
  isLoading: false,
  cardViewSelected: false,
  columns: DISCONNECT_WORKLIST_COLUMNS,
  updatedItem: {},
  selectedItems: [],

  masterCustomerSearchCriteria: {
    filters: {
      type: 'Master Customer',
      name: '',
      tenants: '',
      total: 0,
      limit: 10000,
      offset: 0
    },
    wasChanged: false
  },
  providersSearchCriteria: {
    filters: {
      value: '',
      total: 0,
      limit: 50,
      offset: 0
    },
    wasChanged: false
  },
  disconnectReasonsSearchCriteria: {
    filters: {
      value: '',
      total: 0,
      limit: 50,
      offset: 0
    },
    wasChanged: false
  },
  provisionersSearchCriteria: {
    filters: {
      name: '',
      total: 0,
      limit: 50,
      offset: 0
    },
    wasChanged: false
  },
  endCustomerSearchCriteria: {
    filters: {
      type: 'End Customer',
      name: '',
      tenants: '',
      total: 0,
      limit: 50,
      offset: 0
    },
    wasChanged: false
  },
  meta: null
};

export const disconnectWorklistReducer = createReducer(
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

  on(actions.updateParams, (state, action) => {
    return {
      ...state,
      [action.filterKey]: {
        filters: {
          ...state[action.filterKey].filters,
          [action.key]: action.value,
          ...(action.key !== "offset" && { offset: 0 })
        },
        wasChanged: action.key !== "offset"
      }
    }
  }),

  on(actions.loadDropdownContentSuccess, (state, action) => {
    return {
      ...state,
      [action.optionKey]: state[action.filterKey].wasChanged || state[action.filterKey].filters.offset == 0 ? action.options.collection : [...state[action.optionKey], ...action.options.collection],
      [action.filterKey]: {
        filters: {
          ...state[action.filterKey].filters,
          limit: 50,
          total: action.options.total,
          offset: action.options.offset
        },
        wasChanged: false
      }
    }
  }),

  on(actions.addSelectedItems, (state, action) => {
    const selectedIds = state.selectedItems.map(item => item.id)
    const newlySelected = action.value.filter(item => !selectedIds.includes(item.id));
    return {
      ...state,
      selectedItems: [
        ...state.selectedItems,
        ...newlySelected
      ]
    }
  }),

  on(actions.removeUnselectedItems, (state, action) => {
    const removedIds = action.value?.map(item => item.id) || [];
    const filteredItems = state.selectedItems.filter(item => !removedIds.includes(item.id));
    return {
      ...state,
      selectedItems: [
        ...filteredItems
      ]
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

  on(actions.loadProvisionersSuccess, (state, action) => {
    return {
      ...state,
      provisioners: action.subjects,
      provisionersSearchCriteria: {
        ...state.provisionersSearchCriteria,
        limit: 50,
        // TODO update this after BE is ready
        total: 10000,
        offset: 0
      }
    }
  }),

  on(actions.pageDestroyed, (state) => {
    return {
      ...state,
      disconnectReasons: [],
      providers: [],
      provisioners: [],
      customers: [],
      masterCustomers: []
    }
  }),
  on(actions.loadServiceTypesSuccess, (state, action) => {
    return {
      ...state,
      serviceTypes: action.serviceTypes
    }
  }),

);
