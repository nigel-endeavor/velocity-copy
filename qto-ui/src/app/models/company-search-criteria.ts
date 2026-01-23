import { BaseSearchCriteria } from "./base-search-criteria.model";

export class CompanySearchCriteria extends BaseSearchCriteria {
  type: string;
  tenants: string[];
  masterCustomers: string[];
  active: boolean;
  name?: string;
  total: number;
}
