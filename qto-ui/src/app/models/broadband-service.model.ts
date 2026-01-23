import { Service } from "./service.model";

export class BroadbandService extends Service {
  modemMake: string;
  macAddress: string;
  modemSerialNumber: string;
  customerPremEquipment: string;
  networkProtocol: string;
  pppoeUsername: string;
  pppoePassword: string;
}