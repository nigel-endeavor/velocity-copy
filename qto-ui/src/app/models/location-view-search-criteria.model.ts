import { BaseSearchCriteria, DateSearchCriteriaField } from "./base-search-criteria.model";

export class LocationViewSearchCriteria extends BaseSearchCriteria {
  search: string;
  companyName: string;
  endCustomerClientId: string;
  provisioner: string[];
  clientOrderId: string;
  clientLocationId: string;
  locationName: string;
  locationStatus: string;
  countServices: number[];
  countServicesComparison: any[]; //TODO: comparison params
  services: string;
  completionDate = new DateSearchCriteriaField();
  inventoryAddedDate = new DateSearchCriteriaField();
  progress: number[];
  progressComparison: number[];
  openJeops: string;
  hideTerminalStatuses: boolean;
}
