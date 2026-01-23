import {AbstractBaseModel} from "./abstract-base-model";

export class Note extends AbstractBaseModel {
  [key: string]: any;
  note: string;
  category: string;
  createdDate: Date;
  createdBy: string;
  editedBy: string;
  editedDate: Date;
  internalOnly: boolean;
  editable: boolean;
  editing: boolean = false;
  serviceId: number;
  locationId: number;
  disputeId: number;
}
