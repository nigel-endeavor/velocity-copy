import { AbstractBaseModel } from "./abstract-base-model";

export class LookupValue extends AbstractBaseModel {
  display: string;
  value: string;
  active: boolean;
  sortSequence: number;
  parentId: number;
}
