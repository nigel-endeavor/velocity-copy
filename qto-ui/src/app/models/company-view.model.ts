import { AbstractBaseModel } from "./abstract-base-model";

export class CompanyView extends AbstractBaseModel {
  name: string;
  type: string;
  active: boolean;
  uuid: string;
  clientId: string;
  legacyId: number;
  billingContactName: string;
  billingContactEmail: string;
  billingContactPhone: string;
  tenantName: string;
  inventoryLocationCount: number;
  inventoryMrc: number;
  inventoryMrr: number;
  inventoryNrr: number;
  parentCompanyId: number;
  taskGroupId: number;
  selected?: boolean;
  status: string;
  lastCompletedTask: string;
  nextTask: string;
  nextTaskAssignedTo: string;
  remainingTasks: number;
  progressPercentage: number;
}
