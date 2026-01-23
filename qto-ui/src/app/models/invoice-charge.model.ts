import { AbstractBaseModel } from "./abstract-base-model";

export class InvoiceCharge extends AbstractBaseModel {
  invoiceId: number;
  locationId: number;
  chargeCredit: string;
  unitCost: number;
  totalAmount: number;
  invoicedAmount: number;
  itemDesc: string;
  chargeDesc: string;
  chargeType: string;
  chargeLevel: string;
  masterCustomerName: string;
  endCustomerName: string;
  billableEventMilestoneDescription: string;
  billableEventDate: Date;
  previouslyBilled: number;
}
