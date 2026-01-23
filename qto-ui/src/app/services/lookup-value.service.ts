import { Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';
import { LookupValueSearchCriteria } from '../models/lookup-value-search-criteria.model';
import { LookupValue } from '../models/lookup-value.model';
import { AbstractModelService } from './abstract-model.service';
import {CommonDropdownSearchCriteria} from "../interfaces/commonSearch.interface";
import {PaginatedResult} from "../models/paginated-result.model";

@Injectable({
  providedIn: 'root'
})
export class LookupValueService extends AbstractModelService<LookupValue> {
  override path = '/lookupValues';

  getValues(typeCode: string, companyId: number | null): Observable<string[]> {

    let criteria = new LookupValueSearchCriteria();
    criteria.typeCode = typeCode;
    if (companyId) {
      criteria.companyId = companyId;
    }
    criteria.limit = 10000;
    criteria.active = true;
    return this.search(criteria).pipe(
      map(lookupValues => {
        return lookupValues.collection.map(lookupValue => {
          return lookupValue.value;
        });
      })
    );
  }

  find(typeCode: string, companyId?: number, filters?: CommonDropdownSearchCriteria): Observable<LookupValue[]> {
    let criteria = new LookupValueSearchCriteria();
    criteria.typeCode = typeCode;
    if (companyId) {
      criteria.companyId = companyId;
    }
    criteria.limit = 10000;
    return this.search({
      ...criteria,
      ...filters
    }).pipe(
      map(res => {
        return res.collection;
      })
    );
  }

  findFull(typeCode: string, companyId?: number, filters?: CommonDropdownSearchCriteria): Observable<PaginatedResult<LookupValue>> {
    let criteria = new LookupValueSearchCriteria();
    criteria.typeCode = typeCode;
    if (companyId) {
      criteria.companyId = companyId;
    }
    criteria.limit = 10000;
    return this.search({
      ...criteria,
      ...filters
    }).pipe(
      map(res => {
        return res
      })
    );
  }
}
