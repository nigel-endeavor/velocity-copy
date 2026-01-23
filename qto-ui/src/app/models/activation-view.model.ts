import { AbstractBaseModel } from "./abstract-base-model";

export class ActivationView extends AbstractBaseModel {
  serviceId: number;
  locationId: number;
  orderId: number;
  parentCompanyName: string;
  clientServiceId: string;
  scheduledAttemptStatus: string;
  internalTechAssigned: string;
  scheduledCheckInTime: Date;
  lastUpdateBy: string;
  clientLocationType: string;
  clientLocationInfo: string;
  ttuEquivalent: string;
  selected?: boolean;
  scheduledCheckInFormatted: string;
  fieldTechCheckInFormatted: string;
}

export class ActivationWorklistMeta {
  statusCounts: any;
  ttuEquivalentTotal: number;
}
