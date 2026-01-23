import { Service } from "./service.model";
import { getCurrency } from "../utilities";

export class DiaService extends Service {
  providerActivationMethod: string;
  interfaceConnector: string;
  napNxx: string;
  lastMileProvider: string;
  newAccessCircuitId: string;
  burstableSpeed: string;
  burstableSpeedCost: number;
  routerSerialNumber: string;
  routerMacAddress: string;

  setBurstableSpeedCost(value: string) {
    if (value) {
      this.burstableSpeedCost = getCurrency(value);
    }
  }
}
