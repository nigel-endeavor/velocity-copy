import { createAction, props } from '@ngrx/store';
import { LookupValue } from '../../../models/lookup-value.model';
import { ComparableDateRange } from '../../../interfaces/date-range.interface';
import { PaginatedResult } from '../../../models/paginated-result.model';
import { Company } from '../../../models/company.model';
import { CompanyView } from '../../../models/company-view.model';
import { MultiEditFailureInterface } from '../../../interfaces/multiEditFailure.interface';
import { CommonColumn } from '../../../interfaces/columns.interface';
import { CustomersMeta } from './customer-details.reducer';
import { SubjectInterface } from '../../../models/subject.model';
import { Contact } from '../../../models/contact.model';
import { CompanyTask } from '../../../models/company-task.model';
import { TaskGroup } from '../../../models/task-group.model';
import { CustomerDetailsFilterKeys, CustomerDetailsOptionsKeys } from '../data/customer-details.consts';

export const pageDestroyed = createAction(
  '[CustomerDetails] page Destroyed',
);

export const loadLookupValuesByKey = createAction(
  '[CustomerDetails] Load Lookup Values by key',
  props<{key: string, lookupKey: string }>()
);

export const loadLookupValuesByKeyFailure = createAction(
  '[CustomerDetails] Load Lookup Values Failure',
  props<any>()
);

export const loadLookupValuesByKeySuccess = createAction(
  '[CustomerDetails] Load Lookup Values Success',
  props<{values: LookupValue[], key: string}>()
);

export const loadMetaData = createAction(
  '[CustomerDetails] Load Meta Data',
);

export const loadMetaDataFailure = createAction(
  '[CustomerDetails] Load Meta Data Failure',
  props<any>()
);

export const loadMetaDataSuccess = createAction(
  '[CustomerDetails] Load Meta Data Success',
  props<CustomersMeta>()
);

export const toggleColumn = createAction(
  '[CustomerDetails] Toggle Column',
  props<{columnName: string}>()
);

export const toggleDaterangeColumn = createAction(
  '[CustomerDetails] Toggle DateRange Column',
  props<{columnName: string}>()
);

export const updateDispute = createAction(
  '[CustomerDetails] Dispute update',
  props<{ dispute: CompanyView }>()
);

export const updateDisputeFailure = createAction(
  '[CustomerDetails] Dispute Update Failure',
  props<any>()
);

export const setUpdatedItem = createAction(
  '[CustomerDetails] Updated Item',
  props<{ updatedItem: CompanyView }>()
);

export const addSelectedItems = createAction(
  '[CustomerDetails] Update Add Selected Items',
  props<{ value: CompanyView[] }>()
);

export const removeUnselectedItems = createAction(
  '[CustomerDetails] Update Remove Selected Items',
  props<{ value: CompanyView[] }>()
);

export const loadDropdownContentFailure = createAction(
  '[CustomerDetails] Load DropdownContent Failure',
  props<any>()
);

export const reorderColumns = createAction(
  '[CustomerDetails] Reorder Columns',
  props<{ columns: CommonColumn[] }>()
);

export const loadProvisioners = createAction(
  '[CustomerDetails] Load Provisioners'
);

export const loadProvisionersFailure = createAction(
  '[CustomerDetails] Load Provisioners Failure',
  props<any>()
);

export const loadProvisionersSuccess = createAction(
  '[CustomerDetails] Load Provisioners Success',
  props<{ subjects: SubjectInterface[] }>()
);

export const setSelectedCustomer = createAction(
  '[CustomerDetails] Set Selected Customer',
  props<{ customer: Company | null }>()
);

export const setSelectedBillingContact = createAction(
  '[CustomerDetails] Set Selected Billing Contact',
  props<{ billingContact: Contact | null }>()
);

export const setSelectedTechContact = createAction(
  '[CustomerDetails] Set Selected Technical Contact',
  props<{ techContact: Contact | null }>()
);

export const setSelectedSalesContact = createAction(
  '[CustomerDetails] Set Selected Sales Contact',
  props<{ salesContact: Contact | null }>()
);

export const setSelectedTab = createAction(
  '[Customer Management] Set Selected Tab',
  props<{ tab: string }>()
);

export const loadTenants = createAction(
  '[CustomerDetails] Load Tenants',
);

export const loadTenantsSuccess = createAction(
  '[CustomerDetails] Load Tenants Success',
  props<{ tenants: PaginatedResult<Company> }>()
);

export const setSelectedTenant = createAction(
  '[CustomerDetails] Set Selected Tenant',
  props<{ tenant: Company }>()
);

export const loadSelectedCustomer = createAction(
  '[CustomerDetails] Load Selected Customer',
  props<{ customerId: number }>()
);

export const setSelectedCustomerSuccess = createAction(
  '[CustomerDetails] Set Selected Customer Success',
  props<{ billingContact: Contact, techContact: Contact, salesContact: Contact, authContact: Contact, customerTasks: CompanyTask[], taskGroups: TaskGroup[] }>()
);

export const updateFilters = createAction(
  '[CustomerDetails] update filters',
  props<{ key: string, value: string | number | string[] | boolean }>()
);

export const updateParams = createAction(
  '[CustomerDetails] Load DropdownContent params',
  props<{ key: string, value: string | number, filterKey: CustomerDetailsFilterKeys, optionKey: CustomerDetailsOptionsKeys, orderId?: number | null }>()
);

export const updateSort = createAction(
  '[CustomerDetails] update sort',
  props<{ sort: {dir: string, col: string } }>()
);

export const clearFilters = createAction(
  '[CustomerDetails] clear filters',
);

export const loadCustomerTasksSuccess = createAction(
  '[CustomerDetails] load customer tasks success',
  props<{ customerTasks: CompanyTask[]}>()
);

export const loadTaskGroupsSuccess = createAction(
  '[CustomerDetails] load task groups success',
  props<{ taskGroups: TaskGroup[]}>()
);

export const saveCustomerTask = createAction(
  '[CustomerDetails] save customer task',
  props<{ customerTask: CompanyTask }>()
);

export const saveCustomerTaskSuccess = createAction(
  '[CustomerDetails] save customer task success',
  props<{ customerTask: CompanyTask }>()
);

export const setLoading = createAction(
  '[CustomerDetails] set loading',
  props<{ loading: boolean }>()
);
