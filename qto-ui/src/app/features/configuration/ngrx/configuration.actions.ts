import { createAction, props } from "@ngrx/store";
import { Company } from "src/app/models/company.model";
import { PaginatedResult } from "src/app/models/paginated-result.model";
import { Contact } from "../../../models/contact.model";
import { SubjectInterface } from "../../../models/subject.model";


export const updateFilters = createAction(
  '[Configuration] update filters',
  props<{ key: string, value: string | number | string[] | boolean }>()
);

export const updateSort = createAction(
  '[Configuration] update sort',
  props<{ sort: {dir: string, col: string } }>()
);

export const clearFilters = createAction(
  '[Configuration] clear filters',
);

export const pageDestroyed = createAction(
  '[CostHistory] page destroyed',
);

export const loadTenants = createAction(
  '[Configuration] Load Tenants',
);

export const loadTenantsSuccess = createAction(
  '[Configuration] Load Tenants Success',
  props<{ tenants: PaginatedResult<Company> }>()
);

export const setSelectedTenant = createAction(
  '[Configuration] Set Selected Tenant',
  props<{ tenant: Company }>()
);

export const setSelectedCustomer = createAction(
  '[Configuration] Set Selected Customer',
  props<{ customer: Company | null }>()
);

export const setSelectedBillingContact = createAction(
  '[Configuration] Set Selected Billing Contact',
  props<{ billingContact: Contact | null }>()
);

export const setSelectedTechContact = createAction(
  '[Configuration] Set Selected Technical Contact',
  props<{ techContact: Contact | null }>()
);

export const setSelectedSalesContact = createAction(
  '[Configuration] Set Selected Sales Contact',
  props<{ salesContact: Contact | null }>()
);

export const loadSelectedCustomer = createAction(
  '[Configuration] Load Selected Customer',
  props<{ customerId: number }>()
);

export const setSelectedTab = createAction(
  '[Configuration] Set Selected Tab',
  props<{ tab: string }>()
);

export const setUserHasLookupAdmin = createAction(
  '[Configuration] Set User Has Lookup Admin',
  props<{ hasLookupAdmin: boolean }>()
);

export const setSelectedCustomerSuccess = createAction(
  '[Configuration] Set Selected Customer Success',
  props<{ billingContact: Contact, techContact: Contact, salesContact: Contact, authContact: Contact }>()
);

export const loadProvisioners = createAction(
  '[Configuration] Load Provisioners'
);

export const loadProvisionersFailure = createAction(
  '[Configuration] Load Provisioners Failure',
  props<any>()
);

export const loadProvisionersSuccess = createAction(
  '[Configuration] Load Provisioners Success',
  props<{ subjects: SubjectInterface[] }>()
);
