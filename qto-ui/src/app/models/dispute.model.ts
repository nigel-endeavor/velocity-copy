import { getCurrency } from "../utilities";
import { AbstractBaseModel } from "./abstract-base-model";

export class Dispute extends AbstractBaseModel {
  openDate: Date | null;
  disputeStatus: string;
  disputeType: string;
  disputeAssignment: string;
  summaryBill: string | null;
  invoiceNum: string;
  amountDisputedMrc: number;
  amountDisputedNrc: number;
  vendorTrackingNum: string;
  disputeFollowUpDate: Date | null;
  creditRecognized: Date | null;
  billingReviewComplete: Date | null;
  disputeClosedDate: Date | null;
  serviceId: number;
  initialNote: string;
  initialNoteInternalOnly: boolean;
  realizedCredit: number;
  realizedMrcAdjustment: number;
  annualizedMrcSave: number;

  setMrc(value: string) {
    this.amountDisputedMrc = getCurrency(value);
  }
  setNrc(value: string) {
    this.amountDisputedNrc = getCurrency(value);
  }
  setRealizedCredit(value: string) {
    this.realizedCredit = getCurrency(value);
  }
  setRealizedMrcAdjustment(value: string) {
    this.realizedMrcAdjustment = getCurrency(value);
  }
}