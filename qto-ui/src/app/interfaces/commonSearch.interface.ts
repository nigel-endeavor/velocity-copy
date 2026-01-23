import { BaseSearchParams } from './base-search-params.interface';

export interface CommonDropdownSearchCriteria extends BaseSearchParams {
  type?: string,
  name?: string,
  value?: string,
  total: number,
  tenants?: string,
  orderId?: number | null,
  active?: boolean | null,
}

export interface CommonSearchCriteria {
  filters: CommonDropdownSearchCriteria,
  wasChanged: boolean,
}
