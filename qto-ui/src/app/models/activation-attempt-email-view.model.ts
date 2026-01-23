import { AbstractBaseModel } from "./abstract-base-model";

export class ActivationAttemptEmailView extends AbstractBaseModel {
  address1: string;
  city: string;
  stateProvince: string;
  postalCode: string;
  county: string;
  clientServiceId: string;
  provider: string;
  clientLocationId: string;
  clientLocationInfo: string;
  companyId: number;
  totalAppointmentTime: number;
  voipCompleteDate: string;
  networkCompleteDate: string;
}