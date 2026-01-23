import { createReducer, on } from '@ngrx/store';
import * as actions from './dashboards.actions';
import { Company } from '../../../models/company.model';
import { CommonSearchCriteria } from '../../../interfaces/commonSearch.interface';
import { WipServiceView } from '../../../models/wip-service-view.model';
import { WipServiceJeopView } from '../../../models/wip-service-jeop-view.model';
import { ProviderIntervalsView } from '../../../models/provider-intervals-view.model';
import { ProvidersMap } from '../data/dashboards.consts';
import { ActivationAttemptView } from "../../../models/activation-attempt-view-model";
import { WipLocationJeopView } from '../../../models/wip-location-jeop-view.model';
import { LookupValue } from '../../../models/lookup-value.model';
import { DashboardDataset } from 'src/app/models/dashboard-dataset.model';

export const dashboardsFeatureKey = 'dashboardsState';

export interface DashboardsState {
  filters: {
    selectedTenants: string | null,
    selectedMasterCompanies: string,
    selectedEndCompanies: string,
    selectedServiceTypes: string,
    selectedProviders: string,
    selectedServiceBilledTos: string;
  },

  tabFilters: {
    wip: {
      allStatuses: boolean;
    },
    providers: {
      provider: {
        allTime: boolean;
      },
      survey: {
        allTime: false
      },
      reliance: {
        allTime: boolean;
      },
    },
    financials: {
      monthlySpend: {
        limit: number
      },
      unbillableNetwork: {
        selectedProviders: string
      },
      incrementalNetwork: {
        selectedProviders: string
      }
    },
    inventory: {
      inventoryValuation: {
        numOfMonths: number
      },
      inventoryCounts: {
        numOfMonths: number
      },
      newInventory: {
        numOfMonths: number
      }
    }
  }

  serviceBilledTo: LookupValue[];
  serviceTypes: string[];
  providers: LookupValue[];
  providersSearchCriteria: CommonSearchCriteria;

  masterCustomers: Company[];
  masterCustomerSearchCriteria: CommonSearchCriteria;

  endCustomers: Company[];
  endCustomerSearchCriteria: CommonSearchCriteria;

  wipServiceViews: WipServiceView[];
  wipAllServiceViews: WipServiceView[];
  wipServiceJeopViews: WipServiceJeopView[];
  wipLocationJeopViews: WipLocationJeopView[];

  installIntervals: ProviderIntervalsView[];
  surveyIntervals: ProviderIntervalsView[];
  providerReliance: WipServiceView[];

  monthlySpend: WipServiceView[];
  unbillableNetworkExpenseAccrual: WipServiceView[];
  incrementalNetwork: WipServiceView[];

  serviceIntervals: ActivationAttemptView[];
  receivedToCompleteIntervals: ProviderIntervalsView[];
  receivedToDataProvisioningCompleteIntervals: ProviderIntervalsView[];

  averages: {
    cdiArranged: (string[] | number[])[],
    npiArranged: (string[] | number[])[]
  }

  inventoryValuation: DashboardDataset;
  inventoryCounts: DashboardDataset;
  newInventory: DashboardDataset;

  isLoading: boolean;
}

export const initialState: DashboardsState = {
  filters: {
    selectedTenants: null,
    selectedMasterCompanies: '',
    selectedEndCompanies: '',
    selectedServiceTypes: '',
    selectedProviders: '',
    selectedServiceBilledTos: ''
  },

  tabFilters: {
    wip: {
      allStatuses: false
    },
    providers: {
      provider: {
        allTime: false
      },
      survey: {
        allTime: false
      },
      reliance: {
        allTime: false
      },
    },
    financials: {
      monthlySpend: {
        limit: 10
      },
      unbillableNetwork: {
        selectedProviders: ''
      },
      incrementalNetwork: {
        selectedProviders: ''
      }
    },
    inventory: {
      inventoryValuation: {
        numOfMonths: 6
      },
      inventoryCounts: {
        numOfMonths: 6
      },
      newInventory: {
        numOfMonths: 6
      }
    }
  },
  serviceBilledTo: [],
  serviceTypes: [],
  providers: [],
  providersSearchCriteria: {
    filters: {
      value: '',
      total: 0,
      limit: 50,
      offset: 0
    },
    wasChanged: false
  },

  masterCustomers: [],
  masterCustomerSearchCriteria: {
    filters: {
      type: 'Master Customer',
      name: '',
      tenants: '',
      total: 0,
      limit: 50,
      offset: 0
    },
    wasChanged: false
  },

  endCustomers: [],
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

  wipServiceViews: [],
  wipAllServiceViews: [],
  wipServiceJeopViews: [],
  wipLocationJeopViews: [],

  installIntervals: [],
  surveyIntervals: [],
  providerReliance: [],
  monthlySpend: [],
  unbillableNetworkExpenseAccrual: [],
  incrementalNetwork: [],
  serviceIntervals: [],
  receivedToCompleteIntervals: [],
  receivedToDataProvisioningCompleteIntervals: [],

  averages: {
    cdiArranged: [],
    npiArranged: []
  },

  //@ts-ignore
  inventoryValuation: {
    labels: [],
    data: []
  },
  //@ts-ignore
  inventoryCounts: {
    labels: [],
    data: []
  },
  //@ts-ignore
  newInventory: {
    labels: [],
    data: []
  },

  isLoading: false
};

