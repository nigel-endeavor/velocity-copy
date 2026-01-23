import { AbstractBaseModel } from "./abstract-base-model";

export class Address extends AbstractBaseModel {
  address1: string;
  address2: string;
  city: string;
  state: string;
  postalCode: string;
  country: string;
  latitude: number;
  longitude: number;
  geocodeStatus: string;
  accuracy: string;
  companyId: number;
  isValid: boolean;
  type: string;
  clientLocationId: string;

  override toString() {
    let str = '';
    if (this.address1) {
      str += this.address1 + ' ';
    }
    if (this.address2) {
      str += '\n' + this.address2  + ' ';
    }
    if (this.city) {
      str += '\n' + this.city;
    }
    if (this.city && this.state) {
      str += ', ';
    }
    if (this.state) {
      str += this.state;
    }
    if (this.postalCode) {
      str += ' ' + this.postalCode;
    }
    return str;
  }
}