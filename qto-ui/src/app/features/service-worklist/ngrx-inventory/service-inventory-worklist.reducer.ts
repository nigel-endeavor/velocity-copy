import { createReducer, on } from '@ngrx/store';
import * as actions from './service-inventory-worklist.actions';
import { SubjectInterface } from '../../../models/subject.model';
import { LookupValue } from '../../../models/lookup-value.model';
import { MultiEditFailureInterface } from '../../abstract-table/interfaces/multiEditFailure.interface';
import { CommonColumn } from '../../../interfaces/columns.interface';
import { Company } from '../../../models/company.model';
import { ComparableDateRange } from '../../../interfaces/date-range.interface';
import { SERVICE_INVENTORY_WORKLIST_COLUMNS } from '../data/service-inventory-worklist-columns.consts';
import { LevelOfEffort } from 'src/app/models/level-of-effort.model';
import { ServiceView } from 'src/app/models/service-view.model';
import { CommonSearchCriteria } from 'src/app/interfaces/commonSearch.interface';


export const serviceInventoryWorklistFeatureKey = 'serviceInventoryWorklist';

export interface ServiceInventoryMeta {
  mrc: number;
  mrr: number;
  nrr: number;
  openDisputeMrc: number;
  openDisputeNrc: number;
  annualRecurring: number;
  macdCount: number;
}

export interface ServiceInventoryWorklistState {
  filters: {
    sortDir: string;
    sortField: string;
    search: string;
    locationId: number[];
    locationIdComparison: any[];
    address: string;
    provider: string;
    accountNumber: string;
    summaryBill: number;
    providerCircuitId: string;
    speed: string;
    activeInactive: string;
    mrc: number[];
    mrcComparison: any[];
    nrc: number[];
    nrcComparison: any[];
    annualRecurringCost: number[];
    annualRecurringCostComparison: any[];
    contractSignedDate: ComparableDateRange;
    contractTerm: string;
    circuitTermEndDate: ComparableDateRange;
    inventoryAddedDate: ComparableDateRange;
    macdOpen: boolean;
    clientLocationInfo: string;
    clientLocationType: string;
    countOpenDisputes: number;
    disputeTypes: string;
    icbFlag: string;
    mrcDisputed: number;
    nrcDisputed: number;
    offset: number;
    serviceBilledTo: string;
  };

  providers: LookupValue[];
  disputeTypes: LookupValue[];
  contractTerms: LookupValue[];
  clientManagers: LookupValue[];
  levelOfEffort: LevelOfEffort[];
  serviceJeopardy: LookupValue[];
  serviceBilledTo: LookupValue[];
  jeopardyResponsibility: LookupValue[];
  speed: LookupValue[];
  protocols: LookupValue[];
  mediaTypes: LookupValue[];
  orderTypes: LookupValue[];
  subOrderTypes: LookupValue[];
  serviceTypes: string[];
  provisioners: SubjectInterface[];
  qaManagers: SubjectInterface[];
  customers: Company[];
  masterCustomers: Company[];
  isLoading: boolean;
  cardViewSelected: boolean;
  cantUpdate: MultiEditFailureInterface[];
  columns: CommonColumn[];
  updatedItem: any;
  selectedItems: ServiceView[];
  meta: ServiceInventoryMeta | null;

  masterCustomerSearchCriteria: CommonSearchCriteria;
  endCustomerSearchCriteria: CommonSearchCriteria;
  provisionersSearchCriteria: CommonSearchCriteria;
  providersSearchCriteria: CommonSearchCriteria;
  qaManagerSearchCriteria: CommonSearchCriteria;
}

export const initialState: ServiceInventoryWorklistState = {
  filters: {
    search: '',
    locationId: [],
    locationIdComparison: [],
    address: '',
    provider: '',
    accountNumber: '',
    summaryBill: 0,
    serviceBilledTo: '',
    providerCircuitId: '',
    speed: '',
    activeInactive: 'Active',
    mrc: [],
    mrcComparison: [],
    nrc: [],
    nrcComparison: [],
    annualRecurringCost: [],
    annualRecurringCostComparison: [],
    //@ts-ignore
    contractSignedDate: {
      isEmpty: false,
      dateRange: null
    },
    contractTerm: "",
    //@ts-ignore
    circuitTermEndDate: {
      isEmpty: false,
      dateRange: null
    },
    //@ts-ignore
    inventoryAddedDate: {
      isEmpty: false,
      dateRange: null
    },
    macdOpen: false,
    clientLocationInfo: '',
    clientLocationType: '',
    countOpenDisputes: 0,
    disputeTypes: '',
    icbFlag: '',
    mrcDisputed: 0,
    nrcDisputed: 0,

    offset: 0,
    limit: 25,
    sortDir: '',
    sortField: '',
    format: '',
    fields: '',
    headers: '',
  },
  serviceTypes: [],
  providers: [],
  contractTerms: [],
  disputeTypes: [],
  clientManagers: [],
  levelOfEffort: [],
  serviceJeopardy: [],
  jeopardyResponsibility: [],
  serviceBilledTo: [],
  speed: [],
  protocols: [],
  mediaTypes: [],
  provisioners: [],
  qaManagers: [],
  customers: [],
  masterCustomers: [],
  isLoading: false,
  cardViewSelected: false,
  cantUpdate: [],
  columns: SERVICE_INVENTORY_WORKLIST_COLUMNS,
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
  provisionersSearchCriteria: {
    filters: {
      name: '',
      total: 0,
      limit: 50,
      offset: 0
    },
    wasChanged: false
  },
  qaManagerSearchCriteria: {
    filters: {
      name: '',
      total: 0,
      limit: 50,
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
  }
}

export const serviceInventoryWorklistReducer = createReducer(
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

  on(actions.loadLevelOfEffortSuccess, (state, action) => {
    return {
      ...state,
      levelOfEffort: action.levelOfEffort
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
    actions.loadMetaDataSuccess, (state, action) => {
      return {
        ...state,
        meta: action
      }
    }
  ),

  on(actions.loadDropdownContentSuccess, (state, action) => {
    return {
      ...state,
      [action.optionKey]: state[action.filterKey].wasChanged || state[action.filterKey].filters.offset == 0 ? action.customers.collection : [...state[action.optionKey], ...action.customers.collection],
      [action.filterKey]: {
        filters: {
          ...state[action.filterKey].filters,
          limit: action.optionKey === 'masterCustomers' ? 10000 : 50,
          total: action.customers.total,
          offset: action.customers.offset
        },
        wasChanged: false
      }
    }
  }),

  on(actions.updateParams, (state, action) => {
    return {
      ...state,
      [action.filterKey]: {
        filters: {
          ...state[action.filterKey].filters,
          [action.key]: action.value,
          ...(action.key !== 'offset' && { offset: 0 })
        },
        wasChanged: action.key !== 'offset'
      }
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
