import { ActionReducer } from '@ngrx/store';
import { localStorageSync } from 'ngrx-store-localstorage';
import { DemoStoreState, demoStoreFeatureKey } from '../../features/demo-mode/demo-store.reducer';
import {
  TableDeprecatedState,
  tableDeprecatedStoreFeatureKey
} from '../../components/abstract-table/store/table-deprecated.reducer';
import {
  MultiEditState,
  multieEditFeatureKey,
} from '../../features/multi-edit/store/multie-edit.reducer';
import {
  locationWorklistFeatureKey,
  LocationWorklistState,
  initialState as LocationWorklistInitialState
} from '../../features/locations-worklist/ngrx/location-worklist.reducer';
import {
  locationInventoryWorklistFeatureKey,
  LocationInventoryWorklistState,
  initialState as LocationInventoryWorklistInitialState
} from '../../features/locations-worklist/ngrx-inventory/location-inventory-worklist.reducer';
import {
  activationWorklistFeatureKey,
  ActivationWorklistState,
  initialState as ActivationWorklistInitialState
} from '../../features/activation-worklist/ngrx/activation-worklist.reducer';
import {
  serviceWorklistFeatureKey,
  ServiceWorklistState,
  initialState as ServiceWorklistInitialState
} from '../../features/service-worklist/ngrx/service-worklist.reducer';
import {
  serviceCyberWorklistFeatureKey,
  ServiceCyberWorklistState,
  initialState as ServiceCyberWorklistInitialState
} from "../../features/service-cyber-worklist/ngrx/service-cyber-worklist.reducer";
import {
  serviceInventoryWorklistFeatureKey,
  ServiceInventoryWorklistState,
  initialState as ServiceInventoryWorklistInitialState
} from '../../features/service-worklist/ngrx-inventory/service-inventory-worklist.reducer';
import {
  disputeWorklistFeatureKey,
  DisputeWorklistState,
  initialState as DisputeWorklistInitialState
} from '../../features/disputes-worklist/ngrx/dispute-worklist.reducer';
import {
  disconnectWorklistFeatureKey,
  DisconnectWorklistState,
  initialState as DisconnectWorklistInitialState
} from 'src/app/features/disconnect-worklist/ngrx/disconnect-worklist.reducer';
import {
  dashboardsFeatureKey,
  DashboardsState,
  initialState as DashboardInitialState
} from '../../features/dashboards/ngrx/dashboards.reducer';
import {
  masterCustomersWorklistFeatureKey,
  MasterCustomersWorklistState,
  initialState as MasterCustomersWorklistInitialState
} from '../../features/master-customers-worklist/ngrx/master-customers-worklist.reducer';
import {
  endCustomersWorklistFeatureKey,
  EndCustomersWorklistState,
  initialState as EndCustomersWorklistInitialState
} from '../../features/end-customers-worklist/ngrx/end-customers-worklist.reducer';

import { SERVICE_COLUMNS } from '../../features/service-worklist/data/services-columns.consts';
import { ACTIVATION_WORKLIST_COMPONENTS } from '../../features/activation-worklist/data/activation-worklist-columns.consts';
import { LOCATION_WORKLIST_COMPONENTS } from '../../features/locations-worklist/data/location-worklist-columns.consts';
import { CommonColumn } from '../../interfaces/columns.interface';
import * as dayjs from 'dayjs';
import * as utc from 'dayjs/plugin/utc';
import { LOCATION_INVENTORY_WORKLIST_COLUMNS } from 'src/app/features/locations-worklist/data/location-inventory-worklist-columns.consts';
import { SERVICE_INVENTORY_WORKLIST_COLUMNS } from 'src/app/features/service-worklist/data/service-inventory-worklist-columns.consts';
import { DISCONNECT_WORKLIST_COLUMNS } from 'src/app/features/disconnect-worklist/data/disconnect-worklist-columns.consts';
import { MASTER_CUSTOMERS_WORKLIST_COLUMNS } from '../../features/master-customers-worklist/data/master-customers-worklist-columns.consts';
import { END_CUSTOMERS_WORKLIST_COLUMNS } from '../../features/end-customers-worklist/data/end-customers-worklist-columns.consts';
import { SERVICE_CYBER_WORKLIST_COLUMNS}  from "../../features/service-cyber-worklist/data/service-cyber-worklist-columns.const";
import { DISPUTE_WORKLIST_COLUMNS} from "../../features/disputes-worklist/data/dispute-worklist-columns.consts";

dayjs.extend(utc)

let _syncEnabled = true;

export function setSyncEnabled(enabled: boolean) {
  _syncEnabled = enabled;
}

const resetNewColumns = (newColumns: CommonColumn[], oldColumns: CommonColumn[]) => {
  return newColumns.map(item => {
    const newColumn = {
      ...item
    }
    oldColumns.some(subItem => {
      if (subItem.name === item.name) {
        newColumn.hidden = subItem.hidden;
        return;
      }
    });
    return newColumn
  });
}

