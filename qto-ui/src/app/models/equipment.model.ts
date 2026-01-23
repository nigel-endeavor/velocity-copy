import { AbstractBaseModel } from "./abstract-base-model";

export class Equipment extends AbstractBaseModel {
  [key: string]: any;
  equipmentType: string;
  equipmentSubtype: string;
  make: string;
  model: string;
  serialNumber: string;
  macAddress: string;
  ownership: string;
  shippingMethod: string;
  trackingInfo: string;
  shipDate: Date;
  deliveredDate: Date;
  description: string;
  networkIpRange: string;
  ipAddress: string;
  gateway: string;
  dns1: string;
  dns2: string;
  decommission: boolean;
  decommissionedDate: Date;
}
