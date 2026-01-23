import { createFeatureSelector, createSelector } from '@ngrx/store';

import * as reducer from './service-equipment.reducer';
import { LookupValue } from '../../../../models/lookup-value.model';
import { ServiceEquipmentFilterKeys, ServiceEquipmentOptionsKeys } from '../data/service-equipment.consts';
import { CommonDropdownSearchCriteria } from '../../../../interfaces/commonSearch.interface';
import { initialState } from './service-equipment.reducer';
import { SubjectInterface } from 'src/app/models/subject.model';

export const selectMasterCustomerWorklistState
  = createFeatureSelector<reducer.ServiceEquipmentState>(reducer.serviceEquipmentFeatureKey);

export const getIsLoading = createSelector(selectMasterCustomerWorklistState,
  (state: reducer.ServiceEquipmentState) => {
  return state.isLoading
  }
);

export const getColumns = createSelector(selectMasterCustomerWorklistState,
  (state: reducer.ServiceEquipmentState) => {
  return state.columns
  }
);

export const getFilters = createSelector(selectMasterCustomerWorklistState,
  (state: reducer.ServiceEquipmentState) => {
  return state.filters
  }
);

export const getCardViewSelected = createSelector(selectMasterCustomerWorklistState,
  (state: reducer.ServiceEquipmentState) => {
    return state.cardViewSelected
  }
);

export const getSelectedServiceEquipment = createSelector(selectMasterCustomerWorklistState,
  (state: reducer.ServiceEquipmentState) => {
    return state.selectedServiceEquipment
  }
);

export const getEquipmentTypes = createSelector(selectMasterCustomerWorklistState,
  (state: reducer.ServiceEquipmentState) => {
    return state.equipmentTypes
  }
);

export const getEquipmentSubTypes = createSelector(selectMasterCustomerWorklistState,
  (state: reducer.ServiceEquipmentState) => {
    return state.equipmentSubTypes
  }
);

export const getOwnerships = createSelector(selectMasterCustomerWorklistState,
  (state: reducer.ServiceEquipmentState) => {
    return state.ownerships
  }
);

export const getShippingMethods = createSelector(selectMasterCustomerWorklistState,
  (state: reducer.ServiceEquipmentState) => {
    return state.shippingMethods
  }
);

// export const getParamsByKey = (key: ServiceEquipmentFilterKeys) => createSelector(
//   selectMasterCustomerWorklistState, (state: reducer.ServiceEquipmentState) => state[key].filters
// );

// export const getAppliedFilters = createSelector(getFilters,
//   (filters: any) => {
//     let appliedFilters: any[] = [];
//     Object.keys(filters)
//     //@ts-ignore
//     .filter(f => f != 'offset' && f != 'limit' && f != 'type')
//     .forEach((element: string) => {
//       if (filters[element] instanceof Array) {
//         //@ts-ignore
//         if (filters[element].length != initialState.filters[element].length) {
//           //@ts-ignore
//           appliedFilters.push({ name: element, value: filters[element], initialValue: initialState.filters[element] })
//         }
//       } else if (filters[element] instanceof Object) {
//         //@ts-ignore
//         let obj = initialState.filters[element];
//         if (obj && Object.keys(obj).length > 0) {
//           Object.keys(obj).forEach((subElement: string) => {
//             //@ts-ignore
//             if (filters[element][subElement] != initialState.filters[element][subElement]) {
//               //@ts-ignore
//               appliedFilters.push({ name: element, value: filters[element], initialValue: initialState.filters[element] })
//             }
//           });
//         }
//       } else {
//         //@ts-ignore
//         if (filters[element] != initialState.filters[element]) {
//           //@ts-ignore
//           appliedFilters.push({ name: element, value: filters[element], initialValue: initialState.filters[element] })
//         }
//       }
//     });
//     return appliedFilters.filter(f => !!f.value);
//   }
// );
