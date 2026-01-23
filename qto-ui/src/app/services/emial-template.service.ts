import { Injectable } from '@angular/core';
import { AbstractModelService } from './abstract-model.service';
import { EmailTemplate } from '../models/email-template.model';
import { PaginatedResult } from '../models/paginated-result.model';

@Injectable({
  providedIn: 'root'
})
export class EmailTemplateService extends AbstractModelService<EmailTemplate> {
  override path = '/emailTemplates';
  retrieveByTypeAndEntityId(type: string, entityId: number) {
    return this.http.get<PaginatedResult<EmailTemplate>>(`${this.getUrl()}/${type}?entityId=${entityId}`);
  }
}
