import { AbstractBaseModel } from "./abstract-base-model";

export class ActivationAttemptView extends AbstractBaseModel {
    activationAttemptId: number;
    companyName: string;
    orderId: number;
    clientOrderId: number;
    locationId: number;
    locationName: string;
    clientLocationId: string;
    serviceId: number;
    clientServiceId: string;
    provider: string;
    serviceType: string;
    clientLocationType: string;
    clientLocationInfo: string;
    attemptNumber: number;
    fieldTechCheckIn: Date;
    fieldTechCheckOut: Date;
    aaMonth: number;
    aaYear: number;
    serviceActivationInterval: number;
    scheduledAttemptStatus: string;
    locationActivationInterval: number;
    dataProvisioningComplete: Date;
    serviceComplete: Date;
  }