import { AbstractBaseModel } from "./abstract-base-model";

export class WipServiceView extends AbstractBaseModel {
  serviceId: number;
  locationId: number;
  orderId: number;
  companyName: string;
  provisionerId: number;
  vertekProjectManagerId: number;
  clientProjectManagerId: number;
  provisioner: string;
  vertekProjectManager: string;
  clientProjectManager: string;
  clientOrderId: string;
  clientLocationId: string;
  locationName: string;
  address1: string;
  address2: string;
  city: string;
  stateProvince: string;
  clientServiceId: string;
  provider: string;
  serviceStatus: string;
  serviceMrc: number;
  dataProvisioningCompleteDate: Date;
}
