import { Injectable } from '@angular/core';
import { BaseSearchCriteria } from '../models/base-search-criteria.model';
import { AbstractFileAttachmentService } from './abstract-file-attachment.service';

@Injectable({
  providedIn: 'root'
})
export class LocationFileAttachmentService extends AbstractFileAttachmentService {
  override path = "/locationFileAttachments";
}  

export class LocationFileAttachmentSearchCriteria extends BaseSearchCriteria {
  locationId: number;
}