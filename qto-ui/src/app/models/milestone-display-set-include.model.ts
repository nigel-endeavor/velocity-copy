import { AbstractBaseModel } from "./abstract-base-model";
import { Milestone } from "./milestone.model";

export class MilestoneDisplaySetInclude extends AbstractBaseModel {
  milestone: Milestone;
  active: boolean;
  sequence: number;
  required: boolean;
  adjustable: boolean;
  workflowDriven: boolean;
  hasTime: boolean;
  disallowFuture: boolean;
  status: string;  
  description: string;
}