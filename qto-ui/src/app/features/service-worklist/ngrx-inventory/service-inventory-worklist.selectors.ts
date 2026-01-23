import { createFeatureSelector, createSelector } from '@ngrx/store';

import * as reducer from './service-inventory-worklist.reducer';
import { LookupValue } from '../../../models/lookup-value.model';
import { SubjectInterface } from '../../../models/subject.model';
import { ServiceView } from 'src/app/models/service-view.model';
import { ServiceWorklistFilterKeys, ServiceWorklistOptionsKeys } from '../data/services-worklist.consts';
import { Company } from 'src/app/models/company.model';
import { initialState } from './service-inventory-worklist.reducer';
import { CommonDropdownSearchCriteria } from 'src/app/interfaces/commonSearch.interface';

export const selectServiceInventoryWorklistState
  = createFeatureSelector<reducer.ServiceInventoryWorklistState>(reducer.serviceInventoryWorklistFeatureKey);

export const getIsLoading = createSelector(selectServiceInventoryWorklistState,
  (state: reducer.ServiceInventoryWorklistState) => {
  return state.isLoading
  }
);

export const getColumns = createSelector(selectServiceInventoryWorklistState,
  (state: reducer.ServiceInventoryWorklistState) => {
  return state.columns
  }
);

export const getFilters = createSelector(selectServiceInventoryWorklistState,
  (state: reducer.ServiceInventoryWorklistState) => {
  return state.filters
  }
);

export const getProviders = createSelector(selectServiceInventoryWorklistState,
  (state: reducer.ServiceInventoryWorklistState) => {
  return state.providers
  }
);

export const getProvidersValues = createSelector(getProviders,
  (statuses: LookupValue[]) => {
  return statuses.map(item => item.value);
  }
);

export const getContractTerms = createSelector(selectServiceInventoryWorklistState,
  (state: reducer.ServiceInventoryWorklistState) => {
  return state.contractTerms
  }
);

export const getContractTermValues = createSelector(getContractTerms,
  (statuses: LookupValue[]) => {
  return statuses.map(item => item.value);
  }
);

export const getDisputeTypes = createSelector(selectServiceInventoryWorklistState,
  (state: reducer.ServiceInventoryWorklistState) => {
  return state.disputeTypes
  }
);

export const getDisputeTypeValues = createSelector(getDisputeTypes,
  (statuses: LookupValue[]) => {
    return statuses.map(item => item.value);
  }
);


