import { BaseSearchCriteria, DateSearchCriteriaField } from "./base-search-criteria.model";

export class ServiceViewSearchCriteria extends BaseSearchCriteria {
  search: string;
  locationId: number[];
  locationIdComparison: any[];
  address: string;
  status: string;
  provisioner: string[];
  projectManager: string;
  provider: string;
  customerRequestedInstall = new DateSearchCriteriaField();
  siteSurveyDue = new DateSearchCriteriaField();
  siteSurveySubmit = new DateSearchCriteriaField();
  providerOrderSubmitted = new DateSearchCriteriaField();
  networkProviderFoc = new DateSearchCriteriaField();
  dataProvisioningComplete = new DateSearchCriteriaField();
  followUpDate = new DateSearchCriteriaField();
  clientLocationInfo: string;
  clientLocationType: string;
  hideTerminalStatuses: boolean;
  created = new DateSearchCriteriaField();
  companyName: string;
  endCustomerClientId: string;
  statusAge: number[];
  statusAgeComparison: any[];
  providerCircuitId: string;
  serviceType: string[];
  mrc: number[];
  mrcComparison: any[];
  nrc: number[];
  nrcComparison: any[];
  lconPhone: string;
  inventoryAddedDate = new DateSearchCriteriaField();
  linkBundleType: string;
  linkBundleFrom: string;
}
