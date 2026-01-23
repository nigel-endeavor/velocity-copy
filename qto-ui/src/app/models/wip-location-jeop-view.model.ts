import { WipJeopView } from "./wip-jeop-view.model";

export class WipLocationJeopView extends WipJeopView {
  locationId: number;
  orderId: number;
  companyName: string;
  provisionerId: number;
  vertekProjectManagerId: number;
  clientProjectManagerId: number;
  provisioner: string;
  vertekProjectManager: string;
  clientProjectManager: string;
  clientOrderId: string;
  clientLocationId: string;
  clientLocationType: string;
  locationName: string;
  address1: string;
  address2: string;
  city: string;
  stateProvince: string;
  clientServiceId: string;
  provider: string;
  locationStatus: string;
}
