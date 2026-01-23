import { BaseSearchCriteria } from "./base-search-criteria.model";

export class CompanyViewSearchCriteria extends BaseSearchCriteria {
  type: string;
  tenantName: string;
  masterCustomerId: number;
  clientId: string;
}
