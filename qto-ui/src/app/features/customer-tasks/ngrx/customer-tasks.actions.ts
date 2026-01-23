import { createAction, props } from '@ngrx/store';
import { LookupValue } from '../../../models/lookup-value.model';
import { PaginatedResult } from '../../../models/paginated-result.model';
import { Company } from '../../../models/company.model';
import { CompanyView } from '../../../models/company-view.model';
import { CommonColumn } from '../../../interfaces/columns.interface';
import { SubjectInterface } from '../../../models/subject.model';
import { Contact } from '../../../models/contact.model';
import { CompanyTask } from '../../../models/company-task.model';
import { TaskGroup } from '../../../models/task-group.model';

const actionKey = '[CustomerTasks]';

export const pageDestroyed = createAction(
  `${actionKey} page Destroyed`,
);

export const loadLookupValuesByKey = createAction(
  `${actionKey} Load Lookup Values by key`,
  props<{key: string, lookupKey: string }>()
);

export const loadLookupValuesByKeyFailure = createAction(
  `${actionKey} Load Lookup Values Failure`,
  props<any>()
);

export const loadLookupValuesByKeySuccess = createAction(
  `${actionKey} Load Lookup Values Success`,
  props<{values: LookupValue[], key: string}>()
);

export const setUpdatedItem = createAction(
  `${actionKey} Updated Item`,
  props<{ updatedItem: CompanyView }>()
);

export const addSelectedItems = createAction(
  `${actionKey} Update Add Selected Items`,
  props<{ value: CompanyView[] }>()
);

export const removeUnselectedItems = createAction(
  `${actionKey} Update Remove Selected Items`,
  props<{ value: CompanyView[] }>()
);

export const loadDropdownContentFailure = createAction(
  `${actionKey} Load DropdownContent Failure`,
  props<any>()
);

export const reorderColumns = createAction(
  `${actionKey} Reorder Columns`,
  props<{ columns: CommonColumn[] }>()
);

export const loadProvisioners = createAction(
  `${actionKey} Load Provisioners`
);

export const loadProvisionersFailure = createAction(
  `${actionKey} Load Provisioners Failure`,
  props<any>()
);

export const loadProvisionersSuccess = createAction(
  `${actionKey} Load Provisioners Success`,
  props<{ subjects: SubjectInterface[] }>()
);

export const loadCustomerTasks = createAction(
  `${actionKey} Load Customer Tasks`,
  props<{ customerId: number }>()
);

export const setSelectedCustomer = createAction(
  `${actionKey} Set Selected Customer`,
  props<{ customer: Company | null }>()
);

export const setSelectedBillingContact = createAction(
  `${actionKey} Set Selected Billing Contact`,
  props<{ billingContact: Contact | null }>()
);

export const setSelectedTechContact = createAction(
  `${actionKey} Set Selected Technical Contact`,
  props<{ techContact: Contact | null }>()
);

export const setSelectedSalesContact = createAction(
  `${actionKey} Set Selected Sales Contact`,
  props<{ salesContact: Contact | null }>()
);

export const setSelectedTab = createAction(
  `[Customer Management] Set Selected Tab`,
  props<{ tab: string }>()
);

export const loadTenants = createAction(
  `${actionKey} Load Tenants`,
);

export const loadTenantsSuccess = createAction(
  `${actionKey} Load Tenants Success`,
  props<{ tenants: PaginatedResult<Company> }>()
);

export const setSelectedTenant = createAction(
  `${actionKey} Set Selected Tenant`,
  props<{ tenant: Company }>()
);

export const loadSelectedCustomer = createAction(
  `${actionKey} Load Selected Customer`,
  props<{ customerId: number }>()
);

export const setSelectedCustomerSuccess = createAction(
  `${actionKey} Set Selected Customer Success`,
  props<{ billingContact: Contact, techContact: Contact, salesContact: Contact, authContact: Contact, customerTasks: CompanyTask[], taskGroups: TaskGroup[] }>()
);
export const clearFilters = createAction(
  `${actionKey} clear filters`,
);

export const loadCustomerTasksSuccess = createAction(
  `${actionKey} load customer tasks success`,
  props<{ customerTasks: CompanyTask[]}>()
);

export const loadTaskGroup = createAction(
  `${actionKey} load task group`,
  props<{ taskGroupId: number }>()
);

export const setTaskGroup = createAction(
  `${actionKey} set task group`,
  props<{ taskGroup: TaskGroup | null }>()
);

export const loadTaskGroupSuccess = createAction(
  `${actionKey} load task group success`,
  props<{ taskGroup: TaskGroup}>()
);

export const saveCustomerTask = createAction(
  `${actionKey} save customer task`,
  props<{ customerTask: CompanyTask }>()
);

export const saveCustomerTaskSuccess = createAction(
  `${actionKey} save customer task success`,
  props<{ customerId: number, customerTask: CompanyTask }>()
);
