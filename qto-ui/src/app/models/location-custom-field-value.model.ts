import { AbstractBaseModel } from "./abstract-base-model";

export class LocationCustomFieldValue extends AbstractBaseModel {
  locationId: number;
  customFieldId: number;
  value: string;
}