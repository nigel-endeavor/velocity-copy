import {AbstractBaseModel} from "./abstract-base-model";

export class Jeop extends AbstractBaseModel {
  [key: string]: any;
  description: string;
  level: string;
  startDate: Date | null = null;
  endDate: Date | null = null;
  originator: string;
  responsibility: string;
  assignedTo: string;
  note: string;
  businessDaysOpen: number;
  viewVersion: number;

}
