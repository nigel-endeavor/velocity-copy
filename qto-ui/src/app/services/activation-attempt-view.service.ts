import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/internal/Observable';
import { ActivationAttemptView } from '../models/activation-attempt-view-model';
import { AbstractModelService } from './abstract-model.service';
import { getParamStringFromCriteria } from '../features/dashboards/utils/getParamStringFromCriteria';
import { DashboardSearchCriteria } from '../features/dashboards/models/dashboard-search-criteria.model';

@Injectable({
  providedIn: 'root'
})
export class ActivationAttemptViewService extends AbstractModelService<ActivationAttemptView> {
  override path = '/activationViews';

  getServiceIntervals(criteria: DashboardSearchCriteria, numOfMonths: number = 0): Observable<ActivationAttemptView[]> {
    let url = this.getUrl() + '/activationIntervals?' + getParamStringFromCriteria(criteria) + '&numOfMonths=' + numOfMonths;
    return this.http.get<ActivationAttemptView[]>(url);
  }
}
