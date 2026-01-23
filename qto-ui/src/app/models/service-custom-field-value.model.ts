import { AbstractBaseModel } from "./abstract-base-model";

export class ServiceCustomFieldValue extends AbstractBaseModel {
  serviceId: number;
  customFieldId: number;
  value: string;
}