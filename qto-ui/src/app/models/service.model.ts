import { getCurrency } from "../utilities";
import { AbstractBaseModel } from "./abstract-base-model";

export class Service extends AbstractBaseModel {
  locationId: number;
  orderId: number;
  clientServiceId: string;
  alternateId: string;
  quoteSolutionId: string;
  status: string;
  subStatus: string;
  subProductType: string;
  orderType: string;
  activationLink: string;
  activationPhone: string;
  followUpDate: Date;
  contractTerm: string;
  contractSignedDate: Date;
  circuitTermEndDate: number;
  poNumber: string;
  mrc: number;
  nrc: number;
  mrr: number;
  nrr: number;
  hasIcb: boolean;
  icb: number;
  progressPercentage: number;
  hasOsp: boolean;
  osp: number;
  provider: string;
  underlyingProvider: string;
  accountNumber: string;
  summaryBill: string;
  providerOrderNum: string;
  providerCircuitId: string;
  insideWiringRequired: string;
  dmarc: string;
  additionalIpBlockRequired: string;
  additionalIpBlock: string;
  wanIps: string;
  wanGateway: string;
  wanSubnet: string;
  lanBlock: string;
  lanIps: string;
  lanGateway: string;
  lanSubnet: string;
  dns1: string;
  dns2: string;
  ospConstIntervalEst: string;
  speed: string;
  downloadSpeed: string;
  uploadSpeed: string;
  mediaType: string;
  netStatus: string;
  locationHours: string;
  productInstallInterval: number;
  expediteOrder: boolean;
  trunkGroup: string;
  connectionHandoffType: string;
  tieDownInfo: string;
  type: string;
  typeId: number;
  buildingStatus: string;
  ipFormat: string;
  description: string;
  serviceBilledTo: string;
  currentInventory: boolean;
  billable: boolean;
  active: boolean;
  disconnectReason: string;
  subOrderType: string;
  earlyTerminationFee: number;
  costChangeReason: string;
  inventoryServiceId: number;
  macdCostChange: number;
  macdRevenueChange: number;
  parentMrc: number;
  parentMrr: number;
  clientServiceInfo: string;
  clientServiceType: string;
  accountPasscode: string;
  projectName: string;
  annualRecurringCost: number;
  jobNumber: string;
  recordSource: string;
  ignoreForRenewals: boolean;
  linked: boolean;
  bundled: boolean;
  linkedBundledParent: boolean;
  linkedBundledParentId: number;
  billCycle: number;
  tspCode: string;
  tspCodeExpirationDate: Date | null;
  billToLocation: boolean;
  managedService: boolean;
  productionImpacting: boolean;
  autoRenewal: boolean;
  coTerminus: boolean;
  renewalCancelNoticePeriod: string;
  contractInfo: string;
  submittedInAdvToProvider: boolean;
  parentTsd: string;
  submittedInAdvToTsd: boolean;
  cieTeamedDealInfo: string;
  commissionReductionPercent: string;
  opportunityNum: string;
  netProviderPoints: string;
  promotions: string;
  spiffDetails: string;
  commissionableMrc: number;
  commissionableNrc: number;
  commissionableArc: number;
  externalOrderReference: string;
  customerBillingInstructions: string
  fieldServicesProvider: string;
  address1: string;
  address2: string;
  city: string;
  state: string;
  postalCode: string;
  country: string;
  billingEmail: string;
  shippingAddress1: string;
  shippingAddress2: string;
  shippingCity: string;
  shippingState: string;
  shippingPostalCode: string;
  shippingCountry: string;
  shipTo: string;
  technicalNotes: string;
  numberOfEndpoints: number;
  numberOfUsers: number;
  microsoftLicensing: string;
  isTerminal: boolean;

  constructor() {
    super();
    this.mrc = 0;
    this.nrc = 0;
    this.mrr = 0;
    this.nrr = 0;
    this.icb = 0;
    this.osp = 0;
    this.annualRecurringCost = 0;
  }

  get displayText() {
    return `${this.type}${this.provider ? ': ' + this.provider : ''}`
  }

  get isMacd() {
    return this.orderType ? ['Move', 'Add', 'Change', 'Disconnect'].some(type => this.orderType.includes(type)) : false;
  }

  setDownloadSpeed(value: string) {
    this.downloadSpeed = value;
    this.speed = `${this.downloadSpeed ? this.downloadSpeed : ''} / ${this.uploadSpeed ? this.uploadSpeed : ''}`;
  }

  setUploadSpeed(value: string) {
    this.uploadSpeed = value;
    this.speed = `${this.downloadSpeed ? this.downloadSpeed : ''} / ${this.uploadSpeed ? this.uploadSpeed : ''}`;
  }

  setContractTerm(value: string) {
    this.contractTerm = value;
    if (this.contractTerm == 'MTM') {
      this.ignoreForRenewals = true;
    } else {
      this.ignoreForRenewals = false;
    }
  }

  setMrc(value: string) {
    this.mrc = getCurrency(value);
    this.macdCostChange = this.mrc - this.parentMrc;
  }
  setNrc(value: string) {
    this.nrc = getCurrency(value);
  }
  setMrr(value: string) {
    this.mrr = getCurrency(value);
    this.macdRevenueChange = this.mrr - this.parentMrr;
  }
  setNrr(value: string) {
    this.nrr = getCurrency(value);
  }
  setIcb(value: string) {
    this.icb = getCurrency(value);
  }
  setOsp(value: string) {
    this.osp = getCurrency(value);
  }
  setEarlyTerminationFee(value: string) {
    this.earlyTerminationFee = getCurrency(value);
  }
  setMacdCostChange(value: string) {
    this.macdCostChange = getCurrency(value);
  }
  setAnnualRecurringCost(value: string) {
    this.annualRecurringCost = getCurrency(value);
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
}
