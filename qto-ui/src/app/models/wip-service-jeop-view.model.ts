import {WipServiceView} from "./wip-service-view.model";

export class WipServiceJeopView extends WipServiceView {
  jeopDescription: string;
  startDate: Date;
  endDate: Date;
  responsibility: string;
  assignedTo: string;
}
