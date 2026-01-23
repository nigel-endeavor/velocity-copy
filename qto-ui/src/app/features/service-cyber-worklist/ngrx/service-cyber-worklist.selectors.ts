import { createFeatureSelector, createSelector } from "@ngrx/store";
import { LookupValue } from "../../../models/lookup-value.model";
import { SubjectInterface } from "../../../models/subject.model";
import * as reducer from "./service-cyber-worklist.reducer";
import { Company } from "../../../models/company.model";
import { CommonDropdownSearchCriteria } from "../../../interfaces/commonSearch.interface";
import { ServiceCyberView } from "../../../models/service-cyber-view.model";
import { ServiceCyberWorklistFilterKeys, ServiceCyberWorklistOptionsKeys } from "../data/service-cyber-worklist.const";
import { initialState } from "./service-cyber-worklist.reducer";

export const selectServiceCyberWorklistState = createFeatureSelector<reducer.ServiceCyberWorklistState>(reducer.serviceCyberWorklistFeatureKey);

export const getIsLoading = createSelector(selectServiceCyberWorklistState,
  (state: reducer.ServiceCyberWorklistState) => {
    return state.isLoading
  }
);

export const getColumns = createSelector(selectServiceCyberWorklistState,
  (state: reducer.ServiceCyberWorklistState) => {
    return state.columns
  }
);

export const getFilters = createSelector(selectServiceCyberWorklistState,
  (state: reducer.ServiceCyberWorklistState) => {
    return state.filters
  }
);

export const getProviders = createSelector(selectServiceCyberWorklistState,
  (state: reducer.ServiceCyberWorklistState) => {
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

export const getProvisioners = createSelector(selectServiceCyberWorklistState,
  (state: reducer.ServiceCyberWorklistState) => {
    return state.provisioners
  }
);

export const getProvisionersAsOptions = createSelector(getProvisioners,
  (provisioners: SubjectInterface[]) => {
    return ['Unassigned', ...provisioners.map(item => item.displayName)];
  }
);

export const geti90PojectManagers = createSelector(selectServiceCyberWorklistState,
  (state: reducer.ServiceCyberWorklistState) => {
    return state.i90ProjectManagers
  }
);

export const geti90ProjectManagersAsOptions = createSelector(geti90PojectManagers,
  (i90ProjectManagers: SubjectInterface[]) => {
    return ['Unassigned', ...i90ProjectManagers.map(item => item.displayName)];
  }
);

export const getServiceTypes = createSelector(selectServiceCyberWorklistState,
  (state: reducer.ServiceCyberWorklistState) => {
    return state.serviceTypes ? state.serviceTypes.map(item => item.value) : [];
  }
);

export const getCardViewSelected = createSelector(selectServiceCyberWorklistState,
  (state: reducer.ServiceCyberWorklistState) => {
    return state.cardViewSelected
  }
);

export const getSelectedItems = createSelector(selectServiceCyberWorklistState,
  (state: reducer.ServiceCyberWorklistState) => {
    return state.selectedItems
  }
);

export const getSelectedItemsIds = createSelector(getSelectedItems,
  (selectedItems: ServiceCyberView[]) => {
    return selectedItems.map(item => item.id);
  }
);

export const getParamsByKey = (key: ServiceCyberWorklistFilterKeys) => createSelector(
  selectServiceCyberWorklistState, (state: reducer.ServiceCyberWorklistState) => state[key].filters
);


export const getCustomersByKey = (key: ServiceCyberWorklistOptionsKeys) => createSelector(selectServiceCyberWorklistState,
  (state: reducer.ServiceCyberWorklistState) => {
    return state[key]
  }
);

export const getCustomersByKeyAsOptions = (key: ServiceCyberWorklistOptionsKeys) => createSelector(getCustomersByKey(key),
  (customers: Company[] | any[]) => {
    return customers.map(item => item.name);
  }
);

export const getMultieditConfig = createSelector(
  getCustomersByKey('masterCustomers'),
  getProviders,
  getProvisioners,
  geti90PojectManagers,
  (
    masterCustomers: any[],
    providers: LookupValue[],
    provisioners: SubjectInterface[],
    i90ProjectManagers: SubjectInterface[]
  ) => {
    return {
      masterCustomer: masterCustomers,
      provider: providers,
      provisioner: provisioners,
      i90ProjectManager: i90ProjectManagers
    }
  }
);

export const getMultieditParamsConfig = createSelector(
  getParamsByKey('provisionersSearchCriteria'),
  getParamsByKey('masterCustomerSearchCriteria'),
  getParamsByKey('providersSearchCriteria'),
  getParamsByKey('i90ProjectManagersSearchCriteria'),
  (
    provisionerParams: CommonDropdownSearchCriteria,
    masterCustomerParams: CommonDropdownSearchCriteria,
    providersParams: CommonDropdownSearchCriteria,
    i90ProjectManagerParams: CommonDropdownSearchCriteria,
  ) => {
    return {
      provisioner: provisionerParams,
      masterCustomer: masterCustomerParams,
      provider: providersParams,
      i90ProjectManager: i90ProjectManagerParams
    }
  }
);

export const getUpdatedItem = createSelector(selectServiceCyberWorklistState,
  (state: reducer.ServiceCyberWorklistState) => {
    return state.updatedItem
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
