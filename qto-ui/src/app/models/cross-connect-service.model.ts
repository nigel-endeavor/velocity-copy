import { Service } from "./service.model";

export class CrossConnectService extends Service {
  crossConnectId: string;
  crossConnectRoom: string;
  crossConnectRack: string;
  crossConnectPort: string;
  crossConnectType: string;
  crossConnectDataCenterName: string;
}