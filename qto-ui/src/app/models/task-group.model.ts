import { AbstractBaseModel } from "./abstract-base-model";
import { Task } from "./task.model";

export class TaskGroup extends AbstractBaseModel {
  companyId: number;
  name: string;
  default: boolean;
  active: boolean = true;
  tasks: Task[];
}