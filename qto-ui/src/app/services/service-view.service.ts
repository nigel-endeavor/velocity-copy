import { Injectable } from '@angular/core';
import { AbstractModelService } from './abstract-model.service';
import { ServiceView } from '../models/service-view.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ServiceViewService extends AbstractModelService<ServiceView> {
  override path = '/serviceViews';

  // retrieves service types found in the worklist
  getServiceTypes(): Observable<string[]> {
    return this.http.get<string[]>(this.getUrl() + '/serviceTypes');
  }
}
