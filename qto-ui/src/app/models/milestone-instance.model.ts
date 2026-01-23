import { AbstractBaseModel } from "./abstract-base-model";
import { Milestone } from "./milestone.model";

export class MilestoneInstance extends AbstractBaseModel {
  milestone: Milestone;
  milestoneDate: Date | null;
  count: number;
  param: string;
  historic: boolean;
  note: string;
  useExistingInventoryLocationAddress: boolean;
}

export class LocationMilestoneInstance extends MilestoneInstance {
  locationId: number;
}

export class ServiceMilestoneInstance extends MilestoneInstance {
  serviceId: number;
}
