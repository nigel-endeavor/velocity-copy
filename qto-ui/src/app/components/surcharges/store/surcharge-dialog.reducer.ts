import { createReducer, on } from '@ngrx/store';
import * as actions from './surcharge-dialog.actions';
import { SURCHARGE_COLUMNS } from '../data/surcharge-table-columns.consts';
import { CommonColumn } from '../../../interfaces/columns.interface';
import { ServiceSurcharge } from '../../../models/service-surcharge-model';
import { SurchargeType } from '../../../models/surcharge-type.model';

export const surchargeDialogFeatureKey = 'surchargeDialog';

export interface SurchargeDialogState {
  filters: {
    serviceId: number | undefined;
    offset: number;
    limit: number;
    sortDir: string;
    sortField: string;
    format: string;
    fields: string;
    headers: string;
  };

  isLoading: boolean;
  columns: CommonColumn[];
  selectedSurcharge: ServiceSurcharge | undefined;
  surchargeTypes: SurchargeType[];
}

export const initialState: SurchargeDialogState = {
  filters: {
    serviceId: undefined,
    offset: 0,
    limit: 25,
    sortDir: '',
    sortField: '',
    format: '',
    fields: '',
    headers: ''
  },
  isLoading: false,
  columns: SURCHARGE_COLUMNS,
  selectedSurcharge: undefined,
  surchargeTypes: []
};

export const surchargeTableReducer = createReducer(
  initialState,

  on(actions.updateSort, (state, action) => {
    let resSort;
    if (state.filters.sortDir === action.sort.dir && state.filters.sortField === action.sort.col) {
      resSort = {
        sortDir: '',
        sortField: ''
      }
    } else {
      resSort = {
        sortDir: action.sort.dir,
        sortField: action.sort.col
      }
    }
    return {
      ...state,
      filters: {
        ...state.filters,
        ...resSort
      }
    }
  }),

  on(actions.updateFilters, (state, action) => {
    return {
      ...state,
      filters: {
        ...state.filters,
        [action.key]: action.value
      }
    }
  }),

  on(actions.clearFilters, (state) => {
    return {
      ...state,
     filters: {
       ...initialState.filters
     }
    }
  }),

  on(actions.createSurcharge, (state, action) => {
    const surcharge = new ServiceSurcharge();
    surcharge.serviceId = action.id;
    surcharge.surchargeDate = new Date();
    return {
      ...state,
      selectedSurcharge: surcharge
    }
  }),

  on(actions.loadSurchargeTypesSuccess, (state, action) => {
    return {
      ...state,
      surchargeTypes: action.surchargeTypes
    }
  }),

  on(actions.setSelectedSurcharge, (state, action) => {
    return {
      ...state,
      selectedSurcharge: action.surcharge
    }
  }),

  on(actions.saveSurchargeSuccess, actions.saveSurchargeFailure, (state, action) => {
    return {
      ...state,
      selectedSurcharge: undefined
    }
  }),

  on(actions.deleteSurchargeSuccess, actions.deleteSurchargeFailure, (state, action) => {
    return {
      ...state,
      selectedSurcharge: undefined
    }
  }),

  on(actions.updateSurcharge, (state, action) => {
    return state.selectedSurcharge ? {
      ...state,
      selectedSurcharge: {
        ...state.selectedSurcharge,
        [action.key]: action.value
      }
    } : state;
  }),
  
  on(actions.pageDestroyed, (state) => {
    return {
      ...state,
      selectedSurcharge: undefined
    }
  })
);