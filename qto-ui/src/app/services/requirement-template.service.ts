import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { RequirementTemplate } from '../models/requirement-template.model';
import { AbstractModelService } from './abstract-model.service';

@Injectable({
  providedIn: 'root'
})
export class RequirementTemplateService extends AbstractModelService<RequirementTemplate> {
  override path = '/requirementTemplates';

  getTemplates(companyId: number, showInactive: boolean | null): Observable<RequirementTemplate[]> {
    let url = this.getUrl() + '?companyId=' + companyId;
    if (showInactive) {
      url += '&showInactive=true';
    }
    return this.http.get<RequirementTemplate[]>(url);
  }
}
