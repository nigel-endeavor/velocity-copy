import { AbstractBaseModel } from "./abstract-base-model";

export class TemplateVariable extends AbstractBaseModel {
  label: string;
  path: string;
  type: string;
  templateType: string;
}