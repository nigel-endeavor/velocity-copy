import { BaseSearchCriteria } from "./base-search-criteria.model";

export class TenantOwnedLookupValueSearchCriteria extends BaseSearchCriteria {
  typeCode: string;
  active: boolean;
  configured: boolean;
  value: string;
  companyId: number;
  includeAllTenants: boolean;
}