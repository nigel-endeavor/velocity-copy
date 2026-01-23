import { AbstractBaseModel } from "./abstract-base-model";

export class CustomField extends AbstractBaseModel {
  name: string;
  label: string;
  type: CustomFieldType;
  active: boolean;
  required: boolean;
}

export enum CustomFieldType {
  TEXT = 'TEXT',
  DROPDOWN = 'DROPDOWN',
  BOOLEAN = 'BOOLEAN',
  CURRENCY = 'CURRENCY',
}

export enum CustomFieldTabValue {
  SERVICE_BROKERAGE = 'SERVICE_BROKERAGE',
  NEW_ORDER_BROKERAGE = 'NEW_ORDER_BROKERAGE',
}
