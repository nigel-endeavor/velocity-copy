import { Injectable } from '@angular/core';
import { AbstractModelService } from './abstract-model.service';
import { PaginatedResult } from '../models/paginated-result.model';
import { SurchargeType } from '../models/surcharge-type.model';

@Injectable({
  providedIn: 'root'
})
export class SurchargeTypeService extends AbstractModelService<SurchargeType> {
  override path = '/surchargeTypes';
  retrieveByCompanyId(companyId: number) {
    return this.http.get<PaginatedResult<SurchargeType>>(`${this.getUrl()}?limit=10000&companyId=${companyId}`);
  }
}
