import { createFeatureSelector, createSelector } from '@ngrx/store';

import * as reducer from './end-customers-worklist.reducer';
import { LookupValue } from '../../../models/lookup-value.model';
import { Company } from '../../../models/company.model';
import { EndCustomersWorklistFilterKeys, EndCustomersWorklistOptionsKeys } from '../data/end-customers-worklist.consts';
import { CommonDropdownSearchCriteria } from '../../../interfaces/commonSearch.interface';
import { initialState } from './end-customers-worklist.reducer';
import { SubjectInterface } from 'src/app/models/subject.model';
import { CompanyView } from '../../../models/company-view.model';

export const selectCustomerWorklistState
  = createFeatureSelector<reducer.EndCustomersWorklistState>(reducer.endCustomersWorklistFeatureKey);

export const getIsLoading = createSelector(selectCustomerWorklistState,
  (state: reducer.EndCustomersWorklistState) => {
  return state.isLoading
  }
);

export const getColumns = createSelector(selectCustomerWorklistState,
  (state: reducer.EndCustomersWorklistState) => {
  return state.columns
  }
);

export const getFilters = createSelector(selectCustomerWorklistState,
  (state: reducer.EndCustomersWorklistState) => {
  return state.filters
  }
);

export const getCardViewSelected = createSelector(selectCustomerWorklistState,
  (state: reducer.EndCustomersWorklistState) => {
    return state.cardViewSelected
  }
);

export const getSelectedItems = createSelector(selectCustomerWorklistState,
  (state: reducer.EndCustomersWorklistState) => {
    return state.selectedItems
  }
);

export const getSelectedItemsIds = createSelector(getSelectedItems,
  (selectedItems: CompanyView[]) => {
    return selectedItems.map(item => item.id);
  }
);

export const getParamsByKey = (key: EndCustomersWorklistFilterKeys) => createSelector(
  selectCustomerWorklistState, (state: reducer.EndCustomersWorklistState) => state[key].filters
);


export const getCustomersByKey = (key: EndCustomersWorklistOptionsKeys) => createSelector(selectCustomerWorklistState,
  (state: reducer.EndCustomersWorklistState) => {
    return state[key]
  }
);

export const getCustomersByKeyAsOptions = (key: EndCustomersWorklistOptionsKeys) => createSelector(getCustomersByKey(key),
  (customers: Company[] | any[]) => {
    return customers.map(item => item.name);
  }
);

export const getUpdatedItem = createSelector(selectCustomerWorklistState,
  (state: reducer.EndCustomersWorklistState) => {
    return state.updatedItem
  }
);

export const getMetaData = createSelector(selectCustomerWorklistState,
  (state: reducer.EndCustomersWorklistState) => {
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
