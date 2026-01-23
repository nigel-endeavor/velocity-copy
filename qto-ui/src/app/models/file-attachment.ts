import { AbstractBaseModel } from "./abstract-base-model";

export class FileAttachment extends AbstractBaseModel {
  name: string;
  mimeType: string;
  size: number;
  uploadDate: Date;
  fileModifiedDate: Date;
  uploadedByUsername: string;
  description: string;
  content: FileAttachmentContent = new FileAttachmentContent();
  serviceDisplayText: string;

  expanded: boolean = false;
  dirty: boolean = false;
}

export class FileAttachmentContent extends AbstractBaseModel {
  data: File;
}

export class LocationFileAttachment extends FileAttachment {
  locationId: number;
}

export class ServiceFileAttachment extends FileAttachment {
  serviceId: number;
}

export class CompanyFileAttachment extends FileAttachment {
  companyId: number;
}

export enum FileAttachmentType {
  LOCATION = "Location",
  SERVICE = "Service",
  COMPANY = "Company"
}
