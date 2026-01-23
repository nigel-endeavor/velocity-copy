import { createFeatureSelector, createSelector } from '@ngrx/store';

import * as reducer from './location-worklist.reducer';
import { LookupValue } from '../../../models/lookup-value.model';
import { SubjectInterface } from '../../../models/subject.model';
import { Company } from '../../../models/company.model';
import { initialState } from './location-worklist.reducer';
import { selectServiceWorklistState } from "../../service-worklist/ngrx/service-worklist.selectors";

export const selectLocationWorklistState
  = createFeatureSelector<reducer.LocationWorklistState>(reducer.locationWorklistFeatureKey);

export const getIsLoading = createSelector(selectLocationWorklistState,
  (state: reducer.LocationWorklistState) => {
  return state.isLoading
  }
);

export const getCardViewSelected = createSelector(selectLocationWorklistState,
  (state: reducer.LocationWorklistState) => {
  return state.cardViewSelected
  }
);

export const getColumns = createSelector(selectLocationWorklistState,
  (state: reducer.LocationWorklistState) => {
  return state.columns
  }
);

export const getFilters = createSelector(selectLocationWorklistState,
  (state: reducer.LocationWorklistState) => {
  console.log(state.filters)
  return state.filters
  }
);

export const getUpdatedItem = createSelector(selectLocationWorklistState,
  (state: reducer.LocationWorklistState) => {
  return state.updatedItem
  }
);

export const getStatuses = createSelector(selectLocationWorklistState,
  (state: reducer.LocationWorklistState) => {
  return state.statuses
  }
);

export const getStatusesValues = createSelector(getStatuses,
  (statuses: LookupValue[]) => {
  return statuses.map(item => item.value);
  }
);

export const getServiceTypes = createSelector(selectLocationWorklistState,
  (state: reducer.LocationWorklistState) => {
    return state.serviceTypes;
  }
);

export const getProvisioners = createSelector(selectLocationWorklistState,
  (state: reducer.LocationWorklistState) => {
    return state.provisioners
  }
);

export const getProvisionersAsOptions = createSelector(getProvisioners,
  (provisioners: SubjectInterface[]) => {
    return ['Unassigned', ...provisioners.map(item => item.displayName)];
  }
);

export const getCustomersParams = createSelector(selectLocationWorklistState,
  (state: reducer.LocationWorklistState) => {
    return state.customersParams.filters
  }
);

export const getEndCustomersParams = createSelector(selectLocationWorklistState,
  (state: reducer.LocationWorklistState) => {
    return state.endCustomerParams.filters
  }
);

export const getProvisionersParams = createSelector(selectLocationWorklistState,
  (state: reducer.LocationWorklistState) => {
    return state.provisionersParams.filters
  }
);

export const getCustomers = createSelector(selectLocationWorklistState,
  (state: reducer.LocationWorklistState) => {
    return state.customers
  }
);

export const getCustomersAsOptions = createSelector(getCustomers,
  (customers: Company[]) => {
    return [
      'Empty',
      ...customers.map(item => item.name)
    ]
  }
);

export const getEndCustomers = createSelector(selectLocationWorklistState,
  (state: reducer.LocationWorklistState) => {
    return state.endCustomers
  }
);

export const getEndCustomersAsOptions = createSelector(getEndCustomers,
  (endCustomers: Company[]) => {
    return [
      'Empty',
      ...endCustomers.map(item => item.name)
    ]
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

export const getJeopResponsibility = createSelector(selectLocationWorklistState,
  (state: reducer.LocationWorklistState) => {
    return state.jeopardyResponsibility
  }
);
