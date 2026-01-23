import { createFeatureSelector, createSelector } from '@ngrx/store';

import * as reducer from './activation-worklist.reducer';
import { LookupValue } from '../../../models/lookup-value.model';
import {META_DATA_ORDER} from "../data/activation-worklist-columns.consts";
import { ActivationWorklistFilterKeys, ActivationWorklistOptionKeys } from '../data/activation-worklist.consts';
import { Company } from '../../../models/company.model';
import { initialState } from './activation-worklist.reducer';

export const selectActivationWorklistState
  = createFeatureSelector<reducer.ActivationWorklistState>(reducer.activationWorklistFeatureKey);

export const getIsLoading = createSelector(selectActivationWorklistState,
  (state: reducer.ActivationWorklistState) => {
  return state.isLoading
  }
);

export const getColumns = createSelector(selectActivationWorklistState,
  (state: reducer.ActivationWorklistState) => {
  return state.columns
  }
);

export const getFilters = createSelector(selectActivationWorklistState,
  (state: reducer.ActivationWorklistState) => {
  return state.filters
  }
);

export const getStatuses = createSelector(selectActivationWorklistState,
  (state: reducer.ActivationWorklistState) => {
  return state.statuses
  }
);

export const getStatusesValues = createSelector(getStatuses,
  (statuses: LookupValue[]) => {
  return statuses.map(item => item.value);
  }
);

export const getMetaData = createSelector(selectActivationWorklistState,
  (state: reducer.ActivationWorklistState) => {
    const metaCounts = state.meta?.statusCounts;
    const resultCounts: { status: string, count: number, color: string }[] = [];
    if (metaCounts) {
      META_DATA_ORDER.forEach(metaName => {
        metaCounts.forEach(item => {
          if (item.status === metaName.name) {
            resultCounts.push({
              ...item,
              color: metaName.color
            });
          }
        })
      })
    }

    return {
      ...state.meta,
      statusCounts: resultCounts.length ? resultCounts : state.meta?.statusCounts
    }
  }
);

export const getParamsByKey = (key: ActivationWorklistFilterKeys) => createSelector(
  selectActivationWorklistState, (state: reducer.ActivationWorklistState) => state[key].filters
);


export const getCustomersByKey = (key: ActivationWorklistOptionKeys) => createSelector(selectActivationWorklistState,
  (state: reducer.ActivationWorklistState) => {
    return state[key]
  }
);

export const getCustomersByKeyAsOptions = (key: ActivationWorklistOptionKeys) => createSelector(getCustomersByKey(key),
  (customers: Company[] | any[]) => {
    return customers.map(item => item.name);
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
        if (filters[element].length != initialState.filters[element].length) {
          //@ts-ignore
          appliedFilters.push({ name: element, value: filters[element], initialValue: initialState.filters[element] })
        }
      } else if (element == 'scheduledCheckInTime') {
        //@ts-ignore
        if (filters[element]['dateRange'] != null) {
          //@ts-ignore
          appliedFilters.push({ name: element, value: filters[element], initialValue: initialState.filters[element] })
        }
      } else if (filters[element] instanceof Object) {
        //@ts-ignore
        let obj = initialState.filters[element];
        if (obj && Object.keys(obj).length > 0) {
          Object.keys(obj).forEach((subElement: string) => {
            //@ts-ignore
            if (filters[element][subElement] != initialState.filters[element][subElement]) {
              //@ts-ignore
              appliedFilters.push({ name: element, value: filters[element], initialValue: initialState.filters[element] })
            }
          });
        }
      } else {
        //@ts-ignore
        if (filters[element] != initialState.filters[element]) {
          //@ts-ignore
          appliedFilters.push({ name: element, value: filters[element], initialValue: initialState.filters[element] })
        }
      }
    });
    return appliedFilters.filter(f => !!f.value);
  }
);


