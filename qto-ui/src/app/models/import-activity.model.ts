import { AbstractBaseModel } from "./abstract-base-model";
import { FileAttachment } from "./file-attachment";
import { OrderCreateDto } from "./order-create-dto.model";

export class ImportActivity extends AbstractBaseModel {
  fileAttachment: FileAttachment;
  errorFileAttachment: FileAttachment;
  importStartDate: Date;
  importEndDate: Date;
  importStatus: string;
  uploadedByUsername: string;
  importType: string;
  numSuccessful: number;
  numFailed: number;
  numProcessed: number;
  dtoList: OrderCreateDto[];
}