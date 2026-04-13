import type { BaseModel } from './base.model';

export interface LookupValue extends BaseModel {
  display: string;
  value: string;
  active: boolean;
  sortSequence?: number;
  parentId?: number;
}

export interface LookupType extends BaseModel {
  name: string;
  typeCode: string;
  active: boolean;
  modifiable: boolean;
  sortStrategy?: number;
  category?: string;
  parentLookupTypeId?: number;
  values?: LookupValue[];
}

export function getLookupTypeSortLabel(sortStrategy?: number): string {
  switch (sortStrategy) {
    case 1:
      return 'Sequence';
    case 2:
      return 'ID';
    default:
      return 'Alphabetical';
  }
}
