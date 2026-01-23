import { createReducer, on } from '@ngrx/store';
import * as actions from './master-customers-worklist.actions';
import { LookupValue } from '../../../models/lookup-value.model';
import { MultiEditFailureInterface } from '../../abstract-table/interfaces/multiEditFailure.interface';
import { MASTER_CUSTOMERS_WORKLIST_COLUMNS } from '../data/master-customers-worklist-columns.consts';
import { CommonColumn } from '../../../interfaces/columns.interface';
import { Company } from '../../../models/company.model';
import { CompanyView } from '../../../models/company-view.model';
import { CommonSearchCriteria } from '../../../interfaces/commonSearch.interface';
import { Subject } from 'rxjs';
import { SubjectInterface } from '../../../models/subject.model';

export const masterCustomersWorklistFeatureKey = 'masterCustomersWorklist';

export interface CustomersMeta {
  exampleTotal: number;
}

export interface MasterCustomersWorklistState {
  filters: {
    search: string;
    offset: number;
    limit: number;
    sortDir: string;
    sortField: string;
    format: string;
    fields: string;
    headers: string;
    active: boolean;
    onboarding: boolean;
    type: string;
  };

  customers: Company[];
  assignables: SubjectInterface[];
  assignablesSearchCriteria: CommonSearchCriteria;
  masterCustomers: (Company | Company[]  | LookupValue)[];
  isLoading: boolean;
  cardViewSelected: boolean;
  cantUpdate: MultiEditFailureInterface[];
  columns: CommonColumn[];
  updatedItem: any;
  selectedItems: CompanyView[];

  masterCustomerSearchCriteria: CommonSearchCriteria;
  selectedTenant: Company | null;
  selectedCustomer: Company | null;
  meta: CustomersMeta | null;
}

export const initialState: MasterCustomersWorklistState = {
  filters: {
    search: '',

    offset: 0,
    limit: 25,
    sortDir: '',
    sortField: '',
    format: '',
    fields: '',
    headers: '',
    active: true,
    onboarding: false,
    type: 'Master Customer'
  },
  customers: [],
  assignables: [],
  masterCustomers: [],
  cantUpdate: [],
  isLoading: false,
  cardViewSelected: false,
  columns: MASTER_CUSTOMERS_WORKLIST_COLUMNS,
  updatedItem: {},
  selectedItems: [],
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
  },
  assignablesSearchCriteria: {
    filters: {
      name: '',
      total: 0,
      limit: 50,
      offset: 0
    },
    wasChanged: false
  },
  meta: null,
  selectedTenant: null,
  selectedCustomer: null,
};

export const masterCustomersWorklistReducer = createReducer(
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
        [action.key]: action.value,
        ...(action.key !== 'offset' && { offset: 0 }),
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

  on(actions.loadLookupValuesByKeySuccess, (state, action) => {
    return {
      ...state,
      [action.key]: action.values
    }
  }),

  on(actions.toggleView, (state) => {
    return {
      ...state,
      cardViewSelected: !state.cardViewSelected
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

  on(actions.toggleDaterangeColumn, (state, action) => {
    const newColumns = [...state.columns].map(col => {
      return {
        ...col,
        active: col.propertyName === action.columnName ? !col.active : false
      }
    })
    return {
      ...state,
      columns: newColumns
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

  on(actions.addSelectedItems, (state, action) => {
    const selectedIds = state.selectedItems.map(item => item.id)
    const newlySelected = action.value.filter(item => !selectedIds.includes(item.id));
    return {
      ...state,
      selectedItems: [
        ...state.selectedItems,
        ...newlySelected
      ]
    }
  }),

  on(actions.removeUnselectedItems, (state, action) => {
    const removedIds = action.value?.map(item => item.id) || [];
    const filteredItems = state.selectedItems.filter(item => !removedIds.includes(item.id));
    return {
      ...state,
      selectedItems: [
        ...filteredItems
      ]
    }
  }),

  on(
    actions.sendMultieEditFailure,
    (state, action) => {
      const remainingIds = action.cantUpdate?.map(item => item.service.id) || [];
      const filteredItems = state.selectedItems.filter(item => remainingIds.includes(item.id));
      return {
        ...state,
        selectedItems: [
          ...filteredItems
        ],
        cantUpdate: action.cantUpdate
      }
    }),

  on(actions.reorderColumns, (state, action) => {
    return {
      ...state,
      columns: action.columns
    }
  }),

  on(actions.loadMetaDataSuccess, (state, action) => {
    return {
      ...state,
      meta: action
    }
  }),

  on(actions.pageDestroyed, (state) => {
    return {
      ...state,
      customers: [],
      masterCustomers: []
    }
  }),

  on(actions.loadAssignablesSuccess, (state, action) => {
    return {
      ...state,
      assignables: action.subjects,
      assignablesSearchCriteria: {
        ...state.assignablesSearchCriteria,
        limit: 50,
        // TODO update this after BE is ready
        total: 10000,
        offset: 0
      }
    }
  })
);
