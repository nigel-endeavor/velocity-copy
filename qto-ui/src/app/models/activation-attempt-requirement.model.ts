import { AbstractBaseModel } from "./abstract-base-model";
import { Requirement } from "./requirement.model";

export class ActivationAttemptRequirement extends AbstractBaseModel {
  activationAttemptId: number;
  requirement: Requirement;
  complete: boolean;
  comment: string;
}