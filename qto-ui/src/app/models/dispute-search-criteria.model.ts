import { BaseSearchCriteria } from "./base-search-criteria.model";

export class DisputeSearchCriteria extends BaseSearchCriteria {
  serviceId: number;
  locationId: number;
}