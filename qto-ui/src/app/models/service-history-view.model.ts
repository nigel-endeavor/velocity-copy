import { AbstractBaseModel } from "./abstract-base-model";

export class ServiceHistoryView extends AbstractBaseModel {
  locationId: number;
  orderId: number;
  provider: string;
  orderType: string;
  subOrderType: string;
  providerOrderNum: string;
  parentServiceId: number;
  created: Date;
  complete: Date;
  parentServiceLink: string;
}