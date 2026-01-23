import { AbstractBaseModel } from "./abstract-base-model";
import { Task } from "./task.model";

export class CompanyTask extends AbstractBaseModel {
  companyId: number;
  task: Task;
  value: string;
  completeDate: Date | null;
  comment: string | null;
  assignedTo: string | null;
  //client side property only
  editing: boolean = false;
}