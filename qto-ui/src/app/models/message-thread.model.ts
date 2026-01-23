import { AbstractBaseModel } from "./abstract-base-model";

export class MessageThread extends AbstractBaseModel {
  locationId: number;
  createdBy: number;
  createdDate: number;
  title: string;
  messages: Message[] = [];
  subjectIds: number[] = [];
  createdByDisplayName: string;
}

export class Message extends AbstractBaseModel {
  messageThreadId: number;
  createdBy: number;
  createdDate: Date;
  body: string;
  createdByDisplayName: string;
}