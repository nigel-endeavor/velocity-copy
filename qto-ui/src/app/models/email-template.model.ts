import { AbstractBaseModel } from "./abstract-base-model";

export class  EmailTemplate extends AbstractBaseModel {
  name: string;
  subject: string;
  body: string;
  templateType: string;
  lastUpdatedBy: string;
  lastUpdatedDate: Date;
  companyId: number;
}