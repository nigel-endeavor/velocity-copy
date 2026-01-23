import { Injectable } from '@angular/core';
import { AbstractModelService } from './abstract-model.service';
import {Observable} from "rxjs";
import {WipServiceView} from "../models/wip-service-view.model";
import {ProviderIntervalsView} from "../models/provider-intervals-view.model";
import { getParamStringFromCriteria } from '../features/dashboards/utils/getParamStringFromCriteria';
import { DashboardSearchCriteria } from '../features/dashboards/models/dashboard-search-criteria.model';

@Injectable({
  providedIn: 'root'
})
export class ProvidersService extends AbstractModelService<ProviderIntervalsView> {
  override path = '/providerViews';

  getProviderIntervals(criteria: DashboardSearchCriteria, intervalTypeCode: string, numOfMonths: number = 0): Observable<ProviderIntervalsView[]> {
    let url = this.getUrl() + '/providerIntervals?' + getParamStringFromCriteria(criteria)
      + '&numOfMonths=' + numOfMonths
      + '&intervalTypeCode=' + intervalTypeCode;
    return this.http.get<ProviderIntervalsView[]>(url);
  }

  getProviderReliance(criteria: DashboardSearchCriteria,  numOfMonths: number = 0): Observable<WipServiceView[]> {
    let url = this.getUrl() + '/providerReliance?' + getParamStringFromCriteria(criteria) + '&numOfMonths=' + numOfMonths;
    return this.http.get<WipServiceView[]>(url);
  }
}
