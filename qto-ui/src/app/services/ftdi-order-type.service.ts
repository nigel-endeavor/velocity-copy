import { Injectable } from '@angular/core';
import { AbstractModelService } from './abstract-model.service';
import { FtdiOrderType } from '../models/ftdi-order-type.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class FtdiOrderTypeService extends AbstractModelService<FtdiOrderType> {
  override path = '/ftdiOrderTypes';

  findSort1(vendor: string): Observable<string[]> {
    const url = this.getUrl() + '/sort1?vendor=' + vendor;
    return this.http.get<string[]>(url);
  }

  findSort2(sort1: string): Observable<string[]> {
    const url = `${this.getUrl()}/sort2?sort1=${encodeURIComponent(sort1)}`;
    return this.http.get<string[]>(url);
  }

  findOrderType(sort1: string | null, sort2: string): Observable<FtdiOrderType[]> {
    const url = `${this.getUrl()}/orderType?sort1=${sort1 ? encodeURIComponent(sort1) : sort1}&sort2=${sort2}`;
    return this.http.get<FtdiOrderType[]>(url);
  }
}
