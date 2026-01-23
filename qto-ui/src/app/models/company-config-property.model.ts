import { AbstractBaseModel } from "./abstract-base-model";

export class CompanyConfigProperty extends AbstractBaseModel {
    companyId: number;
    key: string;
    value: string;
    description: string;
    type: string;
    decryptedValue: string;
}
