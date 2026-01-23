import { AbstractBaseModel } from "./abstract-base-model";

export class Contact extends AbstractBaseModel {
  companyId: number;
  firstName: string | null;
  lastName: string | null;
  active: boolean;
  role: string;
  notes: string;
  type: ContactType;
  phone: string;
  email: string;
  lastUpdateBy: string;
  lastUpdateDate: Date;

  constructor(companyId: number | null, type: ContactType) {
    super();
    this.companyId = companyId || 0;
    this.type = type;
    this.active = true;
  }

  get name(): string {
    const first = this.firstName ? this.firstName : '';
    const last = this.lastName ? this.lastName : '';
    if (first && last) {
      return first + ' ' + last;
    }
    return first;
  }

  set name(name: string) {
    let index = name.indexOf(' ');
    if (index > -1) {
      this.firstName = name.slice(0, index);
      this.lastName = name.slice(index + 1);
    } else {
      this.firstName = name;
      this.lastName = null;
    }
  }
}

export enum ContactType {
  SALES = "SALES",
  TECH = "TECH",
  BILLING = "BILLING",
  LCON = "LCON",
  AUTH = "AUTHORIZATION"
}
