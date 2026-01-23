import { BaseSearchCriteria } from "./base-search-criteria.model";

export class AddressSearchCriteria extends BaseSearchCriteria {
  companyId: number;
  search: string;
  isLocation: boolean;
}