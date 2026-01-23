import { BaseSearchCriteria, DateSearchCriteriaField  } from './base-search-criteria.model';
import { ComparableDateRange } from '../interfaces/date-range.interface';

export class ActivationViewSearchCriteria extends BaseSearchCriteria {
  search: string;
  clientServiceId: string;
  scheduledAttemptStatus: string[] | undefined;
  internalTechAssigned: string;
  scheduledCheckInTime: DateSearchCriteriaField | ComparableDateRange;
  lastUpdateBy: string;
  clientLocationType: string;
  clientLocationInfo: string;
}
