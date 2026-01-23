import { BaseSearchCriteria } from "./base-search-criteria.model";

export class CostHistorySearchCriteria extends BaseSearchCriteria {
  serviceId: number;
  locationId: number;
}