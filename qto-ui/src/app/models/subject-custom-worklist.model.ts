import { AbstractBaseModel } from "./abstract-base-model";

export class SubjectCustomWorklist extends AbstractBaseModel {
  authorId: number;
  authorName: string;
  lastViewedDate: Date;
  shared: boolean;
  worklistName: string;
  name: string;
  lastModifiedDate: Date;
  subjectId: number;
  favorite: boolean;
  content: string | null;


}
