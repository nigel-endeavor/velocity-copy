import { Service } from "./service.model";

export class MplsService extends Service {
  lastMileProvider: string;
  accessCircuitId: string;
  portCircuitId: string;
  mplsType: string;
  portSpeed: string;
  interfaceConnector: string;
  providerActivationMethod: string;
  npaNxx: string;
  routingProtocol: string;
  cerIps: string;
  perIps: string;
  vlanTag1: string;
  vlanTag2: string;
  vlanTag3: string;
  vlanTag4: string;
  otherTechnicalNotes: string;
}