import { AbstractBaseModel } from "./abstract-base-model";

export class SurchargeType extends AbstractBaseModel {
  type: string;
  level: string;
  amount: number;
  startDate: Date;
  endDate: Date;
}
