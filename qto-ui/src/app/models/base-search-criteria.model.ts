import { SortSearchParams } from '../interfaces/sort-search-params.interface';

export class BaseSearchCriteria implements SortSearchParams {
  offset: number = 0;
  limit: number = 25;
  sortDir: string;
  sortField: string;
  format: string;
  fields: string = '';
  headers: string = '';
}

export class DateSearchCriteriaField {
  date: string;
  comparison: string;
}
