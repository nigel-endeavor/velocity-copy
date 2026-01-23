import { createReducer, on } from '@ngrx/store';
import * as actions from './customer-details.actions';
import { LookupValue } from '../../../models/lookup-value.model';
import { MultiEditFailureInterface } from '../../abstract-table/interfaces/multiEditFailure.interface';
import { CommonColumn } from '../../../interfaces/columns.interface';
import { ComparableDateRange } from '../../../interfaces/date-range.interface';
import { Company } from '../../../models/company.model';
import { CompanyView } from '../../../models/company-view.model';
import { LevelOfEffort } from '../../../models/level-of-effort.model';
import { CommonSearchCriteria } from '../../../interfaces/commonSearch.interface';
import { SubjectInterface } from 'src/app/models/subject.model';
import { Contact } from '../../../models/contact.model';
import { COMPANY_VIEW_COLUMNS } from '../data/customer-details-columns.const';
import { CompanyTask } from '../../../models/company-task.model';
import { TaskGroup } from '../../../models/task-group.model';

export const customerDetailsFeatureKey = 'customerDetails';

export interface CustomersMeta {
  exampleTotal: number;
}

export interface CustomerDetailsState {
  filters: {
    type: string;
    tenantName: string;
    masterCustomerId: number;
    offset: number;
    limit: number;
    sortDir: string;
    sortField: string;
    format: string;
    fields: string;
    headers: string;
    active: boolean;
  },
  columns: CommonColumn[],

  customers: Company[];
  masterCustomers: (Company | Company[]  | LookupValue)[];
  isLoading: boolean;
  cardViewSelected: boolean;
  cantUpdate: MultiEditFailureInterface[];
  updatedItem: any;
  selectedItems: CompanyView[];

  tenants: Company[];

  taskGroupsSearchCriteria: CommonSearchCriteria;
  selectedTenant: Company | null;
  selectedCustomer: Company | null;
  meta: CustomersMeta | null;

  billingContact: Contact | null;
  techContact: Contact | null;
  salesContact: Contact | null;
  authContact: Contact | null;
  selectedTab: string;

  provisioners: SubjectInterface[];
  customerTasks: CompanyTask[];
  taskGroups: TaskGroup[];
}

export const initialState: CustomerDetailsState = {
  filters: {
    type: '',
    tenantName: '',
    masterCustomerId: 0,
    offset: 0,
    limit: 25,
    sortDir: 'ASC',
    sortField: 'name',
    format: '',
    fields: '',
    headers: '',
    active: true
  },
  columns: COMPANY_VIEW_COLUMNS,

  customers: [],
  masterCustomers: [],
  cantUpdate: [],
  isLoading: false,
  cardViewSelected: false,
  updatedItem: {},
  selectedItems: [],

  taskGroupsSearchCriteria: {
    filters: {
      value: '',
      active: true,
      total: 0,
      limit: 50,
      offset: 0
    },
    wasChanged: false
  },
  meta: null,

  tenants: [],

  selectedTenant: null,
  selectedCustomer: null,
  billingContact: null,
  techContact: null,
  salesContact: null,
  authContact: null,

  provisioners: [],
  customerTasks: [],
  taskGroups: [],

  selectedTab: 'Details',
};

export const customerDetailsReducer = createReducer(
  initialState,

  on(actions.loadLookupValuesByKeySuccess, (state, action) => {
    return {
      ...state,
      [action.key]: action.values
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

  on(actions.loadTenantsSuccess, (state, action) => {
    return {
      ...state,
      tenants: action.tenants.collection,
      selectedTenant: action.tenants.collection[0]
    }
  }),

  on(actions.setSelectedTenant, (state, action) => {
    return {
      ...state,
      selectedTenant: action.tenant,
      selectedMasterCustomer: null,
      selectedEndCustomer: null
    }
  }),

  on(actions.setSelectedCustomer, (state, action) => {
    return {
      ...state,
      selectedCustomer: action.customer
    }
  }),

  on(actions.loadSelectedCustomer, (state) => {
    return {
      ...state,
      billingContact: null,
      techContact: null,
      salesContact: null,
      isLoading: true
    }
  }),

  on(actions.setSelectedCustomerSuccess, (state, action) => {
    return {
      ...state,
      billingContact: action.billingContact,
      techContact: action.techContact,
      salesContact: action.salesContact,
      authContact: action.authContact,
      customerTasks: action.customerTasks,
      taskGroups: action.taskGroups,
      isLoading: false
    }
  }),

  on(actions.setSelectedBillingContact, (state, action) => {
    return {
      ...state,
      billingContact: action.billingContact
    }
  }),

  on(actions.setSelectedTechContact, (state, action) => {
    return {
      ...state,
      techContact: action.techContact
    }
  }),

  on(actions.setSelectedSalesContact, (state, action) => {
    return {
      ...state,
      salesContact: action.salesContact
    }
  }),

  on(actions.setSelectedTab, (state, action) => {
    return {
      ...state,
      selectedTab: action.tab
    }
  }),

  on(actions.loadProvisionersSuccess, (state, action) => {
    return {
      ...state,
      provisioners: action.subjects
    }
  }),

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

  on(actions.loadCustomerTasksSuccess, (state, action) => {
    return {
      ...state,
      customerTasks: action.customerTasks
    }
  }),

  on(actions.setLoading, (state, action) => {
    return {
      ...state,
      isLoading: action.loading
    }
  })
);
