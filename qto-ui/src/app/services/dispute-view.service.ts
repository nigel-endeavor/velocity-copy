import { Injectable } from '@angular/core';
import { AbstractModelService } from './abstract-model.service';
import { DisputeView } from '../models/dispute-view.model';
import { DisputeMeta } from '../features/disputes-worklist/ngrx/dispute-worklist.reducer';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DisputeViewService extends AbstractModelService<DisputeView> {
  override path = '/disputeViews';

  getDisputeWorklistMeta(criteria: any): Observable<DisputeMeta> {
    let params = this.getParams(criteria);
    return this.http.get<DisputeMeta>(this.getUrl() + '/meta', { params: params });
  }

    // retrieves service types found in the worklist
    getServiceTypes(): Observable<string[]> {
      return this.http.get<string[]>(this.getUrl() + '/serviceTypes');
    }  
} 
