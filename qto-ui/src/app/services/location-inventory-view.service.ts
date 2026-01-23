import { Injectable } from '@angular/core';
import { AbstractModelService } from './abstract-model.service';
import { LocationView } from '../models/location-view.model';
import { LocationViewSearchCriteria } from '../models/location-view-search-criteria.model';
import { Observable } from 'rxjs';
import { LocationInventoryMeta } from '../features/locations-worklist/ngrx-inventory/location-inventory-worklist.reducer';

@Injectable({
  providedIn: 'root'
})
export class LocationInventoryViewService extends AbstractModelService<LocationView> {
  override path = '/locationInventoryViews';

  getLocationInventoryWorklistMeta(criteria: any): Observable<LocationInventoryMeta> {
    let params = this.getParams(criteria);
    return this.http.get<LocationInventoryMeta>(this.getUrl() + '/meta', { params: params });
  }

  getLinkLocations(criteria: any): Observable<any> {
    let params = this.getParams(criteria);
    return this.http.get<any>(this.getUrl() + '/link', { params: params });
  }

  getRelocateLocations(criteria: any): Observable<any> {    
    let params = this.getParams(criteria);
    return this.http.get<any>(this.getUrl() + '/relocate', { params: params });
  }

    // retrieves service types found in the location inventory
  getServiceTypes(): Observable<string[]> {
    return this.http.get<string[]>(this.getUrl() + '/serviceTypes');
  }
}
