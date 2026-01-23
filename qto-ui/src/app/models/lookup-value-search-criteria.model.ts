import { BaseSearchCriteria } from './base-search-criteria.model';

export class LookupValueSearchCriteria extends BaseSearchCriteria {
  override limit = 10000;
  typeCode: string;
  companyId: number;
  active: boolean;
  value: string;
  parentId: number;
}
