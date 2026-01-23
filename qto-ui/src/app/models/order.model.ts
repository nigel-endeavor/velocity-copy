import { Type } from "class-transformer";
import { AbstractBaseModel } from "./abstract-base-model";
import { Company } from "./company.model";
import { Contact, ContactType } from "./contact.model";
import { Location } from "./location.model";
import { OrderContact } from "./order-contact";

export class Order extends AbstractBaseModel {
  @Type(() => Company) company: Company;
  clientOrderId: string;
  quoteId: number;
  provisioner: string;
  clientProjectManager: string;
  vertekProjectManager: string;
  activationEngineer: string;
  qaManager: string;
  readonly mrc: number;
  readonly nrc: number;
  readonly mrr: number;
  readonly nrr: number;
  readonly icb: number;
  readonly osp: number;
  readonly annualRecurringCost: number;
  status: string;
  @Type(() => Location) locations: Location[];
  @Type(() => OrderContact) contacts: OrderContact[];
  vertekClient: string;
  createdDate: Date;
  inventoryOrderId: number;

  constructor() {
    super();
    this.locations = [];
    this.contacts = [];
    this.mrc = 0;
    this.nrc = 0;
    this.mrr = 0;
    this.nrr = 0;
    this.icb = 0;
    this.osp = 0;
    this.annualRecurringCost = 0;
  }

  get locationCount(): number {
    return this.locations.filter(l => l.status != 'Location Cancelled').length;
  }

  get serviceCount(): number {
    let count = 0;
    this.locations.forEach((loc: Location) => {
      count += loc.services?.filter(s => s.status != 'Service Cancelled').length;
    });
    return count;
  }

  get serviceListString(): string {
    let services = '';
    this.locations.forEach((loc: Location) => {
      loc.services?.filter(s => s.status != 'Service Cancelled').forEach(s => {
        if (!services.includes(s.type)) {
          services += s.type + ', '
        }
      });
    });
    return services.slice(0, services.length - 2);
  }

  get salesContact(): Contact {
    return this.getContact(ContactType.SALES);
  }

  get techContact(): Contact {
    return this.getContact(ContactType.TECH);
  }

  get authContact(): Contact {
    return this.getContact(ContactType.AUTH);
  }

  private getContact(type: ContactType) {
    const contact = this.contacts.find(c => c.type === type);
    if (contact) {
      return contact;
    } else {
      let newContact = new OrderContact(this.company?.id, type, this.id);
      this.contacts.push(newContact);
      return newContact;
    }
  }
}
