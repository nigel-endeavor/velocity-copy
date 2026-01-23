import { Injectable } from '@angular/core';
import { AbstractModelService } from './abstract-model.service';
import { DisconnectView } from '../models/disconnect-view.model';
import { DisconnectMeta } from '../features/disconnect-worklist/ngrx/disconnect-worklist.reducer';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DisconnectViewService extends AbstractModelService<DisconnectView> {
  override path = '/disconnectViews';

  getDisconnectWorklistMeta(criteria: any): Observable<DisconnectMeta> {
    let params = this.getParams(criteria);
    return this.http.get<DisconnectMeta>(this.getUrl() + '/meta', { params: params });
  }

  // retrieves service types found in the service inventory
  getServiceTypes(): Observable<string[]> {
    return this.http.get<string[]>(this.getUrl() + '/serviceTypes');
  }
}
