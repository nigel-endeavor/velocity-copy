import { createFeatureSelector, createSelector } from '@ngrx/store';

import * as reducer from './dispute-worklist.reducer';
import { LookupValue } from '../../../models/lookup-value.model';
import { Company } from '../../../models/company.model';
import { DisputeView } from '../../../models/dispute-view.model';
import { DisputeWorklistFilterKeys, DisputeWorklistOptionsKeys } from '../data/dispute-worklist.consts';
import { CommonDropdownSearchCriteria } from '../../../interfaces/commonSearch.interface';
import { initialState } from './dispute-worklist.reducer';
import { SubjectInterface } from 'src/app/models/subject.model';
import { selectDisconnectWorklistState } from "../../disconnect-worklist/ngrx/disconnect-worklist.selectors";


export const selectDisputeWorklistState
  = createFeatureSelector<reducer.DisputeWorklistState>(reducer.disputeWorklistFeatureKey);

export const getIsLoading = createSelector(selectDisputeWorklistState,
  (state: reducer.DisputeWorklistState) => {
  return state.isLoading
  }
);

export const getColumns = createSelector(selectDisputeWorklistState,
  (state: reducer.DisputeWorklistState) => {
  return state.columns
  }
);

export const getFilters = createSelector(selectDisputeWorklistState,
  (state: reducer.DisputeWorklistState) => {
  return state.filters
  }
);

export const getProviders = createSelector(selectDisputeWorklistState,
  (state: reducer.DisputeWorklistState) => {
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

export const getServiceBilledTo = createSelector(selectDisputeWorklistState,
  (state: reducer.DisputeWorklistState) => {
    return state.serviceBilledTo
  }
);

export const getServiceTypes = createSelector(selectDisputeWorklistState,
  (state: reducer.DisputeWorklistState) => {
    return state.serviceTypes;
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

export const getDisputeTypes = createSelector(selectDisputeWorklistState,
  (state: reducer.DisputeWorklistState) => {
  return state.disputeTypes
  }
);

export const getDisputeTypeValues = createSelector(getDisputeTypes,
  (statuses: LookupValue[]) => {
  return [
    'Empty',
      ...statuses.map(item => item.value)
    ]
  }
);

  export const getDisputeAssignments = createSelector(selectDisputeWorklistState,
    (state: reducer.DisputeWorklistState) => {
      return state.disputeAssignments;
    }
  );

  export const getDisputeAssignmentOptions = createSelector(getDisputeAssignments,
    (subjects: SubjectInterface[])=>{
      return ['Unassigned', ...subjects.map(item => item.displayName)];
    })

export const getCardViewSelected = createSelector(selectDisputeWorklistState,
  (state: reducer.DisputeWorklistState) => {
    return state.cardViewSelected
  }
);

export const getSelectedItems = createSelector(selectDisputeWorklistState,
  (state: reducer.DisputeWorklistState) => {
    return state.selectedItems
  }
);

export const getSelectedItemsIds = createSelector(getSelectedItems,
  (selectedItems: DisputeView[]) => {
    return selectedItems.map(item => item.id);
  }
);

export const getParamsByKey = (key: DisputeWorklistFilterKeys) => createSelector(
  selectDisputeWorklistState, (state: reducer.DisputeWorklistState) => state[key].filters
);


export const getCustomersByKey = (key: DisputeWorklistOptionsKeys) => createSelector(selectDisputeWorklistState,
  (state: reducer.DisputeWorklistState) => {
    return state[key]
  }
);

export const getCustomersByKeyAsOptions = (key: DisputeWorklistOptionsKeys) => createSelector(getCustomersByKey(key),
  (customers: Company[] | any[]) => {
    return customers.map(item => item.name);
  }
);

export const getMultieditConfig = createSelector(
  getCustomersByKey('masterCustomers'),
  getProviders,
  getDisputeTypes,
  getDisputeAssignments,
  (
    masterCustomers: any[],
    providers: LookupValue[],
    disputeTypes: LookupValue[],
    disputeAssignments:any[],
  ) => {
    return {
      masterCustomer: masterCustomers,
      provider: providers,
      disputeType: disputeTypes,
      disputeAssignment: disputeAssignments,
    }}
);

export const getMultieditParamsConfig = createSelector(
  getParamsByKey('masterCustomerSearchCriteria'),
  getParamsByKey('providersSearchCriteria'),
  getParamsByKey('disputeTypesSearchCriteria'),
  getParamsByKey('disputeAssignmentSearchCriteria'),
  (
    masterCustomerParams: CommonDropdownSearchCriteria,
    providersParams: CommonDropdownSearchCriteria,
    disputeTypesParams: CommonDropdownSearchCriteria,
    disputeAssignmentParams: CommonDropdownSearchCriteria
  ) => {
    return {
      masterCustomer: masterCustomerParams,
      provider: providersParams,
      disputeType: disputeTypesParams,
      disputeAssignment: disputeAssignmentParams,
    }}
);

export const getUpdatedItem = createSelector(selectDisputeWorklistState,
  (state: reducer.DisputeWorklistState) => {
    return state.updatedItem
  }
);

export const getMetaData = createSelector(selectDisputeWorklistState,
  (state: reducer.DisputeWorklistState) => {
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
