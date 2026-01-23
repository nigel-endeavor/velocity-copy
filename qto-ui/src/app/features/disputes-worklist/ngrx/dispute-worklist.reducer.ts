import { createReducer, on } from '@ngrx/store';
import * as actions from './dispute-worklist.actions';
import { LookupValue } from '../../../models/lookup-value.model';
import { MultiEditFailureInterface } from '../../abstract-table/interfaces/multiEditFailure.interface';
import { DISPUTE_WORKLIST_COLUMNS } from '../data/dispute-worklist-columns.consts';
import { CommonColumn } from '../../../interfaces/columns.interface';
import { ComparableDateRange } from '../../../interfaces/date-range.interface';
import { Company } from '../../../models/company.model';
import { DisputeView } from '../../../models/dispute-view.model';
import { LevelOfEffort } from '../../../models/level-of-effort.model';
import { CommonSearchCriteria } from '../../../interfaces/commonSearch.interface';
import { SubjectInterface } from 'src/app/models/subject.model';

export const disputeWorklistFeatureKey = 'disputeWorklist';

export interface DisputeMeta {
  totalDisputeMrc: number;
  totalDisputeNrc: number;
}

export interface DisputeWorklistState {
  filters: {
    search: string;
    locationId: number[];
    locationIdComparison: any[];
    address: string;
    disputeStatus: string[];
    projectManager: string;
    provider: string | undefined;
    disputeType: string;
    disputeAssignment: string;
    clientLocationInfo: string;
    clientLocationType: string;
    disputeOpen: boolean;
    serviceBilledTo: string;
    openDate: ComparableDateRange;
    disputeFollowUpDate: ComparableDateRange;
    creditRecognized: ComparableDateRange;
    billingReviewCompleteDate: ComparableDateRange;
    disputeClosedDate: ComparableDateRange;
    companyName: string;
    statusAge: number[];
    statusAgeComparison: any[];
    providerCircuitId: string;
    serviceType: string[] | undefined;
    mrc: number[];
    mrcComparison: any[];
    nrc: number[];
    nrcComparison: any[];
    lconPhone: string;
    levelOfEffort: string[];

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
  disputeTypes: LookupValue[];
  disputeStatus: LookupValue[];
  clientManagers: LookupValue[];
  levelOfEffort: LevelOfEffort[];
  serviceJeopardy: LookupValue[];
  serviceBilledTo: LookupValue[];
  jeopardyResponsibility: LookupValue[];
  speed: LookupValue[];
  protocols: LookupValue[];
  customers: Company[];
  masterCustomers: Company[];
  isLoading: boolean;
  cardViewSelected: boolean;
  cantUpdate: MultiEditFailureInterface[];
  columns: CommonColumn[];
  updatedItem: any;
  selectedItems: DisputeView[];
  disputeAssignments: SubjectInterface[];

  masterCustomerSearchCriteria: CommonSearchCriteria;
  endCustomerSearchCriteria: CommonSearchCriteria;
  providersSearchCriteria: CommonSearchCriteria;
  disputeTypesSearchCriteria: CommonSearchCriteria;
  disputeStatusSearchCriteria: CommonSearchCriteria;
  disputeAssignmentSearchCriteria: CommonSearchCriteria;
  meta: DisputeMeta | null;
}

export const initialState: DisputeWorklistState = {
  filters: {
    search: '',
    locationId: [],
    locationIdComparison: [],
    address: '',
    disputeStatus: [],
    projectManager: '',
    provider: undefined,
    disputeType: '',
    clientLocationInfo: '',
    clientLocationType: '',
    serviceBilledTo: '',
    disputeOpen: true,
    // @ts-ignore
    openDate: {
      isEmpty: false,
      dateRange: null
    },
    // @ts-ignore
    disputeFollowUpDate: {
      isEmpty: false,
      dateRange: null
    },
    // @ts-ignore
    creditRecognized: {
      isEmpty: false,
      dateRange: null
    },
    // @ts-ignore
    billingReviewCompleteDate: {
      isEmpty: false,
      dateRange: null
    },
    // @ts-ignore
    disputeClosedDate: {
      isEmpty: false,
      dateRange: null
    },
    companyName: '',
    statusAge: [],
    statusAgeComparison: [],
    providerCircuitId: '',
    serviceType: undefined,
    mrc: [],
    mrcComparison: [],
    nrc: [],
    nrcComparison: [],
    lconPhone: '',

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
  disputeTypes: [],
  disputeAssignments: [],
  disputeStatus: [],
  clientManagers: [],
  serviceJeopardy: [],
  jeopardyResponsibility: [],
  speed: [],
  protocols: [],
  customers: [],
  serviceBilledTo: [],
  masterCustomers: [],
  cantUpdate: [],
  isLoading: false,
  cardViewSelected: false,
  columns: DISPUTE_WORKLIST_COLUMNS,
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
  disputeTypesSearchCriteria: {
    filters: {
      value: '',
      total: 0,
      limit: 50,
      offset: 0
    },
    wasChanged: false
  },
  disputeAssignmentSearchCriteria: {
    filters: {
      value: '',
      total: 0,
      limit: 50,
      offset: 0
    },
    wasChanged: false
  },
  disputeStatusSearchCriteria: {
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
  },
  meta: null
};

export const disputeWorklistReducer = createReducer(
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

  on(actions.pageDestroyed, (state) => {
    return {
      ...state,
      providers: [],
      disputeTypes: [],
      disputeAssignments: [],
      clientManagers: [],
      serviceJeopardy: [],
      serviceBilledTo: [],
      jeopardyResponsibility: [],
      speed: [],
      protocols: [],
      customers: [],
      masterCustomers: []
    }
  }),

  on(actions.loadDisputeAssignmentsSuccess, (state, action) => {
    return {
      ...state,
      disputeAssignments: action.disputeAssignments
    }
  }),
  on(actions.loadServiceTypesSuccess, (state, action) => {
    return {
      ...state,
      serviceTypes: action.serviceTypes
    }
  }),

);
