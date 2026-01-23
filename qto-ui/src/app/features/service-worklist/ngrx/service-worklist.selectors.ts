import { createFeatureSelector, createSelector } from '@ngrx/store';

import * as reducer from './service-worklist.reducer';
import { LookupValue } from '../../../models/lookup-value.model';
import { SubjectInterface } from '../../../models/subject.model';
import { Company } from '../../../models/company.model';
import { ServiceView } from '../../../models/service-view.model';
import { LevelOfEffort } from '../../../models/level-of-effort.model';
import { ServiceWorklistFilterKeys, ServiceWorklistOptionsKeys } from '../data/services-worklist.consts';
import { CommonDropdownSearchCriteria } from '../../../interfaces/commonSearch.interface';
import { initialState } from './service-worklist.reducer';
import { selectServiceInventoryWorklistState } from "../ngrx-inventory/service-inventory-worklist.selectors";

export const selectServiceWorklistState
  = createFeatureSelector<reducer.ServiceWorklistState>(reducer.serviceWorklistFeatureKey);

export const getIsLoading = createSelector(selectServiceWorklistState,
  (state: reducer.ServiceWorklistState) => {
  return state.isLoading
  }
);

export const getColumns = createSelector(selectServiceWorklistState,
  (state: reducer.ServiceWorklistState) => {
  return state.columns
  }
);

export const getFilters = createSelector(selectServiceWorklistState,
  (state: reducer.ServiceWorklistState) => {
  return state.filters
  }
);

export const getProviders = createSelector(selectServiceWorklistState,
  (state: reducer.ServiceWorklistState) => {
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

export const getServiceTypes = createSelector(selectServiceWorklistState,
  (state: reducer.ServiceWorklistState) => {
    return state.serviceTypes;
  }
);

export const getServiceBilledTo = createSelector(selectServiceWorklistState,
  (state: reducer.ServiceWorklistState) => {
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

export const getCardViewSelected = createSelector(selectServiceWorklistState,
  (state: reducer.ServiceWorklistState) => {
    return state.cardViewSelected
  }
);

export const getProvisioners = createSelector(selectServiceWorklistState,
  (state: reducer.ServiceWorklistState) => {
    return state.provisioners
  }
);

export const getProvisionersAsOptions = createSelector(getProvisioners,
  (provisioners: SubjectInterface[]) => {
    return ['Unassigned', ...provisioners.map(item => item.displayName)];
  }
);

export const getQaManagers = createSelector(selectServiceWorklistState,
  (state: reducer.ServiceWorklistState) => {
    return state.qaManagers
  }
);

export const getQaManagersAsOptions = createSelector(getQaManagers,
  (qaManagers: SubjectInterface[]) => {
    return ['Unassigned', ...qaManagers.map(item => item.displayName)];
  }
);

export const getSelectedItems = createSelector(selectServiceWorklistState,
  (state: reducer.ServiceWorklistState) => {
    return state ? state.selectedItems : [];
  }
);

export const getSelectedItemsIds = createSelector(getSelectedItems,
  (selectedItems: ServiceView[]) => {
    return selectedItems.map(item => item.id);
  }
);

export const getClientManagers = createSelector(selectServiceWorklistState,
  (state: reducer.ServiceWorklistState) => {
    return state.clientManagers
  }
);

export const getLevelOfEffort = createSelector(selectServiceWorklistState,
  (state: reducer.ServiceWorklistState) => {
    return state.levelOfEffort
  }
);

export const getLevelOfEffortAsOptions = createSelector(getLevelOfEffort,
  (levelOfEffort: LevelOfEffort[]) => {
    return levelOfEffort ? ['Empty', ...levelOfEffort.map(item => item.levelOfEffort)] : ['Empty']
  }
);

export const getServiceJeopardy = createSelector(selectServiceWorklistState,
  (state: reducer.ServiceWorklistState) => {
    return state.serviceJeopardy
  }
);

export const getJeopResponsibility = createSelector(selectServiceWorklistState,
  (state: reducer.ServiceWorklistState) => {
    return state.jeopardyResponsibility
  }
);

export const getSpeed = createSelector(selectServiceWorklistState,
  (state: reducer.ServiceWorklistState) => {
    return state.speed
  }
);

export const getProtocols = createSelector(selectServiceWorklistState,
  (state: reducer.ServiceWorklistState) => {
    return state.protocols
  }
);

export const getMediaTypes = createSelector(selectServiceWorklistState,
  (state: reducer.ServiceWorklistState) => {
    return state.mediaTypes
  }
);

export const getProjectNames = createSelector(selectServiceWorklistState,
  (state: reducer.ServiceWorklistState) => {
    return state.projectNames
  }
);

export const getParamsByKey = (key: ServiceWorklistFilterKeys) => createSelector(
  selectServiceWorklistState, (state: reducer.ServiceWorklistState) => state[key].filters
);


export const getCustomersByKey = (key: ServiceWorklistOptionsKeys) => createSelector(selectServiceWorklistState,
  (state: reducer.ServiceWorklistState) => {
    return state[key]
  }
);

export const getCustomersByKeyAsOptions = (key: ServiceWorklistOptionsKeys) => createSelector(getCustomersByKey(key),
  (customers: Company[] | any[]) => {
    return customers.map(item => item.name);
  }
);

export const getMultiEditDropdownConfig = createSelector(
  getProvisioners,
  getClientManagers,
  getLevelOfEffort,
  getCustomersByKey('masterCustomers'),
  getServiceJeopardy,
  getJeopResponsibility,
  getProviders,
  getSpeed,
  getProtocols,
  getMediaTypes,
  (
    provisioners: SubjectInterface[],
    clientManagers: LookupValue[],
    levelOfEffort: LevelOfEffort[],
    masterCustomers: any[],
    serviceJeopardy: LookupValue[],
    jeopardyResponsibility: LookupValue[],
    providers: LookupValue[],
    speed: LookupValue[],
    protocols: LookupValue[],
    mediaTypes: LookupValue[]
  ) => {
    return {
      provisioner: provisioners,
      vertekProjectManager: provisioners,
      levelOfEffort: levelOfEffort,
      masterCustomer: masterCustomers,
      clientProjectManager: clientManagers,
      qaManager: provisioners,
      jeopDescription: serviceJeopardy,
      jeopResponsibility: jeopardyResponsibility,
      jeopAssignedTo: provisioners,
      provider: providers,
      networkProtocol: protocols,
      uploadSpeed: speed,
      downloadSpeed: speed,
      mediaType: mediaTypes,
      closeJeops: [{value: 'Yes'}]
    }}
);

export const getMultieditParamsConfig = createSelector(
  getParamsByKey('provisionersSearchCriteria'),
  getParamsByKey('masterCustomerSearchCriteria'),
  getParamsByKey('providersSearchCriteria'),
  (
    provisionerParams: CommonDropdownSearchCriteria,
    masterCustomerParams: CommonDropdownSearchCriteria,
    providersParams: CommonDropdownSearchCriteria,
  ) => {
    return {
      provisioner: provisionerParams,
      masterCustomer: masterCustomerParams,
      provider: providersParams
    }}
);

export const getUpdatedItem = createSelector(selectServiceWorklistState,
  (state: reducer.ServiceWorklistState) => {
    return state.updatedItem
  }
);

export const getFilterBuilderNames = createSelector(selectServiceWorklistState,
  (state: reducer.ServiceWorklistState) => {
    return Object.keys(state.filterBuilderOptions).map(key => state.filterBuilderOptions[key].name);
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
