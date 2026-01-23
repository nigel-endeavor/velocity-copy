import { AbstractBaseModel } from "./abstract-base-model";
import { LookupValue } from "./lookup-value.model";

export class Task extends AbstractBaseModel {
  taskGroupId: number;
  lookupValue: LookupValue;
  value: string;
  sortOrder: number;
  required: boolean;
  // client side property only
  active: boolean;
}