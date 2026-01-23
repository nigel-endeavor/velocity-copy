import { Type } from "class-transformer";
import { AbstractBaseModel } from "./abstract-base-model";
import { Contact, ContactType } from "./contact.model";
import { CompanyType } from "./constants/company-type";
import { CompanyTask } from "./company-task.model";

export class Company extends AbstractBaseModel {
  name: string;
  type: CompanyType;
  active: boolean;
  duplicatedMasterCustomerDetails: boolean;
  uuid: string;
  clientId: string;
  @Type(() => Contact) billingContact: Contact | undefined;
  parentCompany: Company;
  accountNotes: string;
  accountManager: number | undefined;
  provisioner: number | undefined;
  i90ProjectManager: number | undefined;
  address1: string;
  address2: string;
  city: string;
  state: string;
  postalCode: string;
  country: string;
  taskGroupId: number | undefined;
  automateEmailAddresses: string;
  automatedEmailsEnabled: boolean;

  //accessor for billing contact
  get xbillingContact(): Contact {
    if (this.billingContact) {
      return this.billingContact;
    } else {
      let newContact = new Contact(this.id, ContactType.BILLING);
      this.billingContact = newContact;
      return newContact
    }
  }
}