const resetDateByCohort = (cohort: string) => {
  const ranges: Record<string, dayjs.Dayjs[]> = {
    'Today': [dayjs().utc().local(), dayjs().utc().local()],
    'Yesterday': [dayjs().utc().local().subtract(1, 'days'), dayjs().utc().local().subtract(1, 'days')],
    'Last 7 Days': [dayjs().utc().local().subtract(6, 'days'), dayjs().utc().local()],
    'Last 30 Days': [dayjs().subtract(29, 'days').utc().local(), dayjs().utc().local()],
    'This Month': [dayjs().utc().local().startOf('month'), dayjs().utc().local().endOf('month')],
    'Last Month': [dayjs().utc().local().subtract(1, 'month').startOf('month'), dayjs().utc().local().subtract(1, 'month').endOf('month')],
  }

  return ranges[cohort]
}

export const deserializeDemoConfigState = (
  state: DemoStoreState,
): DemoStoreState => {
  return {
    ...state,
  };
};

export const deserializeMultieEditState = (
  state: MultiEditState,
): MultiEditState => {
  return {
    ...state,
  };
};

export const deserializeAbstractTableState = (
  state: TableDeprecatedState,
): TableDeprecatedState => {
  return {
    ...state,
    isLoading: false,
    cantUpdate: []
  };
};

export const deserializeLocationWorklistState = (
  state: LocationWorklistState,
): LocationWorklistState => {
  let columns = state.columns;
  if (columns.length != LOCATION_WORKLIST_COMPONENTS.length) {
    columns = resetNewColumns(LOCATION_WORKLIST_COMPONENTS, columns);
  }
  return {
    ...LocationWorklistInitialState,
    filters: {
      ...LocationWorklistInitialState.filters,
      ...state.filters,
      offset: 0
    },
    columns: columns,
    cardViewSelected: state.cardViewSelected
  };
};

export const deserializeLocationInventoryWorklistState = (
  state: LocationInventoryWorklistState,
): LocationInventoryWorklistState => {
  let columns = state.columns;
  if (columns.length != LOCATION_INVENTORY_WORKLIST_COLUMNS.length) {
    columns = resetNewColumns(LOCATION_INVENTORY_WORKLIST_COLUMNS, columns);
  }
  return {
    ...LocationInventoryWorklistInitialState,
    filters: {
      ...LocationInventoryWorklistInitialState.filters,
      ...state.filters,
      offset: 0
    },
    columns: columns,
    cardViewSelected: state.cardViewSelected
  };
};

export const deserializeServiceInventoryWorklistState = (
  state: ServiceInventoryWorklistState,
): ServiceInventoryWorklistState => {
  let columns = state.columns;
  if (columns.length != SERVICE_INVENTORY_WORKLIST_COLUMNS.length) {
    columns = resetNewColumns(SERVICE_INVENTORY_WORKLIST_COLUMNS, columns);
  }
  return {
    ...ServiceInventoryWorklistInitialState,
    filters: {
      ...ServiceInventoryWorklistInitialState.filters,
      ...state.filters,
      offset: 0
    },
    columns: columns,
    cardViewSelected: state.cardViewSelected
  };
};


export const deserializeActivationWorklistState = (
  state: ActivationWorklistState,
): ActivationWorklistState => {
  let columns = state.columns;
  if (columns.length != ACTIVATION_WORKLIST_COMPONENTS.length) {
    columns = resetNewColumns(ACTIVATION_WORKLIST_COMPONENTS, columns);
  }

  if (state.filters.scheduledCheckInTime.dateCohort) {
    const dateRange = resetDateByCohort(state.filters.scheduledCheckInTime.dateCohort) || ['', ''];
    state.filters.scheduledCheckInTime.dateRange = state.filters.scheduledCheckInTime.isEmpty ? null : {
      startDate: dateRange[0]?.format('YYYY-MM-DD').toString(),
      endDate: dateRange[1]?.format('YYYY-MM-DD').toString()
    }
  }

  return {
    ...ActivationWorklistInitialState,
    filters: {
      ...ActivationWorklistInitialState.filters,
      ...state.filters,
      offset: 0
    },
    columns
  };
};

export const deserializeServiceWorklistInitialState = (
  state: ServiceWorklistState,
): ServiceWorklistState => {
  let columns = state.columns;
  if (columns.length != SERVICE_COLUMNS.length) {
    columns = resetNewColumns(SERVICE_COLUMNS, columns);
  }
  return {
    ...ServiceWorklistInitialState,
    filters: {
      ...ServiceWorklistInitialState.filters,
      ...state.filters,
      offset: 0
    },
    columns: columns,
    cardViewSelected: state.cardViewSelected
  };
};

export const deserializeDisputeWorklistInitialState = (
  state: DisputeWorklistState,
): DisputeWorklistState => {
  let columns = state.columns;
  if (columns.length != DISPUTE_WORKLIST_COLUMNS.length) {
    columns = resetNewColumns(DISPUTE_WORKLIST_COLUMNS, columns);
  }
  return {
    ...DisputeWorklistInitialState,
    filters: {
      ...DisputeWorklistInitialState.filters,
      ...state.filters,
      offset: 0
    },
    columns: columns,
    cardViewSelected: state.cardViewSelected
  };
}

