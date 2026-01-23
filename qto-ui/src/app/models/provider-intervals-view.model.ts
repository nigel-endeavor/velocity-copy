import { AbstractBaseModel } from "./abstract-base-model";

export class ProviderIntervalsView extends AbstractBaseModel {
  intervalInstanceId: number;
  serviceId: number;
  locationId: number;
  orderId: number;
  companyName: string;
  clientOrderId: string;
  clientLocationId: string;
  locationName: string;
  clientServiceId: string;
  provider: string;
  serviceType: string;
  intervalTypeCode: string;
  intervalTypeDesc: string;
  openMilestoneCode: string;
  startDate: Date;
  closeMilestoneCode: string;
  endDate: Date;
  calendarDayIntervalTime: number;
  providerCalendarDayDeductTime: number;
  customerCalendarDayDeductTime: number;
  clientCalendarDayDeductTime: number;
  businessDayIntervalTime: number;
  providerBusinessDayDeductTime: number;
  clientBusinessDayDeductTime: number;
  serviceActive: number;
}
