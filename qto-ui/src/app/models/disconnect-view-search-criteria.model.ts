import { BaseSearchCriteria, DateSearchCriteriaField } from "./base-search-criteria.model";

export class DisconnectViewSearchCriteria extends BaseSearchCriteria {
  search: string;
  locationId: number[];
  locationIdComparison: any[];
  clientLocationId: string;
  companyName: string;
  endCustomerClientId: string;
  serviceType: string[];
  address: string;
  provisioner: string[];
  disconnectReason: string[];
  status: string;
  provider: string;
  providerOrderSubmitted = new DateSearchCriteriaField();
  customerRequestedDisconnect = new DateSearchCriteriaField();
  networkProviderFoc = new DateSearchCriteriaField();
  complete = new DateSearchCriteriaField();
  created = new DateSearchCriteriaField();
  statusAge: number[];
  statusAgeComparison: any[];
  mrc: number[];
  mrcComparison: any[];
  earlyTerminationFee: number[];
  earlyTerminationFeeComparison: any[];
  billingReviewComplete = new DateSearchCriteriaField();
}
