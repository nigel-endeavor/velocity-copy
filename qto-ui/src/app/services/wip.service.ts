import { Injectable } from '@angular/core';
import { AbstractModelService } from './abstract-model.service';
import {Observable} from "rxjs";
import {WipServiceView} from "../models/wip-service-view.model";
import {WipServiceJeopView} from "../models/wip-service-jeop-view.model";
import { DashboardSearchCriteria } from '../features/dashboards/models/dashboard-search-criteria.model';
import { getParamStringFromCriteria } from '../features/dashboards/utils/getParamStringFromCriteria';
import { WipLocationJeopView } from '../models/wip-location-jeop-view.model';

@Injectable({
  providedIn: 'root'
})
export class WipService extends AbstractModelService<WipServiceView> {
  override path = '/wipViews';

  getWipServices(criteria: DashboardSearchCriteria, allStatuses: boolean = false): Observable<WipServiceView[]> {
    let queryParams = getParamStringFromCriteria(criteria);
    let url = this.getUrl() + '/wipServices?' + queryParams + (queryParams ? '&' : '') + 'allStatuses=' + allStatuses;
    return this.http.get<WipServiceView[]>(url);
  }

  getWipServiceJeops(criteria: DashboardSearchCriteria): Observable<WipServiceJeopView[]> {
    let url = this.getUrl() + '/wipServiceJeops?' + getParamStringFromCriteria(criteria);
    return this.http.get<WipServiceJeopView[]>(url);
  }

  getWipLocationJeops(criteria: DashboardSearchCriteria): Observable<WipLocationJeopView[]> {
    let url = this.getUrl() + '/wipLocationJeops?' + getParamStringFromCriteria(criteria);
    return this.http.get<WipLocationJeopView[]>(url);
  }

  getMonthlySpend(criteria: DashboardSearchCriteria): Observable<WipServiceView[]> {
    let url = this.getUrl() + '/monthlySpend?' + getParamStringFromCriteria(criteria);
    return this.http.get<WipServiceView[]>(url);
  }

  getIncrementalNetworkSpend(criteria: DashboardSearchCriteria): Observable<WipServiceView[]> {
    let url = this.getUrl() + '/incrementalNetworkSpend?' + getParamStringFromCriteria(criteria);
    return this.http.get<WipServiceView[]>(url);
  }

  getUnbillableNetworkExpenseAccrual(criteria: DashboardSearchCriteria): Observable<WipServiceView[]> {
    let url = this.getUrl() + '/unbillableNetworkExpenseAccrual?' + getParamStringFromCriteria(criteria);
    return this.http.get<WipServiceView[]>(url);
  }
}