export const deserializeServiceCyberWorklistInitialState = (
  state: ServiceCyberWorklistState,
): ServiceCyberWorklistState => {
  let columns = state.columns;
  if (columns.length != SERVICE_CYBER_WORKLIST_COLUMNS.length) {
    columns = resetNewColumns(SERVICE_CYBER_WORKLIST_COLUMNS, columns);
  }
  return {
    ...ServiceCyberWorklistInitialState,
    filters: {
      ...ServiceCyberWorklistInitialState.filters,
      ...state.filters,
      offset: 0
    },
    columns: columns,
    cardViewSelected: state.cardViewSelected
  };
};

export const deserializeMasterCustomersWorklistInitialState = (
  state: MasterCustomersWorklistState,
): MasterCustomersWorklistState => {
  let columns = state.columns;
  if (columns.length != MASTER_CUSTOMERS_WORKLIST_COLUMNS.length) {
    columns = resetNewColumns(MASTER_CUSTOMERS_WORKLIST_COLUMNS, columns);
  }
  return {
    ...MasterCustomersWorklistInitialState,
    filters: {
      ...MasterCustomersWorklistInitialState.filters,
      ...state.filters,
      offset: 0
    },
    columns: columns,
    cardViewSelected: state.cardViewSelected
  };
};

export const deserializeEndCustomersWorklistInitialState = (
  state: EndCustomersWorklistState,
): EndCustomersWorklistState => {
  let columns = state.columns;
  if (columns.length != END_CUSTOMERS_WORKLIST_COLUMNS.length) {
    columns = resetNewColumns(END_CUSTOMERS_WORKLIST_COLUMNS, columns);
  }
  return {
    ...EndCustomersWorklistInitialState,
    filters: {
      ...EndCustomersWorklistInitialState.filters,
      ...state.filters,
      offset: 0
    },
    columns: columns,
    cardViewSelected: state.cardViewSelected
  };
};

export const deserializeDiconnectWorklistInitialState = (
  state: DisconnectWorklistState,
): DisconnectWorklistState => {
  let columns = state.columns;
  if (columns.length != DISCONNECT_WORKLIST_COLUMNS.length) {
    columns = resetNewColumns(DISCONNECT_WORKLIST_COLUMNS, columns);
  }
  return {
    ...DisconnectWorklistInitialState,
    filters: {
      ...DisconnectWorklistInitialState.filters,
      ...state.filters,
      offset: 0
    },
    columns: columns,
    cardViewSelected: state.cardViewSelected
  };
};


export const serializeDashboardStore = (state: DashboardsState): DashboardsState => {

  return {
    ...state,
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
  };
};

export const deserializeDashboardInitialState = (
  state: DashboardsState,
): DashboardsState => {

  return {
    ...DashboardInitialState,
    filters: state.filters,
    masterCustomerSearchCriteria: state.masterCustomerSearchCriteria,
    endCustomerSearchCriteria: state.endCustomerSearchCriteria
  };
};

export function syncLocalStorage(reducer: ActionReducer<any>): ActionReducer<any> {
  if (_syncEnabled) {
    return localStorageSync({
      keys: [
        { [tableDeprecatedStoreFeatureKey]: { deserialize: deserializeAbstractTableState } },
        { [demoStoreFeatureKey]: { deserialize: deserializeDemoConfigState } },
        { [multieEditFeatureKey]: { deserialize: deserializeMultieEditState } },
        { [locationWorklistFeatureKey]: { deserialize: deserializeLocationWorklistState } },
        { [locationInventoryWorklistFeatureKey]: { deserialize: deserializeLocationInventoryWorklistState } },
        { [serviceInventoryWorklistFeatureKey]: { deserialize: deserializeServiceInventoryWorklistState } },
        { [activationWorklistFeatureKey]: { deserialize: deserializeActivationWorklistState } },
        { [serviceWorklistFeatureKey]: { deserialize: deserializeServiceWorklistInitialState } },
        { [serviceCyberWorklistFeatureKey]: { deserialize: deserializeServiceCyberWorklistInitialState } },
        { [masterCustomersWorklistFeatureKey]: { deserialize: deserializeMasterCustomersWorklistInitialState } },
        { [endCustomersWorklistFeatureKey]: { deserialize: deserializeEndCustomersWorklistInitialState } },
        { [disconnectWorklistFeatureKey]: { deserialize: deserializeDiconnectWorklistInitialState } },
        { [dashboardsFeatureKey]: { deserialize: deserializeDashboardInitialState, serialize: serializeDashboardStore } },
        { [disputeWorklistFeatureKey]: { deserialize: deserializeDisputeWorklistInitialState } },
      ],
      rehydrate: true,
    })(reducer);
  } else {
    return reducer;
  }
}
