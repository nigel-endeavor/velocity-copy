import { BaseSearchParams } from './base-search-params.interface';

export interface SortSearchParams extends BaseSearchParams {
  sortDir: string;
  sortField: string;
}
