import { AbstractBaseModel } from "./abstract-base-model";
import { MilestoneDisplaySetInclude } from "./milestone-display-set-include.model";

export class MilestoneDisplaySet extends AbstractBaseModel {
  displayGroup: string;
  displaySetLabel: string;
  displaySetIncludes: MilestoneDisplaySetInclude[];
}
