import { Service } from "./service.model";

export class TelevisionService extends Service {
  plan: string;
  dvrIncluded: boolean;
  receiver: string;
  receiverMac: string;
  dvr: string;
  dvrMac: string;
}