import { createReducer, on } from '@ngrx/store';
import * as actions from './activation-worklist.actions';
import { LookupValue } from '../../../models/lookup-value.model';
import { MultiEditFailureInterface } from '../../abstract-table/interfaces/multiEditFailure.interface';
import { ACTIVATION_WORKLIST_COMPONENTS } from '../data/activation-worklist-columns.consts';
import { CommonColumn } from '../../../interfaces/columns.interface';
import { ComparableDateRange } from '../../../interfaces/date-range.interface';
import { getTodayAsString } from '../../../utilities';
import { Company } from '../../../models/company.model';
import { CommonSearchCriteria } from '../../../interfaces/commonSearch.interface';

export const activationWorklistFeatureKey = 'activationWorklist';

export interface ActivationMetaData {
  statusCounts: { status: string; count: number; color?: string }[];
  ttuEquivTotal: number;
  statusCountHeader: string;
}

export interface ActivationWorklistState {
  filters: {
    search: string;
    clientServiceId: string;
    scheduledAttemptStatus: string[] | undefined;
    internalTechAssigned: string;
    scheduledCheckInTime: ComparableDateRange;
    lastUpdateBy: string;
    clientLocationType: string;
    clientLocationInfo: string;


    offset: number;
    limit: number;
    sortDir: string;
    sortField: string;
    format: string;
    fields: string;
    headers: string;
  };

  statuses: LookupValue[];
  isLoading: boolean;
  cantUpdate: MultiEditFailureInterface[];
  columns: CommonColumn[];
  meta: ActivationMetaData | null;
  masterCustomers: Company[];
  masterCustomerSearchCriteria: CommonSearchCriteria;
}

export const initialState: ActivationWorklistState = {
  filters: {
    search: '',
    clientServiceId: '',
    scheduledAttemptStatus: undefined,
    internalTechAssigned: '',
    // @ts-ignore
    scheduledCheckInTime: {
      isEmpty: false,
      dateCohort: '',
      dateRange: {
        startDate: getTodayAsString(),
        endDate: getTodayAsString()
      }
    },
    lastUpdateBy: '',
    clientLocationType: '',
    clientLocationInfo: '',

    offset: 0,
    limit: 25,
    sortDir: '',
    sortField: '',
    format: '',
    fields: '',
    headers: ''
  },
  statuses: [],
  cantUpdate: [],
  isLoading: false,
  columns: ACTIVATION_WORKLIST_COMPONENTS,
  meta: null,
  masterCustomers: [],
  masterCustomerSearchCriteria: {
    filters: {
      type: 'Master Customer',
      name: '',
      tenants: '',
      total: 0,
      limit: 10000,
      offset: 0
    },
    wasChanged: false
  }
};

export const activationWorklistReducer = createReducer(
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
        ...resSort,
        offset: 0
      }
    }
  }),

  on(actions.updateFilters, (state, action) => {
    return {
      ...state,
      filters: {
        ...state.filters,
        [action.key]: action.value,
        ...(action.key !== 'offset' && { offset: 0 }),
      }
    }
  }),

  on(actions.resetFilters, (state) => {
    return {
      ...state,
     filters: {
       ...initialState.filters
     }
    }
  }),

  on(actions.loadLookupValuesByKeySuccess, (state, action) => {
    return {
      ...state,
      [action.key]: action.values
    }
  }),

  on(actions.loadMetaDataSuccess, (state, action) => {
    return {
      ...state,
      meta: {
        statusCounts: action.statusCounts,
        ttuEquivTotal: action.ttuEquivTotal,
        statusCountHeader: action.statusCountHeader
      }
    }
  }),

  on(actions.toggleColumn, (state, action) => {
    const newColumns = [...state.columns].map(col => {
      return {
        ...col,
        hidden: col.propertyName === action.columnName ? !col.hidden : col.hidden
      }
    })
    return {
      ...state,
      columns: newColumns
    }
  }),

  on(actions.reorderColumns, (state, action) => {
    return {
      ...state,
      columns: action.columns
    }
  }),

  on(actions.loadDropdownContentSuccess, (state, action) => {
    return {
      ...state,
      [action.optionKey]: state[action.filterKey].wasChanged || state[action.filterKey].filters.offset == 0 ? action.options.collection : [...state[action.optionKey], ...action.options.collection],
      [action.filterKey]: {
        filters: {
          ...state[action.filterKey].filters,
          limit: 50,
          total: action.options.total,
          offset: action.options.offset
        },
        wasChanged: false
      }
    }
  }),

  on(actions.updateParams, (state, action) => {
    return {
      ...state,
      [action.filterKey]: {
        filters: {
          ...state[action.filterKey].filters,
          [action.key]: action.value,
          ...(action.key !== "offset" && { offset: 0 })
        },
        wasChanged: action.key !== "offset"
      }
    }
  })
);

