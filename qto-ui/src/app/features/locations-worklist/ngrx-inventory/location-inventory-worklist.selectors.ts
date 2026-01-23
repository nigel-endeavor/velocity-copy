import { createFeatureSelector, createSelector } from '@ngrx/store';

import * as reducer from './location-inventory-worklist.reducer';
import { LookupValue } from '../../../models/lookup-value.model';
import { SubjectInterface } from '../../../models/subject.model';
import { Company } from '../../../models/company.model';
import { initialState } from './location-inventory-worklist.reducer';
import { selectServiceWorklistState } from "../../service-worklist/ngrx/service-worklist.selectors";

export const selectLocationInventoryWorklistState
  = createFeatureSelector<reducer.LocationInventoryWorklistState>(reducer.locationInventoryWorklistFeatureKey);

export const getIsLoading = createSelector(selectLocationInventoryWorklistState,
  (state: reducer.LocationInventoryWorklistState) => {
  return state.isLoading
  }
);

export const getCardViewSelected = createSelector(selectLocationInventoryWorklistState,
  (state: reducer.LocationInventoryWorklistState) => {
  return state.cardViewSelected
  }
);

export const getColumns = createSelector(selectLocationInventoryWorklistState,
  (state: reducer.LocationInventoryWorklistState) => {
  return state.columns
  }
);

export const getFilters = createSelector(selectLocationInventoryWorklistState,
  (state: reducer.LocationInventoryWorklistState) => {
  return state.filters
  }
);

export const getUpdatedItem = createSelector(selectLocationInventoryWorklistState,
  (state: reducer.LocationInventoryWorklistState) => {
  return state.updatedItem
  }
);

export const getStatuses = createSelector(selectLocationInventoryWorklistState,
  (state: reducer.LocationInventoryWorklistState) => {
  return state.statuses
  }
);

export const getStatusesValues = createSelector(getStatuses,
  (statuses: LookupValue[]) => {
  return statuses.map(item => item.value);
  }
);

// provisioners
export const getProvisioners = createSelector(selectLocationInventoryWorklistState,
  (state: reducer.LocationInventoryWorklistState) => {
    return state.provisioners
  }
);

export const getProvisionersAsOptions = createSelector(getProvisioners,
  (provisioners: SubjectInterface[]) => {
    return ['Unassigned', ...provisioners.map(item => item.displayName)];
  }
);

export const getProvisionersParams = createSelector(selectLocationInventoryWorklistState,
  (state: reducer.LocationInventoryWorklistState) => {
    return state.provisionersParams.filters
  }
);

//customer
export const getCustomersParams = createSelector(selectLocationInventoryWorklistState,
  (state: reducer.LocationInventoryWorklistState) => {
    return state.customersParams.filters
  }
);

export const getCustomers = createSelector(selectLocationInventoryWorklistState,
  (state: reducer.LocationInventoryWorklistState) => {
    return state.customers
  }
);

export const getCustomersAsOptions = createSelector(getCustomers,
  (customers: Company[]) => {
    return customers.map(item => item.name);
  }
);

// parent Customer
export const getParentCompany = createSelector(selectLocationInventoryWorklistState,
  (state: reducer.LocationInventoryWorklistState) => {
  return state.parentCompanyName
  }
);

export const getParentCustomerOptions = createSelector(getParentCompany,
  (parentCompanyName: Company[]) => {
    return parentCompanyName.map(item => item.parentCompany);
    }
);

// company name
export const getCompanyNames = createSelector(selectLocationInventoryWorklistState,
  (state: reducer.LocationInventoryWorklistState) => {
    return state.companyName
  }
);

export const getCompanyNameOptions = createSelector(getCompanyNames,
  (companyName: Company[]) => {
    return ['Empty', ...companyName.map(item => item.name)];
  }
);

export const getCompanyNameParams = createSelector(selectLocationInventoryWorklistState,
  (state: reducer.LocationInventoryWorklistState) => {
    return state.companyNameParams.filters
  }
);

// sub order types
export const getSubOrdertypes = createSelector(selectLocationInventoryWorklistState,
  (state: reducer.LocationInventoryWorklistState) => {
    return state.subOrderTypes
  }
);

export const getSubOrderTypeOptions = createSelector(getSubOrdertypes,
  (subOrders: LookupValue[]) => {
    return ['Empty', ...subOrders.map(item => item.value)];
  }
);

export const getServiceTypes = createSelector(selectLocationInventoryWorklistState,
  (state: reducer.LocationInventoryWorklistState) => {
    console.log('service types', state.serviceTypes);
    return state.serviceTypes;
  }
);

// meta data
export const getMetaData = createSelector(selectLocationInventoryWorklistState,
  (state: reducer.LocationInventoryWorklistState) => {
    return {
      ...state.meta
    }
  }
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
