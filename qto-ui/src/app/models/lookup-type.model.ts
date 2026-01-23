import { AbstractBaseModel } from "./abstract-base-model";
import { LookupValue } from "./lookup-value.model";

export class LookupType extends AbstractBaseModel {
  name: string;
  typeCode: string;
  active: boolean;
  modifiable: boolean;
  sortStrategy: number;
  category: string;
  parentLookupTypeId: number;
  values: LookupValue[] = [];

  get sortDisplay(): string {
    switch (this.sortStrategy) {
      case 1:
        return 'Sequence';
      case 2:
        return 'ID';
      default:
        return 'Alphabetical';
    }
  }
}