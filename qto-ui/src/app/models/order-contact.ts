import { Contact, ContactType } from "./contact.model";

export class OrderContact extends Contact {
  orderId: number;

  constructor(companyId: number, type: ContactType, orderId: number) {
    super(companyId, type);
    this.orderId = orderId;
  }
}