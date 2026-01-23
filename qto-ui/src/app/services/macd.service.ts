import { EventEmitter, Injectable } from '@angular/core';
import { AbstractModelService } from './abstract-model.service';
import { Observable } from "rxjs";
import { ServiceView } from '../models/service-view.model';

@Injectable({
  providedIn: 'root'
})
export class MacdService extends AbstractModelService<ServiceView> {
  override path = '/services';
  private macd = '/macd';
  private multiMacd = '/multiMacd';

  public onMacd = new EventEmitter<ServiceView>();

  createMacd(payload: any): Observable<ServiceView> {
    return this.http.post<ServiceView>(`${this.getUrl()}${this.macd}`, payload);
  }

  submitMultiMacd(payload: any): Observable<ServiceView> {
    return this.http.post<any>(`${this.getUrl()}${this.multiMacd}`, payload);
  }

}
