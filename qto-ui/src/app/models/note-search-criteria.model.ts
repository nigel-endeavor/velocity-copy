import { BaseSearchCriteria } from "./base-search-criteria.model";

export class NoteSearchCriteria extends BaseSearchCriteria {
  [key: string]: any;
  category: string;
  createdBy: string;
  internalOnly: boolean;
  createdDate: Date[];
  createdDateComparison: any[];
}
