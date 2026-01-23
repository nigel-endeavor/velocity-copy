import { Injectable } from '@angular/core';
import { BaseSearchCriteria } from '../models/base-search-criteria.model';
import { AbstractFileAttachmentService } from './abstract-file-attachment.service';

@Injectable({
  providedIn: 'root'
})
export class ServiceFileAttachmentService extends AbstractFileAttachmentService {
  override path = '/serviceFileAttachments';
}

export class ServiceFileAttachmentSearchCriteria extends BaseSearchCriteria {
  serviceId: number;
}
