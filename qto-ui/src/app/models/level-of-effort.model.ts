import { AbstractBaseModel } from "./abstract-base-model";

export class LevelOfEffort extends AbstractBaseModel {
  levelOfEffort: string;
  amount: number;
  startDate: Date;
  endDate: Date
}