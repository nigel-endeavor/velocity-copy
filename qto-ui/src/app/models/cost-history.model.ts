import { AbstractBaseModel } from "./abstract-base-model";

export class CostHistory extends AbstractBaseModel {
  serviceId: number;
  costType: string;
  oldValue: number;
  newValue: number;
  changeReason: string;
  updateDate: Date;
  updateBy: string;
  parentServiceId: number;
  locationId: number;
}

export class CostHistoryMeta {
  costChangeThisYear: number;
  costChangeLifetime: number;
}