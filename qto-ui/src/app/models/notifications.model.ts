import { AbstractBaseModel } from "./abstract-base-model";

export interface Notification extends AbstractBaseModel {
  subjectId: number;
  header: string;
  body: string;
  icon: string;
  createdDate: Date;
  dismissed: boolean;
  linkTo: string;
}