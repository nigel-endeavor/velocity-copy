import { createFeatureSelector, createSelector } from '@ngrx/store';

import * as reducer from './master-customers-worklist.reducer';
import { LookupValue } from '../../../models/lookup-value.model';
import { Company } from '../../../models/company.model';
import { MasterCustomersWorklistFilterKeys, MasterCustomersWorklistOptionsKeys } from '../data/master-customers-worklist.consts';
import { CommonDropdownSearchCriteria } from '../../../interfaces/commonSearch.interface';
import { initialState } from './master-customers-worklist.reducer';
import { SubjectInterface } from 'src/app/models/subject.model';
import { CompanyView } from '../../../models/company-view.model';

export const selectMasterCustomerWorklistState
  = createFeatureSelector<reducer.MasterCustomersWorklistState>(reducer.masterCustomersWorklistFeatureKey);

export const getIsLoading = createSelector(selectMasterCustomerWorklistState,
  (state: reducer.MasterCustomersWorklistState) => {
  return state.isLoading
  }
);

export const getColumns = createSelector(selectMasterCustomerWorklistState,
  (state: reducer.MasterCustomersWorklistState) => {
  return state.columns
  }
);

export const getFilters = createSelector(selectMasterCustomerWorklistState,
  (state: reducer.MasterCustomersWorklistState) => {
  return state.filters
  }
);

export const getCardViewSelected = createSelector(selectMasterCustomerWorklistState,
  (state: reducer.MasterCustomersWorklistState) => {
    return state.cardViewSelected
  }
);

export const getSelectedItems = createSelector(selectMasterCustomerWorklistState,
  (state: reducer.MasterCustomersWorklistState) => {
    return state.selectedItems
  }
);

export const getSelectedItemsIds = createSelector(getSelectedItems,
  (selectedItems: CompanyView[]) => {
    return selectedItems.map(item => item.id);
  }
);

export const getParamsByKey = (key: MasterCustomersWorklistFilterKeys) => createSelector(
  selectMasterCustomerWorklistState, (state: reducer.MasterCustomersWorklistState) => state[key].filters
);


export const getCustomersByKey = (key: MasterCustomersWorklistOptionsKeys) => createSelector(selectMasterCustomerWorklistState,
  (state: reducer.MasterCustomersWorklistState) => {
    return state[key]
  }
);

export const getAssignables = createSelector(selectMasterCustomerWorklistState,
  (state: reducer.MasterCustomersWorklistState) => {
    return state.assignables
  }
);

export const getAssignablesAsOptions = createSelector(getAssignables,
  (assignables: SubjectInterface[]) => {
    return ['Unassigned', ...assignables.map(item => item.displayName)];
  }
);

export const getCustomersByKeyAsOptions = (key: MasterCustomersWorklistOptionsKeys) => createSelector(getCustomersByKey(key),
  (customers: Company[] | any[]) => {
    return customers.map(item => item.name);
  }
);

export const getUpdatedItem = createSelector(selectMasterCustomerWorklistState,
  (state: reducer.MasterCustomersWorklistState) => {
    return state.updatedItem
  }
);

export const getMetaData = createSelector(selectMasterCustomerWorklistState,
  (state: reducer.MasterCustomersWorklistState) => {
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
