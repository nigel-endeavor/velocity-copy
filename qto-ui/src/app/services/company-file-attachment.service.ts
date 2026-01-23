import { Injectable } from "@angular/core";
import { AbstractFileAttachmentService } from "./abstract-file-attachment.service";
import { BaseSearchCriteria } from "../models/base-search-criteria.model";

@Injectable({
  providedIn: 'root'
})
export class CompanyFileAttachmentService extends AbstractFileAttachmentService {
  override path = "/companyFileAttachments";
}

export class CompanyFileAttachmentSearchCriteria extends BaseSearchCriteria {
  companyId: number;
}
