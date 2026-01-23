import { Injectable } from '@angular/core';
import { AbstractModelService } from './abstract-model.service';
import { ServiceSurcharge } from '../models/service-surcharge-model';
import { ServiceSurchargeSearchCriteria } from '../models/service-surcharge-search-criteria.model';
import { PaginatedResult } from '../models/paginated-result.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ServiceSurchargeService extends AbstractModelService<ServiceSurcharge> {
  override path = '/serviceSurcharges';
  
  override search(criteria: ServiceSurchargeSearchCriteria) {
    // check if serviceId is null on criteria, if so, abort the call
    // TODO: replace with appropriate approach once we learn it
    if (!criteria.serviceId) {
      //return a blank return this.http.get<PaginatedResult<T>>(this.getUrl(), { params: this.getParams(criteria) });
      return new Observable<PaginatedResult<ServiceSurcharge>>();
      
    } else {
      return super.search(criteria);
    }
  }
}
