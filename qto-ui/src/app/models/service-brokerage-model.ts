import { getCurrency, getPercent } from "../utilities";
import { AbstractBaseModel } from "./abstract-base-model";

export class ServiceBrokerage extends AbstractBaseModel {
  serviceId: number;
  submittedInAdvToProvider: boolean;
  parentTsd: string;
  submittedInAdvToTsd: boolean;
  cieTeamedDealInfo: string;
  commissionReductionPercent: number;
  netProviderPoints: string;
  promotions: string;
  spiffAmount: number;
  commissionableMrc: number;
  commissionableNrc: number;
  commissionableArc: number;
  commissionsSupplier: string;
  commissionsAccountNumber: string;
  subAgentPercent: number;
  subAgent: string;
  subAgentRep: string | undefined;
  secondaryAgency: string;
  secondaryAgencyRep: string | undefined;
  agent: string;
  agentRep: string | undefined;
  agentPercent: number;
  referral: boolean;
  referralName: string | undefined;
  referralPercent: number | undefined;
  customerOrderAlias: string;
  engineerResource: string;
  engineerResourceAllocation: number | undefined;
  commissionPaymentType: string;
  expectedCommission: number;
  commissionIcb: boolean;
  internalCommissionsComments: string;
  grossProfitMrcMultiplier: number;
  percentResidesFromCarrier: string;
  grossProfitMrc: number;
  grossProfit: number;
  grossProfitMrcNeedsToBeEdited: boolean;
  grossProfitNeedsToBeEdited: boolean;
  repGrossProfitNeedsToBeEdited: boolean;
  grossProfitMrcOverride: number;
  grossProfitOverride: number;
  repGrossProfitProduction: number;
  totalContractValue: number;
  upliftMrc: number;
  subaccountMrc: number;

  constructor() {
    super();
  }

  setCommissionableMrc(value: string) {
    this.commissionableMrc = getCurrency(value);
  }
  setCommissionableNrc(value: string) {
    this.commissionableNrc = getCurrency(value);
  }
  setCommissionableArc(value: string) {
    this.commissionableArc = getCurrency(value);
  }
  setSpiffAmount(value: string) {
    this.spiffAmount = getCurrency(value);
  }
  setExpectedCommission(value: string) {
    this.expectedCommission = getCurrency(value);
  }
  setGrossProfitMrc(value: string) {
    this.grossProfitMrc = getCurrency(value);
  }
  setGrossProfit(value: string) {
    this.grossProfit = getCurrency(value);
  }
  setGrossProfitMrcOverride(value: string) {
    this.grossProfitMrcOverride = getCurrency(value);
  }
  setGrossProfitOverride(value: string) {
    this.grossProfitOverride = getCurrency(value);
  }
  setRepGrossProfitProduction(value: string) {
    this.repGrossProfitProduction = getCurrency(value);
  }
  setTotalContractValue(value: string) {
    this.totalContractValue = getCurrency(value);
  }
  setUpliftMrc(value: string) {
    this.upliftMrc = getCurrency(value);
  }
  setSubaccountMrc(value: string) {
    this.subaccountMrc = getCurrency(value);
  }
  setSupAgentPercent(value: string) {
    this.subAgentPercent = getPercent(value);
  }
  setReferralPercent(value: string) {
    this.referralPercent = getPercent(value);
  }
  setEngineerResourceAllocation(value: string) {
    this.engineerResourceAllocation = getPercent(value);
  }
  setCommissionReductionPercent(value: string) {
    this.commissionReductionPercent = getPercent(value);
  }
  setAgentPercent(value: string) {
    this.agentPercent = getPercent(value);
  }
}
