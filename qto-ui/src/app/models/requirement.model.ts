import { AbstractBaseModel } from "./abstract-base-model";
import { LookupValue } from "./lookup-value.model";

export class Requirement extends AbstractBaseModel {
  requirementTemplateId: number;
  lookupValue: LookupValue;
  sortOrder: number;
  required: boolean;
}