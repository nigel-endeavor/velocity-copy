import { Service } from "./service.model";

export class GService extends Service {
  uid: string;
  iccid: string;
  imei: string;
  mdn: string;
  apn: string;
  ratePlan: string;
  rsrp: number;
  rsrq: number;
  sinr: number;
  rssi: number;
  replace4g5g: string;
  macAddress: string;
  serialNumber: string;
  circuitPriority: string;
}