export const getServiceBilledTo = createSelector(selectServiceInventoryWorklistState,
  (state: reducer.ServiceInventoryWorklistState) => {
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
export const getCardViewSelected = createSelector(selectServiceInventoryWorklistState,
  (state: reducer.ServiceInventoryWorklistState) => {
    return state.cardViewSelected
  }
);

export const getSelectedItems = createSelector(selectServiceInventoryWorklistState,
  (state: reducer.ServiceInventoryWorklistState) => {
    return state ? state.selectedItems : [];
  }
);

export const getSelectedItemsIds = createSelector(getSelectedItems,
  (selectedItems: ServiceView[]) => {
    return selectedItems.map(item => item.id);
  }
);

export const getClientManagers = createSelector(selectServiceInventoryWorklistState,
  (state: reducer.ServiceInventoryWorklistState) => {
    return state.clientManagers
  }
);

export const getLevelOfEffort = createSelector(selectServiceInventoryWorklistState,
  (state: reducer.ServiceInventoryWorklistState) => {
    return state.levelOfEffort
  }
);

export const getServiceJeopardy = createSelector(selectServiceInventoryWorklistState,
  (state: reducer.ServiceInventoryWorklistState) => {
    return state.serviceJeopardy
  }
);

export const getJeopResponsibility = createSelector(selectServiceInventoryWorklistState,
  (state: reducer.ServiceInventoryWorklistState) => {
    return state.jeopardyResponsibility
  }
);

export const getSpeed = createSelector(selectServiceInventoryWorklistState,
  (state: reducer.ServiceInventoryWorklistState) => {
    return state.speed
  }
);

export const getProtocols = createSelector(selectServiceInventoryWorklistState,
  (state: reducer.ServiceInventoryWorklistState) => {
    return state.protocols
  }
);

export const getMediaTypes = createSelector(selectServiceInventoryWorklistState,
  (state: reducer.ServiceInventoryWorklistState) => {
    return state.mediaTypes
  }
);


export const getUpdatedItem = createSelector(selectServiceInventoryWorklistState,
  (state: reducer.ServiceInventoryWorklistState) => {
    return state.updatedItem
  }
);

export const getMetaData = createSelector(selectServiceInventoryWorklistState,
  (state: reducer.ServiceInventoryWorklistState) => {
    return {
      ...state.meta
    }
  }
);

export const getCustomersByKey = (key: ServiceWorklistOptionsKeys) => createSelector(selectServiceInventoryWorklistState,
  (state: reducer.ServiceInventoryWorklistState) => {
    return state[key];
  }
);

export const getCustomersByKeyAsOptions = (key: ServiceWorklistOptionsKeys) => createSelector(getCustomersByKey(key),
  (customers: Company[] | any[]) => {
    return customers.map(item => item.name);
  }
);

export const getProvisioners = createSelector(selectServiceInventoryWorklistState,
  (state: reducer.ServiceInventoryWorklistState) => {
    return state.provisioners
  }
);

export const getProvisionersAsOptions = createSelector(getProvisioners,
  (provisioners: SubjectInterface[]) => {
    return ['Unassigned', ...provisioners.map(item => item.displayName)];
  }
);

export const getParamsByKey = (key: ServiceWorklistFilterKeys) => createSelector(
  selectServiceInventoryWorklistState, (state: reducer.ServiceInventoryWorklistState) => state[key].filters
);

export const getOrderTypes = createSelector(selectServiceInventoryWorklistState,
  (state: reducer.ServiceInventoryWorklistState) => {
    return state.orderTypes ? state.orderTypes.map(item => item.value) : [];
  }
);

export const getSubOrderTypes = createSelector(selectServiceInventoryWorklistState,
  (state: reducer.ServiceInventoryWorklistState) => {
    return state.subOrderTypes ? state.subOrderTypes.map(item => item.value) : [];
  }
);

export const getServiceTypes = createSelector(selectServiceInventoryWorklistState,
  (state: reducer.ServiceInventoryWorklistState) => {
    return state.serviceTypes;
  }
);

export const getMultiEditDropdownConfig = createSelector(
  getProvisioners,
  getClientManagers,
  getCustomersByKey('masterCustomers'),
  getCustomersByKey('customers'),
  getProviders,
  getSpeed,
  getContractTerms,
  (
    provisioners: SubjectInterface[],
    clientManagers: LookupValue[],
    masterCustomers: any[],
    endCustomers: any[],
    providers: LookupValue[],
    speed: LookupValue[],
    contractTerms: LookupValue[]
  ) => {
    console.log(contractTerms)
    return {
      provisioner: provisioners,
      vertekProjectManager: provisioners,
      clientProjectManager: clientManagers,
      qaManager: provisioners,
      masterCustomer: masterCustomers,
      endCustomer: endCustomers,
      provider: providers,
      uploadSpeed: speed,
      downloadSpeed: speed,
      contractTerm: contractTerms,
    }}
);

export const getMultieditParamsConfig = createSelector(
  getParamsByKey('provisionersSearchCriteria'),
  getParamsByKey('masterCustomerSearchCriteria'),
  getParamsByKey('endCustomerSearchCriteria'),
  getParamsByKey('providersSearchCriteria'),
  (
    provisionerParams: CommonDropdownSearchCriteria,
    masterCustomerParams: CommonDropdownSearchCriteria,
    endCustomerParams: CommonDropdownSearchCriteria,
    providersParams: CommonDropdownSearchCriteria,
  ) => {
    return {
      provisioner: provisionerParams,
      masterCustomer: masterCustomerParams,
      endCustomer: endCustomerParams,
      provider: providersParams
    }}
);

export const getAppliedFilters = createSelector(getFilters,
  (filters: any) => {
    let appliedFilters: any[] = [];
    Object.keys(filters)
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
