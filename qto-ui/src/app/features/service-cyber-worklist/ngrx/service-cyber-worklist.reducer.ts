import { DateSearchCriteriaField } from "../../../models/base-search-criteria.model";
import { ComparableDateRange } from "../../../interfaces/date-range.interface";
import { LookupValue } from "../../../models/lookup-value.model";
import { SubjectInterface } from "../../../models/subject.model";
import { Company } from "../../../models/company.model";
import { MultiEditFailureInterface } from "../../abstract-table/interfaces/multiEditFailure.interface";
import { CommonColumn } from "../../../interfaces/columns.interface";
import { ServiceCyberView } from "../../../models/service-cyber-view.model";
import { CommonSearchCriteria } from "../../../interfaces/commonSearch.interface";
import { createReducer, on } from "@ngrx/store";
import { SERVICE_CYBER_WORKLIST_COLUMNS } from "../data/service-cyber-worklist-columns.const";
import * as actions from './service-cyber-worklist.actions';

export const serviceCyberWorklistFeatureKey = 'serviceCyberWorklist';

export interface ServiceCyberWorklistState {
  filters: {
    search: string;
    locationId: number[];
    locationIdComparison: any[];
    clientLocationId: string;
    parentCompanyName: string;
    companyName: string;
    serviceType: string[] | undefined;
    address: string;
    status: string;
    followUpDate: ComparableDateRange;
    provisioner: string[] | undefined;
    i90ProjectManager: string[] | undefined;
    provider: string | undefined;
    statusAge: number[];
    statusAgeComparison: any[];
    mrc: number[];
    mrcComparison: any[];
    nrc: number[];
    nrcComparison: any[];
    equipmentTypes: string;
    equipmentCount: number;
    equipmentCountRange: any[];
    customerRequestedInstall: ComparableDateRange;
    created: ComparableDateRange;
    techDataGatheringFormSent: ComparableDateRange;
    techDataGatheringMeetingScheduled: ComparableDateRange;
    techDataGatheringMeetingCompleted: ComparableDateRange;
    emailUsmAnywhereTemplateRequirementsSent: ComparableDateRange;
    inventoryAssignmentVerified: ComparableDateRange;
    newUsmAnywhereServerBuildStarted: ComparableDateRange;
    implementationQaCompleted: ComparableDateRange;
    verifyAssetsInSiemDb: ComparableDateRange;
    verifyLoggingDataSource: ComparableDateRange;
    scheduleVulnerabilityScans: ComparableDateRange;
    bulkAlarmTuningPhase1: ComparableDateRange;
    siemEventFiltering: ComparableDateRange;
    filtersBuiltForReports: ComparableDateRange;
    defaultAlarmRuleAdditions: ComparableDateRange;
    customAlarmRuleAdditions: ComparableDateRange;
    forwardAlarmsToUsmCentral: ComparableDateRange;
    forwardAlarmsToD3SocLive: ComparableDateRange;
    latestNote: string;
    hideTerminalStatuses: boolean;
    macOnly: boolean;
    hostListProvidedByClient: ComparableDateRange;
    halcyonPackageGivenToClient: ComparableDateRange;
    halcyonDeployedToHosts: ComparableDateRange;
    devopsNotifiedOfHalcyonAddition: ComparableDateRange;
    halcyonApiTokenAddedToD3: ComparableDateRange;
    d3ConnectionVerified: ComparableDateRange;
    endLearningMode: ComparableDateRange;
    testEmailSentToClient: ComparableDateRange;
    deployConsultingTenant: ComparableDateRange;
    deployVulnerabilityScans: ComparableDateRange;
    providedCustomerWithReport: ComparableDateRange;
    discussFutureCyrismaManagement: ComparableDateRange;
    reviewExistingCaAndMfaPolicies: ComparableDateRange;
    signInPoliciesEnabled: ComparableDateRange;
    conditionalAccessPolicyVerification: ComparableDateRange;
    geographicRestrictionsEnabled: ComparableDateRange;
    deviceComplianceEnabled: ComparableDateRange;
    passwordResetEnabledForSelfService: ComparableDateRange;
    breakGlassAccountConfigured: ComparableDateRange;
    pimEnablement: ComparableDateRange;
    implementationVerified: ComparableDateRange;

    offset: number;
    limit: number;
    sortDir: string;
    sortField: string;
    format: string;
    fields: string;
    headers: string;
  };

  providers: LookupValue[];
  serviceTypes: LookupValue[];
  provisioners: SubjectInterface[];
  i90ProjectManagers: SubjectInterface[];
  customers: Company[];
  masterCustomers: Company[];
  isLoading: boolean;
  cardViewSelected: boolean;
  cantUpdate: MultiEditFailureInterface[];
  columns: CommonColumn[];
  updatedItem: any;
  selectedItems: ServiceCyberView[];

  masterCustomerSearchCriteria: CommonSearchCriteria;
  endCustomerSearchCriteria: CommonSearchCriteria;
  providersSearchCriteria: CommonSearchCriteria;
  provisionersSearchCriteria: CommonSearchCriteria;
  i90ProjectManagersSearchCriteria: CommonSearchCriteria;
}

