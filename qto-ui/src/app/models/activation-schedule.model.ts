import { AbstractBaseModel } from "./abstract-base-model";

export class ActivationSchedule extends AbstractBaseModel {
  serviceId: number;
  vendor: string;
  requestedDate: Date | null;
  ftdiOrderTypeId: number | null;
  latestRequestedDate: Date | null;
  requestedDays: string;
  technicalNote: string;
  description: string;
  customFields: ActivationScheduleCustom[] = [];
  equipment: ActivationScheduleEquipment[] = [];
  dispatches: FtdiDispatch[] = [];
  applySameDayTurnUpSurcharge: boolean;
}

export class ActivationScheduleCustom extends AbstractBaseModel {
  activationScheduleId: number;
  fieldName: string;
  fieldValue: string;
}

export class ActivationScheduleEquipment extends AbstractBaseModel {
  activationScheduleId: number;
  equipmentType: string;
  quantity: number;
  itemNumber: string;
}

export class FtdiDispatch extends AbstractBaseModel {
  scheduleId: number;
  vendorDispatchId: string;
  orderType: string;
  status: string;
  errorString: string;
}
