import { createFeatureSelector, createSelector } from '@ngrx/store';

import * as reducer from './disconnect-worklist.reducer';
import { LookupValue } from '../../../models/lookup-value.model';
import { SubjectInterface } from '../../../models/subject.model';
import { Company } from '../../../models/company.model';
import { DisconnectWorklistFilterKeys, DisconnectWorklistOptionsKeys } from '../data/disconnect-worklist.consts';
import { CommonDropdownSearchCriteria } from '../../../interfaces/commonSearch.interface';
import { DisconnectView } from '../../../models/disconnect-view.model';
import { initialState } from './disconnect-worklist.reducer';
import { selectServiceWorklistState } from "../../service-worklist/ngrx/service-worklist.selectors";

export const selectDisconnectWorklistState
  = createFeatureSelector<reducer.DisconnectWorklistState>(reducer.disconnectWorklistFeatureKey);

export const getIsLoading = createSelector(selectDisconnectWorklistState,
  (state: reducer.DisconnectWorklistState) => {
    return state.isLoading
  }
);

export const getColumns = createSelector(selectDisconnectWorklistState,
  (state: reducer.DisconnectWorklistState) => {
    return state.columns
  }
);

export const getFilters = createSelector(selectDisconnectWorklistState,
  (state: reducer.DisconnectWorklistState) => {
    return state.filters
  }
);

export const getProviders = createSelector(selectDisconnectWorklistState,
  (state: reducer.DisconnectWorklistState) => {
    return state.providers
  }
);

export const getProvidersValues = createSelector(getProviders,
  (statuses: LookupValue[]) => {
    return [
      'Empty',
      ...statuses.map(item => item.value)
    ]
  }
);

export const getProvisioners = createSelector(selectDisconnectWorklistState,
  (state: reducer.DisconnectWorklistState) => {
    return state.provisioners
  }
);

export const getProvisionersAsOptions = createSelector(getProvisioners,
  (provisioners: SubjectInterface[]) => {
    return ['Unassigned', ...provisioners.map(item => item.displayName)];
  }
);

export const getServiceBilledTo = createSelector(selectDisconnectWorklistState,
  (state: reducer.DisconnectWorklistState) => {
    return state.serviceBilledTo
  }
);

export const getServiceBilledToValues = createSelector(getServiceBilledTo,
  (statuses: LookupValue[]) => {
    return [
      'Empty',
      ...statuses.map(item => item.value)
    ]
  }
);

export const getServiceTypes = createSelector(selectDisconnectWorklistState,
  (state: reducer.DisconnectWorklistState) => {
    return state.serviceTypes;
  }
);

export const getDisconnectReasons = createSelector(selectDisconnectWorklistState,
  (state: reducer.DisconnectWorklistState) => {
    return state.disconnectReasons
  }
);

export const getDisconnectReasonValues = createSelector(getDisconnectReasons,
  (statuses: LookupValue[]) => {
    return [
      'Empty',
      ...statuses.map(item => item.value)
    ]
  }
);

export const getCardViewSelected = createSelector(selectDisconnectWorklistState,
  (state: reducer.DisconnectWorklistState) => {
    return state.cardViewSelected
  }
);

export const getSelectedItems = createSelector(selectDisconnectWorklistState,
  (state: reducer.DisconnectWorklistState) => {
    return state.selectedItems
  }
);

export const getSelectedItemsIds = createSelector(getSelectedItems,
  (selectedItems: DisconnectView[]) => {
    return selectedItems.map(item => item.id);
  }
);

export const getParamsByKey = (key: DisconnectWorklistFilterKeys) => createSelector(
  selectDisconnectWorklistState, (state: reducer.DisconnectWorklistState) => state[key].filters
);


export const getCustomersByKey = (key: DisconnectWorklistOptionsKeys) => createSelector(selectDisconnectWorklistState,
  (state: reducer.DisconnectWorklistState) => {
    return state[key]
  }
);

export const getCustomersByKeyAsOptions = (key: DisconnectWorklistOptionsKeys) => createSelector(getCustomersByKey(key),
  (customers: Company[] | any[]) => {
    return customers.map(item => item.name);
  }
);

export const getMultieditConfig = createSelector(
  getCustomersByKey('masterCustomers'),
  getProviders,
  getProvisioners,
  getDisconnectReasons,
  (
    masterCustomers: any[],
    providers: LookupValue[],
    provisioners: SubjectInterface[],
    disconnectReasons: LookupValue[]
  ) => {
    return {
      masterCustomer: masterCustomers,
      provider: providers,
      provisioner: provisioners,
      disconnectReason: disconnectReasons
    }
  }
);

export const getMultieditParamsConfig = createSelector(
  getParamsByKey('provisionersSearchCriteria'),
  getParamsByKey('masterCustomerSearchCriteria'),
  getParamsByKey('providersSearchCriteria'),
  getParamsByKey('disconnectReasonsSearchCriteria'),
  (
    provisionerParams: CommonDropdownSearchCriteria,
    masterCustomerParams: CommonDropdownSearchCriteria,
    providersParams: CommonDropdownSearchCriteria,
    disconnectReasonsParams: CommonDropdownSearchCriteria,
  ) => {
    return {
      provisioner: provisionerParams,
      masterCustomer: masterCustomerParams,
      provider: providersParams,
      disconnectReason: disconnectReasonsParams
    }
  }
);

export const getUpdatedItem = createSelector(selectDisconnectWorklistState,
  (state: reducer.DisconnectWorklistState) => {
    return state.updatedItem
  }
);

export const getMetaData = createSelector(selectDisconnectWorklistState,
  (state: reducer.DisconnectWorklistState) => {
    return {
      ...state.meta
    }
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

export const getProjectNames = createSelector(selectDisconnectWorklistState,
  (state: reducer.DisconnectWorklistState) => {
    return state.projectNames
  }
);