export const dashboardsReducer = createReducer(
  initialState,

  on(actions.updateCustomerParams, (state, action) => {
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

  on(actions.loadCustomersSuccess, (state, action) => {
    return {
      ...state,
      [action.optionKey]: state[action.filterKey].wasChanged || action.clear ? action.customers.collection : [...state[action.optionKey], ...action.customers.collection],
      [action.filterKey]: {
        filters: {
          ...state[action.filterKey].filters,
          limit: 50,
          total: action.customers.total,
          offset: action.customers.offset
        },
        wasChanged: false
      }
    }
  }),

  on(actions.loadAllWipServices, (state, action) => {
    return {
      ...state,
      isLoading: true
    }
  }),

  on(actions.loadWipServicesSuccess, (state, action) => {
    return {
      ...state,
      wipServiceViews: action.wipServiceViews
    }
  }),

  on(actions.loadAllWipServicesSuccess, (state, action) => {
    return {
      ...state,
      wipAllServiceViews: action.wipAllServiceViews,
      isLoading: false
    }
  }),

  on(actions.loadWipServiceJeopsSuccess, (state, action) => {
    return {
      ...state,
      wipServiceJeopViews: action.wipServiceJeopViews
    }
  }),

  on(actions.loadWipLocationJeopsSuccess, (state, action) => {
    return {
      ...state,
      wipLocationJeopViews: action.wipLocationJeopViews
    }
  }),

  on(actions.loadProviderReliance, (state, action) => {
    return {
      ...state,
      isLoading: true
    }
  }),

  on(actions.loadProviderRelianceSuccess, (state, action) => {
    return {
      ...state,
      providerReliance: action.providerReliance,
      isLoading: false
    }
  }),

  on(actions.loadMonthlySpend, (state, action) => {
    return {
      ...state,
      isLoading: true
    }
  }),

  on(actions.loadMonthlySpendSuccess, (state, action) => {
    return {
      ...state,
      monthlySpend: action.wipServiceViews,
      isLoading: false
    }
  }),

  on(actions.loadUnbillableNetworkExpenseAccrual, (state, action) => {
    return {
      ...state,
      isLoading: true
    }
  }),

  on(actions.loadUnbillableNetworkExpenseAccrualSuccess, (state, action) => {
    return {
      ...state,
      unbillableNetworkExpenseAccrual: action.wipServiceViews,
      isLoading: false
    }
  }),

  on(actions.loadIncrementalNetworkSpend, (state, action) => {
    return {
      ...state,
      isLoading: true
    }
  }),

  on(actions.loadIncrementalNetworkSpendSuccess, (state, action) => {
    return {
      ...state,
      incrementalNetwork: action.wipServiceViews,
      isLoading: false
    }
  }),

  on(actions.loadServiceIntervals, (state, action) => {
    return {
      ...state,
      isLoading: true
    }
  }),

  on(actions.loadServiceIntervalsSuccess, (state, action) => {
    return {
      ...state,
      serviceIntervals: action.activationAttempt,
      isLoading: false
    }
  }),

  on(actions.loadProviderAverages, (state, action) => {
    return {
      ...state,
      isLoading: true
    }
  }),

  on(actions.loadProviderAveragesSuccess, (state, action) => {
    return {
      ...state,
      averages: {
        cdiArranged: action.cdiArranged,
        npiArranged: action.npiArranged
      },
      isLoading: false
    }
  }),

  on(actions.loadProviderInstallIntervals, (state, action) => {
    return {
      ...state,
      isLoading: true
    }
  }),

  on(actions.loadProviderInstallIntervalsSuccess, (state, action) => {
    return {
      ...state,
      installIntervals: action.provInstallIntervals,
      isLoading: false
    }
  }),

  on(actions.loadProviderSurveyIntervals, (state, action) => {
    return {
      ...state,
      isLoading: true
    }
  }),

  on(actions.loadProviderSurveyIntervalsSuccess, (state, action) => {
    return {
      ...state,
      surveyIntervals: action.provSurveyIntervals,
      isLoading: false
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


  on(actions.updateTabFilters, (state, action) => {
    return {
      ...state,
      tabFilters: {
        ...state.tabFilters,
        [action.tabKey]: {
          ...state.tabFilters[action.tabKey],
          [action.key]: action.value
        }
      }
    }
  }),

  on(actions.clearFilters, (state, action) => {
    return {
      ...state,
      filters: initialState.filters
    }
  }),

  on(actions.loadDropdownContentSuccess, (state, action) => {
    return {
      ...state,
      [action.optionKey]: state[action.filterKey].wasChanged ? action.options.collection : [...state[action.optionKey], ...action.options.collection],
      [action.filterKey]: {
        filters: {
          ...state[action.filterKey].filters,
          limit: action.optionKey === 'masterCustomers' ? 10000 : 50,
          total: action.options.total,
          offset: action.options.offset
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
  on(actions.loadInventoryValuation, (state, action) => {
    return {
      ...state,
      isLoading: true
    }
  }),

  on(actions.loadInventoryValuationSuccess, (state, action) => {
    return {
      ...state,
      inventoryValuation: action.data,
      isLoading: false
    }
  }),

  on(actions.loadInventoryCounts, (state, action) => {
    return {
      ...state,
      isLoading: true
    }
  }),

  on(actions.loadInventoryCountsSuccess, (state, action) => {
    return {
      ...state,
      inventoryCounts: action.data,
      isLoading: false
    }
  }),

  on(actions.loadNewInventory, (state, action) => {
    return {
      ...state,
      isLoading: true
    }
  }),

  on(actions.loadNewInventorySuccess, (state, action) => {
    return {
      ...state,
      newInventory: action.data,
      isLoading: false
    }
  }),

  on(actions.loadServiceTypesSuccess, (state, action) => {
    return {
      ...state,
      serviceTypes: action.serviceTypes
    }
  }),

);
