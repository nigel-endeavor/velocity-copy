import { Injectable } from '@angular/core';
import { ActivationView, ActivationWorklistMeta } from '../models/activation-view.model';
import { AbstractModelService } from './abstract-model.service';
import { Observable } from 'rxjs';
import { ActivationViewSearchCriteria } from '../models/activation-view-search-criteria.model';

@Injectable({
  providedIn: 'root'
})
export class ActivationViewService extends AbstractModelService<ActivationView> {
  override path = '/activationViews';

  getActivationWorklistMeta(criteria: ActivationViewSearchCriteria): Observable<ActivationWorklistMeta> {
    let params = this.getParams(criteria);
    return this.http.get<ActivationWorklistMeta>(this.getUrl() + '/meta', { params: params });
  }
}
