import { Contact, ContactType } from "./contact.model";

export class LocationContact extends Contact {
    locationId: number;
    selected?: boolean;

    constructor(companyId: number | null, type: ContactType, locationId: number) {
      super(companyId, type);
      this.locationId = locationId;
    }
}
