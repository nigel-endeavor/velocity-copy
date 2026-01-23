import { Injectable } from '@angular/core';
import { AbstractModelService } from './abstract-model.service';
import { ServiceView } from '../models/service-view.model';
import { Observable } from 'rxjs';
import { ServiceInventoryMeta } from '../features/service-worklist/ngrx-inventory/service-inventory-worklist.reducer';

@Injectable({
  providedIn: 'root'
})
export class ServiceInventoryViewService extends AbstractModelService<ServiceView> {
  override path = '/serviceInventoryViews';

  getServiceInventoryWorklistMeta(criteria: any): Observable<ServiceInventoryMeta> {
    let params = this.getParams(criteria);
    return this.http.get<ServiceInventoryMeta>(this.getUrl() + '/meta', { params: params });
  }

  // retrieves service types found in the service inventory
  getServiceTypes(): Observable<string[]> {
    return this.http.get<string[]>(this.getUrl() + '/serviceTypes');
  }
}
