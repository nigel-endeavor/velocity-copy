import { AbstractBaseModel } from "./abstract-base-model";

export class CustomerImportError extends AbstractBaseModel {
  errorMessage: string;
    masterCustomerName: string;
    clientId: string;
    billingAddress1: string;
    billingAddress2: string;
    billingCity: string;
    billingState: string;
    billingPostalCode: string;
    billingCountry: string;
    billingContactName: string;
    billingContactEmail: string;
    billingContactPhone: string;
}
