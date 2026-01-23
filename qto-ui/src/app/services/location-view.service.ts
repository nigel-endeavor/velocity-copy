import { Injectable } from '@angular/core';
import { LocationView } from '../models/location-view.model';
import { AbstractModelService } from './abstract-model.service';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LocationViewService extends AbstractModelService<LocationView> {
  override path = '/locationViews';

  getRelocateLocations(criteria: any): Observable<any> {    
    let params = this.getParams(criteria);
    return this.http.get<any>(this.getUrl() + '/relocate', { params: params });
  }

    // retrieves service types found in the worklist
    getServiceTypes(): Observable<string[]> {
      return this.http.get<string[]>(this.getUrl() + '/serviceTypes');
    }

}