export const initialState: ServiceCyberWorklistState = {
  filters: {
    search: '',
    locationId: [],
    locationIdComparison: [],
    companyName: '',
    serviceType: undefined,
    address: '',
    status: '',
    // @ts-ignore
    followUpDate: {
      isEmpty: false,
      dateRange: null
    },
    provisioner: undefined,
    i90ProjectManager: undefined,
    provider: undefined,
    statusAge: [],
    mrc: [],
    mrcComparison: [],
    nrc: [],
    nrcComparison: [],
    // @ts-ignore
    customerRequestedInstall: {
      isEmpty: false,
      dateRange: null
    },
    // @ts-ignore
    created: {
      isEmpty: false,
      dateRange: null
    },
    // @ts-ignore
    techDataGatheringFormSent: {
      isEmpty: false,
      dateRange: null
    },
    // @ts-ignore
    techDataGatheringMeetingScheduled: {
      isEmpty: false,
      dateRange: null
    },
    // @ts-ignore
    techDataGatheringMeetingCompleted: {
      isEmpty: false,
      dateRange: null
    },
    // @ts-ignore
    emailUsmAnywhereTemplateRequirementsSent: {
      isEmpty: false,
      dateRange: null
    },
    // @ts-ignore
    inventoryAssignmentVerified: {
      isEmpty: false,
      dateRange: null
    },
    // @ts-ignore
    newUsmAnywhereServerBuildStarted: {
      isEmpty: false,
      dateRange: null
    },
    // @ts-ignore
    implementationQaCompleted: {
      isEmpty: false,
      dateRange: null
    },
    // @ts-ignore
    verifyAssetsInSiemDb: {
      isEmpty: false,
      dateRange: null
    },
    // @ts-ignore
    verifyLoggingDataSource: {
      isEmpty: false,
      dateRange: null
    },
    // @ts-ignore
    scheduleVulnerabilityScans: {
      isEmpty: false,
      dateRange: null
    },
    // @ts-ignore
    bulkAlarmTuningPhase1: {
      isEmpty: false,
      dateRange: null
    },
    // @ts-ignore
    siemEventFiltering: {
      isEmpty: false,
      dateRange: null
    },
    // @ts-ignore
    filtersBuiltForReports: {
      isEmpty: false,
      dateRange: null
    },
    // @ts-ignore
    defaultAlarmRuleAdditions: {
      isEmpty: false,
      dateRange: null
    },
    // @ts-ignore
    customAlarmRuleAdditions: {
      isEmpty: false,
      dateRange: null
    },
    // @ts-ignore
    forwardAlarmsToUsmCentral: {
      isEmpty: false,
      dateRange: null
    },
    // @ts-ignore
    forwardAlarmsToD3SocLive: {
      isEmpty: false,
      dateRange: null
    },
    hideTerminalStatuses: true,
    macOnly: false,
    // @ts-ignore
    hostListProvidedByClient: {
      isEmpty: false,
      dateRange: null
    },
    // @ts-ignore
    halcyonPackageGivenToClient: {
      isEmpty: false,
      dateRange: null
    },
    // @ts-ignore
    halcyonDeployedToHosts: {
      isEmpty: false,
      dateRange: null
    },
    // @ts-ignore
    devopsNotifiedOfHalcyonAddition: {
      isEmpty: false,
      dateRange: null
    },
    // @ts-ignore
    halcyonApiTokenAddedToD3: {
      isEmpty: false,
      dateRange: null
    },
    // @ts-ignore
    d3ConnectionVerified: {
      isEmpty: false,
      dateRange: null
    },
    // @ts-ignore
    endLearningMode: {
      isEmpty: false,
      dateRange: null
    },
    // @ts-ignore
    testEmailSentToClient: {
      isEmpty: false,
      dateRange: null
    },
    // @ts-ignore
    deployConsultingTenant: {
      isEmpty: false,
      dateRange: null
    },
    // @ts-ignore
    deployVulnerabilityScans: {
      isEmpty: false,
      dateRange: null
    },
    // @ts-ignore
    providedCustomerWithReport: {
      isEmpty: false,
      dateRange: null
    },
    // @ts-ignore
    discussFutureCyrismaManagement: {
      isEmpty: false,
      dateRange: null
    },
    // @ts-ignore
    reviewExistingCaAndMfaPolicies: {
      isEmpty: false,
      dateRange: null
    },
    // @ts-ignore
    signInPoliciesEnabled: {
      isEmpty: false,
      dateRange: null
    },
    // @ts-ignore
    conditionalAccessPolicyVerification: {
      isEmpty: false,
      dateRange: null
    },
    // @ts-ignore
    geographicRestrictionsEnabled: {
      isEmpty: false,
      dateRange: null
    },
    // @ts-ignore
    deviceComplianceEnabled: {
      isEmpty: false,
      dateRange: null
    },
    // @ts-ignore
    passwordResetEnabledForSelfService: {
      isEmpty: false,
      dateRange: null
    },
    // @ts-ignore
    breakGlassAccountConfigured: {
      isEmpty: false,
      dateRange: null
    },
    // @ts-ignore
    pimEnablement: {
      isEmpty: false,
      dateRange: null
    },
    // @ts-ignore
    implementationVerified: {
      isEmpty: false,
      dateRange: null
    },

    offset: 0,
    limit: 25,
    sortDir: '',
    sortField: '',
    format: '',
    fields: '',
    headers: ''
  },
  providers: [],
  provisioners: [],
  i90ProjectManagers: [],
  customers: [],
  masterCustomers: [],
  cantUpdate: [],
  isLoading: false,
  cardViewSelected: false,
  columns: SERVICE_CYBER_WORKLIST_COLUMNS,
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
  i90ProjectManagersSearchCriteria: {
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

export const serviceCyberWorklistReducer = createReducer(
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

  on(actions.loadi90ProjectManagersSuccess, (state, action) => {
    return {
      ...state,
      i90ProjectManagers: action.subjects,
      i90ProjectManagersSearchCriteria: {
        ...state.i90ProjectManagersSearchCriteria,
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
      providers: [],
      provisioners: [],
      i90ProjectManagers: [],
      customers: [],
      masterCustomers: []
    }
  })
);

