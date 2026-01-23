import { BaseSearchCriteria } from "src/app/models/base-search-criteria.model";

export class DashboardSearchCriteria {
  tenantNames: string[];
  masterCompanyNames: string[];
  companyNames: string[];
  serviceTypes: string[];
  providers: string[];
  serviceBilledTos: string[];
}
