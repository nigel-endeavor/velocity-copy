import { createFeatureSelector, createSelector } from '@ngrx/store';

import * as reducer from './customer-details.reducer';
import { LookupValue } from '../../../models/lookup-value.model';
import { Company } from '../../../models/company.model';
import { CommonDropdownSearchCriteria } from '../../../interfaces/commonSearch.interface';
import { initialState } from './customer-details.reducer';
import { SubjectInterface } from 'src/app/models/subject.model';
import { CompanyView } from '../../../models/company-view.model';
import { CustomerDetailsFilterKeys } from '../data/customer-details.consts';
import { TaskGroup } from '../../../models/task-group.model';

export const selectCustomerDetailsState
  = createFeatureSelector<reducer.CustomerDetailsState>(reducer.customerDetailsFeatureKey);

export const getIsLoading = createSelector(selectCustomerDetailsState,
  (state: reducer.CustomerDetailsState) => {
  return state.isLoading
  }
);

export const getSelectedItems = createSelector(selectCustomerDetailsState,
  (state: reducer.CustomerDetailsState) => {
    return state.selectedItems
  }
);

export const getSelectedItemsIds = createSelector(getSelectedItems,
  (selectedItems: CompanyView[]) => {
    return selectedItems.map(item => item.id);
  }
);

export const getUpdatedItem = createSelector(selectCustomerDetailsState,
  (state: reducer.CustomerDetailsState) => {
    return state.updatedItem
  }
);

export const getMetaData = createSelector(selectCustomerDetailsState,
  (state: reducer.CustomerDetailsState) => {
    return {
      ...state.meta
    }
  }
);

export const getTenants = createSelector(selectCustomerDetailsState,
  (state: reducer.CustomerDetailsState) => {
    return state.tenants
  }
);

export const getSelectedTenant = createSelector(selectCustomerDetailsState,
  (state: reducer.CustomerDetailsState) => {
    return state.selectedTenant
  }
);

export const getSelectedCustomer = createSelector(selectCustomerDetailsState,
  (state: reducer.CustomerDetailsState) => {
    return state.selectedCustomer
  }
);

export const getBillingContact = createSelector(selectCustomerDetailsState,
  (state: reducer.CustomerDetailsState) => {
    return state.billingContact
  }
);

export const getTechContact = createSelector(selectCustomerDetailsState,
  (state: reducer.CustomerDetailsState) => {
    return state.techContact
  }
);

export const getSalesContact = createSelector(selectCustomerDetailsState,
  (state: reducer.CustomerDetailsState) => {
    return state.salesContact
  }
);

export const getAuthContact = createSelector(selectCustomerDetailsState,
  (state: reducer.CustomerDetailsState) => {
    return state.authContact
  }
);

export const getProvisioners = createSelector(selectCustomerDetailsState,
  (state: reducer.CustomerDetailsState) => {
    return state.provisioners
  }
);

export const getSelectedTab = createSelector(selectCustomerDetailsState,
  (state: reducer.CustomerDetailsState) => {
    return state.selectedTab
  }
);

export const getTabs = createSelector(
  getSelectedTenant,
  getSelectedCustomer,
  (tenant, customer) => {
    if (customer?.type === 'End Customer') {
      return ['Details']; //end customer level tabs
    }
    if (customer?.type === 'Master Customer') {
      if (!customer?.id) {
        return ['Details']; //new master customer level tabs
      }
      return ['Details', 'End Customers', ]; //existing master customer level tabs
    }
    if (tenant) {
      let tabs = ['Master Customers', 'End Customers']; //tenant level tabs
      return tabs;
    }
    return [];
  }
);

export const getFilters = createSelector(selectCustomerDetailsState,
  (state: reducer.CustomerDetailsState) => {
    return state.filters
  }
);

export const getColumns = createSelector(selectCustomerDetailsState,
  (state: reducer.CustomerDetailsState) => {
    return state.columns
  }
);

export const getTaskGroups = createSelector(selectCustomerDetailsState,
  (state: reducer.CustomerDetailsState) => {
    return state.taskGroups
  }
)

export const getTaskGroupValues = createSelector(getTaskGroups,
  (taskGroups: TaskGroup[]) => {
  return [
    'Empty',
      ...taskGroups.map(item => item.name)
    ]
  }
);

export const getParamsByKey = (key: CustomerDetailsFilterKeys) => createSelector(
  selectCustomerDetailsState, (state: reducer.CustomerDetailsState) => state[key].filters
);

export const getAppliedFilters = createSelector(getFilters,
  (filters: any) => {
    let appliedFilters: any[] = [];
    Object.keys(filters)
    //@ts-ignore
    .filter(f => f != 'offset' && f != 'limit' && f != 'type')
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
