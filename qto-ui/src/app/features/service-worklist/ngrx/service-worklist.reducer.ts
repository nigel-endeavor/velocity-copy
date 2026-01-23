import { createReducer, on } from '@ngrx/store';
import * as actions from './service-worklist.actions';
import { LookupValue } from '../../../models/lookup-value.model';
import { MultiEditFailureInterface } from '../../abstract-table/interfaces/multiEditFailure.interface';
import { SERVICE_COLUMNS } from '../data/services-columns.consts';
import { CommonColumn } from '../../../interfaces/columns.interface';
import { ComparableDateRange } from '../../../interfaces/date-range.interface';
import { SubjectInterface } from '../../../models/subject.model';
import { Company } from '../../../models/company.model';
import { ServiceView } from '../../../models/service-view.model';
import { LevelOfEffort } from '../../../models/level-of-effort.model';
import { CommonSearchCriteria } from '../../../interfaces/commonSearch.interface';
import { FilterBuilder, SERVICE_WORKLIST_VIEWS } from '../data/worklist-views.consts';

export const serviceWorklistFeatureKey = 'serviceWorklist';

export interface ServiceWorklistState {
  filters: {
    search: string;
    locationId: number[];
    locationIdComparison: any[];
    address: string;
    status: string;
    provisioner: string[] | undefined;
    projectManager: string;
    provider: string | undefined;
    customerRequestedInstall: ComparableDateRange;
    siteSurveyDue: ComparableDateRange;
    siteSurveySubmit: ComparableDateRange;
    providerOrderSubmitted: ComparableDateRange;
    networkProviderFoc: ComparableDateRange;
    dataProvisioningComplete: ComparableDateRange;
    followUpDate: ComparableDateRange;
    qaCheckOpen: ComparableDateRange;
    firstVendorInvoice: ComparableDateRange;
    returnedToOrderGroup: ComparableDateRange;
    returnedToSales: ComparableDateRange;
    billingReviewComplete: ComparableDateRange;
    clientLocationInfo: string;
    clientLocationType: string;
    hideTerminalStatuses: boolean;
    macOnly: boolean;
    created: ComparableDateRange;
    companyName: string;
    statusAge: number[];
    statusAgeComparison: any[];
    providerCircuitId: string;
    serviceType: string[] | undefined;
    serviceBilledTo: string[];
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
    filterBuilder: any
  };

  providers: LookupValue[];
  clientManagers: LookupValue[];
  levelOfEffort: LevelOfEffort[];
  serviceJeopardy: LookupValue[];
  serviceBilledTo: LookupValue[];
  serviceTypes: string[];
  jeopardyResponsibility: LookupValue[];
  speed: LookupValue[];
  protocols: LookupValue[];
  mediaTypes: LookupValue[];
  projectNames: LookupValue[];
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

  masterCustomerSearchCriteria: CommonSearchCriteria;
  endCustomerSearchCriteria: CommonSearchCriteria;
  provisionersSearchCriteria: CommonSearchCriteria;
  qaManagerSearchCriteria: CommonSearchCriteria;
  providersSearchCriteria: CommonSearchCriteria;

  filterBuilderOptions: Record<string, {
    name: string,
    filterBuilder: FilterBuilder,
    sortings: {
      sortDir: string,
      sortField: string
    }
  }>
}

export const initialState: ServiceWorklistState = {
  filters: {
    search: '',
    locationId: [],
    locationIdComparison: [],
    address: '',
    status: '',
    provisioner: undefined,
    projectManager: '',
    provider: undefined,
    // @ts-ignore
    customerRequestedInstall: {
      isEmpty: false,
      dateRange: null
    },
    // @ts-ignore
    siteSurveyDue: {
      isEmpty: false,
      dateRange: null
    },
    // @ts-ignore
    siteSurveySubmit: {
      isEmpty: false,
      dateRange: null
    },
    // @ts-ignore
    providerOrderSubmitted: {
      isEmpty: false,
      dateRange: null
    },
    // @ts-ignore
    networkProviderFoc: {
      isEmpty: false,
      dateRange: null
    },
    // @ts-ignore
    dataProvisioningComplete: {
      isEmpty: false,
      dateRange: null
    },
    // @ts-ignore
    followUpDate: {
      isEmpty: false,
      dateRange: null
    },
    // @ts-ignore
    qaCheckOpen: {
      isEmpty: false,
      dateRange: null
    },
    // @ts-ignore
    firstVendorInvoice: {
      isEmpty: false,
      dateRange: null
    },
    // @ts-ignore
    returnedToOrderGroup: {
      isEmpty: false,
      dateRange: null
    },
    // @ts-ignore
    returnedToSales: {
      isEmpty: false,
      dateRange: null
    },
    // @ts-ignore
    billingReviewComplete: {
      isEmpty: false,
      dateRange: null
    },
    clientLocationInfo: '',
    clientLocationType: '',
    hideTerminalStatuses: true,
    macOnly: false,
    // @ts-ignore
    created: {
      isEmpty: false,
      dateRange: null
    },
    companyName: '',
    statusAge: [],
    statusAgeComparison: [],
    providerCircuitId: '',
    serviceType: undefined,
    serviceBilledTo: [],
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
    headers: '',
    filterBuilder: null
  },
  serviceTypes: [],
  providers: [],
  clientManagers: [],
  serviceJeopardy: [],
  jeopardyResponsibility: [],
  serviceBilledTo: [],
  speed: [],
  protocols: [],
  mediaTypes: [],
  projectNames: [],
  customers: [],
  masterCustomers: [],
  provisioners: [],
  qaManagers: [],
  cantUpdate: [],
  isLoading: false,
  cardViewSelected: false,
  columns: SERVICE_COLUMNS,
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
  },

  filterBuilderOptions: SERVICE_WORKLIST_VIEWS
};

export const serviceWorklistReducer = createReducer(
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

  on(actions.setFilterBuilder, (state, action) => {
    const chosenOption = Object.keys(state.filterBuilderOptions).find(key => state.filterBuilderOptions[key].name === action.fbName);
    return {
      ...state,
      filters: {
        ...state.filters,
        filterBuilder: chosenOption ? state.filterBuilderOptions[chosenOption].filterBuilder : null,
        sortField: chosenOption ? state.filterBuilderOptions[chosenOption].sortings.sortField : state.filters.sortField,
        sortDir: chosenOption ? state.filterBuilderOptions[chosenOption].sortings.sortDir : state.filters.sortDir,
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

  on(actions.loadQaManagersSuccess, (state, action) => {
    return {
      ...state,
      qaManagers: action.subjects,
      qaManagerSearchCriteria: {
        ...state.qaManagerSearchCriteria,
        limit: 50,
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
    on(actions.loadServiceTypesSuccess, (state, action) => {
      return {
        ...state,
        serviceTypes: action.serviceTypes
      }
    }),
);
