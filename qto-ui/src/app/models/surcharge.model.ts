import { Type } from "class-transformer";
import { AbstractBaseModel } from "./abstract-base-model";
import { SurchargeType } from "./surcharge-type.model";

export class Surcharge extends AbstractBaseModel {
  @Type(() => SurchargeType) surchargeType: SurchargeType;
  surchargeDate: Date;
  addedBy: string;
  invoiceChargeId: number;
  finalized: boolean;
}
