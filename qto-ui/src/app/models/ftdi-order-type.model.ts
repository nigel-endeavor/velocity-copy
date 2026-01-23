import { AbstractBaseModel } from "./abstract-base-model";

export class FtdiOrderType extends AbstractBaseModel {
  vendorOrderTypeId: string;
  vendorName: string;
  category: string;
  orgId: string;
  sort1: string;
  sort2: string;
  orderType: string;
  lastUpdate: number;
  active: boolean;
  equipmentTypes: FtdiEquipmentType[];
  customFields: FtdiCustomField[];
}

export class FtdiEquipmentType extends AbstractBaseModel {
  equipmentType: string;
  partNumber: string;
  clientPartNumber: string;
  itemNumber: string;
  active: boolean;
}

export class FtdiCustomField extends AbstractBaseModel {
  fieldName: string;
  dataType: string;
  active: boolean;
  values: FtdiCustomFieldValue[];
}

export class FtdiCustomFieldValue extends AbstractBaseModel {
  customFieldId: number;
  fieldValue: string;
}