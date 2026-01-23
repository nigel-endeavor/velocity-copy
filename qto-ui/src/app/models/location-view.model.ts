import { AbstractBaseModel } from "./abstract-base-model";

export class LocationView extends AbstractBaseModel {
  orderId: number;
  companyName: string;
  endCustomerClientId: string;
  parentCompanyName: string;
  parentCompanyClientId: string;
  companyId: number;
  provisioner: string;
  clientOrderId: string;
  clientLocationId: string;
  locationName: string;
  locationStatus: string;
  countServices: number;
  services: string;
  completionDate: Date;
  progress: number;
  openJeops: string;
  openJeopResponsibilities: string;
  showJeopIcon: boolean;
  selected?: boolean;
  progressPercentage: number;
  address: string;
  address1: string;
  address2: string;
  city: string;
  stateProvince: string;
  postalCode: string;
}
