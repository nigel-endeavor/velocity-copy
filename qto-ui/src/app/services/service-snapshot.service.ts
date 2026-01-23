import { Injectable } from '@angular/core';
import { AbstractModelService } from './abstract-model.service';
import { DashboardDataset } from '../models/dashboard-dataset.model';
import { DashboardSearchCriteria } from '../features/dashboards/models/dashboard-search-criteria.model';
import { Observable } from 'rxjs';
import { getParamStringFromCriteria } from '../features/dashboards/utils/getParamStringFromCriteria';

@Injectable({
  providedIn: 'root'
})
export class ServiceSnapshotService extends AbstractModelService<DashboardDataset> {
  override path = '/serviceSnapshots';

  getInventoryValuation(criteria: DashboardSearchCriteria, numOfMonths: number = 6): Observable<DashboardDataset> {
    let url = this.getUrl() + '/inventoryValuation?' + getParamStringFromCriteria(criteria) + '&numOfMonths=' + numOfMonths;
    return this.http.get<DashboardDataset>(url);
  }

  getInventoryCounts(criteria: DashboardSearchCriteria, numOfMonths: number = 6): Observable<DashboardDataset> {
    let url = this.getUrl() + '/inventoryCounts?' + getParamStringFromCriteria(criteria) + '&numOfMonths=' + numOfMonths;
    return this.http.get<DashboardDataset>(url);
  }

  getNewInventory(criteria: DashboardSearchCriteria, numOfMonths: number = 6): Observable<DashboardDataset> {
    let url = this.getUrl() + '/newInventory?' + getParamStringFromCriteria(criteria) + '&numOfMonths=' + numOfMonths;
    return this.http.get<DashboardDataset>(url);
  }

  // retrieves service types found in the inventory
  getServiceTypes(): Observable<string[]> {
    return this.http.get<string[]>(this.getUrl() + '/serviceTypes');
  }

}
