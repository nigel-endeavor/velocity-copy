import { Injectable } from '@angular/core';
import { AbstractModelService } from './abstract-model.service';
import { TemplateVariable } from '../models/template-variable.model';
import { PaginatedResult } from '../models/paginated-result.model';

@Injectable({
  providedIn: 'root'
})
export class TemplateVariableService extends AbstractModelService<TemplateVariable> {
  override path = '/templateVariables';
  retrieveByType(type: string) {
    return this.http.get<PaginatedResult<TemplateVariable>>(`${this.getUrl()}/${type}`);
  }
}
