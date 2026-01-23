import { AbstractBaseModel } from "./abstract-base-model";

export interface SubjectInterface extends AbstractBaseModel {
  username: string;
  displayName: string;
  emailAddress: string;
  lastLoginTime: Date;
}
