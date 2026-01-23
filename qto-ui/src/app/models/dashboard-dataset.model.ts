import { AbstractBaseModel } from "./abstract-base-model";

export interface DashboardDataset extends AbstractBaseModel {
  labels: string[];
  data: number[];
}