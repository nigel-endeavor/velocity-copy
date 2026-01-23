import { AbstractBaseModel } from "./abstract-base-model";
import { Requirement } from "./requirement.model";

export class RequirementTemplate extends AbstractBaseModel {
  companyId: number;
  name: string;
  global: boolean;
  default: boolean;
  ttuEquivalent: number;
  active: boolean;
  requirements: Requirement[];
}