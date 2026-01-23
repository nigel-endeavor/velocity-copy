import { createReducer, on } from "@ngrx/store";
import { Company } from "src/app/models/company.model";
import * as actions from "./configuration.actions";
import { CommonColumn } from "src/app/interfaces/columns.interface";
import { Contact } from "../../../models/contact.model";
import { SubjectInterface } from "../../../models/subject.model";


export const configurationFeatureKey = 'configuration';

export interface ConfigurationState {
  isLoading: boolean,

  tenants: Company[],
  selectedTenant: Company | null,
  selectedCustomer: Company | null,
  billingContact: Contact | null,
  techContact: Contact | null,
  salesContact: Contact | null,
  authContact: Contact | null,
  selectedTab: string,

  userHasLookupAdmin: boolean

  provisioners: SubjectInterface[];
}

export const initialState: ConfigurationState = {
  isLoading: false,

  tenants: [],
  selectedTenant: null,
  selectedCustomer: null,
  billingContact: null,
  techContact: null,
  salesContact: null,
  authContact: null,

  selectedTab: 'Lookups',
  userHasLookupAdmin: false,

  provisioners: []
};

export const configurationReducer = createReducer(
  initialState,

  on(actions.loadTenantsSuccess, (state, action) => {
    return {
      ...state,
      tenants: action.tenants.collection,
      selectedTenant: action.tenants.collection[0]
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

  on(actions.setUserHasLookupAdmin, (state, action) => {
    return {
      ...state,
      userHasLookupAdmin: action.hasLookupAdmin
    }
  }),

  on(actions.setSelectedCustomerSuccess, (state, action) => {
    return {
      ...state,
      billingContact: action.billingContact,
      techContact: action.techContact,
      salesContact: action.salesContact,
      authContact: action.authContact,
      isLoading: false
    }
  }),

  on(actions.pageDestroyed, (state) => {
    return {
      ...state,
      billingContact: null,
      techContact: null,
      salesContact: null
    }
  }),

  on(actions.loadProvisionersSuccess, (state, action) => {
    return {
      ...state,
      provisioners: action.subjects
    }
  }),

);
