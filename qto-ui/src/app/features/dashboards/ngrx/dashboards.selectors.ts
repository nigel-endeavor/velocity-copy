import { createFeatureSelector, createSelector } from '@ngrx/store';

import * as reducer from './dashboards.reducer';
import { Company } from '../../../models/company.model';
import { DashboardFilterKeys, DashboardOptionsKeys, TabFilterKeys } from '../data/dashboards.consts';
import { LookupValue } from '../../../models/lookup-value.model';

export const selectDashboardsState
  = createFeatureSelector<reducer.DashboardsState>(reducer.dashboardsFeatureKey);

export const getParamsByKey = (key: DashboardFilterKeys) => createSelector(
  selectDashboardsState, (state: reducer.DashboardsState) => state[key].filters
);

export const getTabFiltersByKey = (key: TabFilterKeys) => createSelector(
  selectDashboardsState, (state: reducer.DashboardsState) => {
    return state.tabFilters[key]
  }
);


export const getCustomersByKey = (key: DashboardOptionsKeys) => createSelector(selectDashboardsState,
  (state: reducer.DashboardsState) => {
    return state[key]
  }
);

export const getServiceBilledTo = createSelector(selectDashboardsState,
  (state: reducer.DashboardsState) => state.serviceBilledTo
);

export const getServiceBilledToValues = createSelector(getServiceBilledTo,
  (values: LookupValue[]) => {
  return [
    'Empty',
      ...values.map(item => item.value)
    ]
  }
);

export const getProviders = createSelector(selectDashboardsState,
  (state: reducer.DashboardsState) => state.providers
);

export const getProvidersValues = createSelector(getProviders,
  (values: LookupValue[]) => {
  return [
    'Empty',
      ...values.map(item => item.value)
    ]
  }
);

export const getFilters = createSelector(selectDashboardsState,
  (state: reducer.DashboardsState) => state.filters
);

export const getSelectedProviders = createSelector(selectDashboardsState,
  (state: reducer.DashboardsState) => {
    return state.providers
  }
);

export const getCustomersByKeyAsOptions = (key: DashboardOptionsKeys) => createSelector(getCustomersByKey(key),
  (customers: Company[] | any[]) => {
    return customers.map(item => item.name);
  }
);

export const getWipServiceViews = createSelector(selectDashboardsState,
  (state: reducer.DashboardsState) => {
    return state.wipServiceViews
  }
);

export const getAllWipServiceViews = createSelector(selectDashboardsState,
  (state: reducer.DashboardsState) => {
    return state.wipAllServiceViews
  }
);

export const getWipServiceJeops = createSelector(selectDashboardsState,
  (state: reducer.DashboardsState) => {
    return state.wipServiceJeopViews
  }
);

export const getWipLocationJeops = createSelector(selectDashboardsState,
  (state: reducer.DashboardsState) => {
    return state.wipLocationJeopViews
  }
);

export const getInstallIntervals = createSelector(selectDashboardsState,
  (state: reducer.DashboardsState) => {
    return state.installIntervals
  }
);

export const getSurveyIntervals = createSelector(selectDashboardsState,
  (state: reducer.DashboardsState) => {
    return state.surveyIntervals
  }
);

export const getProviderReliance = createSelector(selectDashboardsState,
  (state: reducer.DashboardsState) => {
    return state.providerReliance
  }
);

export const getMonthlySpend = createSelector(selectDashboardsState,
  (state: reducer.DashboardsState) => {
    return state.monthlySpend
  }
);

export const getUnbillableNetworkExpenseAccrual = createSelector(selectDashboardsState,
  (state: reducer.DashboardsState) => {
    return state.unbillableNetworkExpenseAccrual
  }
);

export const getIncrementalNetwork = createSelector(selectDashboardsState,
  (state: reducer.DashboardsState) => {
    return state.incrementalNetwork
  }
);

export const getServiceTypes = createSelector(selectDashboardsState,
  (state: reducer.DashboardsState) => {
    return state.serviceTypes;
  }
);

export const getServiceIntervals = createSelector(selectDashboardsState,
  (state: reducer.DashboardsState) => {
    return state.serviceIntervals
  }
);

export const getAverages = createSelector(selectDashboardsState,
  (state: reducer.DashboardsState) => {
    return state.averages
  }
);

export const getIsLoading = createSelector(selectDashboardsState,
  (state: reducer.DashboardsState) => {
    return state.isLoading
  }
);

export const getInventoryValuation = createSelector(selectDashboardsState,
  (state: reducer.DashboardsState) => {
    return state.inventoryValuation
  }
);

export const getInventoryCounts = createSelector(selectDashboardsState,
  (state: reducer.DashboardsState) => {
    return state.inventoryCounts
  }
);

export const getNewInventory = createSelector(selectDashboardsState,
  (state: reducer.DashboardsState) => {
    return state.newInventory
  }
);

export const getAppliedFilters = createSelector(getFilters,
  (filters: any) => {
    let appliedFilters: any[] = [];
    Object.keys(filters)
    //@ts-ignore
    .filter(f => f != 'offset' && f != 'limit')
    .forEach((element: string) => {
      if (filters[element] instanceof Array) {
        //@ts-ignore
        if (filters[element].length != reducer.initialState.filters[element].length) {
          //@ts-ignore
          appliedFilters.push({ name: element, value: filters[element], initialValue: reducer.initialState.filters[element] })
        }
      } else if (filters[element] instanceof Object) {
        //@ts-ignore
        let obj = reducer.initialState.filters[element];
        if (obj && Object.keys(obj).length > 0) {
          Object.keys(obj).forEach((subElement: string) => {
            //@ts-ignore
            if (filters[element][subElement] != reducer.initialState.filters[element][subElement]) {
              //@ts-ignore
              appliedFilters.push({ name: element, value: filters[element], initialValue: reducer.initialState.filters[element] })
            }
          });
        }
      } else {
        //@ts-ignore
        if (filters[element] != reducer.initialState.filters[element]) {
          //@ts-ignore
          appliedFilters.push({ name: element, value: filters[element], initialValue: reducer.initialState.filters[element] })
        }
      }
    });
    return appliedFilters.filter(f => !!f.value);
  }
);