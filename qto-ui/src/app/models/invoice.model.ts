import { AbstractBaseModel } from "./abstract-base-model";

export class Invoice extends AbstractBaseModel {
  clientName: string;
  invoiceNumber: string;
  invoiceStatus: string;
  totalCharges: number;
  invoiceStart: Date;
  invoiceEnd: Date;
  generatedBy: string;
  generatedDate: Date;
}
