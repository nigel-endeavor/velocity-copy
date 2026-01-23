import { BaseSearchCriteria } from "./base-search-criteria.model";

export class JeopSearchCriteria extends BaseSearchCriteria {
  [key: string]: any;
  level: string;
  startDate: Date[];
  startDateComparison: any[];
  endDate: Date[];
  endDateComparison: any[];
  isOpen: boolean;
}
