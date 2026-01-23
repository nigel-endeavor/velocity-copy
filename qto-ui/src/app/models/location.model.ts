import { Type } from "class-transformer";
import { AbstractBaseModel } from "./abstract-base-model";
import { Service } from "./service.model";
import { LocationContact } from "./location-contact.model";
import { ContactType } from "./contact.model";
import { addressToString } from "../utilities";

export class Location extends AbstractBaseModel {
  orderId: number;
  clientLocationId: string;
  clientLocationInfo: string;
  clientLocationType: string;
  quoteLocationId: string;
  name: string;
  status: string;
  buildingType: string;
  timezone: string;
  phoneNumber: string;
  readonly mrc: number;
  readonly nrc: number;
  readonly mrr: number;
  readonly nrr: number;
  readonly icb: number;
  readonly osp: number;
  readonly annualRecurringCost: number;
  readonly inventoryMrc: number;
  readonly inventoryNrc: number;
  readonly inventoryMrr: number;
  readonly inventoryNrr: number;
  readonly inventoryIcb: number;
  readonly inventoryOsp: number;
  readonly inventoryAnnualRecurringCost: number;
  readonly progressPercentage: number;
  readonly commissionableMrc: number;
  readonly commissionableNrc: number;
  readonly commissionableArc: number;
  readonly grossProfitMrcOverride: number;
  readonly grossProfitOverride: number;
  readonly repGrossProfitProduction: number;
  readonly totalContractValue: number;
  readonly upliftMrc: number;
  readonly subaccountMrc: number;
  @Type(() => Service) services: Service[];
  @Type(() => Service) inventoryServices: Service[];
  requirementTemplateId: number;
  @Type(() => LocationContact) lcon: LocationContact;
  levelOfEffort: string;
  active: boolean;
  parentLocationId: number;
  isDisconnect: boolean;
  recordSource: string;
  description: string;
  address1: string;
  address2: string;
  city: string;
  state: string;
  postalCode: string;
  country: string;
  currentInventory: boolean;
  inventoryLocationId: number;

  constructor() {
    super();
    this.mrc = 0;
    this.nrc = 0;
    this.mrr = 0;
    this.nrr = 0;
    this.icb = 0;
    this.osp = 0;
    this.annualRecurringCost = 0;
    if (!this.lcon) {
      this.lcon = new LocationContact(null, ContactType.LCON, this.id);
    }
  }

  get displayText() {
    if (!this.id) {
      return 'New Location';
    }
    let displayText = this.clientLocationId;
    if (this.address1) {
      displayText += '\n' + addressToString(this.address1, this.address2, this.city, this.state, this.postalCode, this.country);
    }
    return displayText;
  }

  //tracks whether or not this locations services are displayed
  //in the locations/services list on the orders page
  displayServices: boolean = false;

  get inventoryServiceListString(): string {
    let services = '';
    this.inventoryServices?.filter(s => s.status != 'Disconnect Complete').forEach(s => {
      if (!services.includes(s.type)) {
        services += s.type + ', '
      }
    });
    return services.slice(0, services.length - 2);
  }
}
