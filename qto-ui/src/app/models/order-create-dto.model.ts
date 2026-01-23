import { getCurrency, getPercent } from "../utilities";
import { Address } from "./address.model";
import { Company } from "./company.model";
import { ServiceType } from "./constants/service-type";
import { Contact } from "./contact.model";
import { AbstractBaseModel } from "./abstract-base-model";

export class OrderCreateDtoWrapper {
  dtoList: OrderCreateDto[] = [];
}

export class OrderCreateDto {
  masterCustomer: Company | null;
  endCustomer: Company | null;
  provisioner: any;
  activationEngineer: any;
  clientProjectManager: string;
  vertekProjectManager: any;
  qaManager: any;
  holdProvisioning: boolean;
  jeopDescription: string;
  jeopResponsibility: string;
  clientOrderId: string;
  result: any;

  locations: OrderCreateLocation[] = [];

  salesContact: Contact | null;
  techContact: Contact | null;
  authContact: Contact | null;
  billingAddress: Address | null;
}

export class OrderCreateLocation {
  address: Address = new Address();
  clientOrderId: string;
  clientLocationId: string;
  lconName: string;
  lconPhone: string;
  lconEmail: string;
  locationInfo: string;
  locationType: string;
  levelOfEffort: string;
  recordSource: string;

  services: OrderCreateService[] = [];
}

export class OrderCreateService {
  uuid: string = Math.random().toString(36).substring(2, 15) + Math.random().toString(36).substring(2, 15);
  serviceType: ServiceType;
  clientServiceId: string;
  quoteId: string;
  projectName: string;
  provider: string;
  serviceBilledTo: string;
  subProductType: string;
  serviceInfo: string;
  clientServiceType: string;
  contractTerm: string;
  poNumber: string;
  mrc: number;
  nrc: number;
  annualNrc: number;
  customerRequestedInstallDate: Date | null;
  downloadSpeed: string;
  uploadSpeed: string;
  mediaType: string;
  zAddress: Address = new Address();
  description: string;
  recordSource: string;
  link: string;
  serviceId: number;
  linkedOrBundled: string = 'none';
  linkedBundledClientServiceId: string;
  autoRenewal: boolean;
  coTerminus: boolean;
  noticePeriodForRenewal: string;
  contractInfo: string;
  additionalIpBlock: string;
  dmarc: string;
  contractSignedDate: Date | null;
  fieldServicesProvider: string;
  commissionableMrc: number;
  commissionableNrc: number;
  commissionableArc: number;
  subAgent: string;
  subAgentPercent: number;
  submittedInAdvToProvider: boolean;
  parentTsd: string;
  submittedInAdvToTsd: boolean;
  referral: boolean;
  referralName: string;
  referralPercent: number;
  commissionPaymentType: string;
  expectedCommission: number;
  commissionReductionPercent: number;
  cieTeamedDealInfo: string;
  commissionIcb: boolean;
  internalCommissionsComments: string;
  opportunityNum: string;
  netProviderPoints: string;
  promotions: string;
  spiffAmount: number;
  engineerResource: boolean;
  engineerResourceAllocation: number;
  customFields: CustomFieldDto[] = [];
  mrr: number;
  nrr: number;
  managedService: boolean
  agent: string;
  agentRep: string;
  agentPercent: number;
  subAgentRep: string;

  setMrc(value: string) {
    this.mrc = getCurrency(value);
  }
  setNrc(value: string) {
    this.nrc = getCurrency(value);
  }
  setMrr(value: string) {
    this.mrr = getCurrency(value);
  }
  setNrr(value: string) {
    this.nrr = getCurrency(value);
  }
  setAnnualNrc(value: string) {
    this.annualNrc = getCurrency(value);
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
  setExpectedCommission(value: string) {
    this.expectedCommission = getCurrency(value);
  }
  setSpiffAmount(value: string) {
    this.spiffAmount = getCurrency(value);
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



export class CustomFieldDto {
    customFieldId: number;
    value: string;
}

