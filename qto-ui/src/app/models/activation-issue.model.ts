import { AbstractBaseModel } from "./abstract-base-model";

export class ActivationIssue extends AbstractBaseModel {
  activationAttemptId: number;
  primaryRootCause: string;
  secondaryRootCause: string;
  tertiaryRootCause: string;
  rank: number;
  note: string;

  setPrimaryRootCause(value: string) {
    this.primaryRootCause = value;
    this.secondaryRootCause = '';
    this.tertiaryRootCause = ''
  }

  setSecondaryRootCause(value: string) {
    this.secondaryRootCause = value;
    this.tertiaryRootCause = '';
  }
